package com.beanu.arad.base;

import android.view.View;
import android.widget.TextView;

/**
 * 设置Toolbar接口
 * Created by beanu on 14/12/24.
 */
public interface ISetupToolBar {

    String setupToolBarTitle();

    boolean setupToolBarLeftButton(View leftButton);

    boolean setupToolBarRightButton2(View rightButton2);

    boolean setupToolBarRightButton1(View rightButton1);

    // 动态改变
    TextView getToolBarTitle();

    View getToolBarLeftButton();

    View getToolBarRightButton2();

    View getToolBarRightButton1();

}
