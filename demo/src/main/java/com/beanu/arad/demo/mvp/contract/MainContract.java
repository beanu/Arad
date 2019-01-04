package com.beanu.arad.demo.mvp.contract;

import com.beanu.arad.base.BaseModel;
import com.beanu.arad.base.BasePresenter;
import com.beanu.arad.base.BaseView;

import io.reactivex.Observable;

public interface MainContract {

    public interface View extends BaseView {
    }

    public abstract class Presenter extends BasePresenter<View, Model> {
    }

    public interface Model extends BaseModel {
        public Observable<String> task();
    }


}