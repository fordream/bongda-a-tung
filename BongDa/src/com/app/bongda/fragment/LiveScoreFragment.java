package com.app.bongda.fragment;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.bongda.R;
import com.app.bongda.base.BaseFragment;
import com.app.bongda.base.BongDaBaseAdapter;
import com.app.bongda.base.ImageLoaderUtils;
import com.app.bongda.callback.APICaller;
import com.app.bongda.callback.APICaller.ICallbackAPI;
import com.app.bongda.callback.progress.LiveScorePorgressExecute;
import com.app.bongda.hander.LiveScoreHander;
import com.app.bongda.inter.CallBackListenner;
import com.app.bongda.lazyload.ImageLoader2;
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
	private int count_showdata_old = 0;
	private int count_showdata = 0;

	// private MyTouchListener mOnTouchListener;
	public LiveScoreFragment(OnItemClickListener onItemClickListener, CallBackListenner callBackListenner, GiaiDau data, String type) {
		super();
		this.callBackListenner = callBackListenner;
		this.onItemClickListener = onItemClickListener;
		this.data = data;
		this.TypeView = type;
	}

	private LiveScoreAdapter countryAdapter = new LiveScoreAdapter();

	private class LiveScoreAdapter extends BongDaBaseAdapter {
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
			if (position < 1) {
				// value_list_favorite = "";
				CommonUtil.getdata(listView.getContext());
				countryAdapter.notifyDataSetChanged();
			}

			String check_quantam = /* liveScore.idmagiai() + "-" + */liveScore.getId();
			if (liveScore.isHeader()) {
				convertView.findViewById(R.id.livescore_header).setVisibility(View.VISIBLE);
				convertView.findViewById(R.id.livescore_main).setVisibility(View.VISIBLE);
			} else {
				convertView.findViewById(R.id.livescore_header).setVisibility(View.GONE);
				convertView.findViewById(R.id.livescore_main).setVisibility(View.VISIBLE);
			}
			// showdata = true;

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
					if(liveScore.gettrangthaitiso()){
						((TextView) convertView.findViewById(R.id.TextView01)).setBackgroundColor(Color.YELLOW);
						LinearLayout ImageView03_ = (LinearLayout) convertView.findViewById(R.id.ImageView03);
						ImageView03_.setBackgroundColor(Color.YELLOW);
						((TextView) convertView.findViewById(R.id.tv1)).setBackgroundColor(Color.YELLOW);
						
					}else{
						((TextView) convertView.findViewById(R.id.TextView01)).setBackgroundColor(Color.WHITE);
						LinearLayout ImageView03_ = (LinearLayout) convertView.findViewById(R.id.ImageView03);
						ImageView03_.setBackgroundColor(Color.WHITE);
						((TextView) convertView.findViewById(R.id.tv1)).setBackgroundColor(Color.WHITE);
					}
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
			ImageView image = (ImageView) convertView.findViewById(R.id.logogiai);
			// text.setText("item "+position);
			imageLoader.DisplayImage(liveScore.sLogoGiai(), image);
			convertView.findViewById(R.id.image_bangxephang).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					callBackListenner.onCallBackListenner(2, liveScore);
				}
			});
			convertView.findViewById(R.id.phongdo_icon).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Log.e("livescore", "liveScore.iID_MaTran===" + liveScore.iID_MaTran());
					callBackListenner.onCallBackListenner(0, liveScore);
				}
			});

			convertView.findViewById(R.id.persion).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					callBackListenner.onCallBackListenner(3, liveScore);
				}
			});

			// show tran quan tam
			img_favorite = (ImageView) convertView.findViewById(R.id.iconlike);

			if (CommonUtil.listQuanTam.contains(check_quantam)) {
				convertView.findViewById(R.id.traitim).setVisibility(View.VISIBLE);
				img_favorite.setImageResource(R.drawable.ico_favorite_on);
			} else {
				convertView.findViewById(R.id.traitim).setVisibility(View.GONE);
				img_favorite.setImageResource(R.drawable.ico_favorite_off);
			}

			// mOnTouchListener = new MyTouchListener(liveScore);
			convertView.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					int action = event.getAction();
					switch (action) {
					case MotionEvent.ACTION_DOWN:
						// Log.e("action", "ACTION_DOWN - " + action_down_x);
						action_down_x = (int) event.getX();
						break;
					case MotionEvent.ACTION_MOVE:
						action_up_x = (int) event.getX();
						// Log.e("action", "ACTION_MOVE :: " + action_down_x +
						// ":::"+ action_up_x);

						difference = action_down_x - action_up_x;
						if (difference > delta1 || difference < delta2) {
							calcuateDifference(liveScore);
						}
						break;
					case MotionEvent.ACTION_UP:
						// Log.e("action", "ACTION_UP - ");

						if (!addfavorite) {
							callBackListenner.onCallBackListenner(5, liveScore);
						} else {
							if (difference <= delta1 && difference >= delta2) {
								callBackListenner.onCallBackListenner(5, liveScore);
							} else {
								calcuateDifference(liveScore);
							}
						}
						break;
					}
					return true;
				}
			});

			// }else{
			// convertView.findViewById(R.id.livescore_row).setVisibility(View.GONE);
			// }
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
	public ImageLoader2 imageLoader;

	@Override
	public void onInitCreateView(View view) {
		Log.e("livescore", "onInitCreateView===" + TypeView);
		/**
		 * init header view
		 */
		HeaderView headerView = (HeaderView) view.findViewById(R.id.headerView1);
		View customeProgressbar = headerView.findViewById(R.id.Button01);
		customeProgressbar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				/*
				 * clear data and reload new data
				 */

				countryAdapter.clear();
				page = 1;
				totalpage = 1;
				currentPage = 1;
				loadData();
			}
		});

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

		if (TypeView == null) {
			// headerView.findViewById(R.id.Button05).setVisibility(View.VISIBLE);
			// headerView.findViewById(R.id.Button05).setOnClickListener(clickListener);
			if (TypeView != null) {
				if (TypeView.equalsIgnoreCase("quantam")) {
					CommonUtil.getdata(getActivity());
				}
			}
		}

		views_err = (TextView) view.findViewById(R.id.error_txt);
		imageLoader = new ImageLoader2(getActivity());

		liveScoreHander = new LiveScoreHander() {
			@Override
			public void executeLiveScore() {
				refresh();
			}
		};

		liveScoreHander.sendEmptyMessageDelayed(0, LiveScoreHander.TIME);
	}

	// ** add load refresh
	private LiveScoreHander liveScoreHander;

	private void refresh() {
		// load all page
		for (int i = 0; i < currentPage; i++) {
			refresh(i);
		}
	}

	private void refresh(int page) {
		ICallbackAPI callbackAPI = new ICallbackAPI() {

			@Override
			public void onSuccess(String response) {
				// save and compare data
				String string_temp = CommonAndroid.parseXMLAction(response);
				if (!string_temp.equalsIgnoreCase("")) {
					// FIXME TRUONGVV
					try {
						JSONArray jsonArray = new JSONArray(string_temp);

						for (int i = 0; i < jsonArray.length(); i++) {
							boolean bNhanDinhChuyenGia = jsonArray.getJSONObject(i).getBoolean("bNhanDinhChuyenGia");
							boolean bGameDuDoan = jsonArray.getJSONObject(i).getBoolean("bGameDuDoan");
							boolean bDaCapNhapVaoBXH = jsonArray.getJSONObject(i).getBoolean("bDaCapNhapVaoBXH");
							if (TypeView == null || (("nhandinhchuyengia".equalsIgnoreCase(TypeView) && bNhanDinhChuyenGia)) //
									|| "phongdo".equalsIgnoreCase(TypeView) || "theogiai".equalsIgnoreCase(TypeView)//
							) {
								String HT = "";
								StringBuilder stringbuilder1 = new StringBuilder("HT ");
								HT = stringbuilder1.append(jsonArray.getJSONObject(i).getString("iCN_BanThang_DoiNha_HT")).append(" - ")
										.append(jsonArray.getJSONObject(i).getString("iCN_BanThang_DoiKhach_HT")).toString();

								String Banthang = (new StringBuilder()).append(jsonArray.getJSONObject(i).getString("iCN_BanThang_DoiNha")).append(" - ")
										.append(jsonArray.getJSONObject(i).getString("iCN_BanThang_DoiKhach")).toString();
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
								if (jsonArray.getJSONObject(i).has("sLogoQuocGia")) {
									sLogoQuocGia = jsonArray.getJSONObject(i).getString("sLogoQuocGia");
								}
								if (jsonArray.getJSONObject(i).has("sLogoGiai")) {
									sLogoGiai = jsonArray.getJSONObject(i).getString("sLogoGiai");
								}
								if (jsonArray.getJSONObject(i).has("sLogoDoiNha")) {
									sLogoDoiNha = jsonArray.getJSONObject(i).getString("sLogoDoiNha");
								}
								if (jsonArray.getJSONObject(i).has("sLogoDoiKhach")) {
									sLogoDoiKhach = jsonArray.getJSONObject(i).getString("sLogoDoiKhach");
								}
								String iID_MaDoiNha = jsonArray.getJSONObject(i).getString("iID_MaDoiNha");
								String iID_MaDoiKhach = jsonArray.getJSONObject(i).getString("iID_MaDoiKhach");

								// update list of adapter
								// FIXME TRUONGVV
								for(int j = 0;j < countryAdapter.getCount();j++){
									LiveScore liveupdate = (LiveScore) countryAdapter.getItem(j);
									if(iID_MaTran.equals(liveupdate.iID_MaTran())){
										liveupdate.setiTrangThai(iTrangThai);
										liveupdate.setiPhut(iPhut);
										if( !tiso.equals(liveupdate.iTiso())){
											liveupdate.settrangthaitiso(true);
										}else{
											liveupdate.settrangthaitiso(false);
										}
										liveupdate.setiTiso(tiso);
										liveupdate.setHT(HT);
										break;
									}
								}
								
							}
						}
					} catch (Exception e) {
					}
				}

				// refresh adapter
				countryAdapter.notifyDataSetChanged();
			}

			@Override
			public void onError(String message) {

			}
		};
		// CommonUtil.getdata(listView.getContext());
		String maGiaiDau = data == null ? null : data.getId();
		String ws = ByUtils.wsFootBall_Lives_page; // ByUtils.wsFootBall_Lives
		if (maGiaiDau == null) {
			String page_load = "" + (page + 1);
			ws = ws.replace("pageload", page_load);
		} else {
			ws = (ByUtils.wsFootBall_Lives_Theo_Giai).replace("magiai", maGiaiDau);
		}

		new APICaller(listView.getContext()).callApi("", false, callbackAPI, ws);
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
	private int page = 1;
	private int totalpage = 1;
	private int currentPage = 1;
	private boolean isLoadMore = true;

	private void loadData() {
		Log.e("Liveco", "loadData");
		check_favorite = false;
		CommonUtil.getdata(listView.getContext());
		String maGiaiDau = data == null ? null : data.getId();
		String ws = ByUtils.wsFootBall_Lives_page; // ByUtils.wsFootBall_Lives
		if (maGiaiDau == null) {
			String page_load = "" + page;
			ws = ws.replace("pageload", page_load);
			Log.e("pram", "param==" + ws);
			new APICaller(listView.getContext()).callApi("", true, callbackAPI, ws);
		} else {
			String ws_theogiai = (ByUtils.wsFootBall_Lives_Theo_Giai).replace("magiai", maGiaiDau);
			Log.e("livaco", "param=" + ws_theogiai);
			new APICaller(listView.getContext()).callApi("", true, callbackAPI, ws_theogiai);
		}
		onLoad++;
	}

	private boolean check_null = false;

	@SuppressWarnings("unused")
	@Override
	public void onInitData() {
		views_err.setVisibility(View.GONE);
		Log.e("Liveco", "onInitData");
		// if (callbackAPI == null) {
		callbackAPI = new ICallbackAPI() {
			@Override
			public void onSuccess(String response) {
				new LiveScorePorgressExecute(response, views_err.getContext()).executeAsynCallBack();
				if (page == 1) {
					countryAdapter.clear();
				}
				String string_temp = CommonAndroid.parseXMLAction(response);
				if (!string_temp.equalsIgnoreCase("")) {
					try {
						JSONArray jsonArray = new JSONArray(string_temp);

						if (jsonArray.length() == 0) {
							views_err.setVisibility(View.VISIBLE);
							views_err.setText(listView.getContext().getResources().getString(R.string.giaichuabatdau));
						} else {
							views_err.setVisibility(View.GONE);
						}

						if (TypeView != null) {
							if (TypeView.equalsIgnoreCase("quantam") && !check_favorite) {
								views_err.setVisibility(View.VISIBLE);
								views_err.setText(listView.getContext().getResources().getString(R.string.khongcodoiyeuthich));
							}
						}
						listView_size = jsonArray.length();

						for (int i = 0; i < jsonArray.length(); i++) {
							boolean bNhanDinhChuyenGia = jsonArray.getJSONObject(i).getBoolean("bNhanDinhChuyenGia");
							boolean bGameDuDoan = jsonArray.getJSONObject(i).getBoolean("bGameDuDoan");
							boolean bDaCapNhapVaoBXH = jsonArray.getJSONObject(i).getBoolean("bDaCapNhapVaoBXH");
							if (TypeView == null || (("nhandinhchuyengia".equalsIgnoreCase(TypeView) && bNhanDinhChuyenGia)) || "phongdo".equalsIgnoreCase(TypeView)
									|| "theogiai".equalsIgnoreCase(TypeView)) {
								check_null = true;
								String HT = "";
								StringBuilder stringbuilder1 = new StringBuilder("HT ");
								HT = stringbuilder1.append(jsonArray.getJSONObject(i).getString("iCN_BanThang_DoiNha_HT")).append(" - ")
										.append(jsonArray.getJSONObject(i).getString("iCN_BanThang_DoiKhach_HT")).toString();

								String Banthang = (new StringBuilder()).append(jsonArray.getJSONObject(i).getString("iCN_BanThang_DoiNha")).append(" - ")
										.append(jsonArray.getJSONObject(i).getString("iCN_BanThang_DoiKhach")).toString();
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
								if (jsonArray.getJSONObject(i).has("sLogoQuocGia")) {
									sLogoQuocGia = jsonArray.getJSONObject(i).getString("sLogoQuocGia");
								}
								if (jsonArray.getJSONObject(i).has("sLogoGiai")) {
									sLogoGiai = jsonArray.getJSONObject(i).getString("sLogoGiai");
								}
								if (jsonArray.getJSONObject(i).has("sLogoDoiNha")) {
									sLogoDoiNha = jsonArray.getJSONObject(i).getString("sLogoDoiNha");
								}
								if (jsonArray.getJSONObject(i).has("sLogoDoiKhach")) {
									sLogoDoiKhach = jsonArray.getJSONObject(i).getString("sLogoDoiKhach");
								}
								String iID_MaDoiNha = jsonArray.getJSONObject(i).getString("iID_MaDoiNha");
								String iID_MaDoiKhach = jsonArray.getJSONObject(i).getString("iID_MaDoiKhach");
								if (i == 0) {
									countryAdapter.addItem(new LiveScore(true, iID_MaTran, sTenGiai, sTenDoiNha, sTenDoiKhach, HT, iPhut, sThoiGian, iC0, tiso, iTrangThai, sMaGiai, sMaDoiNha,
											sMaDoiKhach, iID_MaGiai, bNhanDinhChuyenGia, bGameDuDoan, bDaCapNhapVaoBXH, sLogoQuocGia, sLogoGiai, sLogoDoiNha, sLogoDoiKhach, iID_MaDoiNha,
											iID_MaDoiKhach));
									count_showdata = count_showdata + 1;
								} else if (i > 0) {
									if ((jsonArray.getJSONObject(i).getString("sTenGiai")).equalsIgnoreCase(jsonArray.getJSONObject(i - 1).getString("sTenGiai"))) {
										countryAdapter.addItem(new LiveScore(false, iID_MaTran, sTenGiai, sTenDoiNha, sTenDoiKhach, HT, iPhut, sThoiGian, iC0, tiso, iTrangThai, sMaGiai, sMaDoiNha,
												sMaDoiKhach, iID_MaGiai, bNhanDinhChuyenGia, bGameDuDoan, bDaCapNhapVaoBXH, sLogoQuocGia, sLogoGiai, sLogoDoiNha, sLogoDoiKhach, iID_MaDoiNha,
												iID_MaDoiKhach));
										count_showdata = count_showdata + 1;
									} else {
										countryAdapter.addItem(new LiveScore(true, iID_MaTran, sTenGiai, sTenDoiNha, sTenDoiKhach, HT, iPhut, sThoiGian, iC0, tiso, iTrangThai, sMaGiai, sMaDoiNha,
												sMaDoiKhach, iID_MaGiai, bNhanDinhChuyenGia, bGameDuDoan, bDaCapNhapVaoBXH, sLogoQuocGia, sLogoGiai, sLogoDoiNha, sLogoDoiKhach, iID_MaDoiNha,
												iID_MaDoiKhach));
										count_showdata = count_showdata + 1;
									}
								}
								if (jsonArray.getJSONObject(i).has("totalpage")) {
									totalpage = jsonArray.getJSONObject(i).getInt("totalpage");
								}

							}

						}
						if (count_showdata_old < count_showdata) {
							count_showdata_old = count_showdata;
							currentPage = page;
							if (currentPage < totalpage) {
								isLoadMore = true;
							}
						}
						countryAdapter.notifyDataSetChanged();
						if (!check_null) {
							if (TypeView != null && "nhandinhchuyengia".equalsIgnoreCase(TypeView)) {
								views_err.setVisibility(View.VISIBLE);
								views_err.setText(listView.getContext().getResources().getString(R.string.khongconhandinhchuyengia));
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
		// }
		listView.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				if (totalItemCount > 0 && isLoadMore && totalpage > 1) {
					if (totalItemCount - 1 <= firstVisibleItem + visibleItemCount) {
						Log.e("load more", "totalItemCount==" + totalItemCount + "::firstVisibleItem==" + firstVisibleItem + "::visibleItemCount==" + visibleItemCount);
						page++;
						loadData();
						isLoadMore = false;
					}
				}
			}

		});
		loadData();

	}

	private int delta1 = 20;
	private int delta2 = -20;

	/*
	 * class MyTouchListener implements OnTouchListener { LiveScore liveScore;
	 * 
	 * public MyTouchListener(LiveScore liveScore1) { // TODO Auto-generated
	 * constructor stub this.liveScore = liveScore1; }
	 * 
	 * @Override public boolean onTouch(View v, MotionEvent event) {
	 * 
	 * 
	 * return true; } }
	 */
	private void calcuateDifference(final LiveScore liveScore) {
		((Activity) listView.getContext()).runOnUiThread(new Runnable() {

			@Override
			public void run() {
				if (CommonUtil.listQuanTam == null) {
					CommonUtil.listQuanTam = new ArrayList<String>();
					CommonUtil.getdata(listView.getContext());
				}

				String check_quantam = liveScore.idmagiai() + "-" + liveScore.getId();
				if (TypeView == null) {
					Log.e("KKKKKKKKKK", "difference:::" + difference + ":::" + delta2);
					String check_quantam2 = liveScore.getId();
					if (difference > delta1) {
						if (BongDaServiceManager.getInstance().getBongDaService().getDBManager().liveScoreLikeCheck(liveScore.getId())) {
							// TODO remove live score
							RequestFavorite("0", liveScore.getId());
						}

					} else if (difference < delta2) {
						if (!BongDaServiceManager.getInstance().getBongDaService().getDBManager().liveScoreLikeCheck(liveScore.getId())) {
							Log.e("KKKKKKKKKK", "B*" + CommonUtil.listQuanTam.toString());
							// TODO add live score
							RequestFavorite("1", liveScore.getId());
						}

					}
				}

				// action_down_x = 0;
				// action_up_x = 0;
				difference = 0;

			}
		});
	}
	
	private void RequestFavorite(final String type, final String matranfavorite){
		ICallbackAPI callbackAPIFavorite = new ICallbackAPI() {

			@Override
			public void onSuccess(String response) {
				// save and compare data
				String string_temp = CommonAndroid.parseXMLAction(response);
				if (!string_temp.equalsIgnoreCase("")) {
					try {
						Log.e("string_temp", string_temp);
						if("1".equals(string_temp)){
							Favorite(type, matranfavorite);
						}else{
							Toast.makeText(listView.getContext(), listView.getContext().getResources().getString(R.string.favorite_err) , Toast.LENGTH_SHORT).show();
						}
					} catch (Exception e) {
					}
				}

			}

			@Override
			public void onError(String message) {

			}
		};
		String ws = ByUtils.wsFootBall_Device_Like;
		String deviceId = Secure.getString(listView.getContext().getContentResolver(), Secure.ANDROID_ID);
		ws = ws.replace("deviceid", deviceId);
		ws = ws.replace("matranfavorite", matranfavorite);
		ws = ws.replace("typefavorite", type);
		Log.e("favorite param", ws);
		new APICaller(listView.getContext()).callApi("", true, callbackAPIFavorite, ws);
	}
	
	private void Favorite(String type, String matranfavorite){
		if("1".equals(type)){
			BongDaServiceManager.getInstance().getBongDaService().getDBManager().liveScoreLike(matranfavorite, "1");
			if (!CommonUtil.listQuanTam.contains(matranfavorite)) {
				CommonUtil.listQuanTam.add(matranfavorite);
				CommonUtil.savedata((Activity) listView.getContext());
				CommonUtil.getdata((Activity) listView.getContext());
			}
			countryAdapter.notifyDataSetChanged();
			Toast.makeText(listView.getContext(), "Add to Favorite", Toast.LENGTH_SHORT).show();
		}else{
			BongDaServiceManager.getInstance().getBongDaService().getDBManager().liveScoreLike(matranfavorite, "0");
			if (CommonUtil.listQuanTam.contains(matranfavorite)) {
				CommonUtil.listQuanTam.remove(matranfavorite);
				CommonUtil.savedata((Activity) listView.getContext());
				CommonUtil.getdata((Activity) listView.getContext());
			}
			countryAdapter.notifyDataSetChanged();
			Toast.makeText(listView.getContext(), "Remove favorite", Toast.LENGTH_SHORT).show();
		}
	}
	//
	// class LoadImage extends AsyncTask<Object, Void, Bitmap> {
	//
	// private ImageView imv;
	// private String path;
	//
	// public LoadImage(ImageView imv) {
	// this.imv = imv;
	// this.path = imv.getTag().toString();
	// }
	//
	// public LoadImage(ImageView findViewById, String sLogoGiai) {
	// // TODO Auto-generated constructor stub
	// this.imv = findViewById;
	// this.path = sLogoGiai;
	// }
	//
	// @Override
	// protected Bitmap doInBackground(Object... params) {
	// Bitmap bitmap = null;
	// File file = new
	// File(Environment.getExternalStorageDirectory().getAbsolutePath() + path);
	//
	// if (file.exists()) {
	// bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
	// }
	//
	// return bitmap;
	// }
	//
	// @Override
	// protected void onPostExecute(Bitmap result) {
	// // if (!imv.getTag().toString().equals(path)) {
	// // /* The path is not same. This means that this
	// // image view is handled by some other async task.
	// // We don't do anything and return. */
	// // return;
	// // }
	//
	// if (result != null && imv != null) {
	// imv.setVisibility(View.VISIBLE);
	// imv.setImageBitmap(result);
	// } else {
	// imv.setVisibility(View.GONE);
	// }
	// }
	//
	// }
}
