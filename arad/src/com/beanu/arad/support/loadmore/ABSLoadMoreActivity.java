package com.beanu.arad.support.loadmore;

import android.widget.AbsListView;
import android.widget.BaseAdapter;

import com.beanu.arad.base.ToolBarActivity;

/**
 * 加载更多
 * Created by beanu on 13-11-29.
 */
public abstract class ABSLoadMoreActivity extends ToolBarActivity implements ILoadMoreAdapter, AbsListView.OnScrollListener {

    protected int lastItem;

    protected abstract void loadMore();

    protected abstract BaseAdapter getAdapter();

    protected abstract boolean hasMore();

    protected abstract boolean isError();

    private boolean isScrolling = false;

    @Override
    public boolean hasMoreResults() {
        return hasMore();
    }

    @Override
    public boolean hasError() {
        return isError();
    }

    public boolean isScrolling() {
        return isScrolling;
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int scrollState) {
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
            isScrolling = true;
        } else {
            isScrolling = false;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        lastItem = firstVisibleItem + visibleItemCount;
        if (lastItem == getAdapter().getCount() && hasMoreResults() && visibleItemCount != 0
                && firstVisibleItem + visibleItemCount >= totalItemCount - 1) {
            loadMore();
        }
    }
}
