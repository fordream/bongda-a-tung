package com.app.bongda.service;

import java.util.HashMap;
import java.util.Map;

import com.app.bongda.callback.APICaller;
import com.app.bongda.callback.APICaller.ICallbackAPI;

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

	public long getReload() {
		return preferencesSetting.getLong("mreload", -1);
	}

	public void setReload(long isReload) {
		preferencesSetting.edit().putLong("mreload", isReload).commit();
	}

	private Map<Long, APICaller> map = new HashMap<Long, APICaller>();

	public void callApi(long currentTime, ICallbackAPI callbackAPI, String wsfootballQuocgia) {
		APICaller apiCaller = map.get(new Long(currentTime));
		if (apiCaller == null) {
			apiCaller = new APICaller(this);
			map.put(new Long(currentTime), apiCaller);
			apiCaller.setICallBack(callbackAPI);
			apiCaller.callApi("", false, callbackAPI, wsfootballQuocgia);
		} else {
		}
	}
}