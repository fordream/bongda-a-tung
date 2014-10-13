package com.app.bongda.view;

import com.app.bongda.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SettingItemView extends LinearLayout {

	public SettingItemView(Context context) {
		super(context);
		init();
	}

	private void init() {
		LayoutInflater inflater = (LayoutInflater) getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.setting_item, this);
	}

	public void updateContent(int res, int resStr) {
		findViewById(R.id.imageView1).setBackgroundResource(res);
		TextView textView = (TextView)findViewById(R.id.textView1);
		textView.setText(resStr);
		
	}
}