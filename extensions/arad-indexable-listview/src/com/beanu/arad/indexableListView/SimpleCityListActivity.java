package com.beanu.arad.indexableListView;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;


public class SimpleCityListActivity extends FragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_list_activity);

//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        transaction.add(R.id.fragment_content, new SimpleCityListFragment());
//        transaction.commit();
    }

}
