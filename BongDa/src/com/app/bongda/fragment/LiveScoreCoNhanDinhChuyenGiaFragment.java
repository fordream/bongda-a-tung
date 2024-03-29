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
import android.os.AsyncTask;
import android.os.Environment;
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
import com.app.bongda.inter.CallBackListenner;
import com.app.bongda.lazyload.ImageLoader2;
import com.app.bongda.model.GiaiDau;
import com.app.bongda.model.LiveScore;
import com.app.bongda.service.BongDaServiceManager;
import com.app.bongda.util.ByUtils;
import com.app.bongda.util.CommonAndroid;
import com.app.bongda.util.CommonUtil;
import com.app.bongda.view.HeaderView;

public class LiveScoreCoNhanDinhChuyenGiaFragment extends BaseFragment {
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
	public LiveScoreCoNhanDinhChuyenGiaFragment(OnItemClickListener onItemClickListener, CallBackListenner callBackListenner, GiaiDau data, String type) {
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
				// Log.e("position", "position::" + position);
			}
			// Log.e("position", "position::" + position);

			String check_quantam = /* liveScore.idmagiai() + "-" + */liveScore.getId();
			if (liveScore.isHeader()) {
				convertView.findViewById(R.id.livescore_header).setVisibility(View.VISIBLE);
				convertView.findViewById(R.id.livescore_main).setVisibility(View.VISIBLE);
			} else {
				convertView.findViewById(R.id.livescore_header).setVisibility(View.GONE);
				convertView.findViewById(R.id.livescore_main).setVisibility(View.VISIBLE);
			}

			// if(showdata){
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

//			int status = 0;
//			status = liveScore.iTrangThai();
			/*if (status >= 2) {
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
			}*/
			
			//new
			convertView.findViewById(R.id.TextView03).setVisibility(View.GONE);// live
			convertView.findViewById(R.id.TextView02_ketqua).setVisibility(View.VISIBLE);
			setText(convertView, R.id.TextView02_ketqua, "  -  ");//
			convertView.findViewById(R.id.ImageView031).setVisibility(View.GONE);
			convertView.findViewById(R.id.TextView01).setVisibility(View.GONE);
			convertView.findViewById(R.id.tv1).setVisibility(View.GONE);
			
			// setText(convertView, R.id.TextView01, liveScore.getTime());
			setText(convertView, R.id.TextView02, liveScore.getName());
			setText(convertView, R.id.TextView023, liveScore.getName2());
			
			ImageView image = (ImageView) convertView.findViewById(R.id.logogiai);
			imageLoader.DisplayImage(liveScore.sLogoGiai(), image);
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

			convertView.findViewById(R.id.livescore_row).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					callBackListenner.onCallBackListenner(5, liveScore);
				}
			});

		}

		@Override
		public void showData(Object item, View convertView) {

		}

	}

	@Override
	public int getLayout() {
		return R.layout.livesocre;
	}

	public static int difference = 0;
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

		headerView.setTextHeader(R.string.nhandinhchuyengia);
		
		/** init data */
		listView = (ListView) view.findViewById(R.id.listView1);
		listView.setAdapter(countryAdapter);

		views_err = (TextView) view.findViewById(R.id.error_txt);
		imageLoader = new ImageLoader2(getActivity());
	}

	ICallbackAPI callbackAPI;
	private int page = 1;
	private int totalpage = 1;
	private int currentPage = 1;
	private boolean isLoadMore = true;

	private void loadData() {
		Log.e("Liveco", "loadData");
		String ws = ByUtils.wsFootBall_Lives_Co_NhanDinhChuyenGia;
		String page_load = "" + page;
		ws = ws.replace("pageload", page_load);
		new APICaller(listView.getContext()).callApi("", true, callbackAPI, ws);

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
					// Log.e("data", string_temp);
					try {
						// ArrayList<JSONObject> array = new
						// ArrayList<JSONObject>();
						// array.clear();
						JSONArray jsonArray = new JSONArray(string_temp);

						if (jsonArray.length() == 0) {
							views_err.setVisibility(View.VISIBLE);
							views_err.setText(listView.getContext().getResources().getString(R.string.giaichuabatdau));
							// Toast.makeText(listView.getContext(),
							// listView.getContext().getResources().getString(R.string.giaichuabatdau),
							// Toast.LENGTH_LONG).show();
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
							// Log.e("kkk", i + ":" +
							// jsonArray.getJSONObject(i).getString("iID_MaGiai")
							// + ":" + bNhanDinhChuyenGia + ":" + bGameDuDoan +
							// ":" + bDaCapNhapVaoBXH);

							// String kk =
							// jsonArray.getJSONObject(i).getString("sTenGiai");
							if ("nhandinhchuyengia".equalsIgnoreCase(TypeView) && bNhanDinhChuyenGia) {
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
								// Log.e("kkk",sTenGiai +":" +iTrangThai + ":"
								// +sTenDoiNha);iID_MaGiai
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
						 Log.e("aaaaaaa","count_showdata::" +count_showdata +
						 ":totalpage:" + totalpage);
						// if (onLoad != 1) {
						countryAdapter.notifyDataSetChanged();
						// }
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

}
