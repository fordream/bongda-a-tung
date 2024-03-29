package com.app.bongda.fragment;

import com.app.bongda.R;
import com.app.bongda.inter.CallBackListenner;
import com.app.bongda.service.BongDaServiceManager;
import com.app.bongda.view.HeaderView;
import com.app.bongda.view.adapter.cursor.LiveScoreLikeCusorAdapter;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class LiveScoreLikeFragment extends Fragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	private static OnItemClickListener onItemClickListener;
	private static CallBackListenner callBackListenner;

	public LiveScoreLikeFragment(OnItemClickListener onItemClickListener, CallBackListenner callBackListenner) {
		super();
		this.callBackListenner = callBackListenner;
		this.onItemClickListener = onItemClickListener;
	}

	private static View view;
	static ListView listView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (inflater == null) {
			inflater = (LayoutInflater) container.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		if (view == null) {
			view = inflater.inflate(R.layout.livesocre, null);
		} else {
			return view;
		}

		HeaderView headerView = (HeaderView) view.findViewById(R.id.headerView1);
		headerView.setTextHeader(R.string.tranquantam);
		listView = (ListView) view.findViewById(R.id.listView1);
		listView.setOnItemClickListener(onItemClickListener);
		views_err = (TextView) view.findViewById(R.id.error_txt);
		reloadData();
		return view;
	}

	private static LiveScoreLikeCusorAdapter likeCusorAdapter;
	static TextView views_err;
	public static void reloadData() {
		try {
			int first = listView.getFirstVisiblePosition();
			Cursor c = BongDaServiceManager.getInstance().getBongDaService().getDBManager().liveScoreQueryLiked();
			likeCusorAdapter = new LiveScoreLikeCusorAdapter(view.getContext(), c, false, onItemClickListener, callBackListenner);
			listView.setAdapter(likeCusorAdapter);
			views_err.setVisibility(View.GONE);
			if(likeCusorAdapter.getCount() == 0){
				views_err.setVisibility(View.VISIBLE);
				views_err.setText(listView.getContext().getResources().getString(R.string.khongcodoiyeuthich));
			}
			listView.setSelection(first);
			likeCusorAdapter.notifyDataSetChanged();
		} catch (Exception exception) {
		}
	}
}