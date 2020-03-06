package com.beanu.arad.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.beanu.arad.http.RxHelper;
import com.beanu.arad.utils.TUtil;
import com.uber.autodispose.AutoDisposeConverter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;

/**
 * 基础fragment
 * 2.mvp的泛形实现
 * 3.start activity的封装
 * 4.引入了ViewModel,横竖屏时保持住数据
 * 5.引入了lifecycle,保证请求的及时销毁
 *
 * @author Beanu
 */
public class BaseFragment<P extends BasePresenter, M extends BaseModel> extends Fragment {

    protected P mPresenter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        BaseViewModel viewModel = new ViewModelProvider(this).get(BaseViewModel.class);
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
        //TODO 是不是不应该设置view为空
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
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

    protected P obtainPresenter() {
        return TUtil.getT(this, 0);
    }

    protected M obtainModel() {
        return TUtil.getT(this, 1);
    }
}
