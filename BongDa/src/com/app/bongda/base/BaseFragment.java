package com.app.bongda.base;

import android.content.Context;
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
import com.app.bongda.util.ByUtils;

public abstract class BaseFragment extends Fragment {
	private long currentTime = System.currentTimeMillis();

	public long getCurrentTime() {
		return currentTime;
	}

	public BaseFragment() {
		super();
	}

	private int time = 0;

	// private View view;
	//
	// @Override
	// public View getView() {
	// if (super.getView() == null) {
	// return view;
	// }
	//
	// return super.getView();
	// }
	BaseView view;

	@Override
	final public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		if (ByUtils.USEGROUPVIEW) {
			if (view != null) {
				return view;
			}
		}

		view = new BaseView(container.getContext());
		if (getLayout() != 0) {
			view.init(getLayout());
		}
		onInitCreateView(view);
		if (time == 0) {
			onInitData();
			time++;
		}
		return view;
	}

	public abstract void onInitCreateView(View view);

	public abstract void onInitData();

	public abstract int getLayout();

}
