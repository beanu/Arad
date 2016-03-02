package com.beanu.arad.support.recyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beanu.arad.R;
import com.beanu.arad.support.loadmore.ILoadMoreAdapter;

import java.util.List;

/**
 * 带有loadmore的 recycle adapter
 * Created by beanu on 15/6/17.
 */
public abstract class BaseLoadMoreAdapter<E, VH extends RecyclerView.ViewHolder> extends _BaseAdapter<E, RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_LOADING = 1;

    protected LayoutInflater inflater;
    protected Context context;

    private ILoadMoreAdapter listener;

    public static class LoadMoreViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public LoadMoreViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(android.R.id.text1);
        }
    }


    public BaseLoadMoreAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public BaseLoadMoreAdapter(Context context, List<E> list) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.list = list;
    }

    public BaseLoadMoreAdapter(Context context, List<E> list, ILoadMoreAdapter listener) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.listener = listener;
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {
        return (position >= list.size()) ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        if (list == null)
            return 0;
        if (listener == null)
            return list.size();
        else {
            if (list.size() == 0) {
                return 0;
            }

            return list.size() + (
                    // show the status list row if...
                    (listener.hasMoreResults() // ...or there's another
                            // page
                            || listener.hasError()) // ...or there's an error
                            ? 1
                            : 0);

        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return viewType == VIEW_TYPE_LOADING ? new LoadMoreViewHolder(inflater.inflate(R.layout.arad_list_item_stream_status, parent, false)) : onCreateItemViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_LOADING) {
            onBindHeaderViewHolder(holder, position);
        } else {
            onBindItemViewHolder((VH) holder, position);
        }
    }

    private void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (listener.hasError()) {
            ((LoadMoreViewHolder) holder).textView.setText("发生错误");
        } else {
            ((LoadMoreViewHolder) holder).textView.setText("正在加载更多.....");
        }
    }

    public abstract VH onCreateItemViewHolder(ViewGroup parent);

    public abstract void onBindItemViewHolder(VH holder, int position);

}
