package com.app.bongda.service;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;

import com.app.bongda.callback.APICaller;
import com.app.bongda.callback.APICaller.ICallbackAPI;
import com.app.bongda.model.Country;
import com.app.bongda.util.ByUtils;
import com.app.bongda.util.CommonAndroid;
import com.vnp.core.datastore.database.DBManager;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;

public class BongDaService extends Service {
	private BongDaBinder bongDaBinder;
	private SharedPreferences preferencesSetting;
	private Handler handler = new Handler();

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

	public void callApi(long currentTime, ICallbackAPI callbackAPI, String data) {
		APICaller apiCaller = map.get(new Long(currentTime));
		if (apiCaller == null) {
			apiCaller = new APICaller(this);
			map.put(new Long(currentTime), apiCaller);
			apiCaller.setICallBack(callbackAPI);
			apiCaller.callApi("", false, callbackAPI, data);
		} else {
		}
	}

	/**
	 * 
	 */

	public void registerApi(final Long keyTime, final boolean needReload, final String data) {

		// save to data

		// push to start new api

		if (!map.containsKey(keyTime)) {
			APICaller apiCaller = new APICaller(this);

			apiCaller.callApi("", false, new ICallbackAPI() {

				@Override
				public void onSuccess(String response) {

				}

				@Override
				public void onError(String message) {

				}
			}, data);
		}
	}

	public void unRegisterApi(Long keyTime) {
		if (map.containsKey(keyTime)) {
			map.remove(keyTime);
		}
	}

	/**
	 * Class Store
	 */

	private class StoreApi {
		private Long keyTime;
		private boolean needReload;
		private String data;
		private APICaller.ICallbackAPI callbackAPI;

		public Long getKeyTime() {
			return keyTime;
		}

		public void setKeyTime(Long keyTime) {
			this.keyTime = keyTime;
		}

		public boolean isNeedReload() {
			return needReload;
		}

		public void setNeedReload(boolean needReload) {
			this.needReload = needReload;
		}

		public String getData() {
			return data;
		}

		public void setData(String data) {
			this.data = data;
		}

		public APICaller.ICallbackAPI getCallbackAPI() {
			return callbackAPI;
		}

		public void setCallbackAPI(APICaller.ICallbackAPI callbackAPI) {
			this.callbackAPI = callbackAPI;
		}

	}

	public void startLoadContentBase() {
		callApi(System.currentTimeMillis(), new ICallbackAPI() {
			@Override
			public void onSuccess(final String response) {
				new Thread() {
					public void run() {
						String string_temp = CommonAndroid.parseXMLAction(response);
						if (!string_temp.equalsIgnoreCase("")) {
							try {
								JSONArray jsonarray = new JSONArray(string_temp);
								for (int i = 0; i < jsonarray.length(); i++) {
									String iID_MaQuocGia = jsonarray.getJSONObject(i).getString("iID_MaQuocGia");
									String sTenQuocGia = jsonarray.getJSONObject(i).getString("sTenQuocGia");
									String sLogo = jsonarray.getJSONObject(i).getString("sLogo");
								}
							} catch (JSONException e) {
							}
						}
					};
				}.start();
			}

			@Override
			public void onError(String message) {

			}
		}, ByUtils.wsFootBall_Quocgia);
	}
}