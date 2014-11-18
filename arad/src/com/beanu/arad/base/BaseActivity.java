package com.beanu.arad.base;

import android.support.v7.app.ActionBarActivity;

import com.beanu.arad.http.INetResult;
import com.beanu.arad.widget.dialog.ProgressHUD;
import com.beanu.arad.utils.MessageUtils;

/**
 * 基础类
 * 1.继承了INetResult 具备了网络处理能力
 * 2.添加了ProgressHUD 可以显示等待progress
 */
public class BaseActivity extends ActionBarActivity implements INetResult {

    ProgressHUD mProgressHUD;

    @Override
    public void onRequestSuccess(int requestCode) {

    }

    @Override
    public void onRequestFaild(String errorNo, String errorMessage) {
        showProgress(false);
        MessageUtils.showShortToast(this, errorMessage);
    }

    @Override
    public void onNoConnect() {
        showProgress(false);
        MessageUtils.showShortToast(this, "无网络连接");
    }

    public void showProgress(boolean show) {
        showProgressWithText(show, "加载中...");
    }

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
