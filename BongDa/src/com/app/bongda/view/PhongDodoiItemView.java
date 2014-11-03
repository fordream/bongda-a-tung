package com.app.bongda.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.bongda.R;

public class PhongDodoiItemView extends LinearLayout {

	public PhongDodoiItemView(Context context) {
		super(context);
		init();
	}

	public PhongDodoiItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		LayoutInflater inflater = (LayoutInflater) getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.phongdodoi, this);
	}
	
	public void setText(int id,String text) {
		((TextView) findViewById(id)).setText(text);
	}

}