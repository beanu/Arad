package com.beanu.arad.support.recyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import java.util.List;

/**
 * 基础的recycle view adapter
 * Created by Beanu on 16/2/22.
 */
public abstract class BaseAdapter<E, VH extends RecyclerView.ViewHolder> extends _BaseAdapter<E, RecyclerView.ViewHolder> {

    protected final LayoutInflater inflater;

    public BaseAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
    }

    public BaseAdapter(Context context, List<E> list) {
        this.inflater = LayoutInflater.from(context);
        this.list = list;
    }


}
