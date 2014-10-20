package com.app.bongda.fragment;

import org.json.JSONArray;
import org.json.JSONException;

import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;

import com.app.bongda.R;
import com.app.bongda.base.BaseFragment;
import com.app.bongda.base.BongDaBaseAdapter;
import com.app.bongda.callback.APICaller;
import com.app.bongda.callback.APICaller.ICallbackAPI;
import com.app.bongda.model.GiaiDau;
import com.app.bongda.util.ByUtils;
import com.app.bongda.util.CommonAndroid;
import com.app.bongda.view.BangXepHangItemView;
import com.app.bongda.view.HeaderView;

public class PhongDoDoiDauFragment extends BaseFragment {
	OnItemClickListener onItemClickListener;
	GiaiDau giaidau;
	public PhongDoDoiDauFragment(GiaiDau giaiDau,OnItemClickListener onItemClickListener) {
		super();
		this.onItemClickListener = onItemClickListener;
		this.giaidau = giaiDau;
	}

	private CountryAdapter countryAdapter = new CountryAdapter();

	private class CountryAdapter extends BongDaBaseAdapter {

		@Override
		public int getLayout() {
			return R.layout.phongdodoidau_item;
		}

		@Override
		public void showData(Object item, View convertView) {

		}

	}

	@Override
	public int getLayout() {
		return R.layout.phongdodoidau;
	}
	LinearLayout phongdodoidau_bangephang_listitem;
	@Override
	public void onInitCreateView(View view) {
		/**
		 * init header view
		 */
		HeaderView headerView = (HeaderView) view
				.findViewById(R.id.headerView1);
		headerView.setTextHeader(R.string.xemphongdo);
	
		phongdodoidau_bangephang_listitem = (LinearLayout)view.findViewById(R.id.phongdodoidau_bangephang_listitem);
		phongdodoidau_bangephang_listitem.removeAllViews();
		
	}

	ICallbackAPI callbackAPI;
	private int iID_MaTran;
	@Override
	public void onInitData() {
		callbackAPI = new ICallbackAPI() {
			@Override
			public void onSuccess(String response) {
				CommonAndroid.showDialog(getActivity(), "data2:" + response , null);
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
						CommonAndroid.showDialog(getActivity(), "data2json:" + e.getMessage() , null);
					}
					
				}
				
			}

			@Override
			public void onError(String message) {
				CommonAndroid.showDialog(getActivity(), "data3err:" + message , null);
			}
		};
//		iID_MaTran = 32456;
//        String magiai 		= giaidau.magiai();
//        String madoinha 	= giaidau.madoinha();
//        String madoikhach 	= giaidau.madoikhach();
//        String param = (ByUtils.wsFootBall_Phong_Do_ChiTiet).replace("magiai",
//        		magiai);
//        param = param.replace("madoinha",madoinha);
//        param = param.replace("madoikhach",madoikhach);
        
        String iID_MaTran = giaidau.getId();
//		Log.e("KKKKKKKKKKKKK", "===" + dau.magiai() + "::" + dau.madoinha() + ":" + dau.madoikhach());
		Object aobj[] = new Object[1];
        aobj[0] = Integer.valueOf(iID_MaTran);
        String param = String.format(ByUtils.wsFootBall_Phong_Do, aobj);
        
		new APICaller(getActivity()).callApi("", true,
					callbackAPI, param);
//		CommonAndroid.showDialog(getActivity(), "data33:" + param , null);
		for(int i = 0; i < 6; i ++){
			phongdodoidau_bangephang_listitem.addView(new BangXepHangItemView(getActivity()));
		}
	}
}
