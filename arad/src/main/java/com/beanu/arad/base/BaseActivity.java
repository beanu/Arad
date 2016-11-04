package com.beanu.arad.base;

import android.support.v7.app.AppCompatActivity;

import com.beanu.arad.widget.dialog.ProgressHUD;


/**
 * 基础类
 * 1.添加了ProgressHUD 可以显示等待progress
 */
public class BaseActivity extends AppCompatActivity {


    ProgressHUD mProgressHUD;

    /**
     * 显示一个等待dialog，内容交互或者提交的时候使用
     *
     * @param show 是否要显示
     */
    public void showProgress(boolean show) {
        showProgressWithText(show, "加载中...");
    }

    /**
     * 显示一个等待dialog，内容交互或者提交的时候使用
     *
     * @param show    是否要显示
     * @param message 要显示的文字
     */
    public void showProgressWithText(boolean show, String message) {
        if (show) {
            mProgressHUD = ProgressHUD.show(this, message, true, true, null);
        } else {
            if (mProgressHUD != null) {
                mProgressHUD.dismiss();
            }
        }
    }
}
