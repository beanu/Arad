package com.beanu.arad.base;

import android.view.View;
import android.widget.TextView;

/**
 * 设置Toolbar接口
 * Created by beanu on 14/12/24.
 */
public interface ISetupToolBar {

    abstract String setupToolBarTitle();

    abstract boolean setupToolBarLeftButton(View leftButton);

    abstract boolean setupToolBarRightButton(View rightButton);

    // 动态改变
    abstract TextView getmTitle();

    abstract View getmLeftButton();

    abstract View getmRightButton();
}
