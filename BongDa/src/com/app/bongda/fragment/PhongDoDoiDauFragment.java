package com.app.bongda.fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import android.view.View;
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
		this.view = view;

	}

	ICallbackAPI callbackAPI_LastMatches,callbackAPI_bangxephang, callbackAPI_Chitiet, callbackAPI_tuongthuat;
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

	@Override
	public void onInitData() {
		ImageLoaderUtils.getInstance(null).DisplayImage(giaidau.sLogoDoiNha(),(ImageView) view.findViewById(R.id.logo_doinha));
		ImageLoaderUtils.getInstance(null).DisplayImage(giaidau.sLogoDoiKhach(),(ImageView) view.findViewById(R.id.logo_doikhach));
		callbackAPI_LastMatches = new ICallbackAPI() {
			@Override
			public void onSuccess(String response) {
				String string_temp = response == null ? "" : CommonAndroid.parseXMLAction(response);
				if (!string_temp.equalsIgnoreCase("")) {
					try {
						Log.e("aaaaa", "callbackAPI_LastMatches::" + string_temp);
						JSONArray jsonArray = new JSONArray(string_temp);
						String iID_MaGiai = null;
						if (jsonArray != null) {
							for (int i = 0; i < jsonArray.length(); i++) {
								iID_MaGiai = jsonArray.getJSONObject(i).getString("iID_MaGiai");
								if(jsonArray.getJSONObject(i).has("sTenDoiNha")){
									TenDoiNha = jsonArray.getJSONObject(i).getString("sTenDoiNha");
								}
								if(jsonArray.getJSONObject(i).has("sTenDoiKhach")){
									TenDoiKhach = jsonArray.getJSONObject(i).getString("sTenDoiKhach");
								}
								String sLastMatches_DoiNha = jsonArray.getJSONObject(i).getString("sLastMatches_DoiNha");
								String sLastMatches_DoiKhach = jsonArray.getJSONObject(i).getString("sLastMatches_DoiKhach");
								String[] temps1 = sLastMatches_DoiNha.split(",");
								((TextView) view.findViewById(R.id.doinha_t1)).setText(temps1[0]);
								((TextView) view.findViewById(R.id.doinha_t2)).setText(temps1[1]);
								((TextView) view.findViewById(R.id.doinha_t3)).setText(temps1[2]);
								((TextView) view.findViewById(R.id.doinha_t4)).setText(temps1[3]);
								((TextView) view.findViewById(R.id.doinha_t5)).setText(temps1[4]);
								
								String[] temps2 = sLastMatches_DoiKhach.split(",");
								((TextView) view.findViewById(R.id.doikhach_t1)).setText(temps2[0]);
								((TextView) view.findViewById(R.id.doikhach_t2)).setText(temps2[1]);
								((TextView) view.findViewById(R.id.doikhach_t3)).setText(temps2[2]);
								((TextView) view.findViewById(R.id.doikhach_t4)).setText(temps2[3]);
								((TextView) view.findViewById(R.id.doikhach_t5)).setText(temps2[4]);
							}
							
						}
						String maGiaiDau = iID_MaGiai;
						if (maGiaiDau == null) {
							maGiaiDau = "";
						}
						BongDaServiceManager
								.getInstance()
								.getBongDaService()
								.callApi(
										System.currentTimeMillis(),
										callbackAPI_bangxephang,
										ByUtils.wsFootBall_BangXepHang.replace("bangxephangId",
												maGiaiDau));
						countryAdapter.notifyDataSetChanged();
						PhongDoChiTiet();
						// countryAdapter.notifyDataSetChanged();
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
		
		callbackAPI_bangxephang = new ICallbackAPI() {
			@Override
			public void onSuccess(String response) {
				// CommonAndroid.showDialog(getActivity(), "data2:" + response ,
				// null);
				 Log.e("aaaaa", "callbackAPI_bangxephang::" + response);
				String string_temp = response == null ? "" : CommonAndroid.parseXMLAction(response);
				if (!string_temp.equalsIgnoreCase("")) {
					try {
//						Log.e("aaaaa", "data::" + string_temp);
						JSONArray jsonarray = new JSONArray(string_temp);
						for (int i = 0; i < jsonarray.length(); i++) {
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
		};
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

		String iID_MaTran = giaidau.iID_MaTran();//"58167";//giaidau.getId();
		Log.e("KKKKKKKKKKKKK",
				"===mg:" + giaidau.idmagiai()+ " :mt " + giaidau.iID_MaTran() + "::"+ iID_MaTran );
		Object aobj[] = new Object[1];
		aobj[0] = Integer.valueOf(iID_MaTran);
		String param = String.format(ByUtils.wsFootBall_Phong_Do, aobj);
		Log.e("param_phongdo", "param:" +param);
		new APICaller(view.getContext()).callApi("", true, callbackAPI_LastMatches, param);
		
		// for(int i = 0; i < 6; i ++){
		// phongdodoidau_bangephang_listitem.addView(new
		// BangXepHangItemView(getActivity()));
		// }
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
			new APICaller(view.getContext()).callApi("", true, callbackAPI_Chitiet,
					param2);
		} catch (Exception exception) {

		}
	}
}
