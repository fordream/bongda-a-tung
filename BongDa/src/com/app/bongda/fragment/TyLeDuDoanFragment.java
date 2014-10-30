package com.app.bongda.fragment;

import org.json.JSONArray;
import org.json.JSONException;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;

import com.app.bongda.R;
import com.app.bongda.base.BaseFragment;
import com.app.bongda.callback.APICaller;
import com.app.bongda.callback.APICaller.ICallbackAPI;
import com.app.bongda.model.GiaiDau;
import com.app.bongda.util.ByUtils;
import com.app.bongda.util.CommonAndroid;
import com.app.bongda.view.HeaderView;

public class TyLeDuDoanFragment extends BaseFragment {
	OnItemClickListener onItemClickListener;
	GiaiDau giaidau;
	public TyLeDuDoanFragment(GiaiDau giaiDau,OnItemClickListener onItemClickListener) {
		super();
		this.onItemClickListener = onItemClickListener;
		this.giaidau = giaiDau;
	}

	@Override
	public int getLayout() {
		return R.layout.tyledudoan;
	}

	@Override
	public void onInitCreateView(View view) {
		/**
		 * init header view
		 */
		HeaderView headerView = (HeaderView) view
				.findViewById(R.id.headerView1);
		headerView.setTextHeader(R.string.tyledudoan);
		/** init data */

	}

	ICallbackAPI callbackAPI;
	private String rTyLe_DoiNha;
	private String rTyLe_DoiKhach;
	@Override
	public void onInitData() {
		callbackAPI = new ICallbackAPI() {
			@Override
			public void onSuccess(String response) {
//				CommonAndroid.showDialog(getActivity(), "data2:" + response , null);
				String string_temp = CommonAndroid.parseXMLAction(response);
				if(!string_temp.equalsIgnoreCase("")){
					try {
						JSONArray jsonarray = new JSONArray(string_temp);
						for (int i = 0; i < jsonarray.length(); i++) {
							//parse
							rTyLe_DoiNha = jsonarray.getJSONObject(i).getString("rTyLe_DoiNha");
							rTyLe_DoiKhach = jsonarray.getJSONObject(i).getString("rTyLe_DoiKhach");
							
						}
						
						CommonAndroid.showDialog(getActivity(), "rTyLe_DoiNha=" + rTyLe_DoiNha + ":rTyLe_DoiKhach=" +rTyLe_DoiKhach, null);
					} catch (JSONException e) {
					
					}
				}
				
			}

			@Override
			public void onError(String message) {
				CommonAndroid.showDialog(getActivity(), "data3err:" + message , null);
				Log.e("ERR",message);
			}
		};
		String matran 		= giaidau.getId();
		String param = (ByUtils.wsFootBall_Lives_TyLeDuDoan).replace("matran",
				matran);
		new APICaller(getActivity()).callApi("", true,
				callbackAPI, param);
	}
}
