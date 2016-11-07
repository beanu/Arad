package com.beanu.arad.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.beanu.arad.AradApplication;
import com.beanu.arad.R;
import com.beanu.arad.widget.dialog.ProgressHUD;
import com.oubowu.slideback.SlideBackHelper;
import com.oubowu.slideback.SlideConfig;


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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //如果当前Activity没有上一个页面就不开启滑动返回
        if(AradApplication.activityHelper.getPreActivity()!=null){
            SlideBackHelper.attach(
                    // 当前Activity
                    this,
                    // Activity栈管理工具
                    AradApplication.activityHelper,
                    // 参数的配置
                    new SlideConfig.Builder()
                            // 屏幕是否旋转
                            .rotateScreen(true)
                            // 是否侧滑
                            .edgeOnly(true)
                            // 是否禁止侧滑
                            .lock(false)
                            // 侧滑的响应阈值，0~1，对应屏幕宽度*percent
                            .edgePercent(0.2f)
                            // 关闭页面的阈值，0~1，对应屏幕宽度*percent
                            .slideOutPercent(0.5f).create(),
                    // 滑动的监听
                    null);
        }
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.anim_slide_in, R.anim.anim_none);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_none, R.anim.anim_slide_out);
    }
}
