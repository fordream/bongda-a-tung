package com.app.bongda.fragment;

import org.json.JSONArray;
import org.json.JSONException;

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
import com.app.bongda.callback.APICaller;
import com.app.bongda.callback.APICaller.ICallbackAPI;
import com.app.bongda.model.Country;
import com.app.bongda.model.GiaiDau;
import com.app.bongda.util.ByUtils;
import com.app.bongda.util.CommonAndroid;
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

	ICallbackAPI callbackAPI;
	@Override
	public void onInitData() {
		callbackAPI = new ICallbackAPI() {
			@Override
			public void onSuccess(String response) {
				String string_temp = CommonAndroid.parseXMLAction(response);
				if(!string_temp.equalsIgnoreCase("")){
//					CommonAndroid.showDialog(getActivity(), "data2:" + string_temp , null);
					try {
						JSONArray jsonarray = new JSONArray(string_temp);
						for (int i = 0; i < jsonarray.length(); i++) {
							countryAdapter.addItem(new GiaiDau(jsonarray.getJSONObject(i).getString("iID_MaGiai"), jsonarray.getJSONObject(i).getString("sTenGiai")));
						}
						countryAdapter.notifyDataSetChanged();
					} catch (JSONException e) {
					}
					
				}
				
			}

			@Override
			public void onError(String message) {
			}
		};
		String country_id = country.getId();
		new APICaller(getActivity()).callApi("", true,
				callbackAPI, (ByUtils.wsFootBall_Giai_Theo_QuocGia).replace("quocgiaid", country_id));
//		countryAdapter.addItem(new GiaiDau("1", "Premier Laegue"));
//		countryAdapter.addItem(new GiaiDau("2", "Premier Laegue"));
//		countryAdapter.addItem(new GiaiDau("3", "Premier Laegue"));
//		countryAdapter.addItem(new GiaiDau("4", "Premier Laegue"));
//		countryAdapter.addItem(new GiaiDau("5", "Premier Laegue"));
//		countryAdapter.notifyDataSetChanged();
	}
}
