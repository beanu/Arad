package com.beanu.arad.demo;

import android.os.Bundle;
import android.view.View;

import com.beanu.arad.base.ToolBarActivity;
import com.beanu.arad.demo.mvp.contract.MainContract;
import com.beanu.arad.demo.mvp.model.MainModelImpl;
import com.beanu.arad.demo.mvp.presenter.MainPresenterImpl;

/**
 * @author Beanu
 */
public class MainActivity extends ToolBarActivity<MainPresenterImpl, MainModelImpl> implements MainContract.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.btn_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchActivity(SecondActivity.class);
            }
        });

        findViewById(R.id.btn_next2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchActivity(TestFragmentActivity.class);
            }
        });

        findViewById(R.id.btn_next3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchActivity(MMKVActivity.class);
            }
        });
    }
}
