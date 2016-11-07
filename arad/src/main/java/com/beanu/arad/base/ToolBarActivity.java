package com.beanu.arad.base;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.beanu.arad.R;
import com.beanu.arad.utils.AnimUtil;


/**
 * 加入toolbar的操作
 *
 * @author beanu
 */
public class ToolBarActivity extends BaseActivity implements ISetupToolBar, BaseView {

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
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mLeftButton = findViewById(R.id.toolbar_leftbtn);
        mRightButton = findViewById(R.id.toolbar_rightbtn);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            mActionBar = getSupportActionBar();
            hideHomeAsUp();
        }

        arad_content = findViewById(R.id.arad_content);
        arad_loading = findViewById(R.id.arad_loading);
        arad_loading_empty = findViewById(R.id.arad_loading_empty);
        arad_loading_error = findViewById(R.id.arad_loading_error);
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (mTitle != null && setupToolBarTitle() != null) {
            mTitle.setText(setupToolBarTitle());
            if (mActionBar != null) {
                mActionBar.setDisplayShowTitleEnabled(false);
            }
        }

        if (mLeftButton != null) {
            if (setupToolBarLeftButton(mLeftButton)) {
                mLeftButton.setVisibility(View.VISIBLE);
                hideHomeAsUp();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        AnimUtil.intentSlidOut(this);
    }

    protected void displayHomeAsUp() {
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }

    protected void hideHomeAsUp() {
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(false);
        }
    }

    // 动态改变
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
    public TextView getmTitle() {
        return mTitle;
    }

    @Override
    public View getmLeftButton() {
        return mLeftButton;
    }

    @Override
    public View getmRightButton() {
        return mRightButton;
    }

    public Toolbar getToolbar() {
        return mToolbar;
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
}
