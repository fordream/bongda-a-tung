package com.app.bongda.hander;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public abstract class LiveScoreHander extends Handler {
	public static boolean DESTROY = false;
	private boolean needStop = false;

	public void setNeedStop(boolean needStop) {
		this.needStop = needStop;
	}

	public static final long TIME = 30 * 1000;

	@Override
	public final void dispatchMessage(Message msg) {
		super.dispatchMessage(msg);
		if (!needStop) {
			if (!DESTROY) {
				Log.e("REFRESH", "REFRESH");
				executeLiveScore();
				sendEmptyMessageDelayed(0, TIME);
			}
		}
	}

	public abstract void executeLiveScore();
}
