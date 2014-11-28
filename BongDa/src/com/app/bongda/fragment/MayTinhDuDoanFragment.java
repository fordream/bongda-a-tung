package com.app.bongda.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.StringTokenizer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import android.view.View;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.app.bongda.R;
import com.app.bongda.base.BaseFragment;
import com.app.bongda.base.BongDaBaseAdapter;
import com.app.bongda.callback.APICaller;
import com.app.bongda.callback.APICaller.ICallbackAPI;
import com.app.bongda.callback.progress.LiveScorePorgressExecute;
import com.app.bongda.lazyload.ImageLoader2;
import com.app.bongda.model.GameDuDoan;
import com.app.bongda.model.LiveScore;
import com.app.bongda.util.ByUtils;
import com.app.bongda.util.CommonAndroid;
import com.app.bongda.util.CommonUtil;
import com.app.bongda.view.HeaderView;

public class MayTinhDuDoanFragment extends BaseFragment {
	OnItemClickListener onItemClickListener;
	public ImageLoader2 imageLoader; 
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
		public void showData(int position,Object item, View convertView) {
			final LiveScore liveScore = (LiveScore) item;
			convertView.findViewById(R.id.livescore_header).setVisibility(View.GONE);
			convertView.findViewById(R.id.livescore_main).setVisibility(View.GONE);
			convertView.findViewById(R.id.traitim).setVisibility(View.GONE);
			if(position < 1){
				CommonUtil.getdata(listView.getContext());
//				countryAdapter.notifyDataSetChanged();
			}
			String check_quantam = /*liveScore.idmagiai() + "-" +  */ liveScore.getId() ;
			if (liveScore.isHeader()) {
				convertView.findViewById(R.id.livescore_header).setVisibility(View.VISIBLE);
				convertView.findViewById(R.id.livescore_main).setVisibility(View.VISIBLE);
			} else {
				convertView.findViewById(R.id.livescore_header).setVisibility(View.GONE);
				convertView.findViewById(R.id.livescore_main).setVisibility(View.VISIBLE);
			}
			if (CommonUtil.listQuanTam.contains( check_quantam )) {
				convertView.findViewById(R.id.traitim).setVisibility(View.VISIBLE);
			}else{
				convertView.findViewById(R.id.traitim).setVisibility(View.GONE);
			}
			
			setText(convertView, R.id.text_tengiai, liveScore.sTenGiai()); //ok
			
			int status = 0;
			status = liveScore.iTrangThai();
			if (status >= 2) {
				convertView.findViewById(R.id.TextView_live).setVisibility(View.VISIBLE);// live ok
//				convertView.findViewById(R.id.TextView02_ketqua).setVisibility(View.VISIBLE);
//				setText(convertView, R.id.TextView02_ketqua, liveScore.iTiso());// tiso
//				convertView.findViewById(R.id.ImageView031).setVisibility(View.GONE);
				
				/*setText(convertView, R.id.tv1, liveScore.iHT());
				if (status == 5) {
					setText(convertView, R.id.TextView01, "FT");// time
					convertView.findViewById(R.id.TextView03).setVisibility(View.GONE);// live
				} else if (status == 3) {
					setText(convertView, R.id.TextView01, "HT");// time
				} else if (status >= 10) {
//					setText(convertView, R.id.TextView01, convertView.getContext().getResources().getString(R.string.hoanthidau));
//					convertView.findViewById(R.id.TextView02_ketqua).setVisibility(View.GONE);
					convertView.findViewById(R.id.TextView_live).setVisibility(View.GONE);// live ok
//					convertView.findViewById(R.id.ImageView031).setVisibility(View.VISIBLE);
					java.util.Date localDate1 = new java.util.Date(1000L * Integer.valueOf(liveScore.getDate()));
					Object[] arrayOfObject1 = new Object[2];
					arrayOfObject1[0] = Integer.valueOf(localDate1.getDate());
					arrayOfObject1[1] = Integer.valueOf(1 + localDate1.getMonth());
					setText(convertView, R.id.tv1, String.format("%d/%d", arrayOfObject1));
				} else {
					setText(convertView, R.id.TextView01, liveScore.iPhut() + " '");// time
				}*/
			} else {
				convertView.findViewById(R.id.TextView_live).setVisibility(View.GONE);// live ok
//				convertView.findViewById(R.id.ImageView031).setVisibility(View.VISIBLE);
//				convertView.findViewById(R.id.TextView02_ketqua).setVisibility(View.GONE);
//				setText(convertView, R.id.TextView01, liveScore.getTime());// time
				
				/*int j = Integer.valueOf(liveScore.getDate());
				java.util.Date localDate2 = new java.util.Date(1000L * j);
				System.currentTimeMillis();
				new java.sql.Date(j * 1000);
				Object[] arrayOfObject2 = new Object[2];
				arrayOfObject2[0] = Integer.valueOf(localDate2.getDate());
				arrayOfObject2[1] = Integer.valueOf(1 + localDate2.getMonth());
				setText(convertView, R.id.tv1, String.format("%d/%d", arrayOfObject2));*/
			}
			// setText(convertView, R.id.TextView01, liveScore.getTime());
			setText(convertView, R.id.TextView_tendoinha, liveScore.getName());//ok
			setText(convertView, R.id.TextView_tendoikhach, liveScore.getName2());//ok
			String tiletem = liveScore.sTyLe_ChapBong();
//			tiletem = tiletem.replace("/", ",");
//			Log.e("tylechapbong", "sTyLe_ChapBong=====all:" +tiletem);
			
			try {
				String temp1[];
				int j = tiletem.indexOf(":");
				if (j > 0) {
					String keo1 = "0";
					String keo2 = "0";
					temp1 = tiletem.split(":");
					String keo1_ = temp1[0].trim();
					String keo2_ = temp1[1].trim();
					if(keo1_.indexOf("*") > 0){
						keo1 = keo1_.substring( (keo1_.indexOf("*") + 1));
					}
					if(keo2_.indexOf("*") > 0){
						keo2 = keo2_.substring(0,keo2_.indexOf("*"));
					}
					setText(convertView, R.id.TextView_keo,keo1 + ":" +keo2);
				}else{
					setText(convertView, R.id.TextView_keo, "0:0");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				setText(convertView, R.id.TextView_keo, "0:0");
				e.printStackTrace();
			}
			
			ImageView image= (ImageView) convertView.findViewById(R.id.logogiai);//ok
			imageLoader.DisplayImage(liveScore.sLogoGiai(), image);
		}

		@Override
		public void showData(Object item, View convertView) {
			// TODO Auto-generated method stub
			
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
//		listView.setOnItemClickListener(onItemClickListener);

		listView.setAdapter(countryAdapter);
		imageLoader=new ImageLoader2(getActivity());
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
//							Log.e("kkk", i + ":" + jsonArray.getJSONObject(i).getString("iID_MaGiai") + ":" + bNhanDinhChuyenGia + ":" + bGameDuDoan + ":" + bDaCapNhapVaoBXH);

							String sTyLe_ChapBong_H1 = jsonArray.getJSONObject(i).getString("sTyLe_ChapBong_H1");
							String sTyLe_ChapBong = jsonArray.getJSONObject(i).getString("sTyLe_ChapBong");
							if(sTyLe_ChapBong.equals(null) || sTyLe_ChapBong.equals("")){
								sTyLe_ChapBong = "0*1:1*0";
							}
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
											sMaDoiKhach, iID_MaGiai, bNhanDinhChuyenGia, bGameDuDoan, bDaCapNhapVaoBXH, sLogoQuocGia, sLogoGiai, sLogoDoiNha, sLogoDoiKhach, iID_MaDoiNha, iID_MaDoiKhach,sTyLe_ChapBong));
									count_showdata = count_showdata + 1;
								} else if (i > 0) {
									if ((jsonArray.getJSONObject(i).getString("sTenGiai")).equalsIgnoreCase(jsonArray.getJSONObject(i - 1).getString("sTenGiai"))) {
										countryAdapter.addItem(new LiveScore(false, iID_MaTran, sTenGiai, sTenDoiNha, sTenDoiKhach, HT, iPhut, sThoiGian, iC0, tiso, iTrangThai, sMaGiai, sMaDoiNha,
												sMaDoiKhach, iID_MaGiai, bNhanDinhChuyenGia, bGameDuDoan, bDaCapNhapVaoBXH, sLogoQuocGia, sLogoGiai, sLogoDoiNha, sLogoDoiKhach, iID_MaDoiNha, iID_MaDoiKhach,sTyLe_ChapBong));
										count_showdata = count_showdata + 1;
									} else {
										countryAdapter.addItem(new LiveScore(true, iID_MaTran, sTenGiai, sTenDoiNha, sTenDoiKhach, HT, iPhut, sThoiGian, iC0, tiso, iTrangThai, sMaGiai, sMaDoiNha,
												sMaDoiKhach, iID_MaGiai, bNhanDinhChuyenGia, bGameDuDoan, bDaCapNhapVaoBXH, sLogoQuocGia, sLogoGiai, sLogoDoiNha, sLogoDoiKhach, iID_MaDoiNha, iID_MaDoiKhach,sTyLe_ChapBong));
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
						countryAdapter.notifyDataSetChanged();
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
