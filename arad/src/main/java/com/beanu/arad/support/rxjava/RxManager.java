package com.beanu.arad.support.rxjava;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


/**
 * 用于管理单个presenter的Rxjava相关代码的生命周期处理
 * Created by xsf
 * on 2016.08.14:50
 */
public class RxManager {
    private CompositeDisposable mCompositeSubscription = new CompositeDisposable();

    /**
     * 单纯的Observables 和 Subscribers管理
     */
    public void add(Disposable d) {
        /*订阅管理*/
        mCompositeSubscription.add(d);
    }

    /**
     * 单个presenter生命周期结束，取消订阅和所有rxbus观察
     */
    public void clear() {
        mCompositeSubscription.dispose();// 取消所有订阅
    }

    public void destory() {
        mCompositeSubscription = null;
    }
}
