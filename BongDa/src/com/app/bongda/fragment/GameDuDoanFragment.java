package com.app.bongda.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
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
import com.app.bongda.model.GameDuDoan;
import com.app.bongda.model.LiveScore;
import com.app.bongda.util.ByUtils;
import com.app.bongda.util.CommonAndroid;
import com.app.bongda.util.CommonUtil;
import com.app.bongda.view.HeaderView;

public class GameDuDoanFragment extends BaseFragment {
	OnItemClickListener onItemClickListener;

	public GameDuDoanFragment(OnItemClickListener onItemClickListener) {
		super();
		this.onItemClickListener = onItemClickListener;
	}

	private CountryAdapter countryAdapter = new CountryAdapter();

	private class CountryAdapter extends BongDaBaseAdapter {
		int option_doivote = 1;
		EditText edit1,edit2;
		@Override
		public int getLayout() {
			return R.layout.gamedudoan_item;
		}

		@Override
		public void showData(Object item, View convertView) {
			Log.e("aaaaaaa", "cccccccccccccc");
			final GameDuDoan dudoan = (GameDuDoan) item;
			final View convertView_ = convertView;
			ImageLoaderUtils.getInstance(getActivity()).DisplayImage(dudoan.sLogoGiai(), (ImageView) convertView.findViewById(R.id.logo_giai));
			setText(convertView, R.id.tengiai, dudoan.sTenGiai());
			setText(convertView, R.id.TextView02, dudoan.sTenDoiNha());
			setText(convertView, R.id.TextView03, dudoan.sTenDoiKhach());
			setText(convertView,
					R.id.TextView01,
					"[" + dudoan.iCN_BanThang_DoiNha() + " - "
							+ dudoan.iCN_BanThang_DoiKhach() + "]");

			int j = Integer.valueOf(dudoan.iC0());
			java.util.Date localDate2 = new java.util.Date(1000L * j);
			System.currentTimeMillis();
			new java.sql.Date(j * 1000);
			Object[] arrayOfObject2 = new Object[2];
			arrayOfObject2[0] = Integer.valueOf(localDate2.getDate());
			arrayOfObject2[1] = Integer.valueOf(1 + localDate2.getMonth());
			String times = String.format("%d/%d", arrayOfObject2) + ", "
					+ dudoan.sThoiGian();
			setText(convertView, R.id.TextView05, times);
			String tiletem = dudoan.sTyLe_ChapBong();
			Log.e("tylechapbong", "sTyLe_ChapBong==" +tiletem);
			try {
				String temp1[];
				int j2 = tiletem.indexOf(":");
				if (j2 > 0) {
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
			Log.e("aaaaaaaaa", dudoan.sTyLe_ChapBong());
			edit1 = ((EditText) convertView.findViewById(R.id.nhapsao_1));
			edit2 = ((EditText) convertView.findViewById(R.id.nhapsao_2));
			convertView.findViewById(R.id.nhapsao_1).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					edit2.setText("");
					option_doivote = 1;
					Log.e("aaaaaaaaaa", "option1");
				}
			});
			convertView.findViewById(R.id.nhapsao_2).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					edit1.setText("");
					option_doivote = 2;
					Log.e("aaaaaaaaaa", "option2");
				}
			});
			convertView.findViewById(R.id.submit_vote).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					updateVote(dudoan, v, option_doivote, edit1.getText().toString().trim(), edit2.getText().toString().trim());
				}
			});
		}

	}

	private String numberphone;
	private void updateVote(GameDuDoan dudoan, View v, int option_doivote,String value1,String value2){
		String numberphone_temp = CommonUtil.getdata((Activity) v.getContext(), "numberphonelogin");
		numberphone = numberphone_temp == null ? "" : numberphone_temp;
		Log.e("number", numberphone);
		if(!"".equalsIgnoreCase(numberphone)){
			String iID_MaTran = dudoan.iID_MaTran();
			String iID_MaDoiNha = dudoan.iID_MaDoiNha();
			String iID_MaDoiKhach = dudoan.iID_MaDoiKhach();
			String madoivote = iID_MaDoiNha;
			String novote = value1;
			if(option_doivote == 2){
				madoivote = iID_MaDoiKhach;
				novote = value2;
			}
			String ws = ByUtils.wsFootBall_Lives_Co_GameDuDoan_SetBet;
			ws =  ws.replace("matranvote", iID_MaTran);
			ws =  ws.replace("madoivote", madoivote);
			ws =  ws.replace("nophone", numberphone);
			ws =  ws.replace("novote", novote);
			Log.e("gamedudoan", "param==" + ws);
			new APICaller(getContext()).callApi("", true, callbackAPIVote,
					ws);
		}else{
			Builder builder = new Builder(v.getContext());
			builder.setMessage(R.string.gamedudoan_login);
			builder.setCancelable(false);
			builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
				}
			});
			builder.create().show();
		}
	}
	
	@Override
	public int getLayout() {
		return R.layout.gamedudoan;
	}

	ListView listView;

	@Override
	public void onInitCreateView(View view) {
		/**
		 * init header view
		 */
		HeaderView headerView = (HeaderView) view
				.findViewById(R.id.headerView1);
		headerView.setTextHeader(R.string.gamedudoan);
		/** init data */
		listView = (ListView) view.findViewById(R.id.listView1);
//		listView.setOnItemClickListener(onItemClickListener);

		listView.setAdapter(countryAdapter);
		View customeProgressbar = headerView.findViewById(R.id.Button01);
		customeProgressbar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				/*
				 * clear data and reload new data
				 */
				page = 1;
				totalpage = 1;
				currentPage = 1;
				countryAdapter.clear();
				loadData();
			}
		});
	}

	ICallbackAPI callbackAPI, callbackAPIVote;
	private int page = 1;
	private int totalpage = 1;
	private int currentPage = 1;
	private boolean isLoadMore = true;
	private Context getContext() {
		return listView.getContext();
	}

	@Override
	public void onInitData() {
		callbackAPI = new ICallbackAPI() {
			@Override
			public void onSuccess(String response) {
				String string_temp = CommonAndroid.parseXMLAction(response);
				if (!string_temp.equalsIgnoreCase("")) {
					try {
						Log.e("gamedudoan", "string_temp==" + string_temp);
						ArrayList<JSONObject> array = new ArrayList<JSONObject>();
						array.clear();
						JSONArray jsonArray = new JSONArray(string_temp);
						if (jsonArray.length() == 0) {
							Toast.makeText(
									getContext(),
									getContext().getResources().getString(
											R.string.giaichuabatdau),
									Toast.LENGTH_LONG).show();
						}
						for (int i = 0; i < jsonArray.length(); i++) {
							try {
								array.add(jsonArray.getJSONObject(i));
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
						/*Collections.emptyList();
						Collections.sort(array, new Comparator<JSONObject>() {

							@Override
							public int compare(JSONObject lhs, JSONObject rhs) {
								// TODO Auto-generated method stub

								try {
									return (lhs.getString("sTenGiai")
											.toLowerCase().compareTo(rhs
											.getString("sTenGiai")
											.toLowerCase()));
								} catch (JSONException e) {
									e.printStackTrace();
									return 0;
								}
							}
						});*/

						String sTenDoiNha;
						String sTenDoiKhach;
						String iCN_BanThang_DoiNha;
						String iCN_BanThang_DoiKhach;
						String iC0; // ngay
						String sThoiGian;
						String sTyLe_ChauAu; // "2.20*3.10*2.35"
						String sTyLe_ChapBong; // ":"0.87*0 : 1\/4*0.83"
						String sTyLe_TaiSuu;// ":"0.98*2 1\/4*0.72"
						for (int i = 0; i < array.size(); i++) {
							sTenDoiNha = array.get(i).getString("sTenDoiNha");
							sTenDoiKhach = array.get(i).getString(
									"sTenDoiKhach");
							iCN_BanThang_DoiNha = array.get(i).getString(
									"iCN_BanThang_DoiNha");
							iCN_BanThang_DoiKhach = array.get(i).getString(
									"iCN_BanThang_DoiKhach");
							iC0 = array.get(i).getString("iC0");
							sThoiGian = array.get(i).getString("sThoiGian");
							sTyLe_ChauAu = array.get(i).getString(
									"sTyLe_ChauAu");
							sTyLe_ChapBong = array.get(i).getString(
									"sTyLe_ChapBong");
							sTyLe_TaiSuu = array.get(i).getString(
									"sTyLe_TaiSuu");
							String sTenGiai = jsonArray.getJSONObject(i).getString("sTenGiai");
							String sLogoQuocGia = "";
							String sLogoGiai = "";
							if (jsonArray.getJSONObject(i).has("sLogoQuocGia")){
								sLogoQuocGia = jsonArray.getJSONObject(i).getString("sLogoQuocGia");
							}
							if (jsonArray.getJSONObject(i).has("sLogoGiai")){
								sLogoGiai = jsonArray.getJSONObject(i).getString("sLogoGiai");
							}
							String iID_MaTran = jsonArray.getJSONObject(i).getString("iID_MaTran");
							String iID_MaDoiNha = jsonArray.getJSONObject(i).getString("iID_MaDoiNha");
							String iID_MaDoiKhach = jsonArray.getJSONObject(i).getString("iID_MaDoiKhach");
							
							countryAdapter.addItem(new GameDuDoan(false,
									sTenDoiNha, sTenDoiKhach,
									iCN_BanThang_DoiNha, iCN_BanThang_DoiKhach,
									iC0, sThoiGian, sTyLe_ChauAu,
									sTyLe_ChapBong, sTyLe_TaiSuu,
									sTenGiai, sLogoQuocGia, sLogoGiai, iID_MaTran, iID_MaDoiNha ,iID_MaDoiKhach));

						}
						countryAdapter.notifyDataSetChanged();
						Log.e("aaaaaaa", "bbbbbbbbbbbbbb");
					} catch (JSONException e) {
					}
				}

			}

			@Override
			public void onError(String message) {
			}
		};
		
		callbackAPIVote = new ICallbackAPI() {
			@Override
			public void onSuccess(String response) {
				String string_temp = CommonAndroid.parseXMLAction(response);
				if (!string_temp.equalsIgnoreCase("")) {
					try {
						Log.e("gamedudoan", "string_temp_vote==" + string_temp);
					} catch (Exception e) {
					}
				}

			}

			@Override
			public void onError(String message) {
			}
		};
		
		loadData();
	}
	
	private void loadData(){
		String ws = ByUtils.wsFootBall_Lives_dudoan;
		String page_load = ""+page;
		ws =  ws.replace("pageload", page_load);
		Log.e("gamedudoan", "param==" + ws);
		new APICaller(getContext()).callApi("", true, callbackAPI,
				ws);
	}
}
