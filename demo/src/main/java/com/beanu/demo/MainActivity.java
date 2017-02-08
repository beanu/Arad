package com.beanu.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.beanu.arad.base.ToolBarActivity;
import com.beanu.arad.utils.MessageUtils;
import com.beanu.arad.widget.dialog.BottomPopupMenuFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends ToolBarActivity implements BottomPopupMenuFragment.Listener {

    @BindView(R.id.bt_error)
    Button btError;
    @BindView(R.id.bt_second)
    Button btSecond;
    @BindView(R.id.bt_menu)
    Button btMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bt_second, R.id.bt_error, R.id.bt_menu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_second:
                startActivity(new Intent(this, SecondActivity.class));
                break;
            case R.id.bt_error:
                throw new RuntimeException("哈哈哈，出错了吧");

            case R.id.bt_menu:
                BottomPopupMenuFragment.show("id", "请选择操作", new String[]{"菜单一", "菜单二", "菜单三"}, getSupportFragmentManager());
                break;
        }
    }

    /**
     * 实现 BottomPopupMenuFragment.Listener 后 BottomPopupMenuFragment 会自己去找监听器, 不需要显示设置
     * 但是只能在 父Fragment 和 宿主Activity 中实现该接口, 如果2个都是实现了, 只会执行 父Fragment 中的回调方法
     *
     * @param id              根据该id 判断哪个操作弹出的 底部菜单
     * @param clickMenuString 点击的菜单文字
     * @param clickIndex      第几个菜单项
     */
    @Override
    public void onMenuClick(String id, String clickMenuString, int clickIndex) {
        MessageUtils.showShortToast(this, clickMenuString);
    }
}
