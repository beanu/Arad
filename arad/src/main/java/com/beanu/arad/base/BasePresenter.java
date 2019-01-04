package com.beanu.arad.base;

import com.trello.lifecycle2.android.lifecycle.AndroidLifecycle;
import com.trello.rxlifecycle3.LifecycleProvider;

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
    public M mModel;
    public V mView;
    public LifecycleProvider<Lifecycle.Event> mLifecycleProvider;

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
        mLifecycleProvider = AndroidLifecycle.createLifecycleProvider(owner);
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
}
