package com.app.bongda.vl;

import android.app.Activity;
import android.os.Bundle;

import com.app.bongda.R;
import com.app.bongda.view.BongDaViewPager;

public class X2VLayoutActivity extends Activity {
	private BongDaViewPager bongDaViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vlayout);
		bongDaViewPager = (BongDaViewPager) findViewById(R.id.bongdaviewpager);
		// bongDaViewPager.addFragement(new CountryFragment(null));
	}

	@Override
	public void onBackPressed() {

		if (bongDaViewPager.onBackPressed()) {
			return;
		}
		super.onBackPressed();
	}
}
