package com.app.bongda.fragment;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.app.bongda.R;
import com.app.bongda.base.BaseFragment;
import com.app.bongda.base.BongDaBaseAdapter;
import com.app.bongda.inter.CallBackListenner;
import com.app.bongda.model.GiaiDau;
import com.app.bongda.model.TuongThuatTran;
import com.app.bongda.util.CommonAndroid;
import com.app.bongda.view.HeaderView;

public class TuongThuatTranLiveScoreFragment extends BaseFragment {
	OnItemClickListener onItemClickListener;
	CallBackListenner backListenner;

	public TuongThuatTranLiveScoreFragment(
			OnItemClickListener onItemClickListener,
			CallBackListenner backListenner) {
		super();
		this.onItemClickListener = onItemClickListener;
		this.backListenner = backListenner;
	}

	private CountryAdapter countryAdapter = new CountryAdapter();

	private class CountryAdapter extends BongDaBaseAdapter {

		@Override
		public int getLayout() {
			return R.layout.tuongthuattructiep_item;
		}

		@Override
		public void showData(Object item, View convertView) {

		}

	}

	@Override
	public int getLayout() {
		return R.layout.tuongthuattructiep;
	}

	@Override
	public void onInitCreateView(View view) {
		/**
		 * init header view
		 */
		HeaderView headerView = (HeaderView) view
				.findViewById(R.id.headerView1);
		headerView.setTextHeader(R.string.tuongthuattran);
		headerView.findViewById(R.id.Button02).setVisibility(View.VISIBLE);
		headerView.findViewById(R.id.Button03).setVisibility(View.VISIBLE);
		headerView.findViewById(R.id.Button04).setVisibility(View.VISIBLE);
		view.findViewById(R.id.imageView1s).setOnClickListener(clickListener);
		headerView.findViewById(R.id.Button02).setOnClickListener(clickListener);
		headerView.findViewById(R.id.Button03).setOnClickListener(clickListener);
		headerView.findViewById(R.id.Button04).setOnClickListener(clickListener);
		/** init data */
		ListView listView = (ListView) view.findViewById(R.id.listView1);
		listView.setAdapter(countryAdapter);
		listView.setOnItemClickListener(onItemClickListener);
	}
	
	View.OnClickListener clickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if(v.getId() == R.id.imageView1s){
				backListenner.onCallBackListenner(1, new GiaiDau("1", "aaaa"));
			}else if(v.getId() == R.id.Button02){
				backListenner.onCallBackListenner(2, null);
			}else if(v.getId() == R.id.Button03){
				backListenner.onCallBackListenner(3, null);
			}else if(v.getId() == R.id.Button04){
				backListenner.onCallBackListenner(4, null);
			}
		}
	};

	@Override
	public void onInitData() {
		
		countryAdapter.addItem(new TuongThuatTran(true, "1", "1"));
		countryAdapter.addItem(new TuongThuatTran(true, "1", "1"));
		countryAdapter.addItem(new TuongThuatTran(false, "1", "1"));
		countryAdapter.addItem(new TuongThuatTran(true, "1", "1"));
		countryAdapter.addItem(new TuongThuatTran(true, "1", "1"));
		countryAdapter.addItem(new TuongThuatTran(false, "1", "1"));
		countryAdapter.addItem(new TuongThuatTran(true, "1", "1"));
		countryAdapter.addItem(new TuongThuatTran(true, "1", "1"));
		countryAdapter.addItem(new TuongThuatTran(true, "1", "1"));
		countryAdapter.notifyDataSetChanged();
	}
}
