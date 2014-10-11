package com.app.bongda.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.app.bongda.R;
import com.app.bongda.base.BaseFragment;
import com.app.bongda.base.BongDaBaseAdapter;
import com.app.bongda.model.LiveScore;
import com.app.bongda.model.PhongDo;
import com.app.bongda.view.HeaderView;

public class LiveScoreFragment extends BaseFragment {
	OnItemClickListener onItemClickListener;

	public LiveScoreFragment(OnItemClickListener onItemClickListener) {
		super();
		this.onItemClickListener = onItemClickListener;
	}

	private CountryAdapter countryAdapter = new CountryAdapter();

	private class CountryAdapter extends BongDaBaseAdapter {

		@Override
		public int getLayout() {
			return R.layout.livescore_item;
		}

		@Override
		public void showData(Object item, View convertView) {
			LiveScore liveScore = (LiveScore)item;
			convertView.findViewById(R.id.livescore_header).setVisibility(View.GONE);
			convertView.findViewById(R.id.livescore_main).setVisibility(View.GONE);
			
			if(liveScore.isHeader()){
				convertView.findViewById(R.id.livescore_header).setVisibility(View.VISIBLE);
			}else{
				convertView.findViewById(R.id.livescore_main).setVisibility(View.VISIBLE);
			}
			
			setText(convertView,R.id.textView1, liveScore.getName());
			
			setText(convertView,R.id.TextView01, liveScore.getTime());
			setText(convertView,R.id.TextView02, liveScore.getName());
			setText(convertView,R.id.TextView023, liveScore.getName2());
			setText(convertView,R.id.tv1, liveScore.getDate());
		}

	}

	@Override
	public int getLayout() {
		return R.layout.livesocre;
	}

	@Override
	public void onInitCreateView(View view) {
		/**
		 * init header view
		 */
		HeaderView headerView = (HeaderView) view
				.findViewById(R.id.headerView1);
		headerView.setTextHeader(R.string.livescore);
		/** init data */
		ListView listView = (ListView) view.findViewById(R.id.listView1);
		listView.setOnItemClickListener(onItemClickListener);

		listView.setAdapter(countryAdapter);
	}

	@Override
	public void onInitData() {
		countryAdapter.addItem(new LiveScore(true,"", "Eanglish", "ManCity", "11/10","4:10"));
		countryAdapter.addItem(new LiveScore(false,"", "MU", "ManCity", "11/10","4:10"));
		countryAdapter.addItem(new LiveScore(false,"", "MU", "ManCity", "11/10","4:10"));
		countryAdapter.addItem(new LiveScore(false,"", "MU", "ManCity", "11/10","4:10"));
		countryAdapter.addItem(new LiveScore(false,"", "MU", "ManCity", "11/10","4:10"));
		
		countryAdapter.addItem(new LiveScore(true,"", "Eanglish", "ManCity", "11/10","4:10"));
		countryAdapter.addItem(new LiveScore(false,"", "MU", "ManCity", "11/10","4:10"));
		countryAdapter.addItem(new LiveScore(true,"", "Eanglish", "ManCity", "11/10","4:10"));
		countryAdapter.addItem(new LiveScore(false,"", "MU", "ManCity", "11/10","4:10"));
		countryAdapter.addItem(new LiveScore(true,"", "Eanglish", "ManCity", "11/10","4:10"));
		countryAdapter.addItem(new LiveScore(false,"", "MU", "ManCity", "11/10","4:10"));
		countryAdapter.addItem(new LiveScore(true,"", "Eanglish", "ManCity", "11/10","4:10"));
		countryAdapter.addItem(new LiveScore(false,"", "MU", "ManCity", "11/10","4:10"));
		countryAdapter.notifyDataSetChanged();
	}
}
