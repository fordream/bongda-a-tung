package com.app.bongda.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.app.bongda.R;
import com.app.bongda.base.BaseFragment;
import com.app.bongda.base.BongDaBaseAdapter;
import com.app.bongda.base.ImageLoaderUtils;
import com.app.bongda.callback.APICaller;
import com.app.bongda.callback.APICaller.ICallbackAPI;
import com.app.bongda.inter.CallBackListenner;
import com.app.bongda.model.GiaiDau;
import com.app.bongda.model.LiveScore;
import com.app.bongda.model.TuongThuatTran;
import com.app.bongda.util.ByUtils;
import com.app.bongda.util.CommonAndroid;
import com.app.bongda.util.CommonUtil;
import com.app.bongda.view.HeaderView;

public class TuongThuatTranLiveScoreFragment extends BaseFragment {
	OnItemClickListener onItemClickListener;
	CallBackListenner backListenner;
	LiveScore livecore;
	public static View views;
	SharedPreferences pref_tuongthuat;
	private boolean ListItem = false;
	private int bong = 1;
	private int bong_pen = 2;
	private int thevang = 3;
	private int thedo = 4;

	public TuongThuatTranLiveScoreFragment(LiveScore dau,
			OnItemClickListener onItemClickListener,
			CallBackListenner backListenner) {
		super();
		this.onItemClickListener = onItemClickListener;
		this.backListenner = backListenner;
		this.livecore = dau;
		ListItem = false;
	}

	private CountryAdapter countryAdapter = new CountryAdapter();

	private class CountryAdapter extends BongDaBaseAdapter {

		@Override
		public int getLayout() {
			return R.layout.tuongthuattructiep_item;
		}

		@Override
		public void showData(Object item, View convertView) {
			String sTenGiai = CommonUtil
					.getdata(views.getContext(), "sTenGiai");
			String sTenDoiNha = CommonUtil.getdata(views.getContext(),
					"sTenDoiNha");
			String sTenDoiKhach = CommonUtil.getdata(views.getContext(),
					"sTenDoiKhach");
			String iPhut = CommonUtil.getdata(views.getContext(), "iPhut");
			String Banthang = CommonUtil
					.getdata(views.getContext(), "Banthang");
			String HT = CommonUtil.getdata(views.getContext(), "HT");
			String iTrangThai= CommonUtil.getdata(views.getContext(), "iTrangThai");
			((TextView) views.findViewById(R.id.tuongthuat_textTenTran))
					.setText(sTenGiai);
			((TextView) views.findViewById(R.id.TextView01))
					.setText(sTenDoiNha);
			((TextView) views.findViewById(R.id.TextView02))
					.setText(sTenDoiKhach);
			if(iTrangThai.equalsIgnoreCase("5")){
				((TextView) views.findViewById(R.id.tuongthuat_time))
				.setText("FT");
			}else if(iTrangThai.equalsIgnoreCase("3")){
				((TextView) views.findViewById(R.id.tuongthuat_time))
				.setText("HT");
			}else{
				((TextView) views.findViewById(R.id.tuongthuat_time))
				.setText(iPhut);
			}
			
			((TextView) views.findViewById(R.id.tuongthuat_tiso))
					.setText(Banthang);
			((TextView) views.findViewById(R.id.tuongthuat_ht)).setText(HT);
			if (item != null) {
				final TuongThuatTran tuongthuattran = (TuongThuatTran) item;
				if (tuongthuattran.isDoi() == 1) {
					convertView.findViewById(R.id.doi1).setVisibility(
							View.VISIBLE);
					convertView.findViewById(R.id.doi2)
							.setVisibility(View.GONE);
					setText(convertView, R.id.time1,
							tuongthuattran.isThoigian() + "'");
					setText(convertView, R.id.name1, tuongthuattran.getName());
					ImageView localImageView1 = (ImageView) convertView
							.findViewById(R.id.icon_tuongthuat1);
					if (tuongthuattran.isTrangthai() == bong) { // quabongthuong
						localImageView1
								.setImageResource(R.drawable.chitiettrandau_32);
					} else if (tuongthuattran.isTrangthai() == bong_pen) { // quabongchu
																		// P
						localImageView1
								.setImageResource(R.drawable.chitiettrandau_28);
					} else if (tuongthuattran.isTrangthai() == thevang) { // thevang
						localImageView1
								.setImageResource(R.drawable.chitiettrandau_40);
					} else  if (tuongthuattran.isTrangthai() == thedo){ // thedo
						localImageView1
								.setImageResource(R.drawable.chitiettrandau_43);
					}
				} else {
					convertView.findViewById(R.id.doi1)
							.setVisibility(View.GONE);
					convertView.findViewById(R.id.doi2).setVisibility(
							View.VISIBLE);
					setText(convertView, R.id.time2,
							tuongthuattran.isThoigian() + "'");
					setText(convertView, R.id.name2, tuongthuattran.getName());
					ImageView localImageView2 = (ImageView) convertView
							.findViewById(R.id.icon_tuongthuat2);
					if (tuongthuattran.isTrangthai() == bong) { // quabongthuong
						localImageView2
								.setImageResource(R.drawable.chitiettrandau_32);
					} else if (tuongthuattran.isTrangthai() == bong_pen) { // quabongchu
																		// P
						localImageView2
								.setImageResource(R.drawable.chitiettrandau_28);
					} else if (tuongthuattran.isTrangthai() == thevang) { // thevang
						localImageView2
								.setImageResource(R.drawable.chitiettrandau_40);
					} else if (tuongthuattran.isTrangthai() == thedo){ // thedo
						localImageView2
								.setImageResource(R.drawable.chitiettrandau_43);
					}
				}
			} else {
				convertView.findViewById(R.id.doi1).setVisibility(View.GONE);
				convertView.findViewById(R.id.doi2).setVisibility(View.GONE);
			}

			// logo
			String sLogoGiai = CommonUtil.getdata(views.getContext(),
					"sLogoGiai");
			String sLogoDoiNha = CommonUtil.getdata(views.getContext(),
					"sLogoDoiNha");
			String sLogoDoiKhach = CommonUtil.getdata(views.getContext(),
					"sLogoDoiKhach");

			ImageLoaderUtils.getInstance(views.getContext()).DisplayImage(
					sLogoGiai, (ImageView) views.findViewById(R.id.logogiai));
			ImageLoaderUtils.getInstance(views.getContext()).DisplayImage(
					sLogoDoiNha,
					(ImageView) views.findViewById(R.id.logo_doinha));
			ImageLoaderUtils.getInstance(views.getContext()).DisplayImage(
					sLogoDoiKhach,
					(ImageView) views.findViewById(R.id.logo_doikhach));
			Log.e("aaaa", "sLogoDoiNha" + sLogoDoiNha + "::sLogoDoiKhach"
					+ sLogoDoiKhach);

		}

	}

