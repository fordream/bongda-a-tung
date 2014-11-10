package com.app.bongda.fragment;

import com.app.bongda.R;
import com.app.bongda.inter.CallBackListenner;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;

public class LiveScoreLikeFragment extends Fragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	private OnItemClickListener onItemClickListener;
	private CallBackListenner callBackListenner;

	public LiveScoreLikeFragment(OnItemClickListener onItemClickListener, CallBackListenner callBackListenner) {
		super();
		this.callBackListenner = callBackListenner;
		this.onItemClickListener = onItemClickListener;
	}

	private View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (inflater == null)
			inflater = (LayoutInflater) container.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		if (view == null) {
			view = inflater.inflate(R.layout.livesocre, null);
		}

		return view;
	}
}