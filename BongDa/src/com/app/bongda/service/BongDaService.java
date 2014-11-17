package com.app.bongda.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.app.bongda.callback.APICaller;
import com.app.bongda.callback.APICaller.ICallbackAPI;
import com.app.bongda.callback.progress.CountryProgressExecute;
import com.app.bongda.callback.CallBack;
import com.app.bongda.callback.ProgressExecute;
import com.app.bongda.util.ByUtils;
import com.app.bongda.util.CommonAndroid;
import com.vnp.core.datastore.database.CountryTable;
import com.vnp.core.datastore.database.DBManager;
import com.vnp.core.datastore.database.DoiBongTable;
import com.vnp.core.datastore.database.GiaiDauTable;

public class BongDaService extends Service {
	private BongDaBinder bongDaBinder;
	private SharedPreferences preferencesSetting;
	private Handler handler = new Handler();
	private DBManager dbManager;
	private CountryTable countryTable = new CountryTable();
	private DoiBongTable doiBongTable = new DoiBongTable();
	private GiaiDauTable giaiDauTable = new GiaiDauTable();

	@Override
	public void onCreate() {
		super.onCreate();
		dbManager = new DBManager(this);
		dbManager.open();
		preferencesSetting = getSharedPreferences("SettingXml", 0);
		bongDaBinder = new BongDaBinder(this);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		dbManager.close();
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
		// APICaller apiCaller = map.get(new Long(currentTime));
		// String format = String.format("time : %s data : %s", currentTime,
		// data);
		// Log.e("callApi", format);
		// if (apiCaller == null) {
		// Log.e("callApi", "StartCallApi");
		APICaller apiCaller = new APICaller(this);
		map.put(new Long(currentTime), apiCaller);
		apiCaller.setICallBack(callbackAPI);
		apiCaller.callApi("", false, callbackAPI, data);
		// } else {
		// }
	}

	public void callApi(long currentTime,
			final ProgressExecute progressExecute, String data) {
		// APICaller apiCaller = map.get(new Long(currentTime));
		// if (apiCaller == null) {
		APICaller apiCaller = new APICaller(this);
		map.put(new Long(currentTime), apiCaller);
		ICallbackAPI callbackAPI = new ICallbackAPI() {
			@Override
			public void onSuccess(String response) {
				progressExecute.setResponse(response);
				progressExecute.executeAsynCallBack();
			}

			@Override
			public void onError(String message) {
				progressExecute.onProgressStartFail();
			}
		};
		apiCaller.setICallBack(callbackAPI);
		apiCaller.callApi("", false, callbackAPI, data);
		// } else {
		//
		// }
	}

	/**
	 * 
	 */

