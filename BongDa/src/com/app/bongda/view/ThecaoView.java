package com.app.bongda.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.app.bongda.R;
//com.app.bongda.view.ThecaoView
public class ThecaoView extends LinearLayout {

	public ThecaoView(Context context) {
		super(context);
		init();
	}

	public ThecaoView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.tab_thecao, this);
	}

}