package com.app.bongda.callback.progress;

import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;

import com.app.bongda.callback.ProgressExecute;
import com.app.bongda.service.BongDaServiceManager;
import com.app.bongda.util.CommonAndroid;
import com.vnp.core.datastore.database.LiveScoreLikeTable;

public class LiveScorePorgressExecute extends ProgressExecute {

	public LiveScorePorgressExecute(String response, Context context) {
		super(response, context);
	}

	@Override
	public void onProgress(String response) {
		String string_temp = CommonAndroid.parseXMLAction(response);
		LiveScoreLikeTable liveScoreLikeTable = new LiveScoreLikeTable();
		if (!string_temp.equalsIgnoreCase("")) {
			try {
				JSONArray jsonarray = new JSONArray(string_temp);
				for (int i = 0; i < jsonarray.length(); i++) {
					JSONObject jsonObject = jsonarray.getJSONObject(i);
					ContentValues values = new ContentValues();
					Set<String> columns = liveScoreLikeTable.columNameS();
					for (String column : columns) {
						if (jsonObject.has(column)) {
							values.put(column, jsonObject.getString(column));
						}
					}
					BongDaServiceManager.getInstance().getBongDaService().getDBManager().liveScoreAdd(values);
				}
			} catch (JSONException e) {
			}
		}
	}

	@Override
	public void onProgressSucess() {

	}
}