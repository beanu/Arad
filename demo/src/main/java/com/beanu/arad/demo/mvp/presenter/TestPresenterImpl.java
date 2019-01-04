package com.beanu.arad.demo.mvp.presenter;

import com.beanu.arad.demo.bean.Student;
import com.beanu.arad.demo.mvp.contract.TestContract;
import com.beanu.arad.http.RxHelper;
import com.beanu.arad.support.log.KLog;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Beanu on 2019/01/03
 */

public class TestPresenterImpl extends TestContract.Presenter {

    private Student mStudent = new Student();


    @Override
    public void onCreate() {
        KLog.d("onCreate" + System.identityHashCode(mStudent));

        mModel
                .task()
                .compose(RxHelper.<String>handleResult(mLifecycleProvider))
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

}