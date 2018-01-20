package com.beanu.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.beanu.arad.base.ToolBarActivity;
import com.beanu.arad.support.updateversion.UpdateChecker;
import com.beanu.arad.utils.ToastUtils;
import com.beanu.arad.widget.dialog.BottomPopupMenuFragment;

import java.util.Collections;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends ToolBarActivity implements BottomPopupMenuFragment.Listener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }


    @Override
    public boolean setupToolBarLeftButton(View leftButton) {

        return true;
    }


    @Override
    public boolean setupToolBarRightButton1(View rightButton1) {

        return true;
    }

    @OnClick({R.id.bt_second, R.id.bt_menu, R.id.update})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_second:
                startActivity(new Intent(this, SecondActivity.class));
                break;

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
            case R.id.update:
                UpdateChecker.checkForDialog(MainActivity.this, "适配测试, 不要安装", "http://116.196.90.77:8080/jrl/sign.apk", 10000);
                break;
            default:
                break;
        }
    }

    /**
     * @param id         根据该id 判断哪个操作弹出的 底部菜单
     * @param clickIndex 第几个菜单项
     * @param extData    附加数据
     */

    @Override
    public void onMenuClick(String id, int clickIndex, Map<String, Object> extData) {
        ToastUtils.showShort(((String[]) extData.get("menus"))[clickIndex]);
    }
}
