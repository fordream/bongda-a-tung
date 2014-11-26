package com.app.bongda.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import android.view.View;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.app.bongda.R;
import com.app.bongda.base.BaseFragment;
import com.app.bongda.base.BongDaBaseAdapter;
import com.app.bongda.callback.APICaller;
import com.app.bongda.callback.APICaller.ICallbackAPI;
import com.app.bongda.callback.progress.LiveScorePorgressExecute;
import com.app.bongda.model.GameDuDoan;
import com.app.bongda.model.LiveScore;
import com.app.bongda.util.ByUtils;
import com.app.bongda.util.CommonAndroid;
import com.app.bongda.util.CommonUtil;
import com.app.bongda.view.HeaderView;

public class MayTinhDuDoanFragment extends BaseFragment {
	OnItemClickListener onItemClickListener;

	public MayTinhDuDoanFragment(OnItemClickListener onItemClickListener) {
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

	private ListView listView;
	@Override
	public void onInitCreateView(View view) {
		/**
		 * init header view
		 */
		HeaderView headerView = (HeaderView) view
				.findViewById(R.id.headerView1);
		headerView.setTextHeader(R.string.dudoanketqua);
		/** init data */
		listView = (ListView) view.findViewById(R.id.listView1);
		listView.setOnItemClickListener(onItemClickListener);

		listView.setAdapter(countryAdapter);
	}
	
	ICallbackAPI callbackAPI;
	private int count_showdata_old = 0;
	private int count_showdata = 0;
	@Override
	public void onInitData() {
//		countryAdapter.addItem("");
//		countryAdapter.addItem("");
//		countryAdapter.addItem("");
//		countryAdapter.addItem("");
//		countryAdapter.addItem("");
//		
//		countryAdapter.notifyDataSetChanged();
//		CommonAndroid.showDialog(getActivity(), "Do not support" , null);
		Log.e("Liveco", "onInitData");
		callbackAPI = new ICallbackAPI() {
			@Override
			public void onSuccess(String response) {
				if(page == 1){
					countryAdapter.clear();
				}
				String string_temp = CommonAndroid.parseXMLAction(response);
				if (!string_temp.equalsIgnoreCase("")) {
//					Log.e("data", string_temp);
					try {
						JSONArray jsonArray = new JSONArray(string_temp);
						for (int i = 0; i < jsonArray.length(); i++) {
							boolean bNhanDinhChuyenGia = jsonArray.getJSONObject(i).getBoolean("bNhanDinhChuyenGia");
							boolean bGameDuDoan = jsonArray.getJSONObject(i).getBoolean("bGameDuDoan");
							boolean bDaCapNhapVaoBXH = jsonArray.getJSONObject(i).getBoolean("bDaCapNhapVaoBXH");
							Log.e("kkk", i + ":" + jsonArray.getJSONObject(i).getString("iID_MaGiai") + ":" + bNhanDinhChuyenGia + ":" + bGameDuDoan + ":" + bDaCapNhapVaoBXH);


//							if(TypeView == null || (("nhandinhchuyengia".equalsIgnoreCase(TypeView) && bNhanDinhChuyenGia) ) || "phongdo".equalsIgnoreCase(TypeView) || "theogiai".equalsIgnoreCase(TypeView) ){
//								check_null = true;
								String HT = "";
								StringBuilder stringbuilder1 = new StringBuilder("HT ");
								HT = stringbuilder1.append(jsonArray.getJSONObject(i).getString("iCN_BanThang_DoiNha_HT")).append(" - ").append(jsonArray.getJSONObject(i).getString("iCN_BanThang_DoiKhach_HT")).toString();

								String Banthang = (new StringBuilder()).append(jsonArray.getJSONObject(i).getString("iCN_BanThang_DoiNha")).append(" - ").append(jsonArray.getJSONObject(i).getString("iCN_BanThang_DoiKhach"))
										.toString();
								String iID_MaGiai = jsonArray.getJSONObject(i).getString("iID_MaGiai");
								String sTenGiai = jsonArray.getJSONObject(i).getString("sTenGiai");
								String sTenDoiNha = jsonArray.getJSONObject(i).getString("sTenDoiNha");
								String sTenDoiKhach = jsonArray.getJSONObject(i).getString("sTenDoiKhach");
								int iTrangThai = Integer.parseInt(jsonArray.getJSONObject(i).getString("iTrangThai"));
								String iID_MaTran = jsonArray.getJSONObject(i).getString("iID_MaTran");
								String iC0 = jsonArray.getJSONObject(i).getString("iC0");// ngay
																			// thi
																			// dau
								String iPhut = jsonArray.getJSONObject(i).getString("iPhut");
								String sThoiGian = jsonArray.getJSONObject(i).getString("sThoiGian");// thoi
																						// gian
																						// thi
																						// dau
								String tiso = jsonArray.getJSONObject(i).getString("iCN_BanThang_DoiNha") + " - " + jsonArray.getJSONObject(i).getString("iCN_BanThang_DoiKhach");
								String sMaGiai = jsonArray.getJSONObject(i).getString("sMaGiai");
								String sMaDoiNha = jsonArray.getJSONObject(i).getString("sMaDoiNha");
								String sMaDoiKhach = jsonArray.getJSONObject(i).getString("sMaDoiKhach");
								String sLogoQuocGia = "";
								String sLogoGiai = "";
								String sLogoDoiNha = "";
								String sLogoDoiKhach = "";
								if (jsonArray.getJSONObject(i).has("sLogoQuocGia")){
									sLogoQuocGia = jsonArray.getJSONObject(i).getString("sLogoQuocGia");
								}
								if (jsonArray.getJSONObject(i).has("sLogoGiai")){
									sLogoGiai = jsonArray.getJSONObject(i).getString("sLogoGiai");
								}
								if (jsonArray.getJSONObject(i).has("sLogoDoiNha")){
									sLogoDoiNha = jsonArray.getJSONObject(i).getString("sLogoDoiNha");
								}
								if (jsonArray.getJSONObject(i).has("sLogoDoiKhach")){
									sLogoDoiKhach = jsonArray.getJSONObject(i).getString("sLogoDoiKhach");
								}
								String iID_MaDoiNha = jsonArray.getJSONObject(i).getString("iID_MaDoiNha");
								String iID_MaDoiKhach = jsonArray.getJSONObject(i).getString("iID_MaDoiKhach");
								// Log.e("kkk",sTenGiai +":" +iTrangThai + ":"
								// +sTenDoiNha);iID_MaGiai
								if (i == 0) {
									countryAdapter.addItem(new LiveScore(true, iID_MaTran, sTenGiai, sTenDoiNha, sTenDoiKhach, HT, iPhut, sThoiGian, iC0, tiso, iTrangThai, sMaGiai, sMaDoiNha,
											sMaDoiKhach, iID_MaGiai, bNhanDinhChuyenGia, bGameDuDoan, bDaCapNhapVaoBXH, sLogoQuocGia, sLogoGiai, sLogoDoiNha, sLogoDoiKhach, iID_MaDoiNha, iID_MaDoiKhach));
									count_showdata = count_showdata + 1;
								} else if (i > 0) {
									if ((jsonArray.getJSONObject(i).getString("sTenGiai")).equalsIgnoreCase(jsonArray.getJSONObject(i - 1).getString("sTenGiai"))) {
										countryAdapter.addItem(new LiveScore(false, iID_MaTran, sTenGiai, sTenDoiNha, sTenDoiKhach, HT, iPhut, sThoiGian, iC0, tiso, iTrangThai, sMaGiai, sMaDoiNha,
												sMaDoiKhach, iID_MaGiai, bNhanDinhChuyenGia, bGameDuDoan, bDaCapNhapVaoBXH, sLogoQuocGia, sLogoGiai, sLogoDoiNha, sLogoDoiKhach, iID_MaDoiNha, iID_MaDoiKhach));
										count_showdata = count_showdata + 1;
									} else {
										countryAdapter.addItem(new LiveScore(true, iID_MaTran, sTenGiai, sTenDoiNha, sTenDoiKhach, HT, iPhut, sThoiGian, iC0, tiso, iTrangThai, sMaGiai, sMaDoiNha,
												sMaDoiKhach, iID_MaGiai, bNhanDinhChuyenGia, bGameDuDoan, bDaCapNhapVaoBXH, sLogoQuocGia, sLogoGiai, sLogoDoiNha, sLogoDoiKhach, iID_MaDoiNha, iID_MaDoiKhach));
										count_showdata = count_showdata + 1;
									}
								}
								if(jsonArray.getJSONObject(i).has("totalpage")){
									totalpage = jsonArray.getJSONObject(i).getInt("totalpage");
								}
								
							}
							
						Log.e("maytinhdudoan", "totalpage::" +totalpage);
//						}
						if(count_showdata_old < count_showdata){
							count_showdata_old = count_showdata;
							currentPage = page;
							if(currentPage < totalpage){
								isLoadMore = true;
							}
						}
					} catch (Exception e) {
						Log.e("ERR", e.getMessage());
					}

				}

			}

			@Override
			public void onError(String message) {
				isLoadMore = true;
			}
		};
		listView.setOnScrollListener(new OnScrollListener(){
			@Override
			public void onScrollStateChanged(AbsListView view,
					int scrollState) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				if(totalItemCount > 0 && isLoadMore && totalpage > 1){
					if(totalItemCount - 1 <= firstVisibleItem + visibleItemCount){
						Log.e("load more", "totalItemCount==" + totalItemCount + "::firstVisibleItem==" + firstVisibleItem + "::visibleItemCount==" + visibleItemCount );
						page++;
						loadData();
						isLoadMore = false;
			    	}
				}
			}
			
		});
		loadData();

	
	}
	
	private int page = 1;
	private int totalpage = 1;
	private int currentPage = 1;
	private boolean isLoadMore = true;
	private void loadData() {
		Log.e("Liveco", "loadData");
		String ws = ByUtils.wsFootBall_Lives_Co_MayTinhDuDoan;
		String page_load = ""+page;
		ws =  ws.replace("pageload", page_load);
		Log.e("pram", "param==" + ws);
		new APICaller(listView.getContext()).callApi("", true, callbackAPI,ws);
	}
}
