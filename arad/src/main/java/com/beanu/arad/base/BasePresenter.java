package com.beanu.arad.base;

import com.beanu.arad.http.RxHelper;
import com.uber.autodispose.AutoDisposeConverter;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * MVP
 * 基类presenter，具备了生命周期感知
 *
 * @author Beanu
 */
public abstract class BasePresenter<V, M> implements LifecycleObserver {

    protected M mModel;
    protected V mView;

    private LifecycleOwner lifecycleOwner;

    final public boolean isViewAttached() {
        return mView != null;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {

    }

    /**
     * viewmodel销毁的后，调用此方法
     */
    public void onCleared() {

    }


    void attachLifecycle(LifecycleOwner owner) {
        owner.getLifecycle().addObserver(this);
        lifecycleOwner = owner;
    }

    void detachLifecycle(LifecycleOwner owner) {
        owner.getLifecycle().removeObserver(this);
    }

    void attachView(V view) {
        this.mView = view;
    }

    void detachView() {
        this.mView = null;
    }

    void setModel(M m) {
        this.mModel = m;
    }

    protected <T> AutoDisposeConverter<T> bindLifecycle() {
        if (null == lifecycleOwner) {
            throw new NullPointerException("lifecycleOwner == null");
        }
        return RxHelper.bindLifecycle(lifecycleOwner);
    }

    protected <T> AutoDisposeConverter<T> bindLifecycle(Lifecycle.Event event) {
        if (null == lifecycleOwner) {
            throw new NullPointerException("lifecycleOwner == null");
        }
        return RxHelper.bindLifecycle(lifecycleOwner, event);
    }

}
