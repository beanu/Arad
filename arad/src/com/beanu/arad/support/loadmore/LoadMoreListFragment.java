package com.beanu.arad.support.loadmore;

import android.support.v4.app.ListFragment;
import android.widget.AbsListView;

/**
 * Created by beanu on 13-11-29.
 */
public abstract class LoadMoreListFragment extends ListFragment implements ILoadMoreAdapter, AbsListView.OnScrollListener {

    protected int lastItem;

    protected abstract void loadMore();

    protected abstract boolean hasMore();

    protected abstract boolean isError();

    @Override
    public boolean hasMoreResults() {
        return hasMore();
    }

    @Override
    public boolean hasError() {
        return isError();
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        lastItem = firstVisibleItem + visibleItemCount;
        if (lastItem == getListAdapter().getCount() && hasMoreResults() && visibleItemCount != 0
                && firstVisibleItem + visibleItemCount >= totalItemCount - 1) {
            loadMore();
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }
}