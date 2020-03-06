package com.beanu.arad.base;

import android.content.Intent;
import android.os.Bundle;

import com.beanu.arad.R;
import com.beanu.arad.http.RxHelper;
import com.beanu.arad.utils.TUtil;
import com.uber.autodispose.AutoDisposeConverter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;


/**
 * 基础类
 * 2.mvp的泛形实现
 * 3.start activity的封装
 * 4.引入了ViewModel,横竖屏时保持住数据
 * 5.引入了autoDispose,保证请求的及时销毁
 *
 * @author Beanu
 */

public class BaseActivity<P extends BasePresenter, M extends BaseModel> extends AppCompatActivity {

    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BaseViewModel viewModel = new ViewModelProvider(this).get(BaseViewModel.class);

        mPresenter = (P) viewModel.getPresenter();
        if (mPresenter == null) {
            viewModel.setPresenter(obtainPresenter());
            mPresenter = (P) viewModel.getPresenter();
        }

        if (mPresenter != null) {

            mPresenter.attachLifecycle(this);
            if (this instanceof BaseView) {
                mPresenter.attachView(this);
            }

            M mModel = obtainModel();
            mPresenter.setModel(mModel);

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mPresenter != null) {
            mPresenter.detachLifecycle(this);
            mPresenter.detachView();

            mPresenter.mModel = null;
        }

    }

    protected <T> AutoDisposeConverter<T> bindLifecycle() {
        return RxHelper.bindLifecycle(this);
    }

    protected <T> AutoDisposeConverter<T> bindLifecycle(Lifecycle.Event event) {
        return RxHelper.bindLifecycle(this, event);
    }


    /**
     * 通过Class跳转界面
     **/
    public void launchActivity(Class cls) {
        launchActivity(cls, null);
    }

    /**
     * 通过Class跳转界面
     **/
    public void launchActivityForResult(Class cls, int requestCode) {
        launchActivityForResult(cls, null, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void launchActivityForResult(Class cls, Bundle bundle,
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
    public void launchActivity(Class cls, Bundle bundle) {
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

    protected P obtainPresenter() {
        return TUtil.getT(this, 0);
    }

    protected M obtainModel() {
        return TUtil.getT(this, 1);
    }
}
