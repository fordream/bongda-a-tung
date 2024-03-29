package com.app.bongda.util;

import java.util.ArrayList;
import java.util.Collections;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v4.app.FragmentActivity;

public class CommonUtil {
	public static ArrayList<String> listQuanTam = new ArrayList<String>();

	public CommonUtil(String abc) {
	}

	public static void savedata(Context activity) {
		SharedPreferences pref = activity.getApplicationContext().getSharedPreferences("FavoriteList2", 0);
		Editor editor = pref.edit();
		StringBuilder csvList = new StringBuilder();
		for (int i = 0; i < listQuanTam.size(); i++) {
			csvList.append(listQuanTam.get(i));
			csvList.append(",");
		}
		editor.putString("FList2", csvList.toString());
		editor.commit();
	}

	public static void getdata(Context activity) {
		try {
			SharedPreferences pref = activity.getApplicationContext().getSharedPreferences("FavoriteList2", 0);
			String csvList = pref.getString("FList2", null);
			String[] items = csvList.split(",");
			listQuanTam = new ArrayList<String>();
			listQuanTam.clear();
			for (int i = 0; i < items.length; i++) {
				String temp = items[i].toString();
				if(!listQuanTam.contains(temp) && !temp.equalsIgnoreCase("")){
					listQuanTam.add(items[i].toString());
				}
				
			}
			Collections.sort(listQuanTam);
		} catch (Exception exception) {

		}
	}

	public static void savedata(Context context, String key, String value) {
		SharedPreferences pref = context.getSharedPreferences("Data", 0);
		Editor editor = pref.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public static String getdata(Context activity, String key) {
		String values = null;
		try {
			SharedPreferences pref = activity.getApplicationContext().getSharedPreferences("Data", 0);
			values = pref.getString(key, null);
			return values;
		} catch (Exception exception) {

		}
		return values;
	}

}
