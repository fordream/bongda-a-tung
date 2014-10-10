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
import com.app.bongda.model.GiaiDau;
import com.app.bongda.view.HeaderView;

public class PhongDoFragment extends BaseFragment {
	OnItemClickListener onItemClickListener;
	GiaiDau giaiDau;

	public PhongDoFragment(GiaiDau giaiDau, OnItemClickListener onItemClickListener) {
		super();
		this.onItemClickListener = onItemClickListener;
		this.giaiDau = giaiDau;
	}

	private CountryAdapter countryAdapter = new CountryAdapter();

	private class CountryAdapter extends BongDaBaseAdapter {

		@Override
		public int getLayout() {
			return R.layout.tyso_item;
		}

		@Override
		public void showData(Object item, View convertView) {
			// TODO Auto-generated method stub

		}

	}

	@Override
	public int getLayout() {
		return R.layout.tyso;
	}

	@Override
	public void onInitCreateView(View view) {
		/**
		 * init header view
		 */
		HeaderView headerView = (HeaderView) view
				.findViewById(R.id.headerView1);
		headerView.setTextHeader(R.string.phongdo);
		/** init data */
		ListView listView = (ListView) view.findViewById(R.id.tyso_listview);
		listView.setOnItemClickListener(onItemClickListener);

		listView.setAdapter(countryAdapter);
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
