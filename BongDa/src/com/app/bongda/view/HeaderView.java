package com.app.bongda.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.bongda.R;

public class HeaderView extends LinearLayout {

	public HeaderView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public HeaderView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		LayoutInflater inflater = (LayoutInflater) getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.header, this);
	}

	public void setTextHeader(int bangxephang) {
		((TextView) findViewById(R.id.textView1)).setText(bangxephang);
	}

	public void hiddenProgressbar() {
		findViewById(R.id.Button01).setVisibility(View.GONE);
	}
}