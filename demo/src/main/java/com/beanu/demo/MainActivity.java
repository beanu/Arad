package com.beanu.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.beanu.arad.base.ToolBarActivity;
import com.beanu.arad.utils.MessageUtils;
import com.beanu.arad.widget.dialog.BottomPopupMenuFragment;

import java.util.Collections;
import java.util.Map;

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
                new BottomPopupMenuFragment.Builder()
                        .setId("id")
                        .setTitle("请选择操作")
                        .setMenus("菜单一", "菜单二", "菜单三")
                        .setListener(this)
                        .setExtData(Collections.<String, Object>singletonMap("menus", new String[]{"菜单一", "菜单二", "菜单三"}))
                        .create()
                        .show(getSupportFragmentManager(), "bottom_menu");

                break;
        }
    }

    /**
     *
     * @param id              根据该id 判断哪个操作弹出的 底部菜单
     * @param clickIndex      第几个菜单项
     * @param extData         附加数据
     */

    @Override
    public void onMenuClick(String id, int clickIndex, Map<String, Object> extData) {
        MessageUtils.showShortToast(this, ((String[]) extData.get("menus"))[clickIndex]);
    }
}
