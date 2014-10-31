package com.app.bongda.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.app.bongda.R;
import com.app.bongda.base.BaseFragment;
import com.app.bongda.base.BongDaBaseAdapter;
import com.app.bongda.callback.APICaller;
import com.app.bongda.callback.APICaller.ICallbackAPI;
import com.app.bongda.model.GameDuDoan;
import com.app.bongda.model.LiveScore;
import com.app.bongda.util.ByUtils;
import com.app.bongda.util.CommonAndroid;
import com.app.bongda.view.HeaderView;

public class GameDuDoanFragment extends BaseFragment {
	OnItemClickListener onItemClickListener;

	public GameDuDoanFragment(OnItemClickListener onItemClickListener) {
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
			final GameDuDoan dudoan = (GameDuDoan) item;
			setText(convertView, R.id.TextView02, dudoan.sTenDoiNha());
			setText(convertView, R.id.TextView03, dudoan.sTenDoiKhach());
		}

	}

	@Override
	public int getLayout() {
		return R.layout.gamedudoan;
	}

	@Override
	public void onInitCreateView(View view) {
		/**
		 * init header view
		 */
		HeaderView headerView = (HeaderView) view
				.findViewById(R.id.headerView1);
		headerView.setTextHeader(R.string.gamedudoan);
		/** init data */
		ListView listView = (ListView) view.findViewById(R.id.listView1);
		listView.setOnItemClickListener(onItemClickListener);

		listView.setAdapter(countryAdapter);
	}
	
	ICallbackAPI callbackAPI;

	@Override
	public void onInitData() {
//		countryAdapter.addItem("");
//		countryAdapter.addItem("");
//		countryAdapter.addItem("");
//		countryAdapter.addItem("");
//		countryAdapter.addItem("");
		callbackAPI = new ICallbackAPI() {
			@Override
			public void onSuccess(String response) {
				 CommonAndroid.showDialog(getActivity(), "data2:" +
						 response , null);
				String string_temp = CommonAndroid.parseXMLAction(response);
				if (!string_temp.equalsIgnoreCase("")) {
					try {

						ArrayList<JSONObject> array = new ArrayList<JSONObject>();
						array.clear();
						JSONArray jsonArray = new JSONArray(string_temp);
						if(jsonArray.length() == 0){
							Toast.makeText(getActivity(), getResources().getString(R.string.giaichuabatdau), Toast.LENGTH_LONG).show();
						}
						for (int i = 0; i < jsonArray.length(); i++) {
							try {
								array.add(jsonArray.getJSONObject(i));
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						Collections.emptyList();
						Collections.sort(array, new Comparator<JSONObject>() {

							@Override
							public int compare(JSONObject lhs, JSONObject rhs) {
								// TODO Auto-generated method stub

								try {
									return (lhs.getString("sTenGiai").toLowerCase().compareTo(rhs.getString("sTenGiai").toLowerCase()));
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									return 0;
								}
							}
						});

						String sTenDoiNha;
						String sTenDoiKhach;
						String iCN_BanThang_DoiNha;
						String iCN_BanThang_DoiKhach;
						String iC0; //ngay
						String sThoiGian;
						String sTyLe_ChauAu; //"2.20*3.10*2.35"
						String sTyLe_ChapBong ; //":"0.87*0 : 1\/4*0.83"
						String sTyLe_TaiSuu ;//":"0.98*2 1\/4*0.72"
						for (int i = 0; i < array.size(); i++) {
							sTenDoiNha = array.get(i).getString("sTenDoiNha");
							sTenDoiKhach = array.get(i).getString("sTenDoiKhach");
							iCN_BanThang_DoiNha = array.get(i).getString("iCN_BanThang_DoiNha");
							iCN_BanThang_DoiKhach = array.get(i).getString("iCN_BanThang_DoiKhach");
							iC0 = array.get(i).getString("iC0");
							sThoiGian = array.get(i).getString("sThoiGian");
							sTyLe_ChauAu =array.get(i).getString("sTyLe_ChauAu");
							sTyLe_ChapBong = array.get(i).getString("sTyLe_ChapBong");
							sTyLe_TaiSuu = array.get(i).getString("sTyLe_TaiSuu");
							//TODO
							countryAdapter.addItem(new GameDuDoan(false, sTenDoiNha, sTenDoiKhach ,iCN_BanThang_DoiNha,iCN_BanThang_DoiKhach, iC0,sThoiGian,sTyLe_ChauAu ,sTyLe_ChapBong, sTyLe_TaiSuu));
							Log.e("data", "" + array.get(i));

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
		new APICaller(getActivity()).callApi("", true, callbackAPI,
					ByUtils.wsFootBall_Lives_dudoan);
	}
}
