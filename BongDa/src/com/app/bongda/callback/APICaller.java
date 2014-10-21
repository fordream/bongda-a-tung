package com.app.bongda.callback;

import java.util.Map;
import java.util.Set;

import com.app.bongda.R;

import android.content.Context;

public class APICaller {
	private boolean isOnProgess = false;

	public boolean isOnProgess() {
		return isOnProgess;
	}

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
			final ICallbackAPI callbackAPI, String sendData) {
		isOnProgess = true;
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
				if (callbackAPI != null) {
					callbackAPI.onError(errorMessage);
				}
				isOnProgess = false;
			}

			@Override
			public void onSuccess(String response) {
				super.onSuccess(response);
				if (callbackAPI != null) {
					callbackAPI.onSuccess(response);
				}

				isOnProgess = false;
			}

		};

		if (sendData != null) {
			callBack.addParam(sendData);
		}

		exeCallBack.executeAsynCallBack(callBack);
	}
}
