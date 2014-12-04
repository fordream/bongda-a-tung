/*
 * Copyright 2013 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.app.bongda;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Vibrator;
import android.provider.Settings.Secure;
import android.util.Log;

import com.app.bongda.R;
import com.app.bongda.callback.APICaller;
import com.app.bongda.callback.APICaller.ICallbackAPI;
import com.app.bongda.util.ByUtils;
import com.app.bongda.util.CommonAndroid;
import com.app.bongda.util.CommonUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

/**
 * Handling of GCM messages.
 */
public class GcmBroadcastReceiver extends BroadcastReceiver {
	static final String TAG = "GCMDemo";
	public static final int NOTIFICATION_ID = 1;
	private boolean useBrodcast = false;
	private static final String ACTION = "com.app.bongda.push.mPaserPushBroadcastReciver";

	@Override
	public void onReceive(Context context, Intent intent) {
		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(context);
		Log.e(TAGA, gcm.toString());
		String message = intent.getExtras().getString("message");
		Log.e(TAGA, message);
		NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		int icon = R.drawable.ic_launcher;
		String description = message;
		long time = System.currentTimeMillis();

		String notificationTitle = "Tyso24h";
		Notification notification = new Notification(icon, notificationTitle, time);
		notification.flags = notification.flags | Notification.FLAG_AUTO_CANCEL;
		PendingIntent contentIntent = null;

		int idPush = 0;

		Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
		// Vibrate for 300 milliseconds
		v.vibrate(300);

		Intent notificationIntent = new Intent(context, MainSplashActivity.class);
		contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
		
		notification.setLatestEventInfo(context, notificationTitle, description, contentIntent);
		mNotificationManager.notify(idPush, notification);
	}

	

	public static int getStatusPush(Context context) {
		final SharedPreferences preferences = context.getSharedPreferences(TAGA, 0);
		return preferences.getInt(TAGA, NORMAl);
	}

	public static void saveStatusPush(Context context, int eNABLE2) {
		final SharedPreferences preferences = context.getSharedPreferences(TAGA, 0);
		preferences.edit().putInt(TAGA, eNABLE2).commit();
	}

	public static int NORMAl = 0;
	public static int ENABLE = 1;
	public static int CANCEL = 2;
	static final String TAGA = "register-push";
	static ICallbackAPI callbackAPI;
	public static void register(final Context context) {

		// final SharedPreferences preferences = context.getSharedPreferences(
		// TAGA, 0);
		callbackAPI = new ICallbackAPI() {
			@Override
			public void onSuccess(String response) {
				String string_temp = CommonAndroid.parseXMLAction(response);
				if (!string_temp.equalsIgnoreCase("")) {
					if("1".equalsIgnoreCase(string_temp)){
						CommonUtil.savedata(context, "register_push", "1");
						Log.e("reg push done", string_temp + "");
					}
					
					
				}
			}

			@Override
			public void onError(String message) {
				// CommonAndroid.showDialog(getActivity(), "data3err:" +
				// message, null);
				Log.e("ERR", message);
			}
		};
		final String SENDER_ID = "430574585527";
		final GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(context);
		String register_push = CommonUtil.getdata(context , "register_push");
		
		if ( !"1".equalsIgnoreCase(register_push)){
			new AsyncTask<Void, Void, String>() {
				@Override
				protected String doInBackground(Void... _params) {
					try {
						String regid = gcm.register(SENDER_ID);
						String deviceId = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
						Log.e(TAGA, "regid:" + regid + "==::device::" + deviceId);
						String param = (ByUtils.wsFootBall_Push).replace("deviceid", deviceId);
						param.replace("tokenid", regid);
						new APICaller(context).callApi("", false, callbackAPI, param);
					} catch (IOException ex) {
	
					}
					return null;
				}
	
				@Override
				protected void onPostExecute(String msg) {
	//				LogUtils.e("ABCBCBCBC", msg + "");
				}
			}.execute(null, null, null);
		}
	}

}
