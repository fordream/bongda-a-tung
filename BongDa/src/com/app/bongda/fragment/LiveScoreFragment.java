package com.app.bongda.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.bongda.R;
import com.app.bongda.base.BaseFragment;
import com.app.bongda.base.BongDaBaseAdapter;
import com.app.bongda.base.ImageLoaderUtils;
import com.app.bongda.callback.APICaller;
import com.app.bongda.callback.APICaller.ICallbackAPI;
import com.app.bongda.fragment.Backup_LiveScoreFragment.MyTouchListener;
import com.app.bongda.inter.CallBackListenner;
import com.app.bongda.model.GiaiDau;
import com.app.bongda.model.LiveScore;
import com.app.bongda.service.BongDaServiceManager;
import com.app.bongda.util.ByUtils;
import com.app.bongda.util.CommonAndroid;
import com.app.bongda.util.CommonUtil;
import com.app.bongda.view.HeaderView;

public class LiveScoreFragment extends BaseFragment {
	OnItemClickListener onItemClickListener;
	CallBackListenner callBackListenner;
	GiaiDau data;
	String TypeView;
	private int onLoad = 1;
	ImageView img_favorite;
	private int listView_size = 0;
	private String value_list_favorite = "";
	private boolean check_favorite = false;
	private int count_showdata = 0;
	private MyTouchListener mOnTouchListener;
	public LiveScoreFragment(OnItemClickListener onItemClickListener, CallBackListenner callBackListenner, GiaiDau data, String type) {
		super();
		this.callBackListenner = callBackListenner;
		this.onItemClickListener = onItemClickListener;
		this.data = data;
		this.TypeView = type;
	}

	private CountryAdapter countryAdapter = new CountryAdapter();

	private class CountryAdapter extends BongDaBaseAdapter {
		private boolean showdata = false;
		@Override
		public int getLayout() {
			return R.layout.livescore_item;
		}

		@Override
		public void showData(int position, Object item, View convertView) {
			showdata = false;
			final LiveScore liveScore = (LiveScore) item;
			convertView.findViewById(R.id.livescore_header).setVisibility(View.GONE);
			convertView.findViewById(R.id.livescore_main).setVisibility(View.GONE);
			convertView.findViewById(R.id.traitim).setVisibility(View.GONE);
			convertView.findViewById(R.id.livescore_row).setVisibility(View.GONE);
			if(position < 1){
				value_list_favorite = "";
				CommonUtil.getdata(listView.getContext());
				countryAdapter.notifyDataSetChanged();
				Log.e("position", "position::" + position);
			}
//			Log.e("position", "position::" + position);
			
			String check_quantam = liveScore.idmagiai() + "-" +  liveScore.getId() ;
			if (TypeView != null && "quantam".equalsIgnoreCase(TypeView)) {
				if(CommonUtil.listQuanTam.contains( check_quantam ) && !check_quantam.equalsIgnoreCase(value_list_favorite)){
						value_list_favorite = check_quantam;
						check_favorite = true;
						convertView.findViewById(R.id.livescore_header).setVisibility(View.GONE);
						convertView.findViewById(R.id.livescore_main).setVisibility(View.VISIBLE);
						showdata = true;
				}else{
					convertView.findViewById(R.id.livescore_header).setVisibility(View.GONE);
					convertView.findViewById(R.id.livescore_main).setVisibility(View.GONE);
					showdata = false;
				}
			}else{
				if (liveScore.isHeader()) {
					convertView.findViewById(R.id.livescore_header).setVisibility(View.VISIBLE);
					convertView.findViewById(R.id.livescore_main).setVisibility(View.VISIBLE);
				} else {
					convertView.findViewById(R.id.livescore_header).setVisibility(View.GONE);
					convertView.findViewById(R.id.livescore_main).setVisibility(View.VISIBLE);
				}
				showdata = true;
			}
			/*if (TypeView != null) {
				if (TypeView.equalsIgnoreCase("quantam") && CommonUtil.listQuanTam.contains( check_quantam ) && !check_quantam.equalsIgnoreCase(value_list_favorite)) {
					value_list_favorite = check_quantam;
					check_favorite = true;
					int k = 0;
					int k2 = 0;
					String str_check = "";
					boolean check_header = false;
					for(int i = 0; i < CommonUtil.listQuanTam.size(); i++){
						String str1= CommonUtil.listQuanTam.get(i);
						if( str1.equalsIgnoreCase(check_quantam)){
							k = i;
							str_check = CommonUtil.listQuanTam.get(i);
						}
					}
					for(int j = 0; j <= k;j++ ){
						String str1= CommonUtil.listQuanTam.get(j);
						String[] temps = str_check.split("-");
						String str2 =  temps[0]  + "-";
						if(str1.indexOf(str2) > -1){
							k2++;
							if(k2 > 1)check_header = true;
						}
//						Log.e("check222","=======k"+ k + "::" + str1 + "::" + str2 + ":j:" + j + ":k2:" + k2);
						
					}
//					Log.e("check","======="+ check_header + "::" + k);
					if (!check_header) {
						convertView.findViewById(R.id.livescore_header).setVisibility(View.VISIBLE);
						convertView.findViewById(R.id.livescore_main).setVisibility(View.VISIBLE);
					} else {
						convertView.findViewById(R.id.livescore_header).setVisibility(View.GONE);
						convertView.findViewById(R.id.livescore_main).setVisibility(View.VISIBLE);
					}
					convertView.findViewById(R.id.traitim).setVisibility(View.VISIBLE);
				}else{
					convertView.findViewById(R.id.livescore_header).setVisibility(View.GONE);
					convertView.findViewById(R.id.livescore_main).setVisibility(View.GONE);
				}
			}else{
				if (liveScore.isHeader()) {
					convertView.findViewById(R.id.livescore_header).setVisibility(View.VISIBLE);
				} else {
					convertView.findViewById(R.id.livescore_main).setVisibility(View.VISIBLE);
				}
				if(CommonUtil.listQuanTam.contains( check_quantam )){
					convertView.findViewById(R.id.traitim).setVisibility(View.VISIBLE);
				}else{
					convertView.findViewById(R.id.traitim).setVisibility(View.GONE);
				}
			}*/

			/*if(position >= count_showdata){
				if ( TypeView != null ) {
					if(TypeView.equalsIgnoreCase("quantam")){
						if(!check_favorite){
							views_err.setVisibility(View.VISIBLE);
							views_err.setText(listView.getContext().getResources().getString(R.string.khongcodoiyeuthich));
						}else{
							views_err.setVisibility(View.GONE);
						}
						
					}
				}
			}*/
			
			
			if(showdata){
				convertView.findViewById(R.id.livescore_row).setVisibility(View.VISIBLE);
				// cogamedudoan
				if (liveScore.isGameDuDoan()) {
					convertView.findViewById(R.id.gamedudoan_icon).setVisibility(View.VISIBLE);
					convertView.findViewById(R.id.gamedudoan_icon).setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							callBackListenner.onCallBackListenner(1, liveScore);
						}
					});
				} else {
					convertView.findViewById(R.id.gamedudoan_icon).setVisibility(View.GONE);
				}
	
				// coykienchuyengia
				if (liveScore.isNhanDinhChuyenGia()) {
					convertView.findViewById(R.id.persion).setVisibility(View.VISIBLE);
				} else {
					convertView.findViewById(R.id.persion).setVisibility(View.GONE);
				}
	
				// cobangxephang
				/*
				 * if (liveScore.isDaCapNhapVaoBXH()) {
				 * convertView.findViewById(R.id
				 * .bangxephang_icon).setVisibility(View.VISIBLE);
				 * convertView.findViewById
				 * (R.id.bangxephang_icon).setOnClickListener(new OnClickListener()
				 * {
				 * 
				 * @Override public void onClick(View v) {
				 * callBackListenner.onCallBackListenner(0, liveScore); } }); } else
				 * {
				 * convertView.findViewById(R.id.bangxephang_icon).setVisibility(View
				 * .GONE); }
				 */
	
				setText(convertView, R.id.textView1, liveScore.sTenGiai());
	
				int status = 0;
				status = liveScore.iTrangThai();
				if (status >= 2) {
					convertView.findViewById(R.id.TextView03).setVisibility(View.VISIBLE);// live
					convertView.findViewById(R.id.TextView02_ketqua).setVisibility(View.VISIBLE);
					setText(convertView, R.id.TextView02_ketqua, liveScore.iTiso());// tiso
					convertView.findViewById(R.id.ImageView031).setVisibility(View.GONE);
					setText(convertView, R.id.tv1, liveScore.iHT());
					if (status == 5) {
						setText(convertView, R.id.TextView01, "FT");// time
						convertView.findViewById(R.id.TextView03).setVisibility(View.GONE);// live
					} else if (status == 3) {
						setText(convertView, R.id.TextView01, "HT");// time
					} else if (status >= 10) {
						setText(convertView, R.id.TextView01, convertView.getContext().getResources().getString(R.string.hoanthidau));
						convertView.findViewById(R.id.TextView02_ketqua).setVisibility(View.GONE);
						convertView.findViewById(R.id.TextView03).setVisibility(View.GONE);// live
						convertView.findViewById(R.id.ImageView031).setVisibility(View.VISIBLE);
						java.util.Date localDate1 = new java.util.Date(1000L * Integer.valueOf(liveScore.getDate()));
						Object[] arrayOfObject1 = new Object[2];
						arrayOfObject1[0] = Integer.valueOf(localDate1.getDate());
						arrayOfObject1[1] = Integer.valueOf(1 + localDate1.getMonth());
						setText(convertView, R.id.tv1, String.format("%d/%d", arrayOfObject1));
					} else {
						setText(convertView, R.id.TextView01, liveScore.iPhut() + " '");// time
					}
				} else {
					convertView.findViewById(R.id.TextView03).setVisibility(View.GONE);// live
					convertView.findViewById(R.id.ImageView031).setVisibility(View.VISIBLE);
					convertView.findViewById(R.id.TextView02_ketqua).setVisibility(View.GONE);
					setText(convertView, R.id.TextView01, liveScore.getTime());// time
					int j = Integer.valueOf(liveScore.getDate());
					java.util.Date localDate2 = new java.util.Date(1000L * j);
					System.currentTimeMillis();
					new java.sql.Date(j * 1000);
					Object[] arrayOfObject2 = new Object[2];
					arrayOfObject2[0] = Integer.valueOf(localDate2.getDate());
					arrayOfObject2[1] = Integer.valueOf(1 + localDate2.getMonth());
					setText(convertView, R.id.tv1, String.format("%d/%d", arrayOfObject2));
				}
				// setText(convertView, R.id.TextView01, liveScore.getTime());
				setText(convertView, R.id.TextView02, liveScore.getName());
				setText(convertView, R.id.TextView023, liveScore.getName2());
				// setText(convertView, R.id.tv1, liveScore.getDate());
				ImageLoaderUtils.getInstance(getActivity()).DisplayImage(liveScore.sLogoGiai(), (ImageView) convertView.findViewById(R.id.logogiai));
	
				convertView.findViewById(R.id.image_bangxephang).setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						callBackListenner.onCallBackListenner(2, liveScore);
					}
				});
				// convertView.findViewById(R.id.bangxephang_icon).setVisibility(View.VISIBLE);
				convertView.findViewById(R.id.phongdo_icon).setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Log.e("livescore", "liveScore.iID_MaTran===" + liveScore.iID_MaTran());
						callBackListenner.onCallBackListenner(0, liveScore);
					}
				});
				
				if (addfavorite) {
					convertView.findViewById(R.id.iconlike).setVisibility(View.VISIBLE);
					// show tran quan tam
					img_favorite = (ImageView) convertView.findViewById(R.id.iconlike);
					if (CommonUtil.listQuanTam.contains( check_quantam )) {
						// convertView.findViewById(R.id.traitim).setVisibility(View.VISIBLE);
						img_favorite.setImageResource(R.drawable.ico_favorite_on);
					} else {
						// convertView.findViewById(R.id.traitim).setVisibility(View.GONE);
						img_favorite.setImageResource(R.drawable.ico_favorite_off);
					}
					convertView.findViewById(R.id.iconlike).setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							String check_quantam = liveScore.idmagiai() + "-" +  liveScore.getId() ;
							if (CommonUtil.listQuanTam.contains( check_quantam )) {
								CommonUtil.listQuanTam.remove( check_quantam );
								CommonUtil.savedata(v.getContext());
								img_favorite.setImageResource(R.drawable.ico_favorite_on);
							} else {
								CommonUtil.listQuanTam.add( check_quantam );
								CommonUtil.savedata(v.getContext());
								img_favorite.setImageResource(R.drawable.ico_favorite_off);
							}
							CommonUtil.getdata(listView.getContext());
							countryAdapter.notifyDataSetChanged();
						}
					});
				} else {
					convertView.findViewById(R.id.iconlike).setVisibility(View.GONE);
				}
				mOnTouchListener = new MyTouchListener(liveScore);
				convertView.setOnTouchListener(mOnTouchListener);
			
			}else{
				convertView.findViewById(R.id.livescore_row).setVisibility(View.GONE);
			}
		}

		@Override
		public void showData(Object item, View convertView) {

		}

	}

	@Override
	public int getLayout() {
		return R.layout.livesocre;
	}

	private int action_down_x = 0;
	private int action_up_x = 0;
	public static int difference = 0;
	private boolean addfavorite = false;
	ListView listView;
	TextView views_err;
	@Override
	public void onInitCreateView(View view) {
		Log.e("livescore", "onInitCreateView===" + TypeView);
		/**
		 * init header view
		 */
		HeaderView headerView = (HeaderView) view.findViewById(R.id.headerView1);
		if (TypeView == null) {
			headerView.setTextHeader(R.string.livescore);
		} else {
			if (TypeView.equalsIgnoreCase("quantam")) {
				headerView.setTextHeader(R.string.tranquantam);
			} else {
				headerView.setTextHeader(R.string.livescore);
			}
		}
		/** init data */
		listView = (ListView) view.findViewById(R.id.listView1);
		listView.setAdapter(countryAdapter);

		headerView.findViewById(R.id.Button05).setVisibility(View.VISIBLE);
		headerView.findViewById(R.id.Button05).setOnClickListener(clickListener);
		if (TypeView != null) {
			if (TypeView.equalsIgnoreCase("quantam") ){
				CommonUtil.getdata(getActivity());
			}
		}	
		views_err = (TextView) view.findViewById(R.id.error_txt);
	}

	View.OnClickListener clickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {

			if (v.getId() == R.id.Button05) {
				addfavorite = addfavorite == true ? false : true;
				countryAdapter.notifyDataSetChanged();
			}
		}
	};

	ICallbackAPI callbackAPI;

	private void loadData() {
		Log.e("Liveco", "loadData");
		check_favorite = false;
		CommonUtil.getdata(listView.getContext());
		String maGiaiDau = data == null ? null : data.getId();
		String ws = ByUtils.wsFootBall_Lives;
		if (maGiaiDau == null) {
			new APICaller(listView.getContext()).callApi("", true, callbackAPI,ws);
		} else {
			String ws_theogiai = (ByUtils.wsFootBall_Lives_Theo_Giai).replace("magiai", maGiaiDau);
			Log.e("livaco","param="+ ws_theogiai);
			new APICaller(listView.getContext()).callApi("", true, callbackAPI, ws_theogiai);
		}
//		onLoad++;
	}

	@SuppressWarnings("unused")
	@Override
	public void onInitData() {
		views_err.setVisibility(View.GONE);
		Log.e("Liveco", "onInitData");
//		if (callbackAPI == null) {
			callbackAPI = new ICallbackAPI() {
				@Override
				public void onSuccess(String response) {
					countryAdapter.clear();
					String string_temp = CommonAndroid.parseXMLAction(response);
					if (!string_temp.equalsIgnoreCase("")) {
//						Log.e("data", string_temp);
						try {
							ArrayList<JSONObject> array = new ArrayList<JSONObject>();
							array.clear();
							JSONArray jsonArray = new JSONArray(string_temp);
								
							if (jsonArray.length() == 0) {
								views_err.setVisibility(View.VISIBLE);
								views_err.setText(listView.getContext().getResources().getString(R.string.giaichuabatdau));
//								Toast.makeText(listView.getContext(), listView.getContext().getResources().getString(R.string.giaichuabatdau), Toast.LENGTH_LONG).show();
							}else{
								views_err.setVisibility(View.GONE);
							}
							
							for (int i = 0; i < jsonArray.length(); i++) {
								try {
									if (TypeView != null) {
										array.add(jsonArray.getJSONObject(i));
										if (TypeView.equalsIgnoreCase("quantam")) {
											String check_quantam =jsonArray.getJSONObject(i).getString("iID_MaGiai") + "-" +  jsonArray.getJSONObject(i).getString("iID_MaTran");
//											String matran = jsonArray.getJSONObject(i).getString("iID_MaTran");
											if (CommonUtil.listQuanTam.contains(check_quantam)) {
												check_favorite = true;
											}
										}
									} else {
										array.add(jsonArray.getJSONObject(i));
									}

								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							if (TypeView != null ) {
								if(TypeView.equalsIgnoreCase("quantam") && !check_favorite){
									views_err.setVisibility(View.VISIBLE);
									views_err.setText(listView.getContext().getResources().getString(R.string.khongcodoiyeuthich));
								}
							}
							listView_size = jsonArray.length();
							Collections.emptyList();
							Collections.sort(array, new Comparator<JSONObject>() {

								@Override
								public int compare(JSONObject lhs, JSONObject rhs) {
									// TODO Auto-generated method stub

									try {
										return (lhs.getString("sTenGiai").toLowerCase().compareTo(rhs.getString("sTenGiai").toLowerCase()));
									} catch (JSONException e) {
										// TODO Auto-generated catch
										// block
										e.printStackTrace();
										return 0;
									}
								}
							});

							for (int i = 0; i < array.size(); i++) {
								boolean bNhanDinhChuyenGia = array.get(i).getBoolean("bNhanDinhChuyenGia");
								boolean bGameDuDoan = array.get(i).getBoolean("bGameDuDoan");
								boolean bDaCapNhapVaoBXH = array.get(i).getBoolean("bDaCapNhapVaoBXH");
//								Log.e("kkk", i + ":" + array.get(i).getString("iID_MaGiai") + ":" + bNhanDinhChuyenGia + ":" + bGameDuDoan + ":" + bDaCapNhapVaoBXH);

								// String kk =
								// array.get(i).getString("sTenGiai");
								String HT = "";
								StringBuilder stringbuilder1 = new StringBuilder("HT ");
								HT = stringbuilder1.append(array.get(i).getString("iCN_BanThang_DoiNha_HT")).append(" - ").append(array.get(i).getString("iCN_BanThang_DoiKhach_HT")).toString();

								String Banthang = (new StringBuilder()).append(array.get(i).getString("iCN_BanThang_DoiNha")).append(" - ").append(array.get(i).getString("iCN_BanThang_DoiKhach"))
										.toString();
								String iID_MaGiai = array.get(i).getString("iID_MaGiai");
								String sTenGiai = array.get(i).getString("sTenGiai");
								String sTenDoiNha = array.get(i).getString("sTenDoiNha");
								String sTenDoiKhach = array.get(i).getString("sTenDoiKhach");
								int iTrangThai = Integer.parseInt(array.get(i).getString("iTrangThai"));
								String iID_MaTran = array.get(i).getString("iID_MaTran");
								String iC0 = array.get(i).getString("iC0");// ngay
																			// thi
																			// dau
								String iPhut = array.get(i).getString("iPhut");
								String sThoiGian = array.get(i).getString("sThoiGian");// thoi
																						// gian
																						// thi
																						// dau
								String tiso = array.get(i).getString("iCN_BanThang_DoiNha") + " - " + array.get(i).getString("iCN_BanThang_DoiKhach");
								String sMaGiai = array.get(i).getString("sMaGiai");
								String sMaDoiNha = array.get(i).getString("sMaDoiNha");
								String sMaDoiKhach = array.get(i).getString("sMaDoiKhach");
								String sLogoQuocGia = "";
								String sLogoGiai = "";
								String sLogoDoiNha = "";
								String sLogoDoiKhach = "";
								if (array.get(i).has("sLogoQuocGia")){
									sLogoQuocGia = array.get(i).getString("sLogoQuocGia");
								}
								if (array.get(i).has("sLogoGiai")){
									sLogoGiai = array.get(i).getString("sLogoGiai");
								}
								if (array.get(i).has("sLogoDoiNha")){
									sLogoDoiNha = array.get(i).getString("sLogoDoiNha");
								}
								if (array.get(i).has("sLogoDoiKhach")){
									sLogoDoiKhach = array.get(i).getString("sLogoDoiKhach");
								}
								String iID_MaDoiNha = array.get(i).getString("iID_MaDoiNha");
								String iID_MaDoiKhach = array.get(i).getString("iID_MaDoiKhach");
								// Log.e("kkk",sTenGiai +":" +iTrangThai + ":"
								// +sTenDoiNha);iID_MaGiai
								if (i == 0) {
									countryAdapter.addItem(new LiveScore(true, iID_MaTran, sTenGiai, sTenDoiNha, sTenDoiKhach, HT, iPhut, sThoiGian, iC0, tiso, iTrangThai, sMaGiai, sMaDoiNha,
											sMaDoiKhach, iID_MaGiai, bNhanDinhChuyenGia, bGameDuDoan, bDaCapNhapVaoBXH, sLogoQuocGia, sLogoGiai, sLogoDoiNha, sLogoDoiKhach, iID_MaDoiNha, iID_MaDoiKhach));
									count_showdata = count_showdata + 1;
								} else if (i > 0) {
									if ((array.get(i).getString("sTenGiai")).equalsIgnoreCase(array.get(i - 1).getString("sTenGiai"))) {
										countryAdapter.addItem(new LiveScore(false, iID_MaTran, sTenGiai, sTenDoiNha, sTenDoiKhach, HT, iPhut, sThoiGian, iC0, tiso, iTrangThai, sMaGiai, sMaDoiNha,
												sMaDoiKhach, iID_MaGiai, bNhanDinhChuyenGia, bGameDuDoan, bDaCapNhapVaoBXH, sLogoQuocGia, sLogoGiai, sLogoDoiNha, sLogoDoiKhach, iID_MaDoiNha, iID_MaDoiKhach));
										count_showdata = count_showdata + 1;
									} else {
										countryAdapter.addItem(new LiveScore(true, iID_MaTran, sTenGiai, sTenDoiNha, sTenDoiKhach, HT, iPhut, sThoiGian, iC0, tiso, iTrangThai, sMaGiai, sMaDoiNha,
												sMaDoiKhach, iID_MaGiai, bNhanDinhChuyenGia, bGameDuDoan, bDaCapNhapVaoBXH, sLogoQuocGia, sLogoGiai, sLogoDoiNha, sLogoDoiKhach, iID_MaDoiNha, iID_MaDoiKhach));
										count_showdata = count_showdata + 1;
									}
								}

							}
							Log.e("aaaaaaa","count_showdata::" +count_showdata);
							if (onLoad != 1) {
								countryAdapter.notifyDataSetChanged();
							}
						} catch (Exception e) {
							Log.e("ERR", e.getMessage());
//							views_err.setVisibility(View.VISIBLE);
//							views_err.setText(listView.getContext().getResources().getString(R.string.giaichuabatdau));
						}

					}

				}

				@Override
				public void onError(String message) {
				}
			};
//		}
		loadData();

	}

	class MyTouchListener implements OnTouchListener {
		LiveScore liveScore;

		public MyTouchListener(LiveScore liveScore1) {
			// TODO Auto-generated constructor stub
			this.liveScore = liveScore1;
		}

		@Override
		public boolean onTouch(View v, MotionEvent event) {

			int action = event.getAction();
			switch (action) {
			case MotionEvent.ACTION_DOWN:
				action_down_x = (int) event.getX();
				break;
			case MotionEvent.ACTION_MOVE:
				Log.e("action", "ACTION_MOVE - ");
				action_up_x = (int) event.getX();
				difference = action_down_x - action_up_x;
				break;
			case MotionEvent.ACTION_UP:
				Log.e("action", "ACTION_UP - ");
				// if(difference <= 10 && difference >= -10){
				// callBackListenner.onCallBackListenner(5, liveScore);
				// }else{
				// calcuateDifference(liveScore);
				// }
				if (!addfavorite) {
					callBackListenner.onCallBackListenner(5, liveScore);
				}
				break;
			}
			return true;
		}
	}
}
