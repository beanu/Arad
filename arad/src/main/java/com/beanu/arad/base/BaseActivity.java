package com.beanu.arad.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.beanu.arad.AradApplication;
import com.beanu.arad.R;
import com.beanu.arad.support.slideback.SlideBackHelper;
import com.beanu.arad.support.slideback.SlideConfig;
import com.beanu.arad.support.slideback.widget.SlideBackLayout;
import com.beanu.arad.utils.TUtil;
import com.beanu.arad.widget.dialog.ProgressHUD;


/**
 * 基础类
 * 1.添加了ProgressHUD 可以显示等待progress
 * 2.mvp的泛形实现
 * 3.start activity的封装
 */
public class BaseActivity<T extends BasePresenter, E extends BaseModel> extends AppCompatActivity {

    public T mPresenter;
    public E mModel;

    ProgressHUD mProgressHUD;
    private SlideBackLayout mSlideBackLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        if (mPresenter != null) {
            mPresenter.mContext = this;

            if (this instanceof BaseView) mPresenter.setVM(this, mModel);
        }

    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setStatusBar();
    }

    protected void setStatusBar() {
//        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
    }


    private void initSlideBackLayout() {
        mSlideBackLayout = SlideBackHelper.attach(
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
                        .lock(true)
                        // 侧滑的响应阈值，0~1，对应屏幕宽度*percent
                        .edgePercent(0.2f)
                        // 关闭页面的阈值，0~1，对应屏幕宽度*percent
                        .slideOutPercent(0.5f).create(),
                // 滑动的监听
                null);
    }

    public void enableSlideBack() {
        if (mSlideBackLayout == null) {
            initSlideBackLayout();
        }
        if (mSlideBackLayout != null) {
            mSlideBackLayout.lock(false);
        }
    }

    public void disableSlideBack() {
        if (mSlideBackLayout != null) {
            mSlideBackLayout.lock(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.onDestroy();
    }

    /**
     * 显示一个等待dialog，内容交互或者提交的时候使用
     *
     * @param show true显示 false隐藏
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

    /**
     * 通过Class跳转界面
     **/
    public void startActivity(Class cls) {
        startActivity(cls, null);
    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivityForResult(Class cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivityForResult(Class cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivity(Class cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
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
