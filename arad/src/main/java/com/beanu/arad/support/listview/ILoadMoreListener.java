package com.beanu.arad.support.listview;

public interface ILoadMoreListener {

    /**
     * loadMore操作的时候 是否还有更多的结果
     *
     * @return
     */
    public boolean hasMoreResults();

    /**
     * loadMore发生了错误
     *
     * @return
     */
    public boolean hasError();

    /**
     * 是否在加载中
     *
     * @return
     */
    public boolean isLoading();
}
