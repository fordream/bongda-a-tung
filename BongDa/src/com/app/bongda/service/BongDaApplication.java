package com.app.bongda.service;

import android.app.Application;

public class BongDaApplication extends Application {

	public void onCreate() {
		super.onCreate();
		init();
	}

	public void init() {
		BongDaServiceManager.getInstance().init(this);
		//BongDaServiceManager.getInstance().onResume();
	}
}