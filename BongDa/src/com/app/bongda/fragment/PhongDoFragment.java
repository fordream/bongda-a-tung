package com.app.bongda.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.app.bongda.R;
import com.app.bongda.base.BaseFragment;
import com.app.bongda.base.BongDaBaseAdapter;
import com.app.bongda.model.GiaiDau;
import com.app.bongda.model.PhongDo;
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
			PhongDo phongDo = (PhongDo)(item);
			setText(convertView,R.id.TextView01, phongDo.getTime());
			setText(convertView,R.id.TextView02, phongDo.getName());
			setText(convertView,R.id.TextView023, phongDo.getName2());
			setText(convertView,R.id.textView1, phongDo.getDate());
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
		
		((TextView)view.findViewById(R.id.phongdo_text)).setText(giaiDau.getName());
	}

	@Override
	public void onInitData() {
		countryAdapter.addItem(new PhongDo("", "MU", "ManCity", "11/10","4:10"));
		countryAdapter.addItem(new PhongDo("", "MU", "ManCity", "11/10","4:10"));
		countryAdapter.addItem(new PhongDo("", "MU", "ManCity", "11/10","4:10"));
		countryAdapter.addItem(new PhongDo("", "MU", "ManCity", "11/10","4:10"));
		countryAdapter.addItem(new PhongDo("", "MU", "ManCity", "11/10","4:10"));
		countryAdapter.notifyDataSetChanged();
	}
}