	@Override
	public int getLayout() {
		return R.layout.tuongthuattructiep;
	}

	@Override
	public void onInitCreateView(View view) {
		/**
		 * init header view
		 */
		HeaderView headerView = (HeaderView) view
				.findViewById(R.id.headerView1);
		headerView.setTextHeader(R.string.tuongthuattran);
		headerView.findViewById(R.id.Button02).setVisibility(View.VISIBLE);
		headerView.findViewById(R.id.Button03).setVisibility(View.VISIBLE);
		headerView.findViewById(R.id.Button04).setVisibility(View.VISIBLE);
		view.findViewById(R.id.imageView1s).setOnClickListener(clickListener);
		headerView.findViewById(R.id.Button02)
				.setOnClickListener(clickListener);
		headerView.findViewById(R.id.Button03)
				.setOnClickListener(clickListener);
		headerView.findViewById(R.id.Button04)
				.setOnClickListener(clickListener);

		/** init data */
		ListView listView = (ListView) view.findViewById(R.id.listView1);
		listView.setAdapter(countryAdapter);
		listView.setOnItemClickListener(onItemClickListener);

		this.views = view;

	}

	View.OnClickListener clickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			GiaiDau dau = new GiaiDau(livecore.iID_MaGiai(), livecore.sTenGiai(),
					livecore.sMaGiai(), livecore.madoinha(),
					livecore.madoikhach(), livecore.idmagiai(), livecore.iID_MaTran(), livecore.sLogoDoiNha(), livecore.sLogoDoiKhach(), livecore.iID_MaDoiNha(), livecore.iID_MaDoiKhach());
			dau.sLogoGiai(livecore.sLogoGiai());
			if (v.getId() == R.id.imageView1s) {
				backListenner.onCallBackListenner(1, dau /*
														 * new GiaiDau("1",
														 * "aaaa")
														 */);

			} else if (v.getId() == R.id.Button02) {
				backListenner.onCallBackListenner(2, dau/* null */);
			} else if (v.getId() == R.id.Button03) {
				backListenner.onCallBackListenner(3, dau/* null */);
			} else if (v.getId() == R.id.Button04) {
				backListenner.onCallBackListenner(4, dau/* null */);
			}
		}
	};

	ICallbackAPI callbackAPI;
	private String iID_MaTran;
	public String iCN_BanThang_DoiKhach;
	public String iCN_BanThang_DoiKhach_HT;
	public String iCN_BanThang_DoiNha;
	public String iCN_BanThang_DoiNha_HT;
	private String iID_MaDoiKhach;
	private String iID_MaDoiNha;
	public String iID_MaGiai;
	public String iPhut;
	private boolean isInFront;
	private ListView listView;
	public String sTenDoiKhach;
	public String sTenDoiNha;
	public String sTenGiai;
	public String sLogoGiai;
	public String sLogoDoiNha;
	public String sLogoDoiKhach;
	public String iTrangThai;
	JSONArray jsonArray_the = new JSONArray();
	@Override
	public void onInitData() {

		callbackAPI = new ICallbackAPI() {
			@Override
			public void onSuccess(String response) {
				String string_temp = CommonAndroid.parseXMLAction(response);
				if (!string_temp.equalsIgnoreCase("")) {
					try {
						JSONArray jsonarray = new JSONArray(string_temp);
						jsonArray_the = new JSONArray();
						for (int i = 0; i < jsonarray.length(); i++) {
							// parse
							sTenGiai = jsonarray.getJSONObject(i).getString(
									"sTenGiai");
							sTenDoiNha = jsonarray.getJSONObject(i).getString(
									"sTenDoiNha");
							sTenDoiKhach = jsonarray.getJSONObject(i)
									.getString("sTenDoiKhach");
							iCN_BanThang_DoiNha = jsonarray.getJSONObject(i)
									.getString("iCN_BanThang_DoiNha");
							iCN_BanThang_DoiKhach = jsonarray.getJSONObject(i)
									.getString("iCN_BanThang_DoiKhach");
							iCN_BanThang_DoiNha_HT = jsonarray.getJSONObject(i)
									.getString("iCN_BanThang_DoiNha_HT");
							iCN_BanThang_DoiKhach_HT = jsonarray.getJSONObject(
									i).getString("iCN_BanThang_DoiKhach_HT");
							iPhut = jsonarray.getJSONObject(i).getString(
									"iPhut")
									+ "'";
							sLogoGiai = jsonarray.getJSONObject(i).getString(
									"sLogoGiai");
							sLogoDoiNha = jsonarray.getJSONObject(i).getString(
									"sLogoDoiNha");
							sLogoDoiKhach = jsonarray.getJSONObject(i)
									.getString("sLogoDoiKhach");

							iID_MaDoiNha = jsonarray.getJSONObject(i)
									.getString("iID_MaDoiNha");
							iID_MaDoiKhach = jsonarray.getJSONObject(i)
									.getString("iID_MaDoiKhach");
							iID_MaGiai = jsonarray.getJSONObject(i).getString(
									"iID_MaGiai");
							iTrangThai = jsonarray.getJSONObject(i).getString(
									"iTrangThai");
							loadItem(jsonarray.getJSONObject(i),
									"sThongTin_DoiNha", 1);// GOAL_HOME
							loadItem(jsonarray.getJSONObject(i),
									"sThongTin_DoiKhach", 2);// GOAL_AWAY
							loadItem(jsonarray.getJSONObject(i),
									"sThongTinThe_DoiNha", 3);// YELLOW_CARD_HOME
							loadItem(jsonarray.getJSONObject(i),
									"sThongTinThe_DoiKhach", 4);// YELLOW_CARD_AWAY
							Log.e("data-check", "sThongTin_DoiNha" + jsonarray.getJSONObject(i).getString("sThongTin_DoiNha"));
							Log.e("data-check", "sThongTin_DoiKhach" + jsonarray.getJSONObject(i).getString("sThongTin_DoiKhach"));
							Log.e("data-check", "sThongTinThe_DoiNha" + jsonarray.getJSONObject(i).getString("sThongTinThe_DoiNha"));
							Log.e("data-check", "sThongTinThe_DoiKhach" + jsonarray.getJSONObject(i).getString("sThongTinThe_DoiKhach"));
						}

						//sort data
						ArrayList<JSONObject> array = new ArrayList<JSONObject>();
						array.clear();
						for(int j = 0; j < jsonArray_the.length();j++){
							Log.e("aaaaaaaaaa", "data:::" + jsonArray_the.getJSONObject(j).toString());
							array.add(jsonArray_the.getJSONObject(j));
						}
						Collections.emptyList();
						Collections.sort(array, new Comparator<JSONObject>() {

							@Override
							public int compare(JSONObject lhs, JSONObject rhs) {
								// TODO Auto-generated method stub

								try {
									return (lhs.getString("No").toLowerCase().compareTo(rhs.getString("No").toLowerCase()));
//									return (lhs.getInt("No"));
								} catch (JSONException e) {
									// TODO Auto-generated catch
									// block
									e.printStackTrace();
									return 0;
								}
							}
						});
						for (int i = 0; i < array.size(); i++) {
							Log.e("aaaaaaaaaa", "data2:::" + array.get(i).toString());
							countryAdapter.addItem(new TuongThuatTran(
									array.get(i).getInt("doi"), null, array.get(i).getString("No"), array.get(i).getString("Values"), array.get(i).getInt("Status")));
						}	
						
						String HT = "";
						StringBuilder stringbuilder1 = new StringBuilder("HT ");
						HT = stringbuilder1.append(iCN_BanThang_DoiNha_HT)
								.append(" - ").append(iCN_BanThang_DoiKhach_HT)
								.toString();

						String Banthang = (new StringBuilder())
								.append(iCN_BanThang_DoiNha).append(" - ")
								.append(iCN_BanThang_DoiKhach).toString();

						// save data
						CommonUtil.savedata(views.getContext(), "sTenGiai",
								sTenGiai);
						CommonUtil.savedata(views.getContext(), "sTenDoiNha",
								sTenDoiNha);
						CommonUtil.savedata(views.getContext(), "sTenDoiKhach",
								sTenDoiKhach);
						CommonUtil.savedata(views.getContext(), "iPhut", iPhut);
						CommonUtil.savedata(views.getContext(), "Banthang",
								Banthang);
						CommonUtil.savedata(views.getContext(), "HT", HT);
						CommonUtil.savedata(views.getContext(), "sLogoGiai",
								sLogoGiai);
						CommonUtil.savedata(views.getContext(), "sLogoDoiNha",
								sLogoDoiNha);
						CommonUtil.savedata(views.getContext(),
								"sLogoDoiKhach", sLogoDoiKhach);

						CommonUtil.savedata(views.getContext(), "iID_MaGiai",
								iID_MaGiai);
						CommonUtil.savedata(views.getContext(), "iID_MaDoiNha",
								iID_MaDoiNha);
						CommonUtil.savedata(views.getContext(),
								"iID_MaDoiKhach", iID_MaDoiKhach);
						CommonUtil.savedata(views.getContext(),
								"iTrangThai", iTrangThai);
						
						if (!ListItem) {
							countryAdapter.addItem(null);
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
		iID_MaTran = livecore.getId();
		Object aobj[] = new Object[1];
		aobj[0] = Integer.valueOf(iID_MaTran);
		String param = String.format(ByUtils.wsFootBall_MatchDetail, aobj);
		new APICaller(views.getContext()).callApi("", true, callbackAPI, param);

		countryAdapter.notifyDataSetChanged();
	}

	private void loadItem(JSONObject jsonobject, String s, int status) {
//		Log.e("tuongthuat", "loadItem" + jsonobject.toString());
		int doi = 1;
		if (status == 2 || status == 4) {
			doi = 2;
		}
		if (!jsonobject.has(s)) {
		} else {
			// else goto _L1
			String s3;
			try {
				s3 = jsonobject.getString(s);
				if (s3 == null || s3.equals("null")) {
				} else {
					String temp1[];
					int j = s3.indexOf(",");
					if (j > 0) {
						temp1 = s3.split(",");
						for (int i = 0; i < temp1.length; i++) {
							JSONObject itemObj = new JSONObject();
							String temp2[];
							int k = temp1[i].indexOf(" ");
							if (k > 0) {
								temp2 = temp1[i].split(" ");
								// Log.e("KKKKKKKK", "KKKKKKKKKK11" + temp2[0] +
								// ":::" + temp2[1]);
								// countryAdapter.addItem(new
								// TuongThuatTran(doi, null , temp2[0]
								// ,temp2[1], status));
								String No = temp2[0];
								String Values = temp2[1];
								// check status
								// sThongTin_DoiNha: 13 aa,20 bbbb(Pen),68
								// bbbb(Pen),75 ccc ===> (Pen) icon P
								// sThongTinThe_DoiNha: 30 Delaney(TV),41
								// Campbell(TV),43 Delaney(TD):
								// Log.e("pen",Values );
								// Anywhere in string
								boolean Pen_check = Values.indexOf("(Pen)") > 0;
								boolean TV_check = Values.indexOf("(TV)") > 0;
								if (status == 1 || status == 2) {
									if (Pen_check) {
										int lastcheck = Values
												.lastIndexOf("(Pen)");
										String tem = Values;
										if (lastcheck != -1) {
											tem = Values
													.substring(0, lastcheck);
										}
//										countryAdapter
//												.addItem(new TuongThuatTran(
//														doi, null, No, tem, 10));
										itemObj.put("No", No);
										itemObj.put("doi", doi);
										itemObj.put("Values", tem);
										itemObj.put("Status", bong_pen);
										jsonArray_the.put(itemObj);
									} else {
//										countryAdapter
//												.addItem(new TuongThuatTran(
//														doi, null, No, Values,
//														1));
										itemObj.put("No", No);
										itemObj.put("doi", doi);
										itemObj.put("Values", Values);
										itemObj.put("Status", bong);
										jsonArray_the.put(itemObj);
									}
								} else {
									if (status == 3 || status == 4) {
										if (TV_check) {
											int lastcheck = Values
													.lastIndexOf("(TV)");
											String tem = Values;
											if (lastcheck != -1) {
												tem = Values.substring(0,
														lastcheck);
											}
//											countryAdapter
//													.addItem(new TuongThuatTran(
//															doi, null, No, tem,
//															20));
											itemObj.put("No", No);
											itemObj.put("doi", doi);
											itemObj.put("Values", tem);
											itemObj.put("Status", thevang);
											jsonArray_the.put(itemObj);
										} else {
											int lastcheck = Values
													.lastIndexOf("(TD)");
											String tem = Values;
											if (lastcheck != -1) {
												tem = Values.substring(0,
														lastcheck);
											}
//											countryAdapter
//													.addItem(new TuongThuatTran(
//															doi, null, No, tem,
//															2));
											itemObj.put("No", No);
											itemObj.put("doi", doi);
											itemObj.put("Values", tem);
											itemObj.put("Status", thedo);
											jsonArray_the.put(itemObj);
										}
									}
								}
								ListItem = true;
							}
						}
					} else {
						String temp2[];
						int k = s3.indexOf(" ");
						if (k > 0) {
							temp2 = s3.split(" ");
							// countryAdapter.addItem(new TuongThuatTran(doi,
							// null , temp2[0] ,temp2[1], status));
							String No = temp2[0];
							String Values = temp2[1];
							boolean Pen_check = Values.indexOf("(Pen)") > 0;
							boolean TV_check = Values.indexOf("(TV)") > 0;
							JSONObject itemObj = new JSONObject();
							Log.e("test", "aa" + Pen_check);
							if (status == 1 || status == 2) {
								if (Pen_check) {
									int lastcheck = Values.lastIndexOf("(Pen)");
									String tem = Values;
									if (lastcheck != -1) {
										tem = Values.substring(0, lastcheck);
									}
//									countryAdapter.addItem(new TuongThuatTran(
//											doi, null, No, tem, 10));
									itemObj.put("No", No);
									itemObj.put("doi", doi);
									itemObj.put("Values", tem);
									itemObj.put("Status", bong_pen);
									jsonArray_the.put(itemObj);
								} else {
//									countryAdapter.addItem(new TuongThuatTran(
//											doi, null, No, Values, 1));
									itemObj.put("No", No);
									itemObj.put("doi", doi);
									itemObj.put("Values", Values);
									itemObj.put("Status", bong);
									jsonArray_the.put(itemObj);
								}
							} else {
								if (status == 3 || status == 4) {
									if (TV_check) {
										int lastcheck = Values
												.lastIndexOf("(TV)");
										String tem = Values;
										if (lastcheck != -1) {
											tem = Values
													.substring(0, lastcheck);
										}
//										countryAdapter
//												.addItem(new TuongThuatTran(
//														doi, null, No, tem, 20));
										itemObj.put("No", No);
										itemObj.put("doi", doi);
										itemObj.put("Values", tem);
										itemObj.put("Status", thevang);
										jsonArray_the.put(itemObj);
									} else {
										int lastcheck = Values
												.lastIndexOf("(TD)");
										String tem = Values;
										if (lastcheck != -1) {
											tem = Values
													.substring(0, lastcheck);
										}
//										countryAdapter
//												.addItem(new TuongThuatTran(
//														doi, null, No, tem, 2));
										itemObj.put("No", No);
										itemObj.put("doi", doi);
										itemObj.put("Values", tem);
										itemObj.put("Status", thedo);
										jsonArray_the.put(itemObj);
									}
								}
							}
							ListItem = true;
						}
					}

				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
}
