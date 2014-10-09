package com.app.bongda.callback;

import java.util.Map;
import java.util.Set;

import com.app.bongda.R;

import android.content.Context;

public class APICaller {

	/**
	 * Call Api
	 */
	public interface ICallbackAPI {
		public void onError(String message);

		public void onSuccess(String response);
	}

	private Context context;

	public APICaller(Context mContext) {
		context = mContext;
	}

	public void callApi(final String api, boolean useDialog,
			final ICallbackAPI callbackAPI, Map<String, String> sendData) {
		ExeCallBack exeCallBack = new ExeCallBack();
		exeCallBack.setExeCallBackOption(new ExeCallBackOption(context,
				useDialog, R.string.loading, null));
		ResClientCallBack callBack = new ResClientCallBack() {

			@Override
			public String getApiName() {
				return api;
			}

			@Override
			public void onError(String errorMessage) {
				super.onError(errorMessage);
				if (callbackAPI != null)
					callbackAPI.onError(errorMessage);
			}

			@Override
			public void onSuccess(String response) {
				super.onSuccess(response);
				if (callbackAPI != null)
					callbackAPI.onSuccess(response);
			}

		};
		if (sendData != null) {
			Set<String> keys = sendData.keySet();
			for (String key : keys) {
				callBack.addParam(key, sendData.get(key));
			}
		}

		exeCallBack.executeAsynCallBack(callBack);
	}
	
	public void callApi(final String api, boolean useDialog,
			final ICallbackAPI callbackAPI,String sendData) {
		ExeCallBack exeCallBack = new ExeCallBack();
		exeCallBack.setExeCallBackOption(new ExeCallBackOption(context,
				useDialog, R.string.loading, null));
		ResClientCallBack callBack = new ResClientCallBack() {

			@Override
			public String getApiName() {
				return api;
			}

			@Override
			public void onError(String errorMessage) {
				super.onError(errorMessage);
				if (callbackAPI != null)
					callbackAPI.onError(errorMessage);
			}

			@Override
			public void onSuccess(String response) {
				super.onSuccess(response);
				if (callbackAPI != null)
					callbackAPI.onSuccess(response);
			}

		};
		if (sendData != null) {
			callBack.addParam(sendData);
		}

		exeCallBack.executeAsynCallBack(callBack);
	}
}
