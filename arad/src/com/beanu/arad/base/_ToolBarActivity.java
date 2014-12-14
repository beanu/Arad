package com.beanu.arad.base;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.beanu.arad.R;


/**
 * @author beanu
 */
public abstract class _ToolBarActivity extends BaseActivity {

    private TextView mTitle;
    private ImageView mLeftButton;
    private ImageView mRightButton;
    private Toolbar mActionBarToolbar;


    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        getActionBarToolbar();
    }

    protected Toolbar getActionBarToolbar() {
        if (mActionBarToolbar == null) {
            mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);

            mTitle = (TextView) findViewById(R.id.toolbar_title);
            mLeftButton = (ImageView) findViewById(R.id.toolbar_leftbtn);
            mRightButton = (ImageView) findViewById(R.id.toolbar_rightbtn);
            if (mActionBarToolbar != null) {
                setSupportActionBar(mActionBarToolbar);
            }
        }
        return mActionBarToolbar;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mTitle != null && setupToolBarTitle() != null)
            mTitle.setText(setupToolBarTitle());

        if (mLeftButton != null && setupToolBarLeftButton(mLeftButton)) {
            mLeftButton.setVisibility(View.VISIBLE);
        }
        if (mRightButton != null && setupToolBarRightButton(mRightButton)) {
            mRightButton.setVisibility(View.VISIBLE);
        }
    }

    protected abstract String setupToolBarTitle();

    protected abstract boolean setupToolBarLeftButton(ImageView leftButton);

    protected abstract boolean setupToolBarRightButton(ImageView rightButton);

    // 动态改变
    public TextView getmTitle() {
        return mTitle;
    }

    public ImageView getmLeftButton() {
        return mLeftButton;
    }

    public ImageView getmRightButton() {
        return mRightButton;
    }

}
