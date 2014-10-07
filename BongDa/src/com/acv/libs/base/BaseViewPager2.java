package com.acv.libs.base;

import java.util.List;

import org.acv.bynal.views.HomeItemView;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import app.bynal.woman.R;

import com.acv.libs.viewpagerindicator.CirclePageIndicator;

//com.acv.libs.base.BaseViewPager
public class BaseViewPager2 extends LinearLayout implements Runnable {
	private ViewPager viewPager;
	BaseViewPagerAdapter basePagerAdapter;
//	CirclePageIndicator circlePageIndicator;

	public BaseViewPager2(Context context) {
		super(context);
		init();
		post(this);
	}
	@Override
	public void run() {
//		VNPResize vnpResize = VNPResize.getInstance();
//		vnpResize.resizeSacle(viewPager, LayoutParams.MATCH_PARENT, 230);
	}
	public BaseViewPager2(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
		post(this);
	}

	private void init() {
		LayoutInflater inflater = (LayoutInflater) getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.basepager2, this);
//		circlePageIndicator = (CirclePageIndicator) findViewById(R.id.baseviewpager_indicator2);
		viewPager = (ViewPager) findViewById(R.id.baseviewpager_pager2);

		basePagerAdapter = new BaseViewPagerAdapter() {
			@Override
			public BaseView getView(Context context, Object data) {
				return new HomeItemView(context);
			}
		};
		updateUi();
	}

	public void setListData(List<Object> list) {
		basePagerAdapter.setListData(list);
		updateUi();
	}

	private void updateUi() {
		viewPager.setAdapter(basePagerAdapter);
//		circlePageIndicator.setViewPager(viewPager);
		viewPager.getAdapter().notifyDataSetChanged();
	}
}