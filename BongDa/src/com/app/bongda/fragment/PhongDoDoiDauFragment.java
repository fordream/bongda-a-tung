package com.app.bongda.fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.app.bongda.R;
import com.app.bongda.base.BaseFragment;
import com.app.bongda.base.BongDaBaseAdapter;
import com.app.bongda.base.ImageLoaderUtils;
import com.app.bongda.callback.APICaller;
import com.app.bongda.callback.APICaller.ICallbackAPI;
import com.app.bongda.model.GiaiDau;
import com.app.bongda.model.NhanDinhChuyenGia;
import com.app.bongda.model.PhongDo;
import com.app.bongda.service.BongDaServiceManager;
import com.app.bongda.util.ByUtils;
import com.app.bongda.util.CommonAndroid;
import com.app.bongda.util.CommonUtil;
import com.app.bongda.view.BangXepHangItemView;
import com.app.bongda.view.HeaderView;
import com.app.bongda.view.PhongDodoiItemTanCongPhongThuView;
import com.app.bongda.view.PhongDodoiItemView;
import com.app.bongda.view.TyLeView;

public class PhongDoDoiDauFragment extends BaseFragment {
	OnItemClickListener onItemClickListener;
	GiaiDau giaidau;

	public PhongDoDoiDauFragment(GiaiDau giaiDau,
			OnItemClickListener onItemClickListener) {
		super();
		this.onItemClickListener = onItemClickListener;
		this.giaidau = giaiDau;
	}

	private CountryAdapter countryAdapter = new CountryAdapter();

	private class CountryAdapter extends BongDaBaseAdapter {

		@Override
		public int getLayout() {
			return R.layout.bangxephang_item;
		}

		@Override
		public void showData(Object item, View convertView) {
			final PhongDo phongdo = (PhongDo) item;
			// setText(convertView, R.id.title, phongdo.getName());
		}

	}

	@Override
	public int getLayout() {
		return R.layout.phongdodoidau;
	}

	LinearLayout phongdodoidau_bangephang_listitem;
	BangXepHangItemView bangxephangitem;
	TextView bangxephang_header;
	private String iID_MaDoiNha;
	private String iID_MaDoiKhach;
	private int iID_MaTran;
	private String iID_MaTran_;
	@Override
	public void onInitCreateView(View view) {
		/**
		 * init header view
		 */
		HeaderView headerView = (HeaderView) view
				.findViewById(R.id.headerView1);
		headerView.setTextHeader(R.string.xemphongdo);

		phongdodoidau_bangephang_listitem = (LinearLayout) view
				.findViewById(R.id.phongdodoidau_bangephang_listitem);
		phongdodoidau_bangephang_listitem.removeAllViews();

		// ListView listView = (ListView)
		// view.findViewById(R.id.bangxephang_listview);
		// listView.setAdapter(countryAdapter);
		bangxephang_header = (TextView) view.findViewById(R.id.bangxephang_header);
		bangxephangitem = (BangXepHangItemView) view.findViewById(R.id.bangXepHangItemView1);
		iID_MaDoiNha = giaidau.iID_MaDoiNha();
		iID_MaDoiKhach = giaidau.iID_MaDoiKhach();
		TenDoiNha = giaidau.sTenDoiNha();
		TenDoiKhach = giaidau.sTenDoiKhach();
		Log.e("onInitCreateView", "onInitCreateView::" + TenDoiNha + "::" + iID_MaDoiKhach);
		this.view = view;
		View customeProgressbar = headerView.findViewById(R.id.Button01);
		customeProgressbar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				/*
				 * clear data and reload new data
				 */
				
				countryAdapter.clear();
				loadData();
			}
		});
	}

	ICallbackAPI callbackAPI_LastMatches,callbackAPI_bangxephang, callbackAPI_Chitiet, callbackAPI_tuongthuat, callbackAPI_MayTinhDuDoan;
	private PhongDodoiItemView PhongDodoiItemView1, PhongDodoiItemView2;
	private PhongDodoiItemTanCongPhongThuView PhongDodoiItemTanCongPhongThuView1,
			PhongDodoiItemTanCongPhongThuView2;
	private View view;
	String TenDoiNha = "doi nha";
	String TenDoiKhach = "doi khach";
	String DoiNha_SoTran_GhiBan_SanNha;
	String DoiNha_SoTran_GhiBan_SanKhach;
	String DoiNha_TyLe_GhiBan_SanNha;
	String DoiNha_TyLe_GhiBan_SanKhach;
	String DoiNha_SoTran_Khong_GhiBan_SanNha;
	String DoiNha_SoTran_Khong_GhiBan_SanKhach;
	String DoiNha_SoTran_SachLuoi_SanNha;
	String DoiNha_SoTran_SachLuoi_SanKhach;
	String DoiNha_SoTran_LotLuoi_SanNha;
	String DoiNha_SoTran_LotLuoi_SanKhach;
	String DoiNha_TyLe_LotLuoi_SanNha;
	String DoiNha_TyLe_LotLuoi_SanKhach;
	String DoiNha_TyLe_GhiBan_TrungBinh;
	String DoiNha_SoTran_Khong_GhiBan;
	String DoiNha_SoTran_GhiBan;
	String DoiNha_Hieu_So_Ban_Thang;
	String DoiNha_TyLe_LotLuoi_TrungBinh;
	String DoiNha_SoTran_Khong_LotLuoi;
	String DoiNha_SoTran_LotLuoi;
	String DoiNha_Hieu_So_Ban_Thua;

	String DoiKhach_SoTran_GhiBan_SanNha;
	String DoiKhach_SoTran_GhiBan_SanKhach;
	String DoiKhach_TyLe_GhiBan_SanNha;
	String DoiKhach_TyLe_GhiBan_SanKhach;
	String DoiKhach_SoTran_Khong_GhiBan_SanNha;
	String DoiKhach_SoTran_Khong_GhiBan_SanKhach;
	String DoiKhach_SoTran_SachLuoi_SanNha;
	String DoiKhach_SoTran_SachLuoi_SanKhach;
	String DoiKhach_SoTran_LotLuoi_SanNha;
	String DoiKhach_SoTran_LotLuoi_SanKhach;
	String DoiKhach_TyLe_LotLuoi_SanNha;
	String DoiKhach_TyLe_LotLuoi_SanKhach;
	String DoiKhach_TyLe_GhiBan_TrungBinh;
	String DoiKhach_SoTran_Khong_GhiBan;
	String DoiKhach_SoTran_GhiBan;
	String DoiKhach_Hieu_So_Ban_Thang;
	String DoiKhach_TyLe_LotLuoi_TrungBinh;
	String DoiKhach_SoTran_Khong_LotLuoi;
	String DoiKhach_SoTran_LotLuoi;
	String DoiKhach_Hieu_So_Ban_Thua;

	private boolean show_BXH = false;
	private int sLastMatches_DoiNha_i1 = 0;
	private int sLastMatches_DoiNha_i = 0;
	private int sLastMatches_DoiNha_i2 = 0;
	private int sLastMatches_DoiKhach_i1 = 0;
	private int sLastMatches_DoiKhach_i = 0;
	private int sLastMatches_DoiKhach_i2 = 0;
	@Override
	public void onInitData() {
		ImageLoaderUtils.getInstance(null).DisplayImage(giaidau.sLogoDoiNha(),(ImageView) view.findViewById(R.id.logo_doinha)/*, BitmapFactory.decodeResource(view.getResources(), R.drawable.noimg)*/);
		ImageLoaderUtils.getInstance(null).DisplayImage(giaidau.sLogoDoiKhach(),(ImageView) view.findViewById(R.id.logo_doikhach)/*, BitmapFactory.decodeResource(view.getResources(), R.drawable.noimg)*/);
		callbackAPI_LastMatches = new ICallbackAPI() {
			@Override
			public void onSuccess(String response) {
				String string_temp = response == null ? "" : CommonAndroid.parseXMLAction(response);
				if (!string_temp.equalsIgnoreCase("")) {
					try {
//						Log.e("aaaaa", "callbackAPI_LastMatches::" + string_temp);
						JSONArray jsonArray = new JSONArray(string_temp);
						String iID_MaGiai = null;
						if (jsonArray != null) {
							String sLastMatches_DoiNha = "";
							String sLastMatches_DoiKhach = "";
							for (int i = 0; i < jsonArray.length(); i++) {
								//iID_MaBXH_ChiTiet
								if(jsonArray.getJSONObject(i).has("iID_MaBXH_ChiTiet")){
									show_BXH = true;
									//co bang xep hang
									bangxephang_header.setVisibility(View.VISIBLE);
									phongdodoidau_bangephang_listitem.setVisibility(View.VISIBLE);
									bangxephangitem.setVisibility(View.VISIBLE);
//									Log.e("onInitData", "onInitData==doi nha" + iID_MaDoiNha + ":::" + jsonArray.getJSONObject(i).getString("iID_MaDoi") + ":tendoi:" + jsonArray.getJSONObject(i).getString("sTenDoi"));
									if((jsonArray.getJSONObject(i).getString("iID_MaDoi")).equalsIgnoreCase(iID_MaDoiNha)){
//										Log.e("onInitData", "onInitData==doi nha");
										sLastMatches_DoiNha = jsonArray.getJSONObject(i).getString("sLast5Match");
										sLastMatches_DoiNha_i = i;
									}
									if((jsonArray.getJSONObject(i).getString("iID_MaDoi")).equalsIgnoreCase(iID_MaDoiKhach)){
										sLastMatches_DoiKhach = jsonArray.getJSONObject(i).getString("sLast5Match");
										sLastMatches_DoiKhach_i = i;
									}
									
								}else{
									//ko co bang xep hang
									show_BXH = false;
									bangxephang_header.setVisibility(View.GONE);
									phongdodoidau_bangephang_listitem.setVisibility(View.GONE);
									iID_MaGiai = jsonArray.getJSONObject(i).getString("iID_MaGiai");
									if(jsonArray.getJSONObject(i).has("sTenDoiNha")){
										TenDoiNha = jsonArray.getJSONObject(i).getString("sTenDoiNha");
									}
									if(jsonArray.getJSONObject(i).has("sTenDoiKhach")){
										TenDoiKhach = jsonArray.getJSONObject(i).getString("sTenDoiKhach");
									}
									sLastMatches_DoiNha = jsonArray.getJSONObject(i).getString("sLastMatches_DoiNha");
									sLastMatches_DoiKhach = jsonArray.getJSONObject(i).getString("sLastMatches_DoiKhach");
									
								}
								
							}
							
							//show bxh
							if(show_BXH){
								try {
									if(sLastMatches_DoiNha_i == 0){
										sLastMatches_DoiNha_i1 = 1;
										sLastMatches_DoiNha_i2 = 2;
									}else if(sLastMatches_DoiNha_i == (jsonArray.length() - 1)){
										sLastMatches_DoiNha_i1 = (jsonArray.length() - 2);
										sLastMatches_DoiNha_i2 = (jsonArray.length() - 3);
									}else{
										sLastMatches_DoiNha_i1 = sLastMatches_DoiNha_i - 1;
										sLastMatches_DoiNha_i2 = sLastMatches_DoiNha_i + 1;
									}
									
									if(sLastMatches_DoiKhach_i == 0){
										sLastMatches_DoiKhach_i1 = 1;
										sLastMatches_DoiKhach_i2 = 2;
									}else if(sLastMatches_DoiKhach_i == (jsonArray.length() - 1)){
										sLastMatches_DoiKhach_i1 = (jsonArray.length() - 2);
										sLastMatches_DoiKhach_i2 = (jsonArray.length() - 3);
									}else{
										sLastMatches_DoiKhach_i1 = sLastMatches_DoiKhach_i - 1;
										sLastMatches_DoiKhach_i2 = sLastMatches_DoiKhach_i + 1;
									}
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
								int k= 0;
								phongdodoidau_bangephang_listitem.removeAllViews();
								for (int i = 0; i < jsonArray.length(); i++) {
									//add bang xep hang
									if(i == sLastMatches_DoiNha_i || i == sLastMatches_DoiNha_i1 || i == sLastMatches_DoiNha_i2 || i == sLastMatches_DoiKhach_i || i == sLastMatches_DoiKhach_i1 || i == sLastMatches_DoiKhach_i2){
									String id = jsonArray.getJSONObject(i).getString(
											"iID_MaDoi");
									String name = jsonArray.getJSONObject(i).getString(
											"sTenDoi");
									String sViTri = jsonArray.getJSONObject(i)
											.getString("sViTri");
									String sSoTranDau = jsonArray.getJSONObject(i)
											.getString("sSoTranDau");
									String sDiem = jsonArray.getJSONObject(i)
											.getString("sDiem");
									String sSoTranThang = jsonArray.getJSONObject(i)
											.getString("sSoTranThang");
									String sSoTranHoa = jsonArray.getJSONObject(i)
											.getString("sSoTranHoa");
									String sSoTranThua = jsonArray.getJSONObject(i)
											.getString("sSoTranThua");
									String sBanThang = jsonArray.getJSONObject(i)
											.getString("sBanThang");
									String sBanThua = jsonArray.getJSONObject(i)
											.getString("sBanThua");
									String sHeSo = jsonArray.getJSONObject(i)
											.getString("sHeSo");
									k++;
									PhongDo phongdo = new PhongDo(id, name, sViTri,
											sSoTranDau, sDiem, sSoTranThang,
											sSoTranHoa, sSoTranThua, sBanThang,
											sBanThua, sHeSo, k);
									// countryAdapter.addItem(new PhongDo( id, name,
									// sViTri, sSoTranDau, sDiem, sSoTranThang,
									// sSoTranHoa, sSoTranThua, sBanThang, sBanThua,
									// sHeSo));
									phongdodoidau_bangephang_listitem
											.addView(new BangXepHangItemView(view
													.getContext(), phongdo));
									}
								}
							}
							String[] temps1 = sLastMatches_DoiNha.split(",");
							((TextView) view.findViewById(R.id.doinha_t1)).setText(CheckLastMatches2(temps1[0]));
							((TextView) view.findViewById(R.id.doinha_t2)).setText(CheckLastMatches2(temps1[1]));
							((TextView) view.findViewById(R.id.doinha_t3)).setText(CheckLastMatches2(temps1[2]));
							((TextView) view.findViewById(R.id.doinha_t4)).setText(CheckLastMatches2(temps1[3]));
							((TextView) view.findViewById(R.id.doinha_t5)).setText(CheckLastMatches2(temps1[4]));
							
							((TextView) view.findViewById(R.id.doinha_t1)).setBackgroundColor(CheckLastMatches(temps1[0]));
							((TextView) view.findViewById(R.id.doinha_t2)).setBackgroundColor(CheckLastMatches(temps1[1]));
							((TextView) view.findViewById(R.id.doinha_t3)).setBackgroundColor(CheckLastMatches(temps1[2]));
							((TextView) view.findViewById(R.id.doinha_t4)).setBackgroundColor(CheckLastMatches(temps1[3]));
							((TextView) view.findViewById(R.id.doinha_t5)).setBackgroundColor(CheckLastMatches(temps1[4]));
							
							
							String[] temps2 = sLastMatches_DoiKhach.split(",");
							((TextView) view.findViewById(R.id.doikhach_t1)).setText(CheckLastMatches2(temps2[0]));
							((TextView) view.findViewById(R.id.doikhach_t2)).setText(CheckLastMatches2(temps2[1]));
							((TextView) view.findViewById(R.id.doikhach_t3)).setText(CheckLastMatches2(temps2[2]));
							((TextView) view.findViewById(R.id.doikhach_t4)).setText(CheckLastMatches2(temps2[3]));
							((TextView) view.findViewById(R.id.doikhach_t5)).setText(CheckLastMatches2(temps2[4]));
							
							((TextView) view.findViewById(R.id.doikhach_t1)).setBackgroundColor(CheckLastMatches(temps2[0]));
							((TextView) view.findViewById(R.id.doikhach_t2)).setBackgroundColor(CheckLastMatches(temps2[1]));
							((TextView) view.findViewById(R.id.doikhach_t3)).setBackgroundColor(CheckLastMatches(temps2[2]));
							((TextView) view.findViewById(R.id.doikhach_t4)).setBackgroundColor(CheckLastMatches(temps2[3]));
							((TextView) view.findViewById(R.id.doikhach_t5)).setBackgroundColor(CheckLastMatches(temps2[4]));
							
						}
//						String maGiaiDau = iID_MaGiai;
//						if (maGiaiDau == null) {
//							maGiaiDau = "";
//						}
//						BongDaServiceManager
//								.getInstance()
//								.getBongDaService()
//								.callApi(
//										System.currentTimeMillis(),
//										callbackAPI_bangxephang,
//										ByUtils.wsFootBall_BangXepHang.replace("bangxephangId",
//												maGiaiDau));
//						countryAdapter.notifyDataSetChanged();
						PhongDoChiTiet();
						// countryAdapter.notifyDataSetChanged();
						maytinhdudoan();
					} catch (Exception e) {
						maytinhdudoan();
						Log.e("ERR", "callbackAPI_LastMatches" +e.getMessage());
					}

				}

			}

			@Override
			public void onError(String message) {
				maytinhdudoan();
				Log.e("ERR", message);
			}
		};
		
		/*callbackAPI_bangxephang = new ICallbackAPI() {
			@Override
			public void onSuccess(String response) {
				// CommonAndroid.showDialog(getActivity(), "data2:" + response ,
				// null);
//				 Log.e("aaaaa", "callbackAPI_bangxephang::" + response);
				String string_temp = response == null ? "" : CommonAndroid.parseXMLAction(response);
				if (!string_temp.equalsIgnoreCase("")) {
					try {
//						Log.e("aaaaa", "data::" + string_temp);
						JSONArray jsonarray = new JSONArray(string_temp);
						for (int i = 0; i < jsonarray.length(); i++) {
//							Log.e("onInitData", "onInitData==doi nha" + iID_MaDoiNha + ":::" + jsonarray.getJSONObject(i).getString("iID_MaDoi"));
							String id = jsonarray.getJSONObject(i).getString(
									"iID_MaDoi");
							String name = jsonarray.getJSONObject(i).getString(
									"sTenDoi");
							String sViTri = jsonarray.getJSONObject(i)
									.getString("sViTri");
							String sSoTranDau = jsonarray.getJSONObject(i)
									.getString("sSoTranDau");
							String sDiem = jsonarray.getJSONObject(i)
									.getString("sDiem");
							String sSoTranThang = jsonarray.getJSONObject(i)
									.getString("sSoTranThang");
							String sSoTranHoa = jsonarray.getJSONObject(i)
									.getString("sSoTranHoa");
							String sSoTranThua = jsonarray.getJSONObject(i)
									.getString("sSoTranThua");
							String sBanThang = jsonarray.getJSONObject(i)
									.getString("sBanThang");
							String sBanThua = jsonarray.getJSONObject(i)
									.getString("sBanThua");
							String sHeSo = jsonarray.getJSONObject(i)
									.getString("sHeSo");
							PhongDo phongdo = new PhongDo(id, name, sViTri,
									sSoTranDau, sDiem, sSoTranThang,
									sSoTranHoa, sSoTranThua, sBanThang,
									sBanThua, sHeSo);
							// countryAdapter.addItem(new PhongDo( id, name,
							// sViTri, sSoTranDau, sDiem, sSoTranThang,
							// sSoTranHoa, sSoTranThua, sBanThang, sBanThua,
							// sHeSo));
							phongdodoidau_bangephang_listitem
									.addView(new BangXepHangItemView(view
											.getContext(), phongdo));
						}
						// countryAdapter.notifyDataSetChanged();
					} catch (Exception e) {
						Log.e("ERR", e.getMessage());
					}

				}

			}

			@Override
			public void onError(String message) {
				CommonAndroid.showDialog(view.getContext(), "err:" + message,
						null);
				Log.e("ERR", message);
			}
		};*/
		callbackAPI_Chitiet = new ICallbackAPI() {
			@Override
			public void onSuccess(String response) {
				// CommonAndroid.showDialog(getActivity(), "data2:" + response ,
				// null);
				String tran_txt = " " + phongdodoidau_bangephang_listitem.getContext().getResources().getString(R.string.tran);
				String string_temp = response == null ? "" : CommonAndroid.parseXMLAction(response);
				if (!string_temp.equalsIgnoreCase("")) {
					try {
						JSONArray jsonarray = new JSONArray(string_temp);
						for (int i = 0; i < jsonarray.length(); i++) {
							DoiNha_SoTran_GhiBan_SanNha = jsonarray
									.getJSONObject(i).getString(
											"DoiNha_SoTran_GhiBan_SanNha")
									+ tran_txt;
							DoiNha_SoTran_GhiBan_SanKhach = jsonarray
									.getJSONObject(i).getString(
											"DoiNha_SoTran_GhiBan_SanKhach")
									+ tran_txt;
							DoiNha_TyLe_GhiBan_SanNha = jsonarray
									.getJSONObject(i).getString(
											"DoiNha_TyLe_GhiBan_SanNha")
									+ tran_txt;
							DoiNha_TyLe_GhiBan_SanKhach = jsonarray
									.getJSONObject(i).getString(
											"DoiNha_TyLe_GhiBan_SanKhach")
									+ tran_txt;
							DoiNha_SoTran_Khong_GhiBan_SanNha = jsonarray
									.getJSONObject(i)
									.getString(
											"DoiNha_SoTran_Khong_GhiBan_SanNha")
									+ tran_txt;
							DoiNha_SoTran_Khong_GhiBan_SanKhach = jsonarray
									.getJSONObject(i)
									.getString(
											"DoiNha_SoTran_Khong_GhiBan_SanKhach")
									+ tran_txt;
							DoiNha_SoTran_SachLuoi_SanNha = jsonarray
									.getJSONObject(i).getString(
											"DoiNha_SoTran_SachLuoi_SanNha")
									+ tran_txt;
							DoiNha_SoTran_SachLuoi_SanKhach = jsonarray
									.getJSONObject(i).getString(
											"DoiNha_SoTran_SachLuoi_SanKhach")
									+ tran_txt;
							DoiNha_SoTran_LotLuoi_SanNha = jsonarray
									.getJSONObject(i).getString(
											"DoiNha_SoTran_LotLuoi_SanNha")
									+ tran_txt;
							DoiNha_SoTran_LotLuoi_SanKhach = jsonarray
									.getJSONObject(i).getString(
											"DoiNha_SoTran_LotLuoi_SanKhach")
									+ tran_txt;
							DoiNha_TyLe_LotLuoi_SanNha = jsonarray
									.getJSONObject(i).getString(
											"DoiNha_TyLe_LotLuoi_SanNha")
									+ tran_txt;
							DoiNha_TyLe_LotLuoi_SanKhach = jsonarray
									.getJSONObject(i).getString(
											"DoiNha_TyLe_LotLuoi_SanKhach")
									+ tran_txt;
							DoiNha_TyLe_GhiBan_TrungBinh = jsonarray
									.getJSONObject(i).getString(
											"DoiNha_TyLe_GhiBan_TrungBinh")
									+ tran_txt;
							DoiNha_SoTran_Khong_GhiBan = jsonarray
									.getJSONObject(i).getString(
											"DoiNha_SoTran_Khong_GhiBan")
									+ tran_txt;
							DoiNha_SoTran_GhiBan = jsonarray.getJSONObject(i)
									.getString("DoiNha_SoTran_GhiBan")
									+ tran_txt;
							DoiNha_Hieu_So_Ban_Thang = jsonarray.getJSONObject(
									i).getString("DoiNha_Hieu_So_Ban_Thang")
									+ tran_txt;
							DoiNha_TyLe_LotLuoi_TrungBinh = jsonarray
									.getJSONObject(i).getString(
											"DoiNha_TyLe_LotLuoi_TrungBinh")
									+ tran_txt;
							DoiNha_SoTran_Khong_LotLuoi = jsonarray
									.getJSONObject(i).getString(
											"DoiNha_SoTran_Khong_LotLuoi")
									+ tran_txt;
							DoiNha_SoTran_LotLuoi = jsonarray.getJSONObject(i)
									.getString("DoiNha_SoTran_LotLuoi")
									+ tran_txt;
							DoiNha_Hieu_So_Ban_Thua = jsonarray
									.getJSONObject(i).getString(
											"DoiNha_Hieu_So_Ban_Thua")
									+ tran_txt;
							DoiKhach_SoTran_GhiBan_SanNha = jsonarray
									.getJSONObject(i).getString(
											"DoiKhach_SoTran_GhiBan_SanNha")
									+ tran_txt;
							DoiKhach_SoTran_GhiBan_SanKhach = jsonarray
									.getJSONObject(i).getString(
											"DoiKhach_SoTran_GhiBan_SanKhach")
									+ tran_txt;
							DoiKhach_TyLe_GhiBan_SanNha = jsonarray
									.getJSONObject(i).getString(
											"DoiKhach_TyLe_GhiBan_SanNha")
									+ tran_txt;
							DoiKhach_TyLe_GhiBan_SanKhach = jsonarray
									.getJSONObject(i).getString(
											"DoiKhach_TyLe_GhiBan_SanKhach")
									+ tran_txt;
							DoiKhach_SoTran_Khong_GhiBan_SanNha = jsonarray
									.getJSONObject(i)
									.getString(
											"DoiKhach_SoTran_Khong_GhiBan_SanNha")
									+ tran_txt;
							DoiKhach_SoTran_Khong_GhiBan_SanKhach = jsonarray
									.getJSONObject(i)
									.getString(
											"DoiKhach_SoTran_Khong_GhiBan_SanKhach")
									+ tran_txt;
							DoiKhach_SoTran_SachLuoi_SanNha = jsonarray
									.getJSONObject(i).getString(
											"DoiKhach_SoTran_SachLuoi_SanNha")
									+ tran_txt;
							DoiKhach_SoTran_SachLuoi_SanKhach = jsonarray
									.getJSONObject(i)
									.getString(
											"DoiKhach_SoTran_SachLuoi_SanKhach")
									+ tran_txt;
							DoiKhach_SoTran_LotLuoi_SanNha = jsonarray
									.getJSONObject(i).getString(
											"DoiKhach_SoTran_LotLuoi_SanNha")
									+ tran_txt;
							DoiKhach_SoTran_LotLuoi_SanKhach = jsonarray
									.getJSONObject(i).getString(
											"DoiKhach_SoTran_LotLuoi_SanKhach")
									+ tran_txt;
							DoiKhach_TyLe_LotLuoi_SanNha = jsonarray
									.getJSONObject(i).getString(
											"DoiKhach_TyLe_LotLuoi_SanNha")
									+ tran_txt;
							DoiKhach_TyLe_LotLuoi_SanKhach = jsonarray
									.getJSONObject(i).getString(
											"DoiKhach_TyLe_LotLuoi_SanKhach")
									+ tran_txt;
							DoiKhach_TyLe_GhiBan_TrungBinh = jsonarray
									.getJSONObject(i).getString(
											"DoiKhach_TyLe_GhiBan_TrungBinh")
									+ tran_txt;
							DoiKhach_SoTran_Khong_GhiBan = jsonarray
									.getJSONObject(i).getString(
											"DoiKhach_SoTran_Khong_GhiBan")
									+ tran_txt;
							DoiKhach_SoTran_GhiBan = jsonarray.getJSONObject(i)
									.getString("DoiKhach_SoTran_GhiBan")
									+ tran_txt;
							DoiKhach_Hieu_So_Ban_Thang = jsonarray
									.getJSONObject(i).getString(
											"DoiKhach_Hieu_So_Ban_Thang")
									+ tran_txt;
							DoiKhach_TyLe_LotLuoi_TrungBinh = jsonarray
									.getJSONObject(i).getString(
											"DoiKhach_TyLe_LotLuoi_TrungBinh")
									+ tran_txt;
							DoiKhach_SoTran_Khong_LotLuoi = jsonarray
									.getJSONObject(i).getString(
											"DoiKhach_SoTran_Khong_LotLuoi")
									+ tran_txt;
							DoiKhach_SoTran_LotLuoi = jsonarray
									.getJSONObject(i).getString(
											"DoiKhach_SoTran_LotLuoi")
									+ tran_txt;
							DoiKhach_Hieu_So_Ban_Thua = jsonarray
									.getJSONObject(i).getString(
											"DoiKhach_Hieu_So_Ban_Thua")
									+ tran_txt;

						}

						PhongDodoiItemView1 = (PhongDodoiItemView) view
								.findViewById(R.id.phongDodoiItemView1);
						PhongDodoiItemView2 = (PhongDodoiItemView) view
								.findViewById(R.id.phongDodoiItemView2);
						PhongDodoiItemTanCongPhongThuView1 = (PhongDodoiItemTanCongPhongThuView) view
								.findViewById(R.id.phongDodoiItemTanCongPhongThuView1);
						PhongDodoiItemTanCongPhongThuView2 = (PhongDodoiItemTanCongPhongThuView) view
								.findViewById(R.id.phongDodoiItemTanCongPhongThuView2);

						PhongDodoiItemView1.setText(R.id.tendoi, TenDoiNha);
						PhongDodoiItemView1.setText(R.id.sotranghibansannha1,
								DoiNha_SoTran_GhiBan_SanNha);
						PhongDodoiItemView1.setText(R.id.sotranghibansannha2,
								DoiNha_SoTran_GhiBan_SanKhach);

						PhongDodoiItemView1.setText(
								R.id.tileghibansannhatrungbinh1,
								DoiNha_TyLe_GhiBan_SanNha);
						PhongDodoiItemView1.setText(
								R.id.tileghibansannhatrungbinh2,
								DoiNha_TyLe_GhiBan_SanKhach);

						PhongDodoiItemView1.setText(
								R.id.sotrankhongghibansannha1,
								DoiNha_SoTran_Khong_GhiBan_SanNha);
						PhongDodoiItemView1.setText(
								R.id.sotrankhongghibansannha2,
								DoiNha_SoTran_Khong_GhiBan_SanKhach);

						PhongDodoiItemView1.setText(R.id.sotransachluoisannha1,
								DoiNha_SoTran_SachLuoi_SanNha);
						PhongDodoiItemView1.setText(R.id.sotransachluoisannha2,
								DoiNha_SoTran_SachLuoi_SanKhach);

						PhongDodoiItemView1.setText(R.id.sotranlotluonsannha1,
								DoiNha_SoTran_LotLuoi_SanNha);
						PhongDodoiItemView1.setText(R.id.sotranlotluonsannha2,
								DoiNha_SoTran_LotLuoi_SanKhach);

						PhongDodoiItemView1.setText(
								R.id.tylelotluonsannhatrungbinh1,
								DoiNha_TyLe_LotLuoi_SanNha);
						PhongDodoiItemView1.setText(
								R.id.tylelotluonsannhatrungbinh2,
								DoiNha_TyLe_LotLuoi_SanKhach);

						// doikhach
						PhongDodoiItemView2.setText(R.id.tendoi, TenDoiKhach);
						PhongDodoiItemView2.setText(R.id.sotranghibansannha1,
								DoiKhach_SoTran_GhiBan_SanNha);
						PhongDodoiItemView2.setText(R.id.sotranghibansannha2,
								DoiKhach_SoTran_GhiBan_SanKhach);

						PhongDodoiItemView2.setText(
								R.id.tileghibansannhatrungbinh1,
								DoiKhach_TyLe_GhiBan_SanNha);
						PhongDodoiItemView2.setText(
								R.id.tileghibansannhatrungbinh2,
								DoiKhach_TyLe_GhiBan_SanKhach);

						PhongDodoiItemView2.setText(
								R.id.sotrankhongghibansannha1,
								DoiKhach_SoTran_Khong_GhiBan_SanNha);
						PhongDodoiItemView2.setText(
								R.id.sotrankhongghibansannha2,
								DoiKhach_SoTran_Khong_GhiBan_SanKhach);

						PhongDodoiItemView2.setText(R.id.sotransachluoisannha1,
								DoiKhach_SoTran_SachLuoi_SanNha);
						PhongDodoiItemView2.setText(R.id.sotransachluoisannha2,
								DoiKhach_SoTran_SachLuoi_SanKhach);

						PhongDodoiItemView2.setText(R.id.sotranlotluonsannha1,
								DoiKhach_SoTran_LotLuoi_SanNha);
						PhongDodoiItemView2.setText(R.id.sotranlotluonsannha2,
								DoiKhach_SoTran_LotLuoi_SanKhach);

						PhongDodoiItemView2.setText(
								R.id.tylelotluonsannhatrungbinh1,
								DoiKhach_TyLe_LotLuoi_SanNha);
						PhongDodoiItemView2.setText(
								R.id.tylelotluonsannhatrungbinh2,
								DoiKhach_TyLe_LotLuoi_SanKhach);

						PhongDodoiItemTanCongPhongThuView1.setText(R.id.tendoi,
								TenDoiNha);
						PhongDodoiItemTanCongPhongThuView1.setText(
								R.id.tyleghibantrungbinh1,
								DoiNha_TyLe_GhiBan_TrungBinh);
						PhongDodoiItemTanCongPhongThuView1.setText(
								R.id.sotrankhongghiban1,
								DoiNha_SoTran_Khong_GhiBan);
						PhongDodoiItemTanCongPhongThuView1.setText(
								R.id.sotranghiban1, DoiNha_SoTran_GhiBan);
						PhongDodoiItemTanCongPhongThuView1.setText(
								R.id.hieusobanthang1, DoiNha_Hieu_So_Ban_Thang);
						PhongDodoiItemTanCongPhongThuView1.setText(
								R.id.tylelotluoitrungbinh1,
								DoiNha_TyLe_LotLuoi_TrungBinh);
						PhongDodoiItemTanCongPhongThuView1.setText(
								R.id.sotrankhonglotluoi1,
								DoiNha_SoTran_Khong_LotLuoi);
						PhongDodoiItemTanCongPhongThuView1.setText(
								R.id.sotranlotluoi1, DoiNha_SoTran_LotLuoi);
						PhongDodoiItemTanCongPhongThuView1.setText(
								R.id.hieusobanthua1, DoiNha_Hieu_So_Ban_Thua);

						PhongDodoiItemTanCongPhongThuView2.setText(R.id.tendoi,
								TenDoiKhach);
						PhongDodoiItemTanCongPhongThuView2.setText(
								R.id.tyleghibantrungbinh1,
								DoiKhach_TyLe_GhiBan_TrungBinh);
						PhongDodoiItemTanCongPhongThuView2.setText(
								R.id.sotrankhongghiban1,
								DoiKhach_SoTran_Khong_GhiBan);
						PhongDodoiItemTanCongPhongThuView2.setText(
								R.id.sotranghiban1, DoiKhach_SoTran_GhiBan);
						PhongDodoiItemTanCongPhongThuView2.setText(
								R.id.hieusobanthang1,
								DoiKhach_Hieu_So_Ban_Thang);
						PhongDodoiItemTanCongPhongThuView2.setText(
								R.id.tylelotluoitrungbinh1,
								DoiKhach_TyLe_LotLuoi_TrungBinh);
						PhongDodoiItemTanCongPhongThuView2.setText(
								R.id.sotrankhonglotluoi1,
								DoiKhach_SoTran_Khong_LotLuoi);
						PhongDodoiItemTanCongPhongThuView2.setText(
								R.id.sotranlotluoi1, DoiKhach_SoTran_LotLuoi);
						PhongDodoiItemTanCongPhongThuView2.setText(
								R.id.hieusobanthua1, DoiKhach_Hieu_So_Ban_Thua);
						countryAdapter.notifyDataSetChanged();
					} catch (JSONException e) {
						// CommonAndroid.showDialog(getActivity(), "data2json:"
						// + e.getMessage() , null);
					}

				}

			}

			@Override
			public void onError(String message) {
				CommonAndroid.showDialog(getActivity(), "data3err:" + message,
						null);
				Log.e("ERR", message);
			}
		};

		callbackAPI_MayTinhDuDoan = new ICallbackAPI() {
			@Override
			public void onSuccess(String response) {
				String string_temp = response == null ? "" : CommonAndroid.parseXMLAction(response);
				if (!string_temp.equalsIgnoreCase("")) {
					try {
						Log.e("callbackAPI_MayTinhDuDoan", "callbackAPI_MayTinhDuDoan::" + string_temp);
						JSONArray jsonarray = new JSONArray(string_temp);
//						"rBanThang_DoiNha":3,"rBanThang_DoiKhach":1,"rSoBanCoTheGhi_DoiNha":3,"rSoBanCoTheGhi_DoiKhach":1.5
						String sobancotheghi = phongdodoidau_bangephang_listitem.getContext().getResources().getString(R.string.sobancotheghi) + " ";
						String rBanThang_DoiNha = jsonarray.getJSONObject(0).getString("rBanThang_DoiNha");
						String rBanThang_DoiKhach = jsonarray.getJSONObject(0).getString("rBanThang_DoiKhach");
						String rSoBanCoTheGhi_DoiNha = jsonarray.getJSONObject(0).getString("rSoBanCoTheGhi_DoiNha");
						String rSoBanCoTheGhi_DoiKhach = jsonarray.getJSONObject(0).getString("rSoBanCoTheGhi_DoiKhach");
						((TextView) view.findViewById(R.id.text_result_predict)).setText(rBanThang_DoiNha + " - " + rBanThang_DoiKhach);
						((TextView) view.findViewById(R.id.text_result_predict_home_team_goal)).setText(sobancotheghi + rSoBanCoTheGhi_DoiNha);
						((TextView) view.findViewById(R.id.text_result_predict_away_team_goal)).setText(sobancotheghi + rSoBanCoTheGhi_DoiKhach);
						((TextView) view.findViewById(R.id.text_result_predict_home_team_name)).setText(TenDoiNha);
						((TextView) view.findViewById(R.id.text_result_predict_away_team_name)).setText(TenDoiKhach);
					} catch (Exception e) {
						Log.e("ERR", e.getMessage());
					}

				}

			}

			@Override
			public void onError(String message) {
				Log.e("ERR", message);
			}
		};
		iID_MaTran_ = giaidau.iID_MaTran();//"58167";//giaidau.getId();
		loadData();
//		Log.e("KKKKKKKKKKKKK",
//				"===mg:" + giaidau.idmagiai()+ " :mt " + giaidau.iID_MaTran() + "::"+ iID_MaTran );
		// for(int i = 0; i < 6; i ++){
		// phongdodoidau_bangephang_listitem.addView(new
		// BangXepHangItemView(getActivity()));
		// }
	}

	private void loadData(){
		Object aobj[] = new Object[1];
		aobj[0] = Integer.valueOf(iID_MaTran_);
		iID_MaTran = Integer.valueOf(iID_MaTran_);
		String param = String.format(ByUtils.wsFootBall_Phong_Do, aobj);
//		Log.e("param_phongdo", "param:" +param);
		new APICaller(view.getContext()).callApi("", true, callbackAPI_LastMatches, param);
	}
	
	private void PhongDoChiTiet() {
		try {
			String magiai = giaidau.idmagiai();
			String madoinha = giaidau.iID_MaDoiNha();
			String madoikhach = giaidau.iID_MaDoiKhach();
			String param2 = (ByUtils.wsFootBall_Phong_Do_ChiTiet).replace(
					"magiai", magiai);
			param2 = param2.replace("madoinha", madoinha);
			param2 = param2.replace("madoikhach", madoikhach);
			Log.e("PhongDoChiTiet", "PhongDoChiTiet::" + param2);
			new APICaller(view.getContext()).callApi("", true, callbackAPI_Chitiet,
					param2);
		} catch (Exception exception) {

		}
	}
	
	//l, w, d
	private int CheckLastMatches(String values){
		int color = 1;
		if(values.equalsIgnoreCase("B") || values.equalsIgnoreCase("L") ){
			color = Color.RED;
		}else if(values.equalsIgnoreCase("T") || values.equalsIgnoreCase("W") ){
			color = Color.GREEN;
		}else if(values.equalsIgnoreCase("H") || values.equalsIgnoreCase("D") ){
			color = Color.YELLOW;
		}
		return color;
	}
	
	private String CheckLastMatches2(String values){
		String txt = "";
		if(values.equalsIgnoreCase("B") || values.equalsIgnoreCase("L") ){
			txt = "B";
		}else if(values.equalsIgnoreCase("T") || values.equalsIgnoreCase("W") ){
			txt = "T";
		}else if(values.equalsIgnoreCase("H") || values.equalsIgnoreCase("D") ){
			txt = "H";
		}
		return txt;
	}
	
	private void maytinhdudoan(){
		String param2 = String.format(ByUtils.wsFootBall_MayTinhDuDoan, iID_MaTran);
//		Log.e("param_phongdo", "param2:" +param2);
		new APICaller(view.getContext()).callApi("", false, callbackAPI_MayTinhDuDoan, param2);
	}
}
