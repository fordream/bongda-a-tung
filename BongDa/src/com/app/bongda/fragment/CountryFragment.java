package com.app.bongda.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.app.bongda.R;
import com.app.bongda.base.BaseFragment;
import com.app.bongda.base.BongDaBaseAdapter;
import com.app.bongda.model.Country;
import com.app.bongda.view.HeaderView;

public class CountryFragment extends BaseFragment {
	OnItemClickListener onItemClickListener;

	public CountryFragment(OnItemClickListener onItemClickListener) {
		super();
		this.onItemClickListener = onItemClickListener;
	}

	int count = 0;

	private CountryAdapter countryAdapter = new CountryAdapter();

	private class CountryAdapter extends BongDaBaseAdapter {

		@Override
		public int getLayout() {
			return R.layout.country_item;
		}

		@Override
		public void showData(Object item, View convertView) {
			TextView textView = (TextView)convertView.findViewById(R.id.textView1);
			textView.setText(((Country)item).getName());
		}

	}

	@Override
	public int getLayout() {
		return R.layout.country;
	}

	@Override
	public void onInitCreateView(View view) {
		/**
		 * init header view
		 */
		HeaderView headerView = (HeaderView) view
				.findViewById(R.id.headerView1);
		headerView.setTextHeader(R.string.cacnuoc);
		/** init data */
		ListView listView = (ListView) view.findViewById(R.id.listView1);
		listView.setOnItemClickListener(onItemClickListener);

		listView.setAdapter(countryAdapter);
	}

	@Override
	public void onInitData() {
		countryAdapter.addItem(new Country("1", "View Name"));
		countryAdapter.addItem(new Country("2", "English"));
		countryAdapter.addItem(new Country("3", "Hong Kong"));
		countryAdapter.addItem(new Country("4", "Thai Lan"));
		countryAdapter.addItem(new Country("5", "Singapor"));
		countryAdapter.notifyDataSetChanged();
	}
}
