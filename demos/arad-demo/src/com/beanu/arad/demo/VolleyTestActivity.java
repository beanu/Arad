package com.beanu.arad.demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.beanu.arad.base.BaseListActivity;
import com.beanu.arad.demo.util.BitmapCache;
import com.beanu.arad.utils.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;

public class VolleyTestActivity extends BaseListActivity {

	RequestQueue mQueue;
	ImageLoader mImageLoader;
	List<Item> data;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Theme_AppCompat_Light_DarkActionBar);

		super.onCreate(savedInstanceState);
		mQueue = Volley.newRequestQueue(this);
		mImageLoader = new ImageLoader(mQueue, BitmapCache.getInstance(getSupportFragmentManager()));

		final VolleyListAdapter adapter = new VolleyListAdapter(getApplicationContext(), data, mImageLoader);

		String url = "http://api.eoe.cn/client/news?k=lists&pageNum=3&t=cat&cid=0";

		mQueue.add(new StringRequest(url, new Listener<String>() {

			@Override
			public void onResponse(String response) {

				try {
					JsonNode node = JsonUtil.json2node(response);
					data = JsonUtil.node2pojo(node.findValue("items"), new TypeReference<ArrayList<Item>>() {
					});
					adapter.setData(data);
					adapter.notifyDataSetChanged();
					Log.d("TIME", "Volley http end!");
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub

			}
		}));

		mQueue.start();
		Log.d("TIME", "Volley http begin!");
		setListAdapter(adapter);
	}

	public static class Item {
		String id;
		String thumbnail_url;
		String title;
		String time;
		String short_content;
		String detail_url;

		public String getId() {
			return id;
		}

		public String getThumbnail_url() {
			return thumbnail_url;
		}

		public String getTitle() {
			return title;
		}

		public String getTime() {
			return time;
		}

		public String getShort_content() {
			return short_content;
		}

		public String getDetail_url() {
			return detail_url;
		}

		public void setId(String id) {
			this.id = id;
		}

		public void setThumbnail_url(String thumbnail_url) {
			this.thumbnail_url = thumbnail_url;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public void setTime(String time) {
			this.time = time;
		}

		public void setShort_content(String short_content) {
			this.short_content = short_content;
		}

		public void setDetail_url(String detail_url) {
			this.detail_url = detail_url;
		}

	}

	public static class VolleyListAdapter extends BaseAdapter {

		private List<Item> list;
		private LayoutInflater mlinflater;
		private ImageLoader loader;

		private class ViewHolder {
			public ImageView img;
			public TextView title;
		}

		public VolleyListAdapter(Context context, List<Item> data, ImageLoader loader) {
			this.mlinflater = LayoutInflater.from(context);
			this.list = data;
			this.loader = loader;
		}

		@Override
		public int getCount() {
			if (list == null)
				return 0;
			return list.size();
		}

		public void setData(List<Item> data) {
			this.list = data;
		}

		@Override
		public Object getItem(int position) {
			return Integer.valueOf(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View view, ViewGroup viewGroup) {
			final Item news = list.get(position);
			if (view == null) {
				view = mlinflater.inflate(R.layout.news_item, null);
				ViewHolder vh = new ViewHolder();
				vh.title = (TextView) view.findViewById(R.id.textView1);
				vh.img = (ImageView) view.findViewById(R.id.imageView1);
				view.setTag(vh);
			}

			ViewHolder holder = (ViewHolder) view.getTag();
			ImageListener listener = ImageLoader.getImageListener(holder.img, android.R.drawable.ic_menu_rotate,
					android.R.drawable.ic_delete);
			loader.get(news.getThumbnail_url(), listener);

			holder.title.setText(news.getTitle());
			holder.img.setScaleType(ScaleType.CENTER_CROP);
			return view;
		}
	}

}
