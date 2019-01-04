package com.beanu.arad.demo.mvp.model;

import com.beanu.arad.demo.mvp.contract.TestContract;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by Beanu on 2019/01/03
 */

public class TestModelImpl implements TestContract.Model {

    @Override
    public Observable<String> task() {
        return Observable.interval(0, 1, TimeUnit.SECONDS)
                .map(new Function<Long, String>() {
                    @Override
                    public String apply(Long aLong) throws Exception {
                        return "Fragment " + String.valueOf(aLong);
                    }
                });
    }
}