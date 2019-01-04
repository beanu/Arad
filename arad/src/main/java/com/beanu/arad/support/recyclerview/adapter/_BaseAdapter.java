package com.beanu.arad.support.recyclerview.adapter;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * 基础的RecyclerAdapter
 * Created by yunhe on 2015-06-17.
 */
public abstract class _BaseAdapter<E, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    protected List<E> list;

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public E getItem(int position) {
        return list == null ? null : list.get(position);
    }

    public void setList(List<E> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}
