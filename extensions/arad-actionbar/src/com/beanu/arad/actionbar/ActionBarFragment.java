package com.beanu.arad.actionbar;

import android.os.Bundle;
import android.widget.ImageView;

/**
 * 所有的Fragment的基类 如果一个Activity包含多了Fragment，则每个Fragment都可以定义自己的title。暂不支持嵌套中的fragment标题的设置
 *
 * @author beanu
 */
public class ActionBarFragment extends _MyFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
