package com.beanu.arad.demo.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beanu.arad.demo.R;
import com.beanu.arad.utils.Log;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;


/**
 * A simple {@link Fragment} subclass.
 */
public class RxJavaFragment extends Fragment {


    public RxJavaFragment() {
        // Required empty public constructor

        Observable<Integer> observableTT = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i = 0; i < 5; i++) {
                    subscriber.onNext(i);
                }
                subscriber.onCompleted();
            }
        });

        observableTT.subscribe(new Observer<Integer>() {
            @Override
            public void onCompleted() {
                Log.d("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(Integer integer) {
                Log.d("onNext" + integer);
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rx_java, container, false);
    }

}
