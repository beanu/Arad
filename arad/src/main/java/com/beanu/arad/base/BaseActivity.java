package com.beanu.arad.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.beanu.arad.R;
import com.beanu.arad.support.rxjava.RxManager;
import com.beanu.arad.utils.TUtil;
import com.beanu.arad.widget.dialog.ProgressHUD;
import com.github.anzewei.parallaxbacklayout.ParallaxBack;
import com.github.anzewei.parallaxbacklayout.ParallaxHelper;


/**
 * 基础类
 * 1.添加了ProgressHUD 可以显示等待progress
 * 2.mvp的泛形实现
 * 3.start activity的封装
 */

@ParallaxBack
public class BaseActivity<P extends BasePresenter, M extends BaseModel> extends AppCompatActivity {

    public P mPresenter;
    public M mModel;

    public RxManager mRxManage;

    private ProgressHUD mProgressHUD;

    boolean disableNextPageSlideBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = obtainPresenter();
        mModel = obtainModel();
        if (mPresenter != null) {
            mPresenter.mContext = this;
            if (this instanceof BaseView){
                mPresenter.setVM(this, mModel);
            }
            mPresenter.onCreate(savedInstanceState);
        }
        mRxManage = new RxManager();

        if (getIntent().getBooleanExtra("disableSlideBack", false)){
            disableSlideBack();
        }
    }

    /**
     * 禁止下一个页面滑动返回
     */
    protected void requireDisableNextPageSlideBack(){
        disableNextPageSlideBack = true;
    }

    public void enableSlideBack() {
        ParallaxHelper.enableParallaxBack(this);
    }

    public void disableSlideBack() {
        ParallaxHelper.disableParallaxBack(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mPresenter != null){
            mPresenter.onStart();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPresenter!= null){
            mPresenter.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mPresenter != null){
            mPresenter.onPause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mPresenter != null){
            mPresenter.onStop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }

        if (mRxManage != null) {
            mRxManage.clear();
        }
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

    protected P obtainPresenter(){
        return TUtil.getT(this, 0);
    }

    protected M obtainModel(){
        return TUtil.getT(this, 1);
    }
}
