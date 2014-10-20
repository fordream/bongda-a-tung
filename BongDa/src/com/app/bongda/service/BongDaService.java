package com.app.bongda.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class BongDaService extends Service {
	private BongDaBinder bongDaBinder;

	@Override
	public void onCreate() {
		super.onCreate();
	}

	public BongDaService() {
		super();
		bongDaBinder = new BongDaBinder(this);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return bongDaBinder;
	}

}