	public void registerApi(final Long keyTime, final boolean needReload,
			final String data) {

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

	private List<String> idCountrys = new ArrayList<String>();
	private List<String> lIdMaGiaiDaus = new ArrayList<String>();

	public void startLoadContentBase() {

		callApi(System.currentTimeMillis(), new ICallbackAPI() {
			@Override
			public void onSuccess(final String response) {
				CountryProgressExecute countryProgressExecute = new CountryProgressExecute(
						response, null);
				countryProgressExecute.executeAsynCallBack();

				// new ProgressExecute(response, BongDaService.this) {
				// @Override
				// public void onProgress(String response) {
				// String string_temp = CommonAndroid
				// .parseXMLAction(response);
				// if (!string_temp.equalsIgnoreCase("")) {
				// try {
				// JSONArray jsonarray = new JSONArray(string_temp);
				// for (int i = 0; i < jsonarray.length(); i++) {
				// JSONObject jsonObject = jsonarray
				// .getJSONObject(i);
				// String iID_MaQuocGia = jsonObject
				// .getString("iID_MaQuocGia");
				// idCountrys.add(iID_MaQuocGia);
				//
				// ContentValues values = new ContentValues();
				// Set<String> columns = countryTable
				// .columNameS();
				// for (String column : columns) {
				// if (jsonObject.has(column)) {
				// values.put(column, jsonObject
				// .getString(column));
				// }
				// }
				// long id = dbManager.insertContry(values);
				// }
				// } catch (JSONException e) {
				// }
				// }
				//
				// }
				//
				// @Override
				// public void onProgressSucess() {
				//
				// }
				// }.executeAsynCallBack();
			}

			@Override
			public void onError(String message) {

			}
		}, ByUtils.wsFootBall_Quocgia_Live);
	}

	private void startLoadContentGiaiDauBase() {
		if (idCountrys.size() > 0) {
			final String idCountry = idCountrys.get(0);
			String ws = (ByUtils.wsFootBall_Giai_Theo_QuocGia_Live).replace(
					"quocgiaid", idCountry);

			callApi(System.currentTimeMillis(), new ICallbackAPI() {
				@Override
				public void onSuccess(String response) {

					new ProgressExecute(response, BongDaService.this) {

						@Override
						public void onProgress(String response) {
							String string_temp = CommonAndroid
									.parseXMLAction(response);
							if (!string_temp.equalsIgnoreCase("")) {
								try {
									JSONArray jsonarray = new JSONArray(
											string_temp);
									for (int i = 0; i < jsonarray.length(); i++) {
										JSONObject jsonObject = jsonarray
												.getJSONObject(i);
										String iID_MaGiai = jsonObject
												.getString("iID_MaGiai");
										lIdMaGiaiDaus.add(iID_MaGiai);

										ContentValues values = new ContentValues();
										Set<String> columns = giaiDauTable
												.columNameS();
										for (String column : columns) {
											if (jsonObject.has(column)) {
												values.put(column, jsonObject
														.getString(column));
											}
										}

										long id = dbManager
												.insertGiaiDau(values);
									}
								} catch (JSONException e) {
								}
							}
						}

						@Override
						public void onProgressSucess() {
							idCountrys.remove(idCountry);
							startLoadContentGiaiDauBase();
						}
					}.executeAsynCallBack();
				}

				@Override
				public void onError(String message) {
					idCountrys.remove(idCountry);
					startLoadContentGiaiDauBase();
				}
			}, ws);
		} else if (idCountrys.size() == 0 && lIdMaGiaiDaus.size() > 0) {
			startLoadContentDoiBong();
		}
	}

	private void startLoadContentDoiBong() {
		if (lIdMaGiaiDaus.size() > 0) {
			final String iID_MaGiai = lIdMaGiaiDaus.get(0);
			String ws = (ByUtils.wsFootBall_BangXepHang).replace(
					"bangxephangId", iID_MaGiai);
			callApi(System.currentTimeMillis(), new ICallbackAPI() {
				@Override
				public void onSuccess(String response) {
					new ProgressExecute(response, BongDaService.this) {
						@Override
						public void onProgress(String response) {
							String string_temp = CommonAndroid
									.parseXMLAction(response);
							if (!string_temp.equalsIgnoreCase("")) {
								try {
									JSONArray jsonarray = new JSONArray(
											string_temp);
									for (int i = 0; i < jsonarray.length(); i++) {
										JSONObject jsonObject = jsonarray
												.getJSONObject(i);
										ContentValues values = new ContentValues();
										Set<String> columns = doiBongTable
												.columNameS();
										for (String column : columns) {
											if (jsonObject.has(column)) {
												values.put(column, jsonObject
														.getString(column));
											}
										}

										dbManager.insertDoiBong(values);
									}
								} catch (Exception exception) {
								}
							}
						}

						@Override
						public void onProgressSucess() {
							lIdMaGiaiDaus.remove(iID_MaGiai);
							startLoadContentDoiBong();
						}
					}.executeAsynCallBack();

				}

				@Override
				public void onError(String message) {
					lIdMaGiaiDaus.remove(iID_MaGiai);
					startLoadContentDoiBong();
				}
			}, ws);
		}
	}

	public GiaiDauTable getGiaiDauTable(String idmagiaidau) {
		return dbManager.getGiaiDauTable(idmagiaidau);
	}

	public Cursor query(String tableName, String where) {
		return dbManager.query(tableName, where);
	}

	public void insert(String tableName, ContentValues contentValues,
			String where) {
		dbManager.inset(tableName, contentValues, where);
	}

	public DBManager getDBManager() {
		return dbManager;
	}
}