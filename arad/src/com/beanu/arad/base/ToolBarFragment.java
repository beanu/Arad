package com.beanu.arad.base;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.beanu.arad.R;

/**
 * @author beanu
 */
public class ToolBarFragment extends BaseFragment implements ISetupToolBar {

    private TextView mTitle;
    private View mLeftButton;
    private View mRightButton;

    @Override
    public void onResume() {
        super.onResume();
        FragmentActivity parent = getActivity();
        if (getParentFragment() == null && parent instanceof ToolBarActivity) {
            initToolbar(parent);

            if (setupToolBarTitle() != null) {

                View view = parent.getWindow().getDecorView();

                mTitle = (TextView) view.findViewById(R.id.toolbar_title);
                mLeftButton = view.findViewById(R.id.toolbar_leftbtn);
                mRightButton = view.findViewById(R.id.toolbar_rightbtn);

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

    private Toolbar initToolbar(FragmentActivity parent) {
        Toolbar toolbar = (Toolbar) getView().findViewById(R.id.toolbar);
        if (toolbar != null) {
            AppCompatActivity mActivity = (AppCompatActivity) parent;
            mActivity.setSupportActionBar(toolbar);
            ActionBar actionBar = mActivity.getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(false);
            }
        }
        return toolbar;
    }


    @Override
    public View getmRightButton() {
        return mRightButton;
    }

    @Override
    public TextView getmTitle() {
        return mTitle;
    }

    @Override
    public View getmLeftButton() {
        return mLeftButton;
    }

    @Override
    public String setupToolBarTitle() {
        return null;
    }

    @Override
    public boolean setupToolBarLeftButton(View leftButton) {
        return false;
    }

    @Override
    public boolean setupToolBarRightButton(View rightButton) {
        return false;
    }
}
