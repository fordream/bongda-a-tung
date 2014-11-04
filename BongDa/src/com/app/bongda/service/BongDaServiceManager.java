package com.app.bongda.service;

import com.vnp.core.datastore.database.GiaiDauTable;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.os.IBinder;
import android.util.Log;

public class BongDaServiceManager {
	public interface BongDaServiceManagerListener {
		public void onFail();

		public void onSuccess();

		public void onDisconnected();
	}

	private static BongDaServiceManager instance = new BongDaServiceManager();

	private BongDaServiceManager() {

	}

	public static BongDaServiceManager getInstance() {
		return instance;
	}

	private Context mContext;

	public void init(Context context) {
		if (mContext == null) {
			mContext = context;
		}
	}

	private BongDaService bongDaService;

	public BongDaService getBongDaService() {
		return bongDaService;
	}

	private ServiceConnection conn;

	// = new ServiceConnection() {
	//
	// @Override
	// public void onServiceDisconnected(ComponentName name) {
	// Log.e("MSERVICE", "disconnect");
	// bongDaService = null;
	// }
	//
	// @Override
	// public void onServiceConnected(ComponentName name, IBinder service) {
	// BongDaBinder bongDaBinder = (BongDaBinder) service;
	// Log.e("MSERVICE", "connect to service");
	// bongDaService = bongDaBinder.getBongDaService();
	// }
	// };

	public void onResume(final BongDaServiceManagerListener where) {
		Log.e("MSERVICE", "start bin to service");
		if (getBongDaService() == null) {
			conn = new ServiceConnection() {

				@Override
				public void onServiceDisconnected(ComponentName name) {
					Log.e("MSERVICE", "disconnect");
					bongDaService = null;
					where.onDisconnected();
				}

				@Override
				public void onServiceConnected(ComponentName name,
						IBinder service) {
					BongDaBinder bongDaBinder = (BongDaBinder) service;
					Log.e("MSERVICE", "connect to service");
					bongDaService = bongDaBinder.getBongDaService();
					where.onSuccess();
				}
			};
			Intent service = new Intent(mContext, BongDaService.class);
			mContext.bindService(service, conn, Context.BIND_AUTO_CREATE);
		} else {
			where.onSuccess();
		}
	}

	public void onResume() {
		Log.e("MSERVICE", "start bin to service");
		if (getBongDaService() == null) {
			conn = new ServiceConnection() {

				@Override
				public void onServiceDisconnected(ComponentName name) {
					Log.e("MSERVICE", "disconnect");
					bongDaService = null;
				}

				@Override
				public void onServiceConnected(ComponentName name,
						IBinder service) {
					BongDaBinder bongDaBinder = (BongDaBinder) service;
					Log.e("MSERVICE", "connect to service");
					bongDaService = bongDaBinder.getBongDaService();
				}
			};
			Intent service = new Intent(mContext, BongDaService.class);
			mContext.bindService(service, conn, Context.BIND_AUTO_CREATE);
		} else {
		}
	}

	public void onPause() {
		mContext.unbindService(conn);
	}

	public void startLoadContentBase() {
		if (getBongDaService() != null) {
			getBongDaService().startLoadContentBase();
		}
	}

	public GiaiDauTable getGiaiDauTable(String idmagiaidau) {
		if (getBongDaService() != null)
			return getBongDaService().getGiaiDauTable(idmagiaidau);
		return new GiaiDauTable();
	}

	public Cursor query(String tableName, String where) {
		if (getBongDaService() != null)
			return getBongDaService().query(tableName, where);

		return null;
	}
}