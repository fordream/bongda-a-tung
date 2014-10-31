package com.app.bongda.fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.app.bongda.R;
import com.app.bongda.base.BaseFragment;
import com.app.bongda.base.BongDaBaseAdapter;
import com.app.bongda.base.ImageLoaderUtils;
import com.app.bongda.callback.APICaller;
import com.app.bongda.callback.APICaller.ICallbackAPI;
import com.app.bongda.model.Country;
import com.app.bongda.service.BongDaServiceManager;
import com.app.bongda.util.ByUtils;
import com.app.bongda.util.CommonAndroid;
import com.app.bongda.view.HeaderView;

public class CountryFragment extends BaseFragment {
	OnItemClickListener onItemClickListener;
	ICallbackAPI callbackAPI;

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
			TextView textView = (TextView) convertView.findViewById(R.id.textView1);
			textView.setText(((Country) item).getName());
			String image1 = ((Country) item).logoCountry();
			ImageLoaderUtils.getInstance(getActivity()).DisplayImage(image1, (ImageView) convertView.findViewById(R.id.imageView1));
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
		HeaderView headerView = (HeaderView) view.findViewById(R.id.headerView1);
		headerView.setTextHeader(R.string.cacnuoc);
		/** init data */
		ListView listView = (ListView) view.findViewById(R.id.listView1);
		listView.setOnItemClickListener(onItemClickListener);

		listView.setAdapter(countryAdapter);
	}

	@Override
	public void onResume() {
		super.onResume();
		if (callbackAPI == null)
			callbackAPI = new ICallbackAPI() {
				@Override
				public void onSuccess(String response) {
					String string_temp = CommonAndroid.parseXMLAction(response);
					if (!string_temp.equalsIgnoreCase("")) {
						try {
							JSONArray jsonarray = new JSONArray(string_temp);
							for (int i = 0; i < jsonarray.length(); i++) {
								countryAdapter.addItem(new Country(jsonarray.getJSONObject(i).getString("iID_MaQuocGia"), jsonarray.getJSONObject(i).getString("sTenQuocGia"), jsonarray.getJSONObject(
										i).getString("sLogo")));
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
		BongDaServiceManager.getInstance().getBongDaService().callApi(getCurrentTime(), callbackAPI, ByUtils.wsFootBall_Quocgia);
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onInitData() {
		// callbackAPI = new ICallbackAPI() {
		// @Override
		// public void onSuccess(String response) {
		// String string_temp = CommonAndroid.parseXMLAction(response);
		// if (!string_temp.equalsIgnoreCase("")) {
		// // CommonAndroid.showDialog(getActivity(), "data2:" +
		// // string_temp , null);
		// try {
		// JSONArray jsonarray = new JSONArray(string_temp);
		// for (int i = 0; i < jsonarray.length(); i++) {
		// countryAdapter.addItem(new
		// Country(jsonarray.getJSONObject(i).getString("iID_MaQuocGia"),
		// jsonarray.getJSONObject(i).getString("sTenQuocGia")));
		// Log.e("quocgia", i + ":ten==" +
		// jsonarray.getJSONObject(i).getString("sTenQuocGia") +
		// ":::maquocgia==" + jsonarray.getJSONObject(i).getString("sLogo"));
		// }
		// countryAdapter.notifyDataSetChanged();
		// } catch (JSONException e) {
		// }
		//
		// }
		//
		// }
		//
		// @Override
		// public void onError(String message) {
		// }
		// };
		// new APICaller(getActivity()).callApi("", true, callbackAPI,
		// ByUtils.wsFootBall_Quocgia);
		// countryAdapter.addItem(new Country("2", "English"));
		// countryAdapter.addItem(new Country("3", "Hong Kong"));
		// countryAdapter.addItem(new Country("4", "Thai Lan"));
		// countryAdapter.addItem(new Country("5", "Singapor"));
		// countryAdapter.notifyDataSetChanged();
	}
}
