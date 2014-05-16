package com.beanu.arad.support.loadmore;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.beanu.arad.R;


/**
 * LoadMore专用
 * 
 * @author beanu
 * 
 * @param <T>
 */
public abstract class LoadMoreBaseAdapter<T> extends BaseAdapter {

	private ILoadMoreAdapter listener;
	private List<T> list;
	protected LayoutInflater mlinflater;

	private static final int VIEW_TYPE_ACTIVITY = 0;
	private static final int VIEW_TYPE_LOADING = 1;

	public LoadMoreBaseAdapter(Context context, List<T> data, ILoadMoreAdapter listener) {
		this.mlinflater = LayoutInflater.from(context);
		this.list = data;
		this.listener = listener;
	}

	@Override
	public boolean areAllItemsEnabled() {
		return false;
	}

	@Override
	public boolean isEnabled(int position) {
		return getItemViewType(position) == VIEW_TYPE_ACTIVITY;
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public int getCount() {
		if (list == null)
			return 0;
		if (listener == null)
			return list.size();
		else {
			if (list.size() == 0) {
				return 0;
			}

			return list.size() + (
			// show the status list row if...
					(listener.hasMoreResults() // ...or there's another
												// page
					|| listener.hasError()) // ...or there's an error
					? 1
							: 0);

		}

	}

	@Override
	public int getItemViewType(int position) {
		return (position >= list.size()) ? VIEW_TYPE_LOADING : VIEW_TYPE_ACTIVITY;
	}

	@Override
	public Object getItem(int position) {
		return (getItemViewType(position) == VIEW_TYPE_ACTIVITY) ? list.get(position) : null;
	}

	@Override
	public long getItemId(int position) {
		return (getItemViewType(position) == VIEW_TYPE_ACTIVITY) ? list.get(position).hashCode() : -1;
	}

	@Override
	public View getView(final int position, View view, ViewGroup viewGroup) {
		if (getItemViewType(position) == VIEW_TYPE_LOADING) {

			if (view == null) {
				view = mlinflater.inflate(R.layout.list_item_stream_status, viewGroup, false);
			}

			if (listener.hasError()) {
				view.findViewById(android.R.id.progress).setVisibility(View.GONE);
				((TextView) view.findViewById(android.R.id.text1)).setText("发生错误了");
			} else {
				view.findViewById(android.R.id.progress).setVisibility(View.VISIBLE);
				((TextView) view.findViewById(android.R.id.text1)).setText("正在加载更多");
			}

			return view;

		} else {
			return getViewForLoadMore(position, view, viewGroup);
		}
	}

	public List<T> getListData() {
		return list;
	}

	public void setListData(List<T> list) {
		this.list = list;
	}

	public abstract View getViewForLoadMore(final int position, View view, ViewGroup viewGroup);

}
