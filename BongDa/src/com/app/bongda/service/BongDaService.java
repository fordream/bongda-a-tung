package com.app.bongda.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;

public class BongDaService extends Service {
	private BongDaBinder bongDaBinder;
	private SharedPreferences preferencesSetting;

	@Override
	public void onCreate() {
		super.onCreate();
		preferencesSetting = getSharedPreferences("SettingXml", 0);
		bongDaBinder = new BongDaBinder(this);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return bongDaBinder;
	}

	public boolean getReload() {
		return preferencesSetting.getBoolean("reload", false);
	}

	public void setReload(boolean isReload) {
		preferencesSetting.edit().putBoolean("reload", isReload).commit();
	}
}