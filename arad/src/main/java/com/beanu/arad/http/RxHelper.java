package com.beanu.arad.http;


import com.beanu.arad.error.AradException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by Jam on 16-6-12
 * Description: Rx 一些巧妙的处理
 */
public class RxHelper {

    /**
     * 对结果进行预处理
     */
    public static <T> ObservableTransformer<IHttpModel<T>, T> handleResult() {

        return new ObservableTransformer<IHttpModel<T>, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<IHttpModel<T>> upstream) {
                return upstream.flatMap(new Function<IHttpModel<T>, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(@NonNull IHttpModel<T> result) throws Exception {
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
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<T> subscriber) throws Exception {
                try {
                    //TODO
                    if (data != null){
                        subscriber.onNext(data);
                    }
                    subscriber.onComplete();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });

    }


}
