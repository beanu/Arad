package com.arad.support.recyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

/**
 * 带有头部的recycler adapter
 *
 * @param <E>     item的实体类
 * @param <VH>    头部的viewholder
 * @param <VITEM> 每个项目的viewholder
 */
public abstract class BaseHeaderAdapter<E, VH extends RecyclerView.ViewHolder, VITEM extends RecyclerView.ViewHolder> extends _BaseAdapter<E, RecyclerView.ViewHolder> {
    public static final int VIEW_TYPE_HEADER = 0;
    public static final int VIEW_TYPE_ITEM = 1;

    protected final LayoutInflater inflater;

    public BaseHeaderAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
    }

    public BaseHeaderAdapter(Context context, List<E> list) {
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
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return viewType == VIEW_TYPE_HEADER ? onCreateHeaderViewHolder(parent) : onCreateItemViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_HEADER) {
            onBindHeaderViewHolder((VH) holder, position);
        } else {
            onBindItemViewHolder((VITEM) holder, position - 1);
        }
    }

    public abstract VITEM onCreateItemViewHolder(ViewGroup parent);

    public abstract VH onCreateHeaderViewHolder(ViewGroup parent);

    public abstract void onBindItemViewHolder(VITEM holder, int position);

    public abstract void onBindHeaderViewHolder(VH holder, int position);

}
