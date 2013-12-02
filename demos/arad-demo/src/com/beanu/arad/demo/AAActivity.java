package com.beanu.arad.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.beanu.arad.base.BaseActivity;

public class AAActivity extends BaseActivity {

	private ListView listView;
	private ArrayAdapter<Class<?>> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Theme_AppCompat_Light_DarkActionBar);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aa_activity);

		listView = (ListView) findViewById(R.id.listview);

		adapter = new ArrayAdapter<Class<?>>(getApplicationContext(), R.layout.aa_list_item);

		adapter.add(MainActivity.class);
		adapter.add(ColorBarActivity.class);
		adapter.add(VolleyTestActivity.class);
		adapter.add(LoadMoreActivity.class);

		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(AAActivity.this, adapter.getItem(position));
				startActivity(intent);
			}
		});
	}
}
