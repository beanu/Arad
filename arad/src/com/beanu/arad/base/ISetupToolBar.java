package com.beanu.arad.base;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * 设置Toolbar接口
 * Created by beanu on 14/12/24.
 */
public interface ISetupToolBar {

    abstract String setupToolBarTitle();

    abstract boolean setupToolBarLeftButton(ImageView leftButton);

    abstract boolean setupToolBarRightButton(ImageView rightButton);

    // 动态改变
    abstract TextView getmTitle();

    abstract ImageView getmLeftButton();

    abstract ImageView getmRightButton();
}
