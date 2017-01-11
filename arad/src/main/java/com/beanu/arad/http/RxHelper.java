package com.beanu.arad.http;


import com.beanu.arad.error.AradException;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Jam on 16-6-12
 * Description: Rx 一些巧妙的处理
 */
public class RxHelper {

    /**
     * 对结果进行预处理
     */
    public static <T> Observable.Transformer<IHttpModel<T>, T> handleResult() {

        return new Observable.Transformer<IHttpModel<T>, T>() {
            @Override
            public Observable<T> call(Observable<IHttpModel<T>> tObservable) {
                return tObservable.flatMap(new Func1<IHttpModel<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(IHttpModel<T> result) {
                        if (result.success()) {
                            return createData(result.getResults());
                        } else {
                            AradException exception = new AradException();
                            exception.setError_code(result.getErrorCode());
                            exception.setOriError(result.getMsg());
                            return Observable.error(exception);
                        }
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };

    }

    /**
     * 创建成功的数据
     */
    private static <T> Observable<T> createData(final T data) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    subscriber.onNext(data);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });

    }


}
