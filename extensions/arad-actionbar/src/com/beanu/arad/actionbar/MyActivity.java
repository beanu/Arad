package com.beanu.arad.actionbar;

import android.os.Bundle;
import android.widget.ImageView;

import com.beanu.arad.utils.AnimUtil;

/**
 * 添加自定义头部
 * <p/>
 * 单个页面如果继承了MyActivity,则可以修改ActionBar的title,左右按钮
 *
 * @author beanu
 */
public class MyActivity extends _MyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        AnimUtil.intentSlidOut(this);
    }

    @Override
    protected String getActionBarTitle() {
        return null;
    }

    @Override
    protected boolean setActionBarLeftButton(ImageView leftButton) {
        return false;
    }

    @Override
    protected boolean setActionBarRightButton(ImageView rightButton) {
        return false;
    }
}
