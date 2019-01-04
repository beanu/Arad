package com.beanu.arad.support.listview;

import android.widget.AbsListView;
import android.widget.BaseAdapter;

import com.beanu.arad.base.ToolBarActivity;

/**
 * ListView 加载更多
 * Created by beanu on 13-11-29.
 */
public abstract class ABSLoadMoreActivity extends ToolBarActivity implements ILoadMoreListener, AbsListView.OnScrollListener {

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
        isScrolling = scrollState == AbsListView.OnScrollListener.SCROLL_STATE_FLING;
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
