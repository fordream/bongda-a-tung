package com.app.bongda.callback.progress;

import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.inputmethodservice.Keyboard.Key;
import android.util.Log;

import com.app.bongda.callback.ProgressExecute;
import com.app.bongda.service.BongDaServiceManager;
import com.app.bongda.util.CommonAndroid;
import com.vnp.core.datastore.database.CountryTable;
import com.vnp.core.datastore.database.GiaiDauTable;

public class CountryProgressExecute extends ProgressExecute {
	private String table_new = null;
	public CountryProgressExecute(String response, Context context) {
		super(response, context);
	}

	public CountryProgressExecute(String response, Context context, String table_new_) {
		super(response, context);
		this.table_new = table_new_;
	}

	@Override
	public void onProgress(String response) {
		String string_temp = CommonAndroid.parseXMLAction(response);
		CountryTable countryTable = new CountryTable();
		if (!string_temp.equalsIgnoreCase("")) {
			try {
				JSONArray jsonarray = new JSONArray(string_temp);
				for (int i = 0; i < jsonarray.length(); i++) {
					if(table_new != null){
						jsonarray.getJSONObject(i).put("stype", table_new);
					}
					
					JSONObject jsonObject = jsonarray.getJSONObject(i);
					String iID_MaQuocGia = jsonObject
							.getString("iID_MaQuocGia");
					ContentValues values = new ContentValues();
					Set<String> columns = countryTable.columNameS();
					for (String column : columns) {
						if (jsonObject.has(column)) {
							values.put(column, jsonObject.getString(column));
						}
					}
					String where = String.format("iID_MaQuocGia ='%s'",
							iID_MaQuocGia);
					String where2 = where + " and " + String.format("stype ='%s'",
							table_new);
					BongDaServiceManager.getInstance().getBongDaService().insert(countryTable.getTableName(), values, where2);
//					long id = dbManager.insertContry(values);
				}
			} catch (JSONException e) {
			}
		}
	}

	@Override
	public void onProgressSucess() {

	}
}