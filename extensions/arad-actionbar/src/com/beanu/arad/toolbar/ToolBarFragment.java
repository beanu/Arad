package com.beanu.arad.toolbar;

import android.os.Bundle;
import android.widget.ImageView;

/**
 * @author beanu
 */
public class ToolBarFragment extends _ToolBarFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected String setupToolBarTitle() {
        return null;
    }

    @Override
    protected boolean setupToolBarLeftButton(ImageView leftButton) {
        return false;
    }

    @Override
    protected boolean setupToolBarRightButton(ImageView rightButton) {
        return false;
    }
}
