package com.acv.libs.base;

import java.util.List;

import org.acv.bynal.views.HomeItemView;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import app.bynal.woman.R;

import com.acv.libs.viewpagerindicator.CirclePageIndicator;

//com.acv.libs.base.BaseViewPager
public class BaseViewPager extends LinearLayout implements Runnable {
	private ViewPager viewPager;
	BaseViewPagerAdapter basePagerAdapter;

	// CirclePageIndicator circlePageIndicator;

	public BaseViewPager(Context context) {
		super(context);
		init();
		post(this);

	}

	public BaseViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
		post(this);
	}

	@Override
	public void run() {
		// VNPResize vnpResize = VNPResize.getInstance();
		// vnpResize.resizeSacle(viewPager, LayoutParams.MATCH_PARENT, 230);
	}

	private void init() {
		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.basepager, this);
		// circlePageIndicator = (CirclePageIndicator)
		// findViewById(R.id.baseviewpager_indicator);
		viewPager = (ViewPager) findViewById(R.id.baseviewpager_pager);

		basePagerAdapter = new BaseViewPagerAdapter() {
			@Override
			public BaseView getView(Context context, Object data) {
				BaseView baseView = new HomeItemView(context);
				return baseView;
			}
		};
		updateUi();
	}

	// public void setFragments(List<Fragment> fragments) {
	// basePagerAdapter.setFragments(fragments);
	// updateUi();
	// }
	public void setListData(List<Object> list) {
		basePagerAdapter.setListData(list);
		updateUi();
	}

	private void updateUi() {
		// if (supportFragmentManager != null) {
		viewPager.setAdapter(basePagerAdapter);
		// circlePageIndicator.setViewPager(viewPager);
		viewPager.getAdapter().notifyDataSetChanged();
		// }
	}

	// private FragmentManager supportFragmentManager;
	//
	// public void setSupportFragmentManager(FragmentManager
	// supportFragmentManager) {
	// this.supportFragmentManager = supportFragmentManager;
	// if (basePagerAdapter == null)
	// basePagerAdapter = new BasePagerAdapter(supportFragmentManager);
	// }
}