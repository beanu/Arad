package com.beanu.arad.actionbar;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.beanu.arad.base.BaseListFragment;

/**
 * 
 * @author beanu
 * 
 */
public abstract class _MyListFragment extends BaseListFragment {

	private TextView mTitle;
	private ImageView mLeftButton;
	private ImageView mRightButton;

	@SuppressLint("InlinedApi")
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		FragmentActivity parent = getActivity();

		if (parent instanceof ActionBarActivity) {
			if (((ActionBarActivity) parent).getActionBarTitle() == null) {
				((ActionBarActivity) parent).getSupportActionBar().setCustomView(R.layout.actionbar);
				((ActionBarActivity) parent).getSupportActionBar().setDisplayShowCustomEnabled(true);

				View homeIcon = parent
						.findViewById(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ? android.R.id.home
								: R.id.home);
				((View) homeIcon.getParent()).setVisibility(View.GONE);

				View view = ((android.support.v7.app.ActionBarActivity) parent).getSupportActionBar().getCustomView();
				mTitle = (TextView) view.findViewById(R.id.actionbar_title);
				mLeftButton = (ImageView) view.findViewById(R.id.actionbar_leftbtn);
				mRightButton = (ImageView) view.findViewById(R.id.actionbar_rightbtn);

				mTitle.setText(getActionBarTitle());
				if (!setActionBarLeftButton(mLeftButton)) {
					mLeftButton.setVisibility(View.GONE);
				}
				if (!setActionBarRightButton(mRightButton)) {
					mRightButton.setVisibility(View.GONE);
				}
			}
		}

	};

    // 动态改变
    public TextView getmTitle() {
        return mTitle;
    }

	protected abstract String getActionBarTitle();

	protected abstract boolean setActionBarLeftButton(ImageView leftButton);

	protected abstract boolean setActionBarRightButton(ImageView rightButton);

}
