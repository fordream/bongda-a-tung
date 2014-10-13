package com.app.bongda.fragment;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.app.bongda.R;
import com.app.bongda.base.BaseFragment;
import com.app.bongda.base.BongDaBaseAdapter;
import com.app.bongda.callback.APICaller;
import com.app.bongda.callback.APICaller.ICallbackAPI;
import com.app.bongda.model.BangXepHang;
import com.app.bongda.model.GiaiDau;
import com.app.bongda.model.LiveScore;
import com.app.bongda.util.ByUtils;
import com.app.bongda.util.CommonAndroid;
import com.app.bongda.view.HeaderView;

public class BangXepHangFragment extends BaseFragment {
	OnItemClickListener onItemClickListener;
	GiaiDau dau;

	public BangXepHangFragment(GiaiDau dau,
			OnItemClickListener onItemClickListener) {
		super();
		this.onItemClickListener = onItemClickListener;
		this.dau = dau;
	}

	private CountryAdapter countryAdapter = new CountryAdapter();

	private class CountryAdapter extends BongDaBaseAdapter {

		@Override
		public int getLayout() {
			return R.layout.bangxephang_item;
		}
		@Override
		public void showData(int position, Object item, View convertView) {
			super.showData(position, item, convertView);
			setText(convertView, R.id.stt, position + "");
		}
		@Override
		public void showData(Object item, View convertView) {
			BangXepHang bangxephang = (BangXepHang) item;
			setText(convertView, R.id.tendoi, bangxephang.getName());
			setText(convertView, R.id.pts, bangxephang.getPts());
			setText(convertView, R.id._congtru, bangxephang.get_contru());
			setText(convertView, R.id.d, bangxephang.getD());
			setText(convertView, R.id.ga, bangxephang.getGa());
			setText(convertView, R.id.gf, bangxephang.getGf());
			setText(convertView, R.id.gp, bangxephang.getGp());
			setText(convertView, R.id.l, bangxephang.getL());
			setText(convertView, R.id.w, bangxephang.getW());
		}
	}

	@Override
	public int getLayout() {
		return R.layout.bangxephang;
	}

	View mHeader;

	@Override
	public void onInitCreateView(View view) {
		/**
		 * init header view
		 */
		HeaderView headerView = (HeaderView) view
				.findViewById(R.id.headerView1);
		headerView.setTextHeader(R.string.bangxephang);

		/** init data */
		ListView listView = (ListView) view
				.findViewById(R.id.bangxephang_listview);
		if (mHeader != null) {
			listView.removeHeaderView(mHeader);
		} else {
			mHeader = ((LayoutInflater) getActivity().getSystemService(
					Context.LAYOUT_INFLATER_SERVICE)).inflate(
					R.layout.bangxephang_item, null);
		}

		listView.addHeaderView(mHeader);

		listView.setOnItemClickListener(onItemClickListener);
		listView.setAdapter(countryAdapter);

		((TextView) view.findViewById(R.id.danhsachgiaidau_txtname))
				.setText(dau.getName());
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
							countryAdapter.addItem(new BangXepHang(
									jsonarray.getJSONObject(i).getString("sViTri"), 
									jsonarray.getJSONObject(i).getString("sTenDoi"), 
									jsonarray.getJSONObject(i).getString("sSoTranDau"), 
									jsonarray.getJSONObject(i).getString("sDiem"), 
									jsonarray.getJSONObject(i).getString("sSoTranThang"), 
									jsonarray.getJSONObject(i).getString("sSoTranHoa"),  
									jsonarray.getJSONObject(i).getString("sSoTranThua"),  
									jsonarray.getJSONObject(i).getString("sBanThang"), 
									jsonarray.getJSONObject(i).getString("sBanThua"), 
									jsonarray.getJSONObject(i).getString("sHeSo")));
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
		String maGiaiDau = null;
			new APICaller(getActivity()).callApi("", true,
					callbackAPI, (ByUtils.wsFootBall_BangXepHang).replace("bangxephangId", dau.getId()));
//		countryAdapter.addItem(new BangXepHang("id", "man city", "25", "11",
//				"8", "1", "2", "22", "11", "12"));
//		countryAdapter.addItem(new BangXepHang("id", "man city", "25", "11",
//				"8", "1", "2", "22", "11", "12"));
//		countryAdapter.addItem(new BangXepHang("id", "man city", "25", "11",
//				"8", "1", "2", "22", "11", "12"));
//		countryAdapter.addItem(new BangXepHang("id", "man city", "25", "11",
//				"8", "1", "2", "22", "11", "12"));
//		countryAdapter.addItem(new BangXepHang("id", "man city", "25", "11",
//				"8", "1", "2", "22", "11", "12"));
//		countryAdapter.addItem(new BangXepHang("id", "man city", "25", "11",
//				"8", "1", "2", "22", "11", "12"));
//		countryAdapter.addItem(new BangXepHang("id", "man city", "25", "11",
//				"8", "1", "2", "22", "11", "12"));
//		countryAdapter.addItem(new BangXepHang("id", "man city", "25", "11",
//				"8", "1", "2", "22", "11", "12"));
//		countryAdapter.addItem(new BangXepHang("id", "man city", "25", "11",
//				"8", "1", "2", "22", "11", "12"));
//		countryAdapter.addItem(new BangXepHang("id", "man city", "25", "11",
//				"8", "1", "2", "22", "11", "12"));
//		countryAdapter.addItem(new BangXepHang("id", "man city", "25", "11",
//				"8", "1", "2", "22", "11", "12"));
//		countryAdapter.addItem(new BangXepHang("id", "man city", "25", "11",
//				"8", "1", "2", "22", "11", "12"));
		countryAdapter.notifyDataSetChanged();
	}
}
