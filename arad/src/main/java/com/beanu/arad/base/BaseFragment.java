package com.beanu.arad.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.beanu.arad.R;
import com.beanu.arad.http.RxHelper;
import com.beanu.arad.utils.TUtil;
import com.beanu.arad.widget.dialog.ProgressHUD;
import com.uber.autodispose.AutoDisposeConverter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProviders;

/**
 * 基础fragment
 * 1.加入了progress dialog
 * 2.mvp的泛形实现
 * 3.start activity的封装
 * 4.引入了ViewModel,横竖屏时保持住数据
 * 5.引入了lifecycle,保证请求的及时销毁
 *
 * @author Beanu
 */
public class BaseFragment<P extends BasePresenter, M extends BaseModel> extends Fragment {

    P mPresenter;
    private ProgressHUD mProgressHUD;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        BaseViewModel viewModel = ViewModelProviders.of(this).get(BaseViewModel.class);
        if (mPresenter == null) {
            viewModel.setPresenter(obtainPresenter());
            mPresenter = (P) viewModel.getPresenter();
        }

        if (mPresenter != null) {
            mPresenter.attachLifecycle(this);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (mPresenter != null) {
            M mModel = obtainModel();
            mPresenter.setModel(mModel);
        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mPresenter != null) {
            if (this instanceof BaseView) {
                mPresenter.attachView(this);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.mModel = null;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mPresenter != null) {
            mPresenter.detachLifecycle(this);
        }
    }

    protected <T> AutoDisposeConverter<T> bindLifecycle() {
        return RxHelper.bindLifecycle(this);
    }

    protected <T> AutoDisposeConverter<T> bindLifecycle(Lifecycle.Event event) {
        return RxHelper.bindLifecycle(this, event);
    }

    public void showProgress(boolean show) {
        showProgressWithText(show, getString(R.string.arad_loading));
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
        intent.setClass(getActivity(), cls);
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
        intent.setClass(getActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode, @Nullable Bundle options) {
        Activity act = getActivity();
        if (act != null && act instanceof BaseActivity) {
            intent.putExtra("disableSlideBack", ((BaseActivity) act).disableNextPageSlideBack);
        }
        super.startActivityForResult(intent, requestCode, options);
    }

    @Override
    public void startActivity(Intent intent, @Nullable Bundle options) {
        Activity act = getActivity();
        if (act != null && act instanceof BaseActivity) {
            intent.putExtra("disableSlideBack", ((BaseActivity) act).disableNextPageSlideBack);
        }
        super.startActivity(intent, options);
    }

    protected P obtainPresenter() {
        return TUtil.getT(this, 0);
    }

    protected M obtainModel() {
        return TUtil.getT(this, 1);
    }
}
