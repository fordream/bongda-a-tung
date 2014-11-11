package com.app.bongda.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.app.bongda.base.ImageLoaderUtils;
import com.app.bongda.R;
import com.app.bongda.base.BaseFragment;
import com.app.bongda.base.BongDaBaseAdapter;
import com.app.bongda.callback.APICaller;
import com.app.bongda.callback.APICaller.ICallbackAPI;
import com.app.bongda.inter.CallBackListenner;
import com.app.bongda.model.GiaiDau;
import com.app.bongda.model.LiveScore;
import com.app.bongda.model.NhanDinhChuyenGia;
import com.app.bongda.util.ByUtils;
import com.app.bongda.util.CommonAndroid;
import com.app.bongda.view.HeaderView;

public class NhanDinhChuyenGiaFragment extends BaseFragment {
	OnItemClickListener onItemClickListener;
	CallBackListenner callBackListenner;
	GiaiDau data;
	ListView listView;
	public NhanDinhChuyenGiaFragment(GiaiDau dau, Object object) {
		// TODO Auto-generated constructor stub
		super();
		this.data = dau;
	}

	private CountryAdapter countryAdapter = new CountryAdapter();

	private class CountryAdapter extends BongDaBaseAdapter {

		@Override
		public int getLayout() {
			return R.layout.nhandinhchuyengia_item;
		}

		@Override
		public void showData(Object item, View convertView) {
			final NhanDinhChuyenGia NhanDinhChuyenGia = (NhanDinhChuyenGia) item;
			setText(convertView, R.id.title, NhanDinhChuyenGia.tieude());
			setText(convertView, R.id.key, NhanDinhChuyenGia.key());
			setText(convertView, R.id.body, NhanDinhChuyenGia.noidung());
			String image1 = NhanDinhChuyenGia.anh2();
			ImageLoaderUtils.getInstance(getActivity()).DisplayImage(image1, (ImageView) convertView.findViewById(R.id.image_nhandinhchuyengia));
			Log.e("a", "a:====" + image1);
		}

	}

	@Override
	public int getLayout() {
		return R.layout.nhandinhchuyengia;
	}

	@Override
	public void onInitCreateView(View view) {
		/**
		 * init header view
		 */
		HeaderView headerView = (HeaderView) view
				.findViewById(R.id.headerView1);
		headerView.setTextHeader(R.string.nhandinhchuyengia);
		/** init data */
		listView = (ListView) view.findViewById(R.id.listView1);
		listView.setOnItemClickListener(onItemClickListener);

		listView.setAdapter(countryAdapter);
	}

	ICallbackAPI callbackAPI;

	@Override
	public void onInitData() {
		
		callbackAPI = new ICallbackAPI() {
			@Override
			public void onSuccess(String response) {
//				 CommonAndroid.showDialog(getActivity(), "data2:" +
//						 response , null);
				Log.e("data22", response);
				 String string_temp = response == null ? "" : CommonAndroid.parseXMLAction(response);
					if (!string_temp.equalsIgnoreCase("")) {
						Log.e("data", string_temp);
						try {
							JSONArray jsonarray = new JSONArray(string_temp);
							for (int i = 0; i < jsonarray.length(); i++) {
								String id = jsonarray.getJSONObject(i).getString("iID_MaNhanDinh_Info");
								String matran = jsonarray.getJSONObject(i).getString("iID_MaTran");
								String tieude= jsonarray.getJSONObject(i).getString("sTieuDe");
								String key = jsonarray.getJSONObject(i).getString("sKey");
								String anh= jsonarray.getJSONObject(i).getString("sAnh");
								String anh2 = jsonarray.getJSONObject(i).getString("sAnhChiTiet");
								String noidung = jsonarray.getJSONObject(i).getString("sNoiDung");
								String date = jsonarray.getJSONObject(i).getString("dNgayDang");
								countryAdapter.addItem(new NhanDinhChuyenGia(id, matran, tieude, key,anh, anh2,  noidung,date));
							}
							if(jsonarray.length() == 0){
								Toast.makeText(listView.getContext(), listView.getContext().getResources().getString(R.string.khongconhandinhchuyengia), Toast.LENGTH_LONG).show();
							}
							countryAdapter.notifyDataSetChanged();
						} catch (JSONException e) {
					        // TODO Auto-generated catch block
					        e.printStackTrace();
					    }
					}	
			}

			@Override
			public void onError(String message) {
				Log.e("Err", "" + message);
			}
		};
		String matran = data == null ? null : data.iID_MaTran();
		if (matran == null) {
			new APICaller(listView.getContext()).callApi("", true, callbackAPI,
					ByUtils.wsFootBall_Nhan_Dinh_Chuyen_Gia);
			Log.e("nhandinh","nhandinhchuyengia");
			
		} else {
			new APICaller(listView.getContext()).callApi("", true, callbackAPI,
					(ByUtils.wsFootBall_Nhan_Dinh_Chuyen_Gia_Theo_Tran).replace("matran",
							matran));
		}

	}
}
