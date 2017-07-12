package com.beanu.arad.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.beanu.arad.R;


/**
 * 1.加入toolbar的操作
 * 2.视图不同状态的显示，加载中，加载失败，空数据，加载完成 四个状态。使用方式，用layout包含arad_loading.xml和自己的布局，自己的布局id定义为arad_content
 *
 * @author beanu
 */
public class ToolBarFragment<T extends BasePresenter, E extends BaseModel> extends BaseFragment<T, E> implements ISetupToolBar, BaseView {

    private TextView mTitle;
    private ImageView mLeftButton;
    private ImageView mRightButton;
    private TextView mRightText;

    private ActionBar mActionBar;
    private Toolbar mToolbar;

    private View arad_content;
    private View arad_loading;
    private View arad_loading_error;
    private View arad_loading_empty;
    private View.OnClickListener mOnRetryListener;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        arad_content = view.findViewById(R.id.arad_content);
        arad_loading = view.findViewById(R.id.arad_loading);
        arad_loading_empty = view.findViewById(R.id.arad_loading_empty);
        arad_loading_error = view.findViewById(R.id.arad_loading_error);

        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
    }

    @Override
    public void onResume() {
        super.onResume();
        FragmentActivity parent = getActivity();

        View view;
        if (mToolbar != null) {
            view = getView();
        } else {
            view = parent.getWindow().getDecorView();
            mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        }

        if (getParentFragment() == null && parent instanceof AppCompatActivity) {
            mActionBar = initToolbar(parent);

            if (view != null) {
                mTitle = (TextView) view.findViewById(R.id.toolbar_title);
                mLeftButton = (ImageView) view.findViewById(R.id.toolbar_leftbtn);
                mRightButton = (ImageView) view.findViewById(R.id.toolbar_rightbtn);
                mRightText = (TextView) view.findViewById(R.id.toolbar_txt_right_btn);

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

                if (mRightText != null) {
                    if (setupToolBarRightText(mRightText)) {
                        mRightText.setVisibility(View.VISIBLE);
                    } else {
                        mRightText.setVisibility(View.GONE);
                    }
                }

            }

        }
    }

    private ActionBar initToolbar(FragmentActivity parent) {
        ActionBar actionBar = null;
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
    public View getToolBarRightButton() {
        return mRightButton;
    }

    @Override
    public View getToolBarRightText() {
        return mRightText;
    }

    @Override
    public TextView getToolBarTitle() {
        return mTitle;
    }

    @Override
    public View getToolBarLeftButton() {
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

    @Override
    public boolean setupToolBarRightText(TextView rightText) {
        return false;
    }

    @Override
    public void contentLoading() {
        if (arad_loading != null) {
            arad_loading.setVisibility(View.VISIBLE);
        }
        if (arad_content != null) {
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
        if (arad_loading != null) {
            arad_loading.setVisibility(View.GONE);
        }
        if (arad_content != null) {
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
        if (arad_loading != null) {
            arad_loading.setVisibility(View.GONE);
        }
        if (arad_content != null) {
            arad_content.setVisibility(View.GONE);
        }
        if (arad_loading_empty != null) {
            arad_loading_empty.setVisibility(View.GONE);
        }
        if (arad_loading_error != null) {
            arad_loading_error.setVisibility(View.VISIBLE);
            arad_loading_error.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnRetryListener != null) {
                        mOnRetryListener.onClick(view);
                    }
                }
            });
        }

    }


    @Override
    public void contentLoadingEmpty() {
        if (arad_loading != null) {
            arad_loading.setVisibility(View.GONE);
        }
        if (arad_content != null) {
            arad_content.setVisibility(View.GONE);
        }
        if (arad_loading_empty != null) {
            arad_loading_empty.setVisibility(View.VISIBLE);
        }
        if (arad_loading_error != null) {
            arad_loading_error.setVisibility(View.GONE);
        }
    }

    @Override
    public void showProgress() {
        showProgress(true);
    }

    @Override
    public void hideProgress() {
        showProgress(false);
    }

    public void setOnRetryListener(View.OnClickListener onRetryListener) {
        mOnRetryListener = onRetryListener;
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
