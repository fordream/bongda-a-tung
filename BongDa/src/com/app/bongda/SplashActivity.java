package com.app.bongda;

import android.app.Activity;
import android.app.TabActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

import com.app.bongda.view.IndivicatorView;

public class SplashActivity extends TabActivity implements OnTabChangeListener {
	private static final String CHANGETAG = "CHANGETAG";
	public static final void changTab(int index, Context context){
		Intent intent = new Intent(CHANGETAG);
		intent.putExtra("index", index);
		context.sendBroadcast(intent);
		
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabexampleslide);
		getTabHost().setOnTabChangedListener(this);

		addTab(X1Activity.class, "Home", "Home", R.drawable.menu_1);
		addTab(X2Activity.class, "M1", "M1", R.drawable.menu_2);
		addTab(X3Activity.class, "M2", "M2", R.drawable.menu_3);
		addTab(X4Activity.class, "M3", "M3", R.drawable.menu_4);
		addTab(X5Activity.class, "M3", "M3", R.drawable.menu_5);
	}

	@Override
	protected void onResume() {
		super.onResume();
		registerReceiver(broadcastReceiver, new IntentFilter(CHANGETAG));
	}

	@Override
	protected void onPause() {
		super.onPause();
		unregisterReceiver(broadcastReceiver);
	}

	private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			int index = intent.getIntExtra("index", 0);
			getTabHost().setCurrentTab(index);
		}
	};

	@Override
	public void onTabChanged(String tabId) {

	}

	public void addTab(Class<?> activity, String tabSpect, String indicator,
			int type) {
		TabHost tabHost = getTabHost();
		TabSpec firstTabSpec = tabHost.newTabSpec(tabSpect);
		Intent intent = new Intent(this, activity);
		intent.putExtra("type", type);
		firstTabSpec.setIndicator(new IndivicatorView(this, type)).setContent(
				intent);
		tabHost.addTab(firstTabSpec);
	}

	protected Context getContext() {
		return this;
	}

	protected Activity getActivity() {
		return this;
	}

}
