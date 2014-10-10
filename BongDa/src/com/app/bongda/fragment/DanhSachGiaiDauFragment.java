package com.app.bongda.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.app.bongda.R;
import com.app.bongda.base.BaseFragment;
import com.app.bongda.base.BongDaBaseAdapter;
import com.app.bongda.model.Country;
import com.app.bongda.model.GiaiDau;
import com.app.bongda.view.HeaderView;

public class DanhSachGiaiDauFragment extends BaseFragment {
	OnItemClickListener onItemClickListener;
	Country country;

	public DanhSachGiaiDauFragment(Country country,
			OnItemClickListener onItemClickListener) {
		super();
		this.onItemClickListener = onItemClickListener;
		this.country = country;
	}

	private CountryAdapter countryAdapter = new CountryAdapter();

	private class CountryAdapter extends BongDaBaseAdapter {

		@Override
		public int getLayout() {
			return R.layout.danhsachgiaidau_item;
		}

		@Override
		public void showData(Object item, View convertView) {
			GiaiDau giaiDau = (GiaiDau)item;
			TextView textView = (TextView)convertView.findViewById(R.id.textView1);
			textView.setText(giaiDau.getName());
		}

	}

	@Override
	public int getLayout() {
		return R.layout.danhsachgiaidau;
	}

	@Override
	public void onInitCreateView(View view) {
		HeaderView headerView = (HeaderView) view
				.findViewById(R.id.headerView1);
		headerView.setTextHeader(R.string.quocgia);
		/** init data */
		ListView listView = (ListView) view
				.findViewById(R.id.danhsachgiaidau_listview);
		listView.setOnItemClickListener(onItemClickListener);
		listView.setAdapter(countryAdapter);
		
		((TextView)view.findViewById(R.id.danhsachgiaidau_txtname)).setText(country.getName());
	}

	@Override
	public void onInitData() {
		countryAdapter.addItem(new GiaiDau("1", "Premier Laegue"));
		countryAdapter.addItem(new GiaiDau("2", "Premier Laegue"));
		countryAdapter.addItem(new GiaiDau("3", "Premier Laegue"));
		countryAdapter.addItem(new GiaiDau("4", "Premier Laegue"));
		countryAdapter.addItem(new GiaiDau("5", "Premier Laegue"));
		countryAdapter.notifyDataSetChanged();
	}
}
