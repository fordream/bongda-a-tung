package com.app.bongda.service;

import com.app.bongda.util.CommonAndroid;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

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
			Log.e("MSERVICE", "disconnect");
			bongDaService = null;
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			BongDaBinder bongDaBinder = (BongDaBinder) service;
			Log.e("MSERVICE", "connect to service");
			bongDaService = bongDaBinder.getBongDaService();
		}
	};

	public void onResume() {
		Log.e("MSERVICE", "start bin to service");
		Intent service = new Intent(mContext, BongDaService.class);
		mContext.bindService(service, conn, Context.BIND_AUTO_CREATE);
	}

	public void onPause() {
		mContext.unbindService(conn);
	}
}
