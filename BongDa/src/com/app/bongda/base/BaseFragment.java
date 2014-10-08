package com.app.bongda.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {
	private BaseFragment parent;

	public void setParent(BaseFragment parent) {
		this.parent = parent;
	}

	public BaseFragment(BaseFragment parent) {
		super();
		this.parent = parent;
	}

	public BaseFragment() {
		super();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (getLayout() != 0) {
			return inflater.inflate(getLayout(), null);
		}
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	public abstract int getLayout();

}
