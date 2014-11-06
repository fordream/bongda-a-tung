package com.app.bongda.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

@SuppressLint("ServiceCast")
public class BaseView extends LinearLayout {

	public BaseView(Context context) {
		super(context);
	}

	public BaseView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void init(int layout) {
		((LayoutInflater) getContext().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE)).inflate(layout, this);
	}
}