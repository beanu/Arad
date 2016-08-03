package com.arad.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.arad.R;
import com.arad.support.loading.LoadingPager;
import com.arad.utils.AnimUtil;
import com.arad.utils.UIUtils;


/**
 * @author beanu
 */
public abstract class ToolBarActivity extends BaseActivity implements ISetupToolBar {

    private TextView mTitle;
    private View mLeftButton;
    private View mRightButton;

    private ActionBar mActionBar;
    private Toolbar mToolbar;

    private LoadingPager loadingPage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadingPage = new LoadingPager(UIUtils.getContext(), R.layout.arad_loading, R.layout.arad_load_error, R.layout.arad_load_empty) {
            @Override
            protected View createSuccessView() {
                return onCreateView();//传递给子类
            }
        };

        //可以点击
        loadingPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingPage.setEnabled(false);
                onLoadingPageClick();
            }
        });
//      显示 loading的页面
        loadingPage.state_loading();
        setContentView(loadingPage);
    }

    protected abstract View onCreateView();

    protected abstract void onLoadingPageClick();

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

    public LoadingPager getLoadingPage() {
        return loadingPage;
    }


}
