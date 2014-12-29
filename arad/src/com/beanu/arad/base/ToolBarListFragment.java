package com.beanu.arad.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.beanu.arad.R;

/**
 * @author beanu
 */
public class ToolBarListFragment extends BaseListFragment implements ISetupToolBar {

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

    @Override
    public ImageView getmRightButton() {
        return mRightButton;
    }

    @Override
    public TextView getmTitle() {
        return mTitle;
    }

    @Override
    public ImageView getmLeftButton() {
        return mLeftButton;
    }

    @Override
    public String setupToolBarTitle() {
        return null;
    }

    @Override
    public boolean setupToolBarLeftButton(ImageView leftButton) {
        return false;
    }

    @Override
    public boolean setupToolBarRightButton(ImageView rightButton) {
        return false;
    }
}
