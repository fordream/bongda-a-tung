package com.app.bongda.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.app.bongda.R;

public class PhongDodoiItemTanCongPhongThuView extends LinearLayout {

	public PhongDodoiItemTanCongPhongThuView(Context context) {
		super(context);
		init();
	}

	public PhongDodoiItemTanCongPhongThuView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		LayoutInflater inflater = (LayoutInflater) getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.phongdodoi_tancong_phongthu, this);
	}

}