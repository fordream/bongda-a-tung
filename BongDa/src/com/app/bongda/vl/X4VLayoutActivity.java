package com.app.bongda.vl;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.app.bongda.R;
import com.app.bongda.view.BongDaViewPager;

public class X4VLayoutActivity extends Activity {
	private BongDaViewPager bongDaViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vlayout);
		bongDaViewPager = (BongDaViewPager) findViewById(R.id.bongdaviewpager);
		Log.e("ABCDF", "aaaa");
	}

	@Override
	public void onBackPressed() {

		if (bongDaViewPager.onBackPressed()) {
			return;
		}
		super.onBackPressed();
	}

}
