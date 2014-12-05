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
import com.vnp.core.datastore.database.GiaiDauTable;

public class DanhSachGiaiDauProgressExecute extends ProgressExecute {

	public DanhSachGiaiDauProgressExecute(String response, Context context) {
		super(response, context);
	}

	@Override
	public void onProgress(String response) {
		String string_temp = response == null ? "" : CommonAndroid.parseXMLAction(response);
		if (!string_temp.equalsIgnoreCase("")) {
			try {
				GiaiDauTable dauTable = new GiaiDauTable();
				JSONArray jsonarray = new JSONArray(string_temp);
				for (int i = 0; i < jsonarray.length(); i++) {
					Set<String> sets = dauTable.columNameS();
					JSONObject jsonObject = jsonarray.getJSONObject(i);
					
					ContentValues contentValues = new ContentValues();
					
					for (String colum : sets) {
						if (jsonObject.has(colum)) {
							contentValues.put(colum, jsonObject.getString(colum));
						}
					}
					String where = String.format("iID_MaGiai = '%s' and iID_MaQuocGia='%s'", contentValues.getAsString("iID_MaGiai"),contentValues.getAsString("iID_MaQuocGia"));
					
					BongDaServiceManager.getInstance().getBongDaService().insert(dauTable.getTableName(), contentValues, where);
					// new
					// GiaiDau(jsonarray.getJSONObject(i).getString("iID_MaGiai"),
					// jsonarray.getJSONObject(i).getString("sTenGiai")));
				}
			} catch (Exception e) {
			}
		}
	}

	@Override
	public void onProgressSucess() {

	}
}