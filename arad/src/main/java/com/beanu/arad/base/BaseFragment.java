package com.beanu.arad.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.beanu.arad.support.rxjava.RxManager;
import com.beanu.arad.utils.TUtil;
import com.beanu.arad.widget.dialog.ProgressHUD;

/**
 * 基础fragment
 * 1.加入了progress dialog
 * 2.mvp的泛形实现
 * 3.start activity的封装
 * 4.加入rxManager
 */
public class BaseFragment<P extends BasePresenter, M extends BaseModel> extends Fragment {

    public P mPresenter;
    public M mModel;

    public RxManager mRxManage;

    private ProgressHUD mProgressHUD;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = obtainPresenter();
        mModel = obtainModel();
        if (mPresenter != null) {
            mPresenter.mContext = this.getActivity();
            if (this instanceof BaseView) {
                mPresenter.setVM(this, mModel);
            }
            mPresenter.onCreate(savedInstanceState);
        }

        mRxManage = new RxManager();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mPresenter != null){
            mPresenter.onStart();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPresenter != null){
            mPresenter.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mPresenter !=  null){
            mPresenter.onPause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mPresenter != null){
            mPresenter.onStop();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }

        if (mRxManage != null) {
            mRxManage.clear();
        }

    }

    public void showProgress(boolean show) {
        showProgressWithText(show, "加载中...");
    }

    public void showProgressWithText(boolean show, String message) {
        if (show) {
            mProgressHUD = ProgressHUD.show(getActivity(), message, true, true, null);
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
        intent.setClass(getActivity(), cls);
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
        intent.setClass(getActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    @Override public void startActivityForResult(Intent intent, int requestCode, @Nullable Bundle options) {
        Activity act = getActivity();
        if (act != null && act instanceof BaseActivity){
            intent.putExtra("disableSlideBack", ((BaseActivity)act).disableNextPageSlideBack);
        }
        super.startActivityForResult(intent, requestCode, options);
    }

    @Override public void startActivity(Intent intent, @Nullable Bundle options) {
        Activity act = getActivity();
        if (act != null && act instanceof BaseActivity){
            intent.putExtra("disableSlideBack", ((BaseActivity)act).disableNextPageSlideBack);
        }
        super.startActivity(intent, options);
    }

    protected P obtainPresenter(){
        return TUtil.getT(this, 0);
    }

    protected M obtainModel(){
        return TUtil.getT(this, 1);
    }
}
