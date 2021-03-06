package com.beanu.arad.demo;

import android.os.Bundle;
import android.view.View;

import com.beanu.arad.base.ToolBarActivity;
import com.beanu.arad.demo.bean.Student;
import com.beanu.arad.demo.bean.StudentLiveData;
import com.beanu.arad.demo.mvp.contract.MainContract;
import com.beanu.arad.demo.mvp.model.MainModelImpl;
import com.beanu.arad.demo.mvp.presenter.MainPresenterImpl;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;

/**
 * @author Beanu
 */
public class MainActivity extends ToolBarActivity<MainPresenterImpl, MainModelImpl> implements MainContract.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Student student = new Student("我是livedata");
        StudentLiveData.getInstance().setValue(student);


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

        findViewById(R.id.btn_next4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchActivity(LiveDataActivity.class);
            }
        });
    }


    @Override
    public void initTopBar(QMUITopBarLayout topBarLayout) {
        topBarLayout.setTitle("首页");
    }
}
