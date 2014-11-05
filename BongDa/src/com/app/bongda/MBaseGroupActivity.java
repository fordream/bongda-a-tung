package com.app.bongda;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.app.bongda.base.BaseGroupActivity;
import com.app.bongda.group.MActivity;
import com.app.bongda.model.BaseItem;
import com.app.bongda.util.ByUtils;

public class MBaseGroupActivity extends BaseGroupActivity {
	public enum FRAGMENT {
		BANGXEMHANG, COUNTRY, DANHSACHGIAIDAU, DUDOANKETQUA, LIVESCORE, PHONGDODOIDAU, PHONGDO, TUONGTHUATTRAN, TUOGNTHUATTRANLIVESCORE, TYLEDUDOAN
	}

	public void startActivity(FRAGMENT typeFragment, BaseItem object) {
		Bundle extras = new Bundle();
		extras.putSerializable("FRAGMENT", typeFragment);
		extras.putParcelable("BaseItem", object);
		addView(typeFragment.name(), MActivity.class, extras);
	}

	@Override
	protected void onResume() {
		super.onResume();
		registerReceiver(broadcastReceiver, new IntentFilter(
				ByUtils.ACTION.ACTION_GROUP_CHANGE));
	}

	@Override
	protected void onPause() {
		super.onPause();
		unregisterReceiver(broadcastReceiver);
	}

	private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			FRAGMENT typeFragment = (FRAGMENT) intent
					.getSerializableExtra("FRAGMENT");
			BaseItem baseItem = (BaseItem) intent
					.getParcelableExtra("BaseItem");

			startActivity(typeFragment, baseItem);
		}
	};
}