package com.beanu.arad.support.recyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

/**
 * 带有头部的recycler adapter
 * Created by yunhe on 2015-06-17.
 */
public abstract class BaseHeaderRecyclerAdapter<E, VH extends RecyclerView.ViewHolder> extends BaseRecyclerAdapter<E, VH> {
    public static final int VIEW_TYPE_HEADER = 0;
    public static final int VIEW_TYPE_ITEM = 1;

    private final LayoutInflater inflater;

    public BaseHeaderRecyclerAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
    }

    public BaseHeaderRecyclerAdapter(Context context, List<E> list) {
        this.inflater = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? VIEW_TYPE_HEADER : VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + 1;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return viewType == VIEW_TYPE_HEADER ? onCreateHeaderViewHolder(parent) : onCreateItemViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_HEADER) {
            onBindHeaderViewHolder((VH) holder, position);
        } else {
            onBindItemViewHolder((VH) holder, position - 1);
        }
    }

    public abstract VH onCreateItemViewHolder(ViewGroup parent);

    public abstract VH onCreateHeaderViewHolder(ViewGroup parent);

    public abstract void onBindItemViewHolder(VH holder, int position);

    public abstract void onBindHeaderViewHolder(VH holder, int position);

}
