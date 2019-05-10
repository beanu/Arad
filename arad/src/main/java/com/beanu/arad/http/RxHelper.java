package com.beanu.arad.http;


import com.beanu.arad.error.AradException;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.AutoDisposeConverter;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
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
 * Description: Rx 一些巧妙的处理
 *
 * @author Beanu
 */
public class RxHelper {


    private RxHelper() {
        throw new IllegalStateException("Can't instance the RxHelper");
    }

    /**
     * 对结果进行预处理,输入的参数是规范的格式
     */
    public static <T> ObservableTransformer<IHttpModel<T>, T> handleResult() {

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
                                    exception.setErrorCode(result.getErrorCode());
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
     * 对结果进行预处理（没有IHttpModel的包裹）
     */
    public static <T> ObservableTransformer<T, T> handleResultNoWarp() {

        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<T> upstream) {
                return upstream
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
                    if (data != null) {
                        subscriber.onNext(data);
                        subscriber.onComplete();
                    }
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });

    }


    /**
     * 自动解绑Dispose
     *
     * @param lifecycleOwner activity or fragment
     * @param <T>            数据
     * @return AutoDisposeConverter
     */
    public static <T> AutoDisposeConverter<T> bindLifecycle(LifecycleOwner lifecycleOwner) {
        return AutoDispose.autoDisposable(
                AndroidLifecycleScopeProvider.from(lifecycleOwner)
        );
    }


    /**
     * 在event生命周期的时候，自动解绑Dispose
     *
     * @param lifecycleOwner activity or fragment
     * @param event          生命周期事件
     * @param <T>            数据
     * @return AutoDisposeConverter
     */
    public static <T> AutoDisposeConverter<T> bindLifecycle(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
        return AutoDispose.autoDisposable(
                AndroidLifecycleScopeProvider.from(lifecycleOwner, event)
        );
    }


}