package com.beanu.arad.demo.mvp.presenter;

import com.beanu.arad.demo.bean.Student;
import com.beanu.arad.demo.mvp.contract.MainContract;
import com.beanu.arad.http.RxHelper;
import com.beanu.arad.support.log.KLog;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Beanu on 2018/12/29
 *
 * @author Beanu
 */

public class MainPresenterImpl extends MainContract.Presenter {

    private Student mStudent = new Student();

    @Override
    public void onCreate() {
        KLog.d("onCreate" + System.identityHashCode(mStudent));
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {

        mModel
                .task()
                .compose(RxHelper.<String>handleResultNoWarp())
                .as(bindLifecycle())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        if (isViewAttached()) {
                            KLog.d(s);
                        } else {
                            KLog.d(s + " no view");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    @Override
    public void onDestroy() {
        KLog.d("onDestroy" + System.identityHashCode(mStudent));
    }


    @Override
    public void onCleared() {
        KLog.d("onCleared" + System.identityHashCode(mStudent));
    }

    public Student getStudent() {
        return mStudent;
    }
}