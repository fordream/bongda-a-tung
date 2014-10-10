package com.app.bongda.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.app.bongda.R;
import com.app.bongda.base.BaseFragment;
import com.app.bongda.base.BongDaBaseAdapter;
import com.app.bongda.model.GiaiDau;
import com.app.bongda.view.HeaderView;

public class BangXepHangFragment extends BaseFragment {
	OnItemClickListener onItemClickListener;
	GiaiDau dau;

	public BangXepHangFragment(GiaiDau dau,
			OnItemClickListener onItemClickListener) {
		super();
		this.onItemClickListener = onItemClickListener;
		this.dau = dau;
	}

	private CountryAdapter countryAdapter = new CountryAdapter();

	private class CountryAdapter extends BongDaBaseAdapter {

		@Override
		public int getLayout() {
			return R.layout.country_item;
		}

		@Override
		public void showData(Object item, View convertView) {
			// TODO Auto-generated method stub

		}

	}

	@Override
	public int getLayout() {
		return R.layout.bangxephang;
	}

	@Override
	public void onInitCreateView(View view) {
		/**
		 * init header view
		 */
		HeaderView headerView = (HeaderView) view
				.findViewById(R.id.headerView1);
		headerView.setTextHeader(R.string.bangxephang);

		/** init data */
		ListView listView = (ListView) view.findViewById(R.id.listView1);
		listView.setOnItemClickListener(onItemClickListener);
		listView.setAdapter(countryAdapter);
		
		((TextView)view.findViewById(R.id.danhsachgiaidau_txtname)).setText(dau.getName());
	}

	@Override
	public void onInitData() {
		countryAdapter.addItem("");
		countryAdapter.addItem("");
		countryAdapter.addItem("");
		countryAdapter.addItem("");
		countryAdapter.addItem("");
		countryAdapter.notifyDataSetChanged();
	}
}
