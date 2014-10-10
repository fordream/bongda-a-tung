package com.app.bongda.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {

	public BaseFragment() {
		super();
	}

	private int time = 0;

	@Override
	final public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
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

	public abstract void onInitCreateView(View view);

	public abstract void onInitData();

	public abstract int getLayout();

}
