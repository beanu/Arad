package com.beanu.arad.toolbar;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.beanu.arad.actionbar.R;
import com.beanu.arad.base.BaseListFragment;

/**
 * @author beanu
 */
public abstract class _ToolBarListFragment extends BaseListFragment {

    private TextView mTitle;
    private ImageView mLeftButton;
    private ImageView mRightButton;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FragmentActivity parent = getActivity();
        if (getParentFragment() == null && parent instanceof ToolBarActivity) {
            if (setupToolBarTitle() != null) {

                View view = parent.getWindow().getDecorView();

                mTitle = (TextView) view.findViewById(R.id.toolbar_title);
                mLeftButton = (ImageView) view.findViewById(R.id.toolbar_leftbtn);
                mRightButton = (ImageView) view.findViewById(R.id.toolbar_rightbtn);

                if (mTitle != null && setupToolBarTitle() != null)
                    mTitle.setText(setupToolBarTitle());

                if (mLeftButton != null) {
                    if (setupToolBarLeftButton(mLeftButton)) {
                        mLeftButton.setVisibility(View.VISIBLE);
                    } else {
                        mLeftButton.setVisibility(View.GONE);
                    }
                }

                if (mRightButton != null) {
                    if (setupToolBarRightButton(mRightButton)) {
                        mRightButton.setVisibility(View.VISIBLE);
                    } else {
                        mRightButton.setVisibility(View.GONE);
                    }
                }

            }

        }

    }

    protected abstract String setupToolBarTitle();

    protected abstract boolean setupToolBarLeftButton(ImageView leftButton);

    protected abstract boolean setupToolBarRightButton(ImageView rightButton);

    public ImageView getmRightButton() {
        return mRightButton;
    }

    public TextView getmTitle() {
        return mTitle;
    }

    public ImageView getmLeftButton() {
        return mLeftButton;
    }
}
