package com.app.bongda.view;

import java.util.ArrayList;
import java.util.List;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;

public class BaseViewOfFaragmentPagerAdapter extends PagerAdapter {
	public void addFragement(Fragment fragment) {
		list.add(fragment);
		pager.setAdapter(BaseViewOfFaragmentPagerAdapter.this);
		notifyDataSetChanged();
		pager.setCurrentItem(getCount() - 1);
	}

	private List<Fragment> list = new ArrayList<Fragment>();
	private ViewPager pager;
	private Handler handler = new Handler() {
		public void dispatchMessage(android.os.Message msg) {
			super.dispatchMessage(msg);
			if (msg.what == 0) {
				// size = 2;
				int position = Integer.parseInt(msg.obj.toString());
				notifyDataSetChanged();
				pager.setCurrentItem(getCount() - 1);
			} else if (msg.what == 1) {
				list.remove(list.size() - 1);
				pager.setAdapter(BaseViewOfFaragmentPagerAdapter.this);
				notifyDataSetChanged();
				pager.setCurrentItem(getCount() - 1);
			}
		};
	};

	public BaseViewOfFaragmentPagerAdapter(ViewPager mpager) {
		this.pager = mpager;
		pager.setAdapter(this);
		pager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				if (arg0 < getCount() - 1) {
					Message msg = new Message();
					msg.what = 1;
					handler.sendMessageAtTime(msg, 10);
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object instantiateItem(ViewGroup collection, int position) {
		Fragment data = (Fragment) list.get(position);
		View view = data.onCreateView(null, collection, null);
		((ViewPager) collection).addView(view);
		return view;
	}

	@Override
	public void destroyItem(View collection, int position, Object view) {
		((ViewPager) collection).removeView((View) view);
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}
}