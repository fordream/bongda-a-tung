package com.app.bongda.view.adapter.cursor;

import com.app.bongda.R;
import com.app.bongda.base.ImageLoaderUtils;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class CountryCusorAdapter extends CursorAdapter {

	public CountryCusorAdapter(Context context, Cursor c, boolean autoRequery) {
		super(context, c, autoRequery);
	}

	public CountryCusorAdapter(Context context, Cursor c, int flags) {
		super(context, c, flags);
	}

	@Override
	public View newView(Context arg0, Cursor arg1, ViewGroup arg2) {
		View view = (View) ((LayoutInflater) arg2.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.country_item, null);
		showData(arg1, view);
		return view;
	}

	public void showData(Cursor cursor, View convertView) {
		TextView textView = (TextView) convertView.findViewById(R.id.textView1);
		textView.setText(cursor.getString(cursor.getColumnIndex("sTenQuocGia")));
		String image1 = cursor.getString(cursor.getColumnIndex("sLogo"));
		ImageLoaderUtils.getInstance(null).DisplayImage(image1, (ImageView) convertView.findViewById(R.id.imageView1));
	}

	@Override
	public void bindView(View arg0, Context arg1, Cursor arg2) {
		showData(arg2, arg0);
	}
}
