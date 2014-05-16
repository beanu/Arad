package com.beanu.arad.support.loadmore;

public interface ILoadMoreAdapter {

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
}
