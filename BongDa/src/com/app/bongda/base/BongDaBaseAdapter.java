package com.app.bongda.base;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class BongDaBaseAdapter extends BaseAdapter {
	private List<Object> data = new ArrayList<Object>();

	public BongDaBaseAdapter() {
		super();
	}

	public void addItem(Object string) {
		data.add(string);
	}

	public void clear() {
		data.clear();
		notifyDataSetChanged();
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
		if (convertView == null) {
			convertView = ((LayoutInflater) parent.getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
					.inflate(getLayout(), null);
		}

		showData(getItem(position), convertView);
		return convertView;
	}

	public abstract void showData(Object item, View convertView);

	public abstract int getLayout();

}
