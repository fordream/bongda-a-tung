package com.app.bongda.view;

import com.app.bongda.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class IndivicatorView extends LinearLayout {

	private int res;
	public IndivicatorView(Context context, int res) {
		super(context);
		this.res = res;
		init();
	}

	public IndivicatorView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public IndivicatorView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.indivicator, this);
		findViewById(R.id.imageView1).setBackgroundResource(res);
	}
}