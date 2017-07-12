package com.beanu.arad.support.recyclerview.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.beanu.arad.support.listview.ILoadMoreListener;

/**
 * RecyvleView load more 监听
 * Created by beanu on 15/6/17.
 */
public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {

    public static String TAG = EndlessRecyclerOnScrollListener.class.getSimpleName();

    int lastVisiblePostion, totalItemCount;

    private LinearLayoutManager mLinearLayoutManager;
    private ILoadMoreListener listener;


    public EndlessRecyclerOnScrollListener(LinearLayoutManager linearLayoutManager, ILoadMoreListener listener) {
        this.mLinearLayoutManager = linearLayoutManager;
        this.listener = listener;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        totalItemCount = mLinearLayoutManager.getItemCount();
        lastVisiblePostion = mLinearLayoutManager.findLastVisibleItemPosition();
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if (newState == RecyclerView.SCROLL_STATE_IDLE && (lastVisiblePostion + 1) == totalItemCount) {
            if (listener.hasMoreResults() && !listener.isLoading())
                onLoadMore();
        }
    }

    //TODO 正在加载的过程中，如果再上拉，还是能够执行此方法
    public abstract void onLoadMore();
}
