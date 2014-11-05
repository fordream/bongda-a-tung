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
import android.view.animation.AnimationUtils;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

import com.app.bongda.group.X1GroupActivity;
import com.app.bongda.group.X2GroupActivity;
import com.app.bongda.group.X3GroupActivity;
import com.app.bongda.group.X4GroupActivity;
import com.app.bongda.group.X4VLayoutActivity;
import com.app.bongda.util.ByUtils;
import com.app.bongda.view.IndivicatorView;

public class SplashActivity extends TabActivity implements OnTabChangeListener {
	private static final String CHANGETAG = "CHANGETAG";

	public static final void changTab(int index, Context context) {
		Intent intent = new Intent(CHANGETAG);
		intent.putExtra("index", index);
		context.sendBroadcast(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabexampleslide);
		getTabHost().setOnTabChangedListener(this);

		// Animation animation = AnimationUtils.loadAnimation(getContext(),
		// R.anim.splash);
		//
		// animation.setAnimationListener(new AnimationListener() {
		//
		// @Override
		// public void onAnimationStart(Animation animation) {
		//
		// }
		//
		// @Override
		// public void onAnimationRepeat(Animation animation) {
		//
		// }
		//
		// @Override
		// public void onAnimationEnd(Animation animation) {
		// //BongDaServiceManager.getInstance().startLoadContentBase();
		// getTabHost().setVisibility(View.VISIBLE);
		// getTabHost().startAnimation(
		// AnimationUtils.loadAnimation(getContext(),
		// R.anim.splash));
		// if (ByUtils.USEGROUPVIEW) {
		// addTab(X1GroupActivity.class, "Home", "Home",
		// R.drawable.menu_1);
		// addTab(X2GroupActivity.class, "M1", "M1", R.drawable.menu_2);
		// addTab(X3GroupActivity.class, "M2", "M2", R.drawable.menu_3);
		// addTab(X4GroupActivity.class, "M3", "M3", R.drawable.menu_4);
		// } else {
		// addTab(X1Activity.class, "Home", "Home", R.drawable.menu_1);
		// addTab(X2Activity.class, "M1", "M1", R.drawable.menu_2);
		// addTab(X3Activity.class, "M2", "M2", R.drawable.menu_3);
		// addTab(X4Activity.class, "M3", "M3", R.drawable.menu_4);
		// }
		//
		// addTab(X5Activity.class, "M3", "M3", R.drawable.menu_5);
		// getTabHost().setCurrentTab(4);
		// }
		// });
		// findViewById(R.id.ic_logo).startAnimation(animation);
		getTabHost().setVisibility(View.VISIBLE);
		// getTabHost().startAnimation(
		// AnimationUtils.loadAnimation(getContext(),
		// R.anim.splash));
		if (ByUtils.USEGROUPVIEW) {
			addTab(X1GroupActivity.class, "Home", "Home", R.drawable.menu_1);
			addTab(X2GroupActivity.class, "M1", "M1", R.drawable.menu_2);
			addTab(X3GroupActivity.class, "M2", "M2", R.drawable.menu_3);
			addTab(X4VLayoutActivity.class, "M3", "M3", R.drawable.menu_4);
		} else {
			addTab(X1Activity.class, "Home", "Home", R.drawable.menu_1);
			addTab(X2Activity.class, "M1", "M1", R.drawable.menu_2);
			addTab(X3Activity.class, "M2", "M2", R.drawable.menu_3);
			addTab(X4Activity.class, "M3", "M3", R.drawable.menu_4);
		}

		addTab(X5Activity.class, "M3", "M3", R.drawable.menu_5);
		// getTabHost().setCurrentTab(4);
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
				startActivity(new Intent(SplashActivity.this,
						PhongDoCacDoiActivity.class));
			} else if (index == 6) {
				// May tinh du doan
				// chua co
				startActivity(new Intent(SplashActivity.this,
						MayTinhDuDoanActivity.class));
			} else if (index == 5) {
				// nhan dinh cua chuyen gia
				// crash
				startActivity(new Intent(SplashActivity.this,
						NhanDinhChuyenGiaActivity.class));
			} else if (index == 4) {
				// game du doan
				// chua co
				startActivity(new Intent(SplashActivity.this,
						GameDuDoanActivity.class));

			}
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

	@Override
	public void startActivity(Intent intent) {
		startActivityForResult(intent, ByUtils.REQUEST);
	}

	@Override
	public void startActivityForResult(Intent intent, int requestCode) {
		super.startActivityForResult(intent, ByUtils.REQUEST);
	}

}
