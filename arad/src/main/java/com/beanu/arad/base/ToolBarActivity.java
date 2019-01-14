package com.beanu.arad.base;

import android.view.View;
import android.widget.TextView;

import com.beanu.arad.R;
import com.beanu.arad.utils.ToastUtils;
import com.beanu.arad.utils.statusbar.ImmersionBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;


/**
 * 1.加入toolbar的操作
 * 2. 加入了全局的loading
 *
 * @author beanu
 */
public class ToolBarActivity<T extends BasePresenter, E extends BaseModel> extends BaseActivity<T, E> implements ISetupToolBar, BaseView {

    private TextView mTitle;
    private View mLeftButton;
    private View mRightButton1;
    private View mRightButton2;

    private ActionBar mActionBar;
    private Toolbar mToolbar;//标题栏
    private View mStatusBar;//状态栏

    private View arad_content;
    private View arad_loading;
    private View arad_loading_error;
    private View arad_loading_empty;
    private View.OnClickListener mOnRetryListener;

    protected ImmersionBar mImmersionBar;

    @Override
    protected void onStart() {
        super.onStart();

        if (mActionBar != null) {
            mActionBar.setDisplayShowTitleEnabled(false);
        }

        if (mTitle != null && setupToolBarTitle() != null) {
            mTitle.setText(setupToolBarTitle());
        }

        if (mLeftButton != null) {
            if (setupToolBarLeftButton(mLeftButton)) {
                mLeftButton.setVisibility(View.VISIBLE);
                hideHomeAsUp();
            } else {
                mLeftButton.setVisibility(View.GONE);
            }
        }

        if (mRightButton2 != null) {
            if (setupToolBarRightButton2(mRightButton2)) {
                mRightButton2.setVisibility(View.VISIBLE);
            } else {
                mRightButton2.setVisibility(View.GONE);
            }
        }

        if (mRightButton1 != null) {
            if (setupToolBarRightButton1(mRightButton1)) {
                mRightButton1.setVisibility(View.VISIBLE);
            } else {
                mRightButton1.setVisibility(View.GONE);
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null) {
            mImmersionBar.destroy();
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        mToolbar = findViewById(R.id.arad_toolbar);
        mTitle = findViewById(R.id.arad_toolbar_title);
        mLeftButton = findViewById(R.id.arad_toolbar_left_button);
        mRightButton2 = findViewById(R.id.arad_toolbar_right_view2);
        mRightButton1 = findViewById(R.id.arad_toolbar_right_view1);
        mStatusBar = findViewById(R.id.arad_status_bar);


        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            mActionBar = getSupportActionBar();
            hideHomeAsUp();
        }

        arad_content = findViewById(R.id.arad_content);
        arad_loading = findViewById(R.id.arad_loading);
        arad_loading_empty = findViewById(R.id.arad_loading_empty);
        arad_loading_error = findViewById(R.id.arad_loading_error);

        setStatusBar();
    }

    protected void setStatusBar() {
        mImmersionBar = ImmersionBar.with(this);
        //在沉浸式状态栏的时候，保证内容视图与状态栏不重叠。两种方案。
        if (mStatusBar != null) {
            mImmersionBar.statusBarView(mStatusBar).init();
        } else {
            mImmersionBar.statusBarColor(R.color.colorPrimary).fitsSystemWindows(true).init();
        }
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
    public boolean setupToolBarRightButton2(View rightButton2) {
        return false;
    }

    @Override
    public boolean setupToolBarRightButton1(View rightButton1) {
        return false;
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
    public View getToolBarRightButton2() {
        return mRightButton2;
    }

    @Override
    public View getToolBarRightButton1() {
        return mRightButton1;
    }

    public Toolbar getToolbar() {
        return mToolbar;
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

    @Override
    public void showMessage(@NonNull String message) {
        ToastUtils.showShort(message);
    }

    public void setOnRetryListener(View.OnClickListener onRetryListener) {
        mOnRetryListener = onRetryListener;
    }
}
