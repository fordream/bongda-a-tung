package com.acv.libs.base;

import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import app.bynal.woman.R;

public class MBaseActivity extends FragmentActivity {
	private Handler handler = new Handler();

	public void changeFragemt(final BaseFragment rFragment) {
//		handler.postDelayed(new Runnable() {
//
//			@Override
//			public void run() {
				FragmentManager fragmentManager = getSupportFragmentManager();
				FragmentTransaction ft = fragmentManager.beginTransaction();
				ft.replace(R.id.mainhomeframe, rFragment);
				ft.commit();
				HeaderOption headerOption = rFragment.getHeaderOption();
//			}
//		}, MenuSlideView.TIMECALLMENU);

	}
}
