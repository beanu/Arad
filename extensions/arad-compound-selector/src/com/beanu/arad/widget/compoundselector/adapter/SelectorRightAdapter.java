package com.beanu.arad.widget.compoundselector.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.beanu.arad.widget.compoundselector.R;


public class SelectorRightAdapter extends BaseAdapter {

	Context context;
	LayoutInflater layoutInflater;
	String[][] data;
	public int leftPoition;

	public SelectorRightAdapter(Context context, String[][] data, int leftPoition) {
		this.context = context;
		this.data = data;
		layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.leftPoition = leftPoition;
	}

	public void setLeftPosition(int position) {
		this.leftPoition = position;
	}

	@Override
	public int getCount() {
		return data.length;
	}

	@Override
	public Object getItem(int position) {
		return data[leftPoition][position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.acs_selector_list_right_item, null);
			viewHolder = new ViewHolder();
			viewHolder.textView = (TextView) convertView.findViewById(R.id.selector_list_right_item_textView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.textView.setText(data[leftPoition][position]);
		return convertView;
	}

	public static class ViewHolder {
		public TextView textView;
	}

}
