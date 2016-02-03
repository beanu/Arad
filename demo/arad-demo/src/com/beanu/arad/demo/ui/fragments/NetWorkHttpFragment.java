package com.beanu.arad.demo.ui.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beanu.arad.demo.R;
import com.beanu.arad.demo.ui.module.NetWorkActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class NetWorkHttpFragment extends Fragment {


    public NetWorkHttpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_net_work_http, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @OnClick(R.id.button)
    public void onClick() {
        Intent intent = new Intent(getActivity(), NetWorkActivity.class);
        startActivity(intent);
    }
}
