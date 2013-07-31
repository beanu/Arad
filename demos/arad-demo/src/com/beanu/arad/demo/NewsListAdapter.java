package com.beanu.arad.demo;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.beanu.arad.Arad;

/**
 * 新闻列表
 * 
 * @author beanu
 * 
 */
public class NewsListAdapter extends BaseAdapter {

	private List<Map<String, String>> list;
	private LayoutInflater mlinflater;

	private class ViewHolder {
		public ImageView img;
		public TextView title;
	}

	public NewsListAdapter(Context context, List<Map<String, String>> data) {
		this.mlinflater = LayoutInflater.from(context);
		this.list = data;
	}

	@Override
	public int getCount() {
		return list.size();
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
		final Map<String, String> news = list.get(position);
		if (view == null) {
			view = mlinflater.inflate(R.layout.news_item, null);
			ViewHolder vh = new ViewHolder();
			vh.title = (TextView) view.findViewById(R.id.textView1);
			vh.img = (ImageView) view.findViewById(R.id.imageView1);
			view.setTag(vh);
		}

		ViewHolder holder = (ViewHolder) view.getTag();
		Arad.imageLoader.display("http://192.168.1.210:8088/appserver2/" + news.get("url"), holder.img,
				R.drawable.ic_launcher);

		holder.title.setText(news.get("title"));
		holder.img.setScaleType(ScaleType.CENTER_CROP);
		return view;
	}
}
