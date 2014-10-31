package com.app.bongda.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;

import com.app.bongda.callback.APICaller;
import com.app.bongda.callback.APICaller.ICallbackAPI;
import com.app.bongda.callback.ProgressExecute;
import com.app.bongda.model.Country;
import com.app.bongda.model.GiaiDau;
import com.app.bongda.util.ByUtils;
import com.app.bongda.util.CommonAndroid;
import com.vnp.core.datastore.database.DBManager;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

public class BongDaService extends Service {
	private BongDaBinder bongDaBinder;
	private SharedPreferences preferencesSetting;
	private Handler handler = new Handler();
	private DBManager dbManager;

	@Override
	public void onCreate() {
		super.onCreate();
		dbManager = new DBManager(this);
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

	private List<String> idCountrys = new ArrayList<String>();
	private List<String> lIdMaGiaiDaus = new ArrayList<String>();

	public void startLoadContentBase() {
		if (idCountrys.size() > 0 || lIdMaGiaiDaus.size() > 0) {
			return;
		}

		callApi(System.currentTimeMillis(), new ICallbackAPI() {
			@Override
			public void onSuccess(final String response) {
				new ProgressExecute(response, BongDaService.this) {
					@Override
					public void onProgress(String response) {
						String string_temp = CommonAndroid.parseXMLAction(response);
						if (!string_temp.equalsIgnoreCase("")) {
							try {
								JSONArray jsonarray = new JSONArray(string_temp);
								for (int i = 0; i < jsonarray.length(); i++) {
									String iID_MaQuocGia = jsonarray.getJSONObject(i).getString("iID_MaQuocGia");
									String sTenQuocGia = jsonarray.getJSONObject(i).getString("sTenQuocGia");
									String sLogo = jsonarray.getJSONObject(i).getString("sLogo");
									ContentValues values = new ContentValues();
									values.put("iID_MaQuocGia", iID_MaQuocGia);
									values.put("sTenQuocGia", sTenQuocGia);
									values.put("sLogo", sLogo);
									long id = dbManager.insertContry(values);
									idCountrys.add(iID_MaQuocGia);
								}

								startLoadContentGiaiDauBase();
							} catch (JSONException e) {
							}
						}
					}

					@Override
					public void onProgressSucess() {

					}
				}.executeAsynCallBack();
			}

			@Override
			public void onError(String message) {

			}
		}, ByUtils.wsFootBall_Quocgia);
	}

	private void startLoadContentGiaiDauBase() {
		if (idCountrys.size() > 0) {
			final String idCountry = idCountrys.get(0);
			String ws = (ByUtils.wsFootBall_Giai_Theo_QuocGia).replace("quocgiaid", idCountry);

			callApi(System.currentTimeMillis(), new ICallbackAPI() {
				@Override
				public void onSuccess(String response) {

					new ProgressExecute(response, BongDaService.this) {

						@Override
						public void onProgress(String response) {
							String string_temp = CommonAndroid.parseXMLAction(response);
							if (!string_temp.equalsIgnoreCase("")) {
								try {
									JSONArray jsonarray = new JSONArray(string_temp);
									for (int i = 0; i < jsonarray.length(); i++) {
										String iID_MaGiai = jsonarray.getJSONObject(i).getString("iID_MaGiai");
										String sTenGiai = jsonarray.getJSONObject(i).getString("sTenGiai");

										ContentValues values = new ContentValues();
										values.put("iID_MaQuocGia", idCountry);
										values.put("iID_MaGiai", iID_MaGiai);
										values.put("sTenGiai", sTenGiai);

										lIdMaGiaiDaus.add(iID_MaGiai);
										long id = dbManager.insertGiaiDau(values);
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
			final String idMagiadau = lIdMaGiaiDaus.get(0);
			String ws = (ByUtils.wsFootBall_BangXepHang).replace("bangxephangId", idMagiadau);
			callApi(System.currentTimeMillis(), new ICallbackAPI() {
				@Override
				public void onSuccess(String response) {
					new ProgressExecute(response, BongDaService.this) {
						@Override
						public void onProgress(String response) {
							String string_temp = CommonAndroid.parseXMLAction(response);
							if (!string_temp.equalsIgnoreCase("")) {
								try {
									JSONArray jsonarray = new JSONArray(string_temp);
									for (int i = 0; i < jsonarray.length(); i++) {
										String sViTri = jsonarray.getJSONObject(i).getString("sViTri");
										String sTenDoi = jsonarray.getJSONObject(i).getString("sTenDoi");
										String sSoTranDau = jsonarray.getJSONObject(i).getString("sSoTranDau");
										String sDiem = jsonarray.getJSONObject(i).getString("sDiem");
										String sSoTranThang = jsonarray.getJSONObject(i).getString("sSoTranThang");
										String sSoTranHoa = jsonarray.getJSONObject(i).getString("sSoTranHoa");
										String sSoTranThua = jsonarray.getJSONObject(i).getString("sSoTranThua");
										String sBanThang = jsonarray.getJSONObject(i).getString("sBanThang");
										String sBanThua = jsonarray.getJSONObject(i).getString("sBanThua");
										String sHeSo = jsonarray.getJSONObject(i).getString("sHeSo");
									}
								} catch (Exception exception) {
								}
							}
						}

						@Override
						public void onProgressSucess() {
							lIdMaGiaiDaus.remove(idMagiadau);
							startLoadContentDoiBong();
						}
					}.executeAsynCallBack();

				}

				@Override
				public void onError(String message) {
					lIdMaGiaiDaus.remove(idMagiadau);
					startLoadContentDoiBong();
				}
			}, ws);
		}

	}
}