package com.app.bongda;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

public class SplashActivity extends TabActivity implements OnTabChangeListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabexampleslide);
		getTabHost().setOnTabChangedListener(this);

		addTab(HomeActivity.class, "Home", "Home", 0);

	}

	@Override
	public void onTabChanged(String tabId) {

	}

	public void addTab(Class<?> activity, String tabSpect, String indicator,
			int type) {
		TabHost tabHost = getTabHost();
		TabSpec firstTabSpec = tabHost.newTabSpec(tabSpect);
		Intent intent = new Intent(this, activity);
		intent.putExtra("type", type);
		firstTabSpec.setIndicator(indicator).setContent(intent);
		tabHost.addTab(firstTabSpec);
	}

	public void addTab(Class<?> activity, String tabSpect, View indicator) {
		TabHost tabHost = getTabHost();
		TabSpec firstTabSpec = tabHost.newTabSpec(tabSpect);
		firstTabSpec.setIndicator(indicator).setContent(
				new Intent(this, activity));
		tabHost.addTab(firstTabSpec);
	}

	protected Context getContext() {
		return this;
	}

	protected Activity getActivity() {
		return this;
	}

}
