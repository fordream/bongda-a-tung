package com.app.bongda.callback;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import android.util.Log;

import com.app.bongda.util.ByUtils;

public abstract class ResClientCallBack extends CallBack {
	private Map<String, String> maps = new HashMap<String, String>();
	private String ParamInfo = "";

	public final void addParam(String name, String value) {
		maps.put(name, value);
	}
	
	public final void addParam(String value) {
		ParamInfo = value;
	}

	public RestClient.RequestMethod getMedthod() {
		return RestClient.RequestMethod.POST;
	}

	public abstract String getApiName();

	@Override
	public Object execute() {
		RestClient client = new RestClient(ByUtils.BASESERVER + getApiName());
		client.addParam(ParamInfo);
		Set<String> set = maps.keySet();
		for (String key : set) {
			client.addParam(key, maps.get(key));
			Log.e("ResClientCallBack", key + " : " + maps.get(key));
		}
		try {
			client.execute(getMedthod());
		} catch (Exception e) {
		}

		return client;
	}

	@Override
	public void onCallBack(Object object) {
		RestClient client = (RestClient) object;
		if (client.getResponseCode() == 200) {
			onSuccess(client.getResponse());
		} else {
			onError(client.getErrorMessage());

		}
	}

	public void onError(String errorMessage) {

	}

	public void onSuccess(String response) {

	}
}