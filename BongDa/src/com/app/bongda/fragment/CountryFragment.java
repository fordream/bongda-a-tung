package com.app.bongda.fragment;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
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
import com.app.bongda.callback.APICaller.ICallbackAPI;
import com.app.bongda.model.Country;
import com.app.bongda.service.BongDaServiceManager;
import com.app.bongda.util.ByUtils;
import com.app.bongda.util.CommonAndroid;
import com.app.bongda.view.HeaderView;
import com.vnp.core.datastore.database.CountryTable;

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

		if (ByUtils.USEGROUPVIEW) {
			Cursor cursor = BongDaServiceManager.getInstance().getBongDaService().query(new CountryTable().getTableName(), null);
			listView.setAdapter(new CursorAdapter(view.getContext(), cursor) {

				@Override
				public View newView(Context arg0, Cursor arg1, ViewGroup arg2) {
					View view = (View) ((LayoutInflater) arg2.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.country_item, null);
					showData(arg1, view);
					return view;
				}

				public void showData(Cursor cursor, View convertView) {
					TextView textView = (TextView) convertView.findViewById(R.id.textView1);
					textView.setText(cursor.getString(cursor.getColumnIndex("sTenQuocGia")));
					String image1 = cursor.getString(cursor.getColumnIndex("sLogo"));
					ImageLoaderUtils.getInstance(getActivity()).DisplayImage(image1, (ImageView) convertView.findViewById(R.id.imageView1));
				}

				@Override
				public void bindView(View arg0, Context arg1, Cursor arg2) {
					showData(arg2, arg0);
				}
			});
			// loadata();
		}
	}

	private void loadata() {
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
	public void onResume() {
		super.onResume();
		loadata();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onInitData() {

	}
}
