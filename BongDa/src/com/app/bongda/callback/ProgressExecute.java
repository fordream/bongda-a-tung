package com.app.bongda.callback;

import android.content.Context;

public abstract class ProgressExecute extends ExeCallBack {

	private String response;

	private Context context;

	public Context getContext() {
		return context;
	}

	public ProgressExecute(String response, Context context) {
		this.response = response;
		this.context = context;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public void executeAsynCallBack() {
		super.executeAsynCallBack(new CallBack() {

			@Override
			public void onCallBack(Object object) {
				onProgressSucess();
			}

			@Override
			public Object execute() {
				onProgress(response);
				return null;
			}
		});
	}

	public abstract void onProgress(String response);

	public abstract void onProgressSucess();

	public void onProgressStartFail() {

	}
}