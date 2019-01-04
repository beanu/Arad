package com.beanu.arad.base;

import android.content.Intent;
import android.os.Bundle;

import com.beanu.arad.R;
import com.beanu.arad.utils.TUtil;
import com.beanu.arad.widget.dialog.ProgressHUD;
import com.github.anzewei.parallaxbacklayout.ParallaxBack;
import com.github.anzewei.parallaxbacklayout.ParallaxHelper;
import com.trello.lifecycle2.android.lifecycle.AndroidLifecycle;
import com.trello.rxlifecycle3.LifecycleProvider;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProviders;


/**
 * 基础类
 * 1.添加了ProgressHUD 可以显示等待progress
 * 2.mvp的泛形实现
 * 3.start activity的封装
 * 4.引入了ViewModel,横竖屏时保持住数据
 * 5.引入了lifecycle,保证请求的及时销毁
 *
 * @author Beanu
 */

@ParallaxBack
public class BaseActivity<P extends BasePresenter, M extends BaseModel> extends AppCompatActivity {

    protected P mPresenter;
    protected LifecycleProvider<Lifecycle.Event> mLifecycleProvider;
    private ProgressHUD mProgressHUD;
    boolean disableNextPageSlideBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BaseViewModel viewModel = ViewModelProviders.of(this).get(BaseViewModel.class);

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

        } else {
            //如果Presenter不为空，则使用Presenter中的LifecycleProvider
            mLifecycleProvider = AndroidLifecycle.createLifecycleProvider(this);
        }

        if (getIntent().getBooleanExtra("disableSlideBack", false)) {
            disableSlideBack();
        }
    }

    /**
     * 禁止下一个页面滑动返回
     */
    protected void requireDisableNextPageSlideBack() {
        disableNextPageSlideBack = true;
    }

    public void enableSlideBack() {
        ParallaxHelper.enableParallaxBack(this);
    }

    public void disableSlideBack() {
        ParallaxHelper.disableParallaxBack(this);
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

    /**
     * 显示一个等待dialog，内容交互或者提交的时候使用
     *
     * @param show true显示 false隐藏
     */
    void showProgress(boolean show) {
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
