package com.app.bongda.vl;

import android.app.Activity;
import android.os.Bundle;

import com.app.bongda.MBaseGroupActivity;
import com.app.bongda.MBaseGroupActivity.FRAGMENT;
import com.app.bongda.fragment.CountryFragment;
import com.app.bongda.view.BongDaViewPager;
import com.app.bongda.R;

public class X1VLayoutActivity extends Activity {
	private BongDaViewPager bongDaViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vlayout);
		bongDaViewPager = (BongDaViewPager) findViewById(R.id.bongdaviewpager);
	}
	@Override
	public void onBackPressed() {

		if (bongDaViewPager.onBackPressed()) {
			return;
		}
		super.onBackPressed();
	}
}
