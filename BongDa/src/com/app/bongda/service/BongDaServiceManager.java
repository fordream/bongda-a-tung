package com.app.bongda.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

public class BongDaServiceManager {
	private static BongDaServiceManager instance = new BongDaServiceManager();

	private BongDaServiceManager() {

	}

	public static BongDaServiceManager getInstance() {
		return instance;
	}

	private Context mContext;

	public void init(Context context) {
		mContext = context;
	}

	private BongDaService bongDaService;

	public BongDaService getBongDaService() {
		return bongDaService;
	}

	private ServiceConnection conn = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			bongDaService = null;
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			BongDaBinder bongDaBinder = (BongDaBinder) service;
			bongDaService = bongDaBinder.getBongDaService();
		}
	};

	public void onResume() {
		Intent service = new Intent(mContext, BongDaService.class);
		mContext.bindService(service, conn, Context.BIND_AUTO_CREATE);
	}

	public void onPause() {
		mContext.unbindService(conn);
	}
}
