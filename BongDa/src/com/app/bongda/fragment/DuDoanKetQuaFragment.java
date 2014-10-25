package com.app.bongda.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.app.bongda.R;
import com.app.bongda.base.BaseFragment;
import com.app.bongda.base.BongDaBaseAdapter;
import com.app.bongda.callback.APICaller;
import com.app.bongda.callback.APICaller.ICallbackAPI;
import com.app.bongda.model.LiveScore;
import com.app.bongda.util.ByUtils;
import com.app.bongda.util.CommonAndroid;
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
	
	ICallbackAPI callbackAPI;

	@Override
	public void onInitData() {
		countryAdapter.addItem("");
		countryAdapter.addItem("");
		countryAdapter.addItem("");
		countryAdapter.addItem("");
		countryAdapter.addItem("");
		countryAdapter.notifyDataSetChanged();
		callbackAPI = new ICallbackAPI() {
			@Override
			public void onSuccess(String response) {
				 CommonAndroid.showDialog(getActivity(), "data2:" +
						 response , null);
//				String string_temp = CommonAndroid.parseXMLAction(response);
//				if (!string_temp.equalsIgnoreCase("")) {
//					// CommonAndroid.showDialog(getActivity(), "data2:" +
//					// string_temp , null);
////					 Log.e("data",string_temp);
//				
//						countryAdapter.notifyDataSetChanged();
//					} catch (JSONException e) {
//					}
//
//				}

			}

			@Override
			public void onError(String message) {
			}
		};
		new APICaller(getActivity()).callApi("", true, callbackAPI,
					ByUtils.wsFootBall_Lives_dudoan);
	}
}
