package com.app.bongda.base;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.bongda.callback.APICaller;
import com.app.bongda.callback.APICaller.ICallbackAPI;
import com.app.bongda.service.BongDaServiceManager;

public abstract class BaseFragment extends Fragment {
	private long currentTime = System.currentTimeMillis();

	public long getCurrentTime() {
		return currentTime;
	}

	public BaseFragment() {
		super();
	}

	private int time = 0;

//	private View view;
//
//	@Override
//	public View getView() {
//		if (super.getView() == null) {
//			return view;
//		}
//
//		return super.getView();
//	}

	@Override
	final public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
//		if (view != null) {
//			return view;
//		}

		View view = super.onCreateView(inflater, container, savedInstanceState);
		if (getLayout() != 0) {
			view = inflater.inflate(getLayout(), null);
		}

		onInitCreateView(view);
		if (time == 0) {
			onInitData();
			time++;
		}
		return view;
	}

//	private Handler handler;
//	private APICaller apiCaller;
//
//	final public void onCallReloadData() {
//
//		if (apiCaller != null && !apiCaller.isOnProgess()) {
//			apiCaller.callApi("", false, new ICallbackAPI() {
//
//				@Override
//				public void onSuccess(String response) {
//					try {
//						onReloadSuccess(response);
//					} catch (Exception exception) {
//
//					}
//				}
//
//				@Override
//				public void onError(String message) {
//					try {
//						onReloadError(message);
//					} catch (Exception exception) {
//
//					}
//				}
//			}, getCallReloadDataStr());
//		}
//	}
//
//	public void onReloadSuccess(String response) {
//		Log.e("onCallReloadData", "onReloadSuccess" + getClass().getName());
//	}
//
//	public void onReloadError(String message) {
//		Log.e("onCallReloadData", "onReloadSuccess" + getClass().getName());
//	}
//
//	public String getCallReloadDataStr() {
//		return "";
//	}

//	@Override
//	public void onResume() {
//		super.onResume();
//
//		apiCaller = new APICaller(getActivity());
//
//		handler = new Handler() {
//			@Override
//			public void dispatchMessage(Message msg) {
//				long time = BongDaServiceManager.getInstance()
//						.getBongDaService().getReload();
//				if (time != -1) {
//					onCallReloadData();
//				}
//
//				startReload();
//			}
//		};
//		startReload();
//	}

//	@Override
//	public void onPause() {
//		super.onPause();
//		if (handler != null) {
//			handler.removeMessages(0);
//		}
//	}
//
//	final private void startReload() {
//		long time = BongDaServiceManager.getInstance().getBongDaService()
//				.getReload();
//
//		if (time == -1) {
//			time = 5000;
//		}
//
//		if (handler != null) {
//			handler.sendEmptyMessageDelayed(0, time);
//		}
//	}

	public abstract void onInitCreateView(View view);

	public abstract void onInitData();

	public abstract int getLayout();

}
