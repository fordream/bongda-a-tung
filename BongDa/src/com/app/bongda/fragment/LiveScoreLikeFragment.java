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
	ListView listView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (inflater == null)
			inflater = (LayoutInflater) container.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		if (view == null) {
			view = inflater.inflate(R.layout.livesocre, null);
		} else {
			return view;
		}

		HeaderView headerView = (HeaderView) view.findViewById(R.id.headerView1);
		headerView.setTextHeader(R.string.tranquantam);
		listView = (ListView) view.findViewById(R.id.listView1);
		Cursor c = BongDaServiceManager.getInstance().getBongDaService().getDBManager().liveScoreQueryLiked();
		listView.setAdapter(new LiveScoreLikeCusorAdapter(container.getContext(), c, false, onItemClickListener, callBackListenner));

		return view;
	}
}