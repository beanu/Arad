package com.beanu.demo;

import android.os.Bundle;
import android.view.View;

import com.arad.base.ToolBarActivity;
import com.arad.support.log.KLog;

public class MainActivity extends ToolBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected View onCreateView() {
        return getLayoutInflater().inflate(R.layout.activity_main, null);
    }

    @Override
    protected void onLoadingPageClick() {
        getLoadingPage().state_error();
        KLog.d("ddddd");
    }
}
