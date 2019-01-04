package com.beanu.arad.support.recyclerview.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.beanu.arad.support.listview.ILoadMoreListener;

/**
 * RecyvleView load more 监听
 * Created by beanu on 15/6/17.
 */
public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {

    public static String TAG = EndlessRecyclerOnScrollListener.class.getSimpleName();

    int lastVisiblePostion, totalItemCount;

    private RecyclerView.LayoutManager mLayoutManager;
    private ILoadMoreListener listener;

    public EndlessRecyclerOnScrollListener(@NonNull RecyclerView.LayoutManager layoutManager, @NonNull ILoadMoreListener listener) {
        this.mLayoutManager = layoutManager;
        this.listener = listener;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        totalItemCount = mLayoutManager.getItemCount();

        if (mLayoutManager instanceof LinearLayoutManager) {
            lastVisiblePostion = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();

        } else if (mLayoutManager instanceof StaggeredGridLayoutManager) {
            int positions[] = ((StaggeredGridLayoutManager) mLayoutManager).findLastVisibleItemPositions(null);
            int max = 0;
            for (int pos : positions) {
                if (pos > max) {
                    max = pos;
                }
            }
            lastVisiblePostion = max;
        }
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if (newState == RecyclerView.SCROLL_STATE_IDLE && (lastVisiblePostion + 1) == totalItemCount) {
            if (listener.hasMoreResults() && !listener.isLoading())
                onLoadMore();
        }
    }

    public abstract void onLoadMore();
}
