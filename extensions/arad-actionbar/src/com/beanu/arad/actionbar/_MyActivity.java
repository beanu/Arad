package com.beanu.arad.actionbar;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * @author beanu
 */
public abstract class _MyActivity extends ActionBarActivity {

    private TextView mTitle;
    private ImageView mLeftButton;
    private ImageView mRightButton;

    @SuppressLint("InlinedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setCustomView(R.layout.actionbar);
        getSupportActionBar().setDisplayShowCustomEnabled(true);

        View homeIcon = findViewById(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ? android.R.id.home
                : R.id.home);
        ((View) homeIcon.getParent()).setVisibility(View.GONE);

        View view = getSupportActionBar().getCustomView();
        mTitle = (TextView) view.findViewById(R.id.actionbar_title);
        mLeftButton = (ImageView) view.findViewById(R.id.actionbar_leftbtn);
        mRightButton = (ImageView) view.findViewById(R.id.actionbar_rightbtn);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTitle.setText(getActionBarTitle());
        if (!setActionBarLeftButton(mLeftButton)) {
            mLeftButton.setVisibility(View.GONE);
        }
        if (!setActionBarRightButton(mRightButton)) {
            mRightButton.setVisibility(View.GONE);
        }
    }

    protected abstract String getActionBarTitle();

    protected abstract boolean setActionBarLeftButton(ImageView leftButton);

    protected abstract boolean setActionBarRightButton(ImageView rightButton);

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
