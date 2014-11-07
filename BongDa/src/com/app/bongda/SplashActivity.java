package com.app.bongda;

import android.app.Activity;
import android.app.TabActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

import com.app.bongda.util.ByUtils;
import com.app.bongda.view.IndivicatorView;
import com.app.bongda.vl.X1VLayoutActivity;
import com.app.bongda.vl.X2VLayoutActivity;
import com.app.bongda.vl.X3VLayoutActivity;
import com.app.bongda.vl.X4VLayoutActivity;

public class SplashActivity extends TabActivity implements OnTabChangeListener {
	private static final String CHANGETAG = "CHANGETAG";

	public static final void changTab(int index, Context context) {
		Intent intent = new Intent(CHANGETAG);
		intent.putExtra("index", index);
		context.sendBroadcast(intent);
	}

	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(R.anim.nothing, R.anim.top_to_bot);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabexampleslide);
		getTabHost().setOnTabChangedListener(this);
		getTabHost().setVisibility(View.VISIBLE);
		
		if (ByUtils.USEGROUPVIEW) {
			addTab(X1VLayoutActivity.class, "Home", "Home", R.drawable.menu_1);
			addTab(X2VLayoutActivity.class, "M1", "M1", R.drawable.menu_2);
			addTab(X3VLayoutActivity.class, "M2", "M2", R.drawable.menu_3);
			addTab(X4VLayoutActivity.class, "M3", "M3", R.drawable.menu_4);
		} else {
			// addTab(X1Activity.class, "Home", "Home", R.drawable.menu_1);
			// addTab(X2Activity.class, "M1", "M1", R.drawable.menu_2);
			// addTab(X3Activity.class, "M2", "M2", R.drawable.menu_3);
			// addTab(X4Activity.class, "M3", "M3", R.drawable.menu_4);
		}

		addTab(X5Activity.class, "M4", "M4", R.drawable.menu_5);
	}

	@Override
	protected void onResume() {
		super.onResume();
		registerReceiver(broadcastReceiver, new IntentFilter(CHANGETAG));
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			overridePendingTransition(R.anim.nothing, R.anim.top_to_bot);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	};

	@Override
	protected void onPause() {
		super.onPause();
		unregisterReceiver(broadcastReceiver);
	}

	private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			int index = intent.getIntExtra("index", 0);

			if (index <= 3) {
				getTabHost().setCurrentTab(index);
			} else if (index == 7) {
				// phong do cac doi
				// country -> cac giai dau -> phong do
				startActivity(new Intent(SplashActivity.this, PhongDoCacDoiActivity.class));
			} else if (index == 6) {
				// May tinh du doan
				// chua co
				startActivity(new Intent(SplashActivity.this, MayTinhDuDoanActivity.class));
			} else if (index == 5) {
				// nhan dinh cua chuyen gia
				// crash
				startActivity(new Intent(SplashActivity.this, NhanDinhChuyenGiaActivity.class));
			} else if (index == 4) {
				// game du doan
				// chua co
				startActivity(new Intent(SplashActivity.this, GameDuDoanActivity.class));

			}
		}
	};

	@Override
	public void onTabChanged(String tabId) {

	}

	public void addTab(Class<?> activity, String tabSpect, String indicator, int type) {
		TabHost tabHost = getTabHost();
		TabSpec firstTabSpec = tabHost.newTabSpec(tabSpect);
		Intent intent = new Intent(this, activity);
		intent.putExtra("type", type);
		firstTabSpec.setIndicator(new IndivicatorView(this, type)).setContent(intent);
		tabHost.addTab(firstTabSpec);
	}

	protected Context getContext() {
		return this;
	}

	protected Activity getActivity() {
		return this;
	}

	@Override
	public void startActivity(Intent intent) {
		startActivityForResult(intent, ByUtils.REQUEST);
	}

	@Override
	public void startActivityForResult(Intent intent, int requestCode) {
		super.startActivityForResult(intent, ByUtils.REQUEST);
	}

}
