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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.bongda.R;
import com.app.bongda.base.BaseFragment;
import com.app.bongda.base.BongDaBaseAdapter;
import com.app.bongda.callback.APICaller;
import com.app.bongda.callback.APICaller.ICallbackAPI;
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

		@Override
		public int getLayout() {
			return R.layout.livescore_item;
		}

		@Override
		public void showData(int position,Object item, View convertView) {
			final LiveScore liveScore = (LiveScore) item;
			if(TypeView != null){
				if (TypeView.equalsIgnoreCase("quantam") && !CommonUtil.listQuanTam.contains(liveScore.getId()))
	            {
					convertView.findViewById(R.id.livescore_header).setVisibility(View.GONE);
					convertView.findViewById(R.id.livescore_main).setVisibility(View.GONE);
					return;
	            }
			}
			
			convertView.findViewById(R.id.livescore_header).setVisibility(View.GONE);
			convertView.findViewById(R.id.livescore_main).setVisibility(View.GONE);

			//show tran quan tam
			if (CommonUtil.listQuanTam.contains(liveScore.getId()))
            {
				convertView.findViewById(R.id.traitim).setVisibility(View.VISIBLE);
			}else{
				convertView.findViewById(R.id.traitim).setVisibility(View.GONE);
			}
			if (liveScore.isHeader()) {
				convertView.findViewById(R.id.livescore_header).setVisibility(View.VISIBLE);
			} else {
				convertView.findViewById(R.id.livescore_main).setVisibility(View.VISIBLE);
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
					setText(convertView, R.id.TextView01, getResources().getString(R.string.hoanthidau));
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

			convertView.findViewById(R.id.imageView2).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					callBackListenner.onCallBackListenner(0, liveScore);
				}
			});

			convertView.findViewById(R.id.ImageView01).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					callBackListenner.onCallBackListenner(2, liveScore);
				}
			});
			mOnTouchListener = new MyTouchListener(liveScore);
			convertView.setOnTouchListener(mOnTouchListener);
		}

		@Override
		public void showData(Object item, View convertView) {
			// TODO Auto-generated method stub
			
		}

	}

	@Override
	public int getLayout() {
		return R.layout.livesocre;
	}


    private int action_down_x = 0;
    private int action_up_x = 0;
    public static int difference = 0;
    ListView listView;
	@Override
	public void onInitCreateView(View view) {
		/**
		 * init header view
		 */
		HeaderView headerView = (HeaderView) view.findViewById(R.id.headerView1);
		if(TypeView == null){
			headerView.setTextHeader(R.string.livescore);
		}else{
			if(TypeView.equalsIgnoreCase("quantam")){
				headerView.setTextHeader(R.string.tranquantam);
			}else{
				headerView.setTextHeader(R.string.livescore);
			}
		}
		/** init data */
		listView = (ListView) view.findViewById(R.id.listView1);
		listView.setAdapter(countryAdapter);
		

	}
	
	private void calcuateDifference(final LiveScore liveScore) {
		getActivity().runOnUiThread(new Runnable() {

			@Override
			public void run() {
				if (CommonUtil.listQuanTam == null)
	            {
	                CommonUtil.listQuanTam = new ArrayList<String>();
	                CommonUtil.getdata(getActivity());
	            }
	            if (difference > 10) {
					if (CommonUtil.listQuanTam.contains(liveScore.getId()))
		            {
		                CommonUtil.listQuanTam.remove(liveScore.getId());
		                CommonUtil.savedata(getActivity());
		                countryAdapter.notifyDataSetChanged();
		                Log.e("KKKKKKKKKK", "A*" + CommonUtil.listQuanTam.toString() );
		            }
					Toast.makeText(getActivity(), "Remove favorite", Toast.LENGTH_LONG).show();
				}
				if (difference < -10) {
					if (!CommonUtil.listQuanTam.contains(liveScore.getId()))
		            {
		                CommonUtil.listQuanTam.add(liveScore.getId());
		                CommonUtil.savedata(getActivity());
		                countryAdapter.notifyDataSetChanged();
		                Log.e("KKKKKKKKKK", "B*" + CommonUtil.listQuanTam.toString() );
		            }
					Toast.makeText(getActivity(), "Add to Favorite", Toast.LENGTH_LONG).show();
				}
				action_down_x = 0;
				action_up_x = 0;
				difference = 0;
				
			}
		});
	}


	ICallbackAPI callbackAPI;

	@Override
	public void onResume() {
		super.onResume();
		String maGiaiDau = data == null ? null : data.getId();
		if (maGiaiDau == null) {
			 
			if(TypeView == null && onLoad == 1){
				BongDaServiceManager.getInstance().getBongDaService().callApi(getCurrentTime(), callbackAPI, ByUtils.wsFootBall_Lives);
			}else{
//				new APICaller(getActivity()).callApi("", true, callbackAPI,
//						 ByUtils.wsFootBall_Lives);
				if(TypeView == null){
					new APICaller(getActivity()).callApi("", true, callbackAPI,
							 ByUtils.wsFootBall_Lives);
				}else{
					if(TypeView.equalsIgnoreCase("quantam")){
						if (CommonUtil.listQuanTam.size() > 0){
							new APICaller(getActivity()).callApi("", true, callbackAPI,
									 ByUtils.wsFootBall_Lives);
							
						}else{
							Toast.makeText(getActivity(), getResources().getString(R.string.khongcodoiyeuthich), Toast.LENGTH_LONG).show();
						}
					}
					
				}
			}
		} else {
			if(TypeView == null  && onLoad == 1){
				BongDaServiceManager.getInstance().getBongDaService().callApi(getCurrentTime(), callbackAPI, (ByUtils.wsFootBall_Lives_Theo_Giai).replace("magiai", maGiaiDau));
			}else{
				 new APICaller(getActivity()).callApi("", true, callbackAPI,
				 (ByUtils.wsFootBall_Lives_Theo_Giai).replace("magiai",
				 maGiaiDau));
			}
		}
		onLoad++;
		
	}

	@SuppressWarnings("unused")
	@Override
	public void onInitData() {
		if (callbackAPI == null)
			callbackAPI = new ICallbackAPI() {
				@Override
				public void onSuccess(String response) {
					countryAdapter.clear();
					String string_temp = CommonAndroid.parseXMLAction(response);
					if (!string_temp.equalsIgnoreCase("")) {
//						 CommonAndroid.showDialog(getActivity(), "data2:" +
//						 string_temp , null);
						// Log.e("data",string_temp);
						try {
//							JSONArray jsonarray = new JSONArray(string_temp);
							
							ArrayList<JSONObject> array = new ArrayList<JSONObject>();
							array.clear();
							JSONArray jsonArray = new JSONArray(string_temp);
							if(jsonArray.length() == 0){
								Toast.makeText(getActivity(), getResources().getString(R.string.giaichuabatdau), Toast.LENGTH_LONG).show();
							}
							CommonUtil.getdata(getActivity());
//							Log.e("KKK","kkk"+ "*****"+ CommonUtil.listQuanTam.toString());
							
							for (int i = 0; i < jsonArray.length(); i++) {
								try {
									if(TypeView != null){
										if(TypeView.equalsIgnoreCase("quantam")){
											String matran = jsonArray.getJSONObject(i).getString("iID_MaTran");
											if (CommonUtil.listQuanTam.contains(matran)){
												array.add(jsonArray.getJSONObject(i));
											}
										}else{
											array.add(jsonArray.getJSONObject(i));
										}
									}else{
										array.add(jsonArray.getJSONObject(i));
									}
									
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

							for (int i = 0; i < array.size(); i++) {
								String bNhanDinhChuyenGia = array.get(i).getString("bNhanDinhChuyenGia");
								String bGameDuDoan = array.get(i).getString("bGameDuDoan");
								String bDaCapNhapVaoBXH = array.get(i).getString("bDaCapNhapVaoBXH");
//								Log.e("kkk", i + "::" + bNhanDinhChuyenGia + ":" + bGameDuDoan + ":" + bDaCapNhapVaoBXH);

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
								// Log.e("kkk",sTenGiai +":" +iTrangThai + ":"
								// +sTenDoiNha);iID_MaGiai
								if (i == 0) {
									countryAdapter.addItem(new LiveScore(true, iID_MaTran, sTenGiai, sTenDoiNha, sTenDoiKhach, HT, iPhut, sThoiGian, iC0, tiso, iTrangThai, sMaGiai, sMaDoiNha,
											sMaDoiKhach, iID_MaGiai));
									countryAdapter.addItem(new LiveScore(false, iID_MaTran, sTenGiai, sTenDoiNha, sTenDoiKhach, HT, iPhut, sThoiGian, iC0, tiso, iTrangThai, sMaGiai, sMaDoiNha,
											sMaDoiKhach, iID_MaGiai));

								} else if (i > 0) {
									if ((array.get(i).getString("sTenGiai")).equalsIgnoreCase(array.get(i - 1).getString("sTenGiai"))) {
										countryAdapter.addItem(new LiveScore(false, iID_MaTran, sTenGiai, sTenDoiNha, sTenDoiKhach, HT, iPhut, sThoiGian, iC0, tiso, iTrangThai, sMaGiai, sMaDoiNha,
												sMaDoiKhach, iID_MaGiai));
									} else {
										countryAdapter.addItem(new LiveScore(true, iID_MaTran, sTenGiai, sTenDoiNha, sTenDoiKhach, HT, iPhut, sThoiGian, iC0, tiso, iTrangThai, sMaGiai, sMaDoiNha,
												sMaDoiKhach, iID_MaGiai));
										countryAdapter.addItem(new LiveScore(false, iID_MaTran, sTenGiai, sTenDoiNha, sTenDoiKhach, HT, iPhut, sThoiGian, iC0, tiso, iTrangThai, sMaGiai, sMaDoiNha,
												sMaDoiKhach, iID_MaGiai));

									}
								}

							}
							if(onLoad != 1){
								countryAdapter.notifyDataSetChanged();
							}
						} catch (JSONException e) {
						}

					}

				}

				@Override
				public void onError(String message) {
				}
			};

	}
	
	class MyTouchListener implements OnTouchListener
    {
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
				if(difference <= 10 && difference >= -10){
					callBackListenner.onCallBackListenner(3, liveScore);
				}else{
					calcuateDifference(liveScore);
				}
				break;
			}
			return true;
		}
    }
}
