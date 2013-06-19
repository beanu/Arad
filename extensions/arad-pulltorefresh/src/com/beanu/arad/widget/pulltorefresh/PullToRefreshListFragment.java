
/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.beanu.arad.widget.pulltorefresh;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.beanu.arad.base.BaseFragment;
import com.beanu.arad.pulltorefresh.R;

/**
 * A sample implementation of how to use {@link PullToRefreshListView} with
 * {@link ListFragment}. This implementation simply replaces the ListView that
 * {@code ListFragment} creates with a new PullToRefreshListView. This means
 * that ListFragment still works 100% (e.g. <code>setListShown(...)</code> ).
 * <p/>
 * The new PullToRefreshListView is created in the method
 * {@link #onCreatePullToRefreshListView(LayoutInflater, Bundle)}. If you wish
 * to customise the {@code PullToRefreshListView} then override this method and
 * return your customised instance.
 * 
 * @author Chris Banes
 * 
 */
public class PullToRefreshListFragment extends BaseFragment {
	protected View footerView;
	protected PullToRefreshListView pullToRefreshListView;
	protected TextView empty;
	protected ProgressBar progressBar;

	private volatile boolean enableRefreshTime = true;// TODO判断很短的时间内如果多次下来 不刷新

	public boolean isListViewFling() {
		return !enableRefreshTime;
	}

	protected boolean isFooterDisplay;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.pull_to_refresh_listview_layout, container, false);
		empty = (TextView) view.findViewById(R.id.empty);
		progressBar = (ProgressBar) view.findViewById(R.id.progressbar);
		pullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.listView);

		footerView = inflater.inflate(R.layout.pull_to_refresh_listview_footer_layout, null);
		getListView().addFooterView(footerView);
		getListView().setHeaderDividersEnabled(false);
		dismissFooterView();

		return view;
	}

	/**
	 * 返回listview 视图
	 */
	protected ListView getListView() {
		return pullToRefreshListView.getRefreshableView();
	}

	/**
	 * 决定是否显示listview视图
	 * 
	 * @param show
	 */
	protected void showListView(boolean show) {
		if (show) {
			progressBar.setVisibility(View.GONE);
			pullToRefreshListView.setVisibility(View.VISIBLE);
		} else {
			progressBar.setVisibility(View.VISIBLE);
			pullToRefreshListView.setVisibility(View.GONE);
		}

	}

	/**
	 * 显示列表的底部view，用来表示‘加载更多’
	 */
	protected void showFooterView() {
		TextView tv = ((TextView) footerView.findViewById(R.id.listview_footer));
		tv.setVisibility(View.VISIBLE);
		tv.setText(getString(R.string.loading));
		View view = footerView.findViewById(R.id.refresh);
		view.setVisibility(View.VISIBLE);
		view.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.refresh));
		isFooterDisplay = true;
	}

	/**
	 * 隐藏列表的底部view
	 */
	protected void dismissFooterView() {
		footerView.findViewById(R.id.refresh).setVisibility(View.GONE);
		footerView.findViewById(R.id.refresh).clearAnimation();
		footerView.findViewById(R.id.listview_footer).setVisibility(View.GONE);
		isFooterDisplay = false;
	}

}