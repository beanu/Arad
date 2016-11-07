package com.beanu.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.beanu.arad.base.ToolBarActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends ToolBarActivity {

    @BindView(R.id.bt_error)
    Button btError;
    @BindView(R.id.bt_second)
    Button btSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bt_second, R.id.bt_error})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_second:
                startActivity(new Intent(this, SecondActivity.class));
                break;
            case R.id.bt_error:
                throw new RuntimeException("哈哈哈，出错了吧");
        }
    }
}
