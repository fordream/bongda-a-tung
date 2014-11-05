package com.app.bongda.view;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
//com.app.bongda.view.BongDaViewPager
public class BongDaViewPager extends ViewPager {
	private BaseViewOfFaragmentPagerAdapter adapter;

	public BongDaViewPager(Context context) {
		super(context);
		init();

	}

	public BongDaViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		adapter = new BaseViewOfFaragmentPagerAdapter(this);
		
		
	}

	public void addFragement(Fragment fragment) {
		adapter.addFragement(fragment);
	}
}