package com.app.bongda.callback;

public abstract class CallBack {
	public CallBack() {
	}

	public abstract Object execute();

	public abstract void onCallBack(Object object);

}