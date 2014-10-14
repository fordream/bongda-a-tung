package com.app.bongda.fragment;

import org.json.JSONArray;
import org.json.JSONException;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.app.bongda.R;
import com.app.bongda.base.BaseFragment;
import com.app.bongda.base.BongDaBaseAdapter;
import com.app.bongda.callback.APICaller;
import com.app.bongda.callback.APICaller.ICallbackAPI;
import com.app.bongda.inter.CallBackListenner;
import com.app.bongda.model.GiaiDau;
import com.app.bongda.util.ByUtils;
import com.app.bongda.util.CommonAndroid;
import com.app.bongda.view.HeaderView;

public class TuongThuatTranLiveScoreFragment extends BaseFragment {
	OnItemClickListener onItemClickListener;
	CallBackListenner backListenner;
	GiaiDau dau;
	public TuongThuatTranLiveScoreFragment(GiaiDau dau,
			OnItemClickListener onItemClickListener,
			CallBackListenner backListenner) {
		super();
		this.onItemClickListener = onItemClickListener;
		this.backListenner = backListenner;
		this.dau = dau;
	}

	private CountryAdapter countryAdapter = new CountryAdapter();

	private class CountryAdapter extends BongDaBaseAdapter {

		@Override
		public int getLayout() {
			return R.layout.tuongthuattructiep_item;
		}

		@Override
		public void showData(Object item, View convertView) {

		}

	}

	@Override
	public int getLayout() {
		return R.layout.tuongthuattructiep;
	}

	@Override
	public void onInitCreateView(View view) {
		/**
		 * init header view
		 */
		HeaderView headerView = (HeaderView) view
				.findViewById(R.id.headerView1);
		headerView.setTextHeader(R.string.tuongthuattran);
		headerView.findViewById(R.id.Button02).setVisibility(View.VISIBLE);
		headerView.findViewById(R.id.Button03).setVisibility(View.VISIBLE);
		headerView.findViewById(R.id.Button04).setVisibility(View.VISIBLE);
		/** init data */
		ListView listView = (ListView) view.findViewById(R.id.listView1);
		listView.setOnItemClickListener(onItemClickListener);

		listView.setAdapter(countryAdapter);

		view.findViewById(R.id.imageView2).setOnClickListener(clickListener);
		headerView.findViewById(R.id.Button02).setOnClickListener(clickListener);
		headerView.findViewById(R.id.Button03).setOnClickListener(clickListener);
		headerView.findViewById(R.id.Button04).setOnClickListener(clickListener);
		((TextView) view.findViewById(R.id.textTenTran)).setText(dau.getName());
	}
	
	View.OnClickListener clickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if(v.getId() == R.id.imageView2){
				backListenner.onCallBackListenner(1, new GiaiDau(dau.getId(), dau.getName()));
			}else if(v.getId() == R.id.Button02){
				backListenner.onCallBackListenner(2, null);
			}else if(v.getId() == R.id.Button03){
				backListenner.onCallBackListenner(3, null);
			}else if(v.getId() == R.id.Button04){
				backListenner.onCallBackListenner(4, null);
			}
		}
	};

	ICallbackAPI callbackAPI;
	private String iID_MaTran;
	@Override
	public void onInitData() {
		callbackAPI = new ICallbackAPI() {
			@Override
			public void onSuccess(String response) {
//				CommonAndroid.showDialog(getActivity(), "data2:" + response , null);
				String string_temp = CommonAndroid.parseXMLAction(response);
				if(!string_temp.equalsIgnoreCase("")){
					CommonAndroid.showDialog(getActivity(), "data2:" + string_temp , null);
					try {
						JSONArray jsonarray = new JSONArray(string_temp);
						for (int i = 0; i < jsonarray.length(); i++) {
							//parse
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
		iID_MaTran = dau.getId();
		Object aobj[] = new Object[1];
        aobj[0] = Integer.valueOf(iID_MaTran);
        String param = String.format(ByUtils.wsFootBall_MatchDetail, aobj);
		new APICaller(getActivity()).callApi("", true,
					callbackAPI, param);
		countryAdapter.addItem("");
		countryAdapter.addItem("");
		countryAdapter.addItem("");
		countryAdapter.addItem("");
		countryAdapter.addItem("");
		countryAdapter.notifyDataSetChanged();
	}
}
