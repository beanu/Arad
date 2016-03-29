package com.beanu.arad.base;

import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.beanu.arad.R;
import com.beanu.arad.utils.AnimUtil;


/**
 * @author beanu
 */
public class ToolBarActivity extends BaseActivity implements ISetupToolBar {

    private TextView mTitle;
    private View mLeftButton;
    private View mRightButton;

    private ActionBar mActionBar;
    private Toolbar mToolbar;

    private View arad_content;
    private ContentLoadingProgressBar arad_progress;

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
            displayHomeAsUp();
        }

        arad_content = findViewById(R.id.arad_content);
        arad_progress = (ContentLoadingProgressBar) findViewById(R.id.arad_progress);
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
                displayHomeAsUp();
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
                    onBackPressed();
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
