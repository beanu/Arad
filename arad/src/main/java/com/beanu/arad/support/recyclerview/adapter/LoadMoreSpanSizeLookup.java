//package com.beanu.arad.support.recyclerview.adapter;
//
//import android.support.v7.widget.GridLayoutManager;
//
//import com.beanu.arad.support.listview.ILoadMoreListener;
//
///**
// * 适合带有loadmore的gridlayout
// * Created by yunhe on 2015-06-18.
// */
//public class LoadMoreSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {
//
//    private final GridLayoutManager layoutManager;
//    private ILoadMoreListener listener;
//
//    public LoadMoreSpanSizeLookup(GridLayoutManager layoutManager, ILoadMoreListener listener) {
//        this.layoutManager = layoutManager;
//        this.listener = listener;
//    }
//
//
//    //设置每个项目ITEM占用多少列
//    @Override
//    public int getSpanSize(int position) {
//        if (listener.hasMoreResults()) {
//            return position == (layoutManager.getItemCount() - 1) ? layoutManager.getSpanCount() : 1;
//        } else {
//            return 1;
//        }
//
//    }
//
//}
