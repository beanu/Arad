package com.arad.base;

import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.arad.R;

/**
 * @author beanu
 */
public class ToolBarFragment extends BaseFragment implements ISetupToolBar {

    private TextView mTitle;
    private View mLeftButton;
    private View mRightButton;

    private ActionBar mActionBar;
    private Toolbar mToolbar;

    private View arad_content;
    private ContentLoadingProgressBar arad_progress;

    @Override
    public void onResume() {
        super.onResume();
        FragmentActivity parent = getActivity();
        View view = parent.getWindow().getDecorView();

        if (getParentFragment() == null && parent instanceof AppCompatActivity) {
            mActionBar = initToolbar(parent);

            if (setupToolBarTitle() != null) {

                mTitle = (TextView) view.findViewById(R.id.toolbar_title);
                mLeftButton = view.findViewById(R.id.toolbar_leftbtn);
                mRightButton = view.findViewById(R.id.toolbar_rightbtn);

                if (mTitle != null && setupToolBarTitle() != null) {
                    mTitle.setText(setupToolBarTitle());
                    if (mActionBar != null) {
                        mActionBar.setDisplayShowTitleEnabled(false);
                    }
                }

                if (mLeftButton != null && setupToolBarLeftButton(mLeftButton)) {
                    mLeftButton.setVisibility(View.VISIBLE);
                    hideHomeAsUp();
                } else {
                    displayHomeAsUp();
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


        arad_content = view.findViewById(R.id.arad_content);
        arad_progress = (ContentLoadingProgressBar) view.findViewById(R.id.arad_progress);
    }

    private ActionBar initToolbar(FragmentActivity parent) {
        ActionBar actionBar = null;
        mToolbar = (Toolbar) parent.getWindow().getDecorView().findViewById(R.id.toolbar);
        if (mToolbar != null) {
            AppCompatActivity mActivity = (AppCompatActivity) parent;
            mActivity.setSupportActionBar(mToolbar);
            actionBar = mActivity.getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(false);
            }
        }

        return actionBar;
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

    /**
     * 加载内容
     */
    public void contentLoading() {
        if (arad_progress != null && arad_content != null) {
            arad_progress.show();
            arad_content.setVisibility(View.GONE);
        }
    }

    /**
     * 内容加载完成
     */
    public void contentLoadingComplete() {
        if (arad_progress != null && arad_content != null) {
            arad_progress.hide();
            arad_content.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 内容加载失败
     */
    public void contentLoadingError() {
        if (arad_progress != null && arad_content != null) {
            arad_progress.hide();
            arad_content.setVisibility(View.VISIBLE);
        }
    }

    private void displayHomeAsUp() {
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().onBackPressed();
                }
            });
        }
    }

    private void hideHomeAsUp() {
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(false);
        }
    }
}
