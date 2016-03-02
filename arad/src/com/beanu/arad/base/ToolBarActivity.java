package com.beanu.arad.base;

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
            mLeftButton = findViewById(R.id.toolbar_leftbtn);
            mRightButton = findViewById(R.id.toolbar_rightbtn);
            if (mActionBarToolbar != null) {
                setSupportActionBar(mActionBarToolbar);
                ActionBar actionBar = getSupportActionBar();
                if (actionBar != null) {
                    actionBar.setDisplayHomeAsUpEnabled(true);
                }
            }


        }
        return mActionBarToolbar;
    }

    @Override
    protected void onStart() {
        super.onStart();
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

    @Override
    protected void onResume() {
        super.onResume();


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

//  //TODO
//    http://angeldevil.me/2014/12/24/toolbar-as-actionbar-and-centered-title/
//    @Override
//    protected void onTitleChanged(CharSequence title, int color) {
//        super.onTitleChanged(title, color);
//        if (toolbarTitle != null) {
//            toolbarTitle.setText(title);
//        }
//    }
}
