package com.beanu.arad.demo.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beanu.arad.demo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NetWorkHttpFragment extends Fragment {


    public NetWorkHttpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_net_work_http, container, false);
    }


}
