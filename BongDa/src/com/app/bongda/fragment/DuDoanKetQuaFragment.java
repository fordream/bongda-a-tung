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
import com.app.bongda.view.HeaderView;

public class DuDoanKetQuaFragment extends BaseFragment {
	OnItemClickListener onItemClickListener;

	public DuDoanKetQuaFragment(OnItemClickListener onItemClickListener) {
		super();
		this.onItemClickListener = onItemClickListener;
	}

	private CountryAdapter countryAdapter = new CountryAdapter();

	private class CountryAdapter extends BongDaBaseAdapter {

		@Override
		public int getLayout() {
			return R.layout.dudoanketqua_item;
		}

		@Override
		public void showData(Object item, View convertView) {
			
		}

	}

	@Override
	public int getLayout() {
		return R.layout.dudoanketqua;
	}

	@Override
	public void onInitCreateView(View view) {
		/**
		 * init header view
		 */
		HeaderView headerView = (HeaderView) view
				.findViewById(R.id.headerView1);
		headerView.setTextHeader(R.string.dudoanketqua);
		/** init data */
		ListView listView = (ListView) view.findViewById(R.id.listView1);
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
