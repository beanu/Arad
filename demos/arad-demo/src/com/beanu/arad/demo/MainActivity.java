package com.beanu.arad.demo;

import android.os.Bundle;

import com.beanu.arad.base.BaseActivity;

public class MainActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

//		if (savedInstanceState == null) {
//			FragmentTransaction tr = getSupportFragmentManager()
//					.beginTransaction();
//			tr.add(R.id.fragment, NewsListFragment.newInstance()).commit();
//		}

	}
}
