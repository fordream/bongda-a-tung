package com.app.bongda;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.app.bongda.callback.APICaller;
import com.app.bongda.callback.APICaller.ICallbackAPI;
import com.app.bongda.util.ByUtils;
import com.app.bongda.util.CommonAndroid;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;


public class X1Activity extends Activity {
	Context context;
	ICallbackAPI callbackAPI;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		setContentView(R.layout.x1);
		callbackAPI = new ICallbackAPI() {
			@Override
			public void onSuccess(String response) {
				String string_temp = CommonAndroid.parseXMLAction(response);
				CommonAndroid.showDialog(context, "data:" + string_temp , null);
				
				try {
					JSONObject jsonObject = new JSONObject(string_temp);
					
				} catch (JSONException e) {
//					CommonAndroid.showDialog(context, e.getMessage() , null);
				}
				
			}

			@Override
			public void onError(String message) {
			}
		};
		
		CallAPISearch();
	}
	
	public void CallAPISearch(){
		new APICaller(this).callApi("", true,
				callbackAPI, ByUtils.wsFootBall_Quocgia);
		
	}
}
