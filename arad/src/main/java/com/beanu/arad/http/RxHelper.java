package com.beanu.arad.http;


import com.beanu.arad.error.AradException;
import com.trello.rxlifecycle3.LifecycleProvider;

import androidx.lifecycle.Lifecycle;
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
     * 对结果进行预处理,输入的参数是规范的格式
     */
    public static <T> ObservableTransformer<IHttpModel<T>, T> handleSpecResultNoLifecycle() {

        return new ObservableTransformer<IHttpModel<T>, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<IHttpModel<T>> upstream) {
                return upstream
                        .flatMap(new Function<IHttpModel<T>, ObservableSource<T>>() {
                            @Override
                            public ObservableSource<T> apply(@NonNull IHttpModel<T> result) {
                                if (result.success()) {
                                    return createData(result.getResults());
                                } else {
                                    AradException exception = new AradException();
                                    exception.setError_code(result.getErrorCode());
                                    exception.setOriError(result.getMsg());
                                    return Observable.error(exception);
                                }
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };

    }


    /**
     * 对结果进行预处理
     */
    public static <T> ObservableTransformer<T, T> handleResultNoLifecycle() {

        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<T> upstream) {
                return upstream
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };

    }

    public static <T> ObservableTransformer<T, T> handleResult(final LifecycleProvider<Lifecycle.Event> provider) {

        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<T> upstream) {
                return upstream
                        .compose(provider.<T>bindToLifecycle())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };

    }

    public static <T> ObservableTransformer<T, T> handleResult(final LifecycleProvider<Lifecycle.Event> provider, final Lifecycle.Event event) {

        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<T> upstream) {
                return upstream
                        .compose(provider.<T>bindUntilEvent(event))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };

    }

    public static <T> ObservableTransformer<IHttpModel<T>, T> handleSpecResult(final LifecycleProvider<Lifecycle.Event> provider) {

        return new ObservableTransformer<IHttpModel<T>, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<IHttpModel<T>> upstream) {
                return upstream
                        .flatMap(new Function<IHttpModel<T>, ObservableSource<T>>() {
                            @Override
                            public ObservableSource<T> apply(@NonNull IHttpModel<T> result) {
                                if (result.success()) {
                                    return createData(result.getResults());
                                } else {
                                    AradException exception = new AradException();
                                    exception.setError_code(result.getErrorCode());
                                    exception.setOriError(result.getMsg());
                                    return Observable.error(exception);
                                }
                            }
                        })
                        .compose(provider.<T>bindToLifecycle())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };

    }

    public static <T> ObservableTransformer<IHttpModel<T>, T> handleSpecResult(final LifecycleProvider<Lifecycle.Event> provider, final Lifecycle.Event event) {

        return new ObservableTransformer<IHttpModel<T>, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<IHttpModel<T>> upstream) {
                return upstream
                        .flatMap(new Function<IHttpModel<T>, ObservableSource<T>>() {
                            @Override
                            public ObservableSource<T> apply(@NonNull IHttpModel<T> result) {
                                if (result.success()) {
                                    return createData(result.getResults());
                                } else {
                                    AradException exception = new AradException();
                                    exception.setError_code(result.getErrorCode());
                                    exception.setOriError(result.getMsg());
                                    return Observable.error(exception);
                                }
                            }
                        })
                        .compose(provider.<T>bindUntilEvent(event))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };

    }

    /**
     * 创建成功的数据
     */
    private static <T> Observable<T> createData(final T data) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<T> subscriber) {
                try {
                    //TODO
                    if (data != null) {
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
