package com.beanu.arad.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.beanu.arad.R;


/**
 * 加入toolbar的操作
 *
 * @author beanu
 */
public class ToolBarFragment extends BaseFragment implements ISetupToolBar, BaseView {

    private TextView mTitle;
    private View mLeftButton;
    private View mRightButton;

    private ActionBar mActionBar;
    private Toolbar mToolbar;

    private View arad_content;
    private View arad_loading;
    private View arad_loading_error;
    private View arad_loading_empty;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        arad_content = view.findViewById(R.id.arad_content);
        arad_loading = view.findViewById(R.id.arad_loading);
        arad_loading_empty = view.findViewById(R.id.arad_loading_empty);
        arad_loading_error = view.findViewById(R.id.arad_loading_error);
    }

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

    private ActionBar initToolbar(FragmentActivity parent) {
        ActionBar actionBar = null;
        mToolbar = (Toolbar) parent.getWindow().getDecorView().findViewById(R.id.toolbar);
        if (mToolbar != null) {
            AppCompatActivity mActivity = (AppCompatActivity) parent;
            mActivity.setSupportActionBar(mToolbar);
            actionBar = mActivity.getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(false);
                actionBar.setDisplayShowTitleEnabled(false);
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

    @Override
    public void contentLoading() {
        if (arad_loading != null && arad_content != null) {
            arad_loading.setVisibility(View.VISIBLE);
            arad_content.setVisibility(View.GONE);
        }
        if (arad_loading_empty != null) {
            arad_loading_empty.setVisibility(View.GONE);
        }
        if (arad_loading_error != null) {
            arad_loading_error.setVisibility(View.GONE);
        }
    }


    @Override
    public void contentLoadingComplete() {
        if (arad_loading != null && arad_content != null) {
            arad_loading.setVisibility(View.GONE);
            arad_content.setVisibility(View.VISIBLE);
        }

        if (arad_loading_empty != null) {
            arad_loading_empty.setVisibility(View.GONE);
        }
        if (arad_loading_error != null) {
            arad_loading_error.setVisibility(View.GONE);
        }
    }


    @Override
    public void contentLoadingError() {
        if (arad_loading != null && arad_content != null) {
            arad_loading.setVisibility(View.GONE);
            arad_content.setVisibility(View.GONE);
        }
        if (arad_loading_empty != null) {
            arad_loading_empty.setVisibility(View.GONE);
        }
        if (arad_loading_error != null) {
            arad_loading_error.setVisibility(View.VISIBLE);
        }

    }


    @Override
    public void contentLoadingEmpty() {
        if (arad_loading != null && arad_content != null) {
            arad_loading.setVisibility(View.GONE);
            arad_content.setVisibility(View.GONE);
        }
        if (arad_loading_empty != null) {
            arad_loading_empty.setVisibility(View.VISIBLE);
        }
        if (arad_loading_error != null) {
            arad_loading_error.setVisibility(View.GONE);
        }
    }


    protected void displayHomeAsUp() {
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

    protected void hideHomeAsUp() {
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(false);
        }
    }
}
