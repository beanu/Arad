package com.beanu.arad.demo;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beanu.arad.Arad;
import com.beanu.arad.demo.support.event.NavEvent;

import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 导航
 */
public class NavFragment extends Fragment {

    public NavFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nav, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @OnClick({R.id.nav_nav, R.id.nav_list, R.id.nav_widget, R.id.nav_network, R.id.nav_pic, R.id.nav_anim})
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.nav_nav) {
            clickEvent(MainActivity.Fragments.nav.name());
        } else if (id == R.id.nav_list) {
            clickEvent(MainActivity.Fragments.listView.name());
        }
    }

    private void clickEvent(String tag) {
        Arad.bus.post(new NavEvent(tag));
    }
}
