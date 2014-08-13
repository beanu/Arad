package com.beanu.arad.actionbar;

import android.widget.ImageView;

/**
 * ListFragment 也实现了自定的ActionBar功能
 * 
 * @author beanu
 * 
 */
public class MyListFragment extends _MyListFragment {

	@Override
	protected String getActionBarTitle() {
		return null;
	}

	@Override
	protected boolean setActionBarLeftButton(ImageView leftButton) {

		return false;
	}

	@Override
	protected boolean setActionBarRightButton(ImageView rightButton) {

		return false;
	}

}
