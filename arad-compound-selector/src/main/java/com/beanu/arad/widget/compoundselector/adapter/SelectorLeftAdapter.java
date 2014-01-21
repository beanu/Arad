package com.beanu.arad.widget.compoundselector.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.beanu.arad.R;

import java.util.List;

public class SelectorLeftAdapter extends BaseAdapter {
	Context context;
	private List<String> data;
	LayoutInflater inflater;

	public SelectorLeftAdapter(Context context, List<String> data) {
		this.context = context;
		this.data = data;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.acs_selector_list_left_item, null);
			holder = new ViewHolder();
			holder.textView = (TextView) convertView.findViewById(R.id.selector_list_left_item_textview);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.textView.setText(data.get(position));
		return convertView;
	}

	public static class ViewHolder {
		public TextView textView;
	}

}
