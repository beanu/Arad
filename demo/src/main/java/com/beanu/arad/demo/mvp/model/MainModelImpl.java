package com.beanu.arad.demo.mvp.model;

import com.beanu.arad.demo.mvp.contract.MainContract;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by Beanu on 2018/12/29
 *
 * @author Beanu
 */

public class MainModelImpl implements MainContract.Model {

    @Override
    public Observable<String> task() {
        return Observable.interval(0, 1, TimeUnit.SECONDS)
                .map(new Function<Long, String>() {
                    @Override
                    public String apply(Long aLong) throws Exception {
                        return String.valueOf(aLong);
                    }
                });
    }
}