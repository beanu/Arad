package com.beanu.arad.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.beanu.arad.Arad;
import com.beanu.arad.base.BaseListFragment;
import com.beanu.arad.http.AjaxCallBack;
import com.beanu.arad.http.AjaxParams;

public class NewsListFragment extends BaseListFragment {

	List<Map<String, String>> data;
	NewsListAdapter adapter;

	public static NewsListFragment newInstance() {
		NewsListFragment fragment = new NewsListFragment();
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		data = new ArrayList<Map<String, String>>();
		adapter = new NewsListAdapter(getActivity(), data);
		setListAdapter(adapter);

		AjaxParams params = new AjaxParams();
		params.put("k", "lists");
		params.put("pageNum", "3");
		params.put("t", "cat");
		params.put("cid", "0");
		Log.d("TIME", "Afinal http begin!");
		Arad.http.get("http://api.eoe.cn/client/news", params, new AjaxCallBack<String>() {

			@Override
			public void onLoading(long count, long current) {
				Log.d("debug", current / count + "%");
			}

			@Override
			public void onSuccess(String t) {
				try {
					JSONObject response = new JSONObject(t).getJSONObject("response");
					String datalist = response.getString("items");

					JSONArray dataListList = new JSONArray(datalist);
					for (int i = 0; i < dataListList.length(); i++) {
						Map<String, String> map = new HashMap<String, String>();
						JSONObject news = dataListList.getJSONObject(i);
						map.put("title", news.getString("title"));
						map.put("url", news.getString("thumbnail_url"));
						data.add(map);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				adapter.notifyDataSetChanged();
				Log.d("TIME", "Afinal http end!");
			}

		});
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.list_fragment, container, false);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Toast.makeText(getActivity(), "position:" + position, Toast.LENGTH_SHORT).show();
	}

}
