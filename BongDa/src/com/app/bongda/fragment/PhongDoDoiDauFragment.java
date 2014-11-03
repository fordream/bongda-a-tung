package com.app.bongda.fragment;

import org.json.JSONArray;
import org.json.JSONException;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.app.bongda.R;
import com.app.bongda.base.BaseFragment;
import com.app.bongda.base.BongDaBaseAdapter;
import com.app.bongda.base.ImageLoaderUtils;
import com.app.bongda.callback.APICaller;
import com.app.bongda.callback.APICaller.ICallbackAPI;
import com.app.bongda.model.GiaiDau;
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
	public PhongDoDoiDauFragment(GiaiDau giaiDau,OnItemClickListener onItemClickListener) {
		super();
		this.onItemClickListener = onItemClickListener;
		this.giaidau = giaiDau;
	}

	private CountryAdapter countryAdapter = new CountryAdapter();

	private class CountryAdapter extends BongDaBaseAdapter {

		@Override
		public int getLayout() {
			return R.layout.phongdodoidau_item;
		}

		@Override
		public void showData(Object item, View convertView) {

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
	
		phongdodoidau_bangephang_listitem = (LinearLayout)view.findViewById(R.id.phongdodoidau_bangephang_listitem);
		phongdodoidau_bangephang_listitem.removeAllViews();
		this.view = view;
		
	}

	ICallbackAPI callbackAPI, callbackAPI_Chitiet, callbackAPI_tuongthuat ;
	private String iID_MaDoiNha;
	private String iID_MaDoiKhach;
	private String iID_MaGiai;
	private PhongDodoiItemView PhongDodoiItemView1, PhongDodoiItemView2;
	private PhongDodoiItemTanCongPhongThuView PhongDodoiItemTanCongPhongThuView1, PhongDodoiItemTanCongPhongThuView2;
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
	
	public String iCN_BanThang_DoiKhach;
    public String iCN_BanThang_DoiKhach_HT;
    public String iCN_BanThang_DoiNha;
    public String iCN_BanThang_DoiNha_HT;
    public String sTenDoiKhach;
    public String sTenDoiNha;
    public String sTenGiai;
    public String sLogoGiai;
    public String sLogoDoiNha;
    public String sLogoDoiKhach;
	@Override
	public void onInitData() {
		callbackAPI_tuongthuat = new ICallbackAPI() {
			@Override
			public void onSuccess(String response) {
//				CommonAndroid.showDialog(getActivity(), "data2:" + response , null);
				String string_temp = CommonAndroid.parseXMLAction(response);
				if(!string_temp.equalsIgnoreCase("")){
					try {
						JSONArray jsonarray = new JSONArray(string_temp);
						for (int i = 0; i < jsonarray.length(); i++) {
							//parse
							sTenGiai = jsonarray.getJSONObject(i).getString("sTenGiai");
							sTenDoiNha = jsonarray.getJSONObject(i).getString("sTenDoiNha");
							sTenDoiKhach = jsonarray.getJSONObject(i).getString("sTenDoiKhach");
							iCN_BanThang_DoiNha = jsonarray.getJSONObject(i).getString("iCN_BanThang_DoiNha");
							iCN_BanThang_DoiKhach = jsonarray.getJSONObject(i).getString("iCN_BanThang_DoiKhach");
							iCN_BanThang_DoiNha_HT= jsonarray.getJSONObject(i).getString("iCN_BanThang_DoiNha_HT");
							iCN_BanThang_DoiKhach_HT = jsonarray.getJSONObject(i).getString("iCN_BanThang_DoiKhach_HT");
							sLogoGiai = jsonarray.getJSONObject(i).getString("sLogoGiai");
							sLogoDoiNha = jsonarray.getJSONObject(i).getString("sLogoDoiNha");
							sLogoDoiKhach = jsonarray.getJSONObject(i).getString("sLogoDoiKhach");
							
							iID_MaDoiNha = jsonarray.getJSONObject(i).getString("iID_MaDoiNha");
							iID_MaDoiKhach = jsonarray.getJSONObject(i).getString("iID_MaDoiKhach");
							iID_MaGiai = jsonarray.getJSONObject(i).getString("iID_MaGiai");
							
						}
						ImageLoaderUtils.getInstance(getActivity()).DisplayImage(sLogoDoiNha, (ImageView) view.findViewById(R.id.logo_doinha));
						ImageLoaderUtils.getInstance(getActivity()).DisplayImage(sLogoDoiKhach, (ImageView) view.findViewById(R.id.logo_doikhach));
						
						if(!iID_MaGiai.equalsIgnoreCase("") && !iID_MaDoiNha.equalsIgnoreCase("")  && !iID_MaDoiKhach.equalsIgnoreCase("")){
							TenDoiNha = sTenDoiNha;
							TenDoiKhach = sTenDoiKhach;
							PhongDoChiTiet();
						}
						
					} catch (JSONException e) {
					}
					
				}
				
			}

			@Override
			public void onError(String message) {
			}
		};
		
		callbackAPI = new ICallbackAPI() {
			@Override
			public void onSuccess(String response) {
//				CommonAndroid.showDialog(getActivity(), "data2:" + response , null);
//				Log.e("aaaaa", "data::" + response);
				String string_temp = CommonAndroid.parseXMLAction(response);
				if(!string_temp.equalsIgnoreCase("")){
					try {
						JSONArray jsonarray = new JSONArray(string_temp);
						for (int i = 0; i < jsonarray.length(); i++) {
							//parse
							String callbackAPI = jsonarray.get(i).toString();
//							Log.e("callbackAPI",i + "::"+ callbackAPI );
//							iID_MaDoiNha = jsonarray.getJSONObject(i).getString("iID_MaDoiNha");
//							iID_MaDoiKhach = jsonarray.getJSONObject(i).getString("iID_MaDoiKhach");
//							iID_MaGiai = jsonarray.getJSONObject(i).getString("iID_MaGiai");
						}
					} catch (JSONException e) {
//						CommonAndroid.showDialog(getActivity(), "data2json:" + e.getMessage() , null);
					}
					
				}
				
			}

			@Override
			public void onError(String message) {
				CommonAndroid.showDialog(getActivity(), "err:" + message , null);
				Log.e("ERR",message);
			}
		};
		callbackAPI_Chitiet = new ICallbackAPI() {
			@Override
			public void onSuccess(String response) {
//				CommonAndroid.showDialog(getActivity(), "data2:" + response , null);
				String string_temp = CommonAndroid.parseXMLAction(response);
				if(!string_temp.equalsIgnoreCase("")){
					try {
						JSONArray jsonarray = new JSONArray(string_temp);
						for (int i = 0; i < jsonarray.length(); i++) {
							//parse
//							String callbackAPI_Chitiet = jsonarray.get(i).toString();
//							Log.e("callbackAPI_Chitiet",i + "::"+ callbackAPI_Chitiet );
							DoiNha_SoTran_GhiBan_SanNha = jsonarray.getJSONObject(i).getString("DoiNha_SoTran_GhiBan_SanNha");
							DoiNha_SoTran_GhiBan_SanKhach = jsonarray.getJSONObject(i).getString("DoiNha_SoTran_GhiBan_SanKhach");
							DoiNha_TyLe_GhiBan_SanNha = jsonarray.getJSONObject(i).getString("DoiNha_TyLe_GhiBan_SanNha");
							DoiNha_TyLe_GhiBan_SanKhach = jsonarray.getJSONObject(i).getString("DoiNha_TyLe_GhiBan_SanKhach");
							DoiNha_SoTran_Khong_GhiBan_SanNha = jsonarray.getJSONObject(i).getString("DoiNha_SoTran_Khong_GhiBan_SanNha");
							DoiNha_SoTran_Khong_GhiBan_SanKhach = jsonarray.getJSONObject(i).getString("DoiNha_SoTran_Khong_GhiBan_SanKhach");
							DoiNha_SoTran_SachLuoi_SanNha = jsonarray.getJSONObject(i).getString("DoiNha_SoTran_SachLuoi_SanNha");
							DoiNha_SoTran_SachLuoi_SanKhach = jsonarray.getJSONObject(i).getString("DoiNha_SoTran_SachLuoi_SanKhach");
							DoiNha_SoTran_LotLuoi_SanNha = jsonarray.getJSONObject(i).getString("DoiNha_SoTran_LotLuoi_SanNha");
							DoiNha_SoTran_LotLuoi_SanKhach = jsonarray.getJSONObject(i).getString("DoiNha_SoTran_LotLuoi_SanKhach");
							DoiNha_TyLe_LotLuoi_SanNha = jsonarray.getJSONObject(i).getString("DoiNha_TyLe_LotLuoi_SanNha");
							DoiNha_TyLe_LotLuoi_SanKhach = jsonarray.getJSONObject(i).getString("DoiNha_TyLe_LotLuoi_SanKhach");
							DoiNha_TyLe_GhiBan_TrungBinh = jsonarray.getJSONObject(i).getString("DoiNha_TyLe_GhiBan_TrungBinh");
							DoiNha_SoTran_Khong_GhiBan = jsonarray.getJSONObject(i).getString("DoiNha_SoTran_Khong_GhiBan");
							DoiNha_SoTran_GhiBan = jsonarray.getJSONObject(i).getString("DoiNha_SoTran_GhiBan");
							DoiNha_Hieu_So_Ban_Thang = jsonarray.getJSONObject(i).getString("DoiNha_Hieu_So_Ban_Thang");
							DoiNha_TyLe_LotLuoi_TrungBinh = jsonarray.getJSONObject(i).getString("DoiNha_TyLe_LotLuoi_TrungBinh");
							DoiNha_SoTran_Khong_LotLuoi = jsonarray.getJSONObject(i).getString("DoiNha_SoTran_Khong_LotLuoi");
							DoiNha_SoTran_LotLuoi = jsonarray.getJSONObject(i).getString("DoiNha_SoTran_LotLuoi");
							DoiNha_Hieu_So_Ban_Thua = jsonarray.getJSONObject(i).getString("DoiNha_Hieu_So_Ban_Thua");
							
							DoiKhach_SoTran_GhiBan_SanNha = jsonarray.getJSONObject(i).getString("DoiKhach_SoTran_GhiBan_SanNha");
							DoiKhach_SoTran_GhiBan_SanKhach = jsonarray.getJSONObject(i).getString("DoiKhach_SoTran_GhiBan_SanKhach");
							DoiKhach_TyLe_GhiBan_SanNha = jsonarray.getJSONObject(i).getString("DoiKhach_TyLe_GhiBan_SanNha");
							DoiKhach_TyLe_GhiBan_SanKhach = jsonarray.getJSONObject(i).getString("DoiKhach_TyLe_GhiBan_SanKhach");
							DoiKhach_SoTran_Khong_GhiBan_SanNha = jsonarray.getJSONObject(i).getString("DoiKhach_SoTran_Khong_GhiBan_SanNha");
							DoiKhach_SoTran_Khong_GhiBan_SanKhach = jsonarray.getJSONObject(i).getString("DoiKhach_SoTran_Khong_GhiBan_SanKhach");
							DoiKhach_SoTran_SachLuoi_SanNha = jsonarray.getJSONObject(i).getString("DoiKhach_SoTran_SachLuoi_SanNha");
							DoiKhach_SoTran_SachLuoi_SanKhach = jsonarray.getJSONObject(i).getString("DoiKhach_SoTran_SachLuoi_SanKhach");
							DoiKhach_SoTran_LotLuoi_SanNha = jsonarray.getJSONObject(i).getString("DoiKhach_SoTran_LotLuoi_SanNha");
							DoiKhach_SoTran_LotLuoi_SanKhach = jsonarray.getJSONObject(i).getString("DoiKhach_SoTran_LotLuoi_SanKhach");
							DoiKhach_TyLe_LotLuoi_SanNha = jsonarray.getJSONObject(i).getString("DoiKhach_TyLe_LotLuoi_SanNha");
							DoiKhach_TyLe_LotLuoi_SanKhach = jsonarray.getJSONObject(i).getString("DoiKhach_TyLe_LotLuoi_SanKhach");
							DoiKhach_TyLe_GhiBan_TrungBinh = jsonarray.getJSONObject(i).getString("DoiKhach_TyLe_GhiBan_TrungBinh");
							DoiKhach_SoTran_Khong_GhiBan = jsonarray.getJSONObject(i).getString("DoiKhach_SoTran_Khong_GhiBan");
							DoiKhach_SoTran_GhiBan = jsonarray.getJSONObject(i).getString("DoiKhach_SoTran_GhiBan");
							DoiKhach_Hieu_So_Ban_Thang = jsonarray.getJSONObject(i).getString("DoiKhach_Hieu_So_Ban_Thang");
							DoiKhach_TyLe_LotLuoi_TrungBinh = jsonarray.getJSONObject(i).getString("DoiKhach_TyLe_LotLuoi_TrungBinh");
							DoiKhach_SoTran_Khong_LotLuoi = jsonarray.getJSONObject(i).getString("DoiKhach_SoTran_Khong_LotLuoi");
							DoiKhach_SoTran_LotLuoi = jsonarray.getJSONObject(i).getString("DoiKhach_SoTran_LotLuoi");
							DoiKhach_Hieu_So_Ban_Thua = jsonarray.getJSONObject(i).getString("DoiKhach_Hieu_So_Ban_Thua");

						}
						
						PhongDodoiItemView1 = (PhongDodoiItemView) view.findViewById(R.id.phongDodoiItemView1);
						PhongDodoiItemView2 = (PhongDodoiItemView) view.findViewById(R.id.phongDodoiItemView2);
						PhongDodoiItemTanCongPhongThuView1 = (PhongDodoiItemTanCongPhongThuView) view.findViewById(R.id.phongDodoiItemTanCongPhongThuView1);
						PhongDodoiItemTanCongPhongThuView2 = (PhongDodoiItemTanCongPhongThuView) view.findViewById(R.id.phongDodoiItemTanCongPhongThuView2);
						
						PhongDodoiItemView1.setText(R.id.tendoi, TenDoiNha);
						PhongDodoiItemView1.setText(R.id.sotranghibansannha1,DoiNha_SoTran_GhiBan_SanNha);
						PhongDodoiItemView1.setText(R.id.sotranghibansannha2,DoiNha_SoTran_GhiBan_SanKhach);
						
						PhongDodoiItemView1.setText(R.id.tileghibansannhatrungbinh1, DoiNha_TyLe_GhiBan_SanNha);
						PhongDodoiItemView1.setText(R.id.tileghibansannhatrungbinh2, DoiNha_TyLe_GhiBan_SanKhach);
						
						PhongDodoiItemView1.setText(R.id.sotrankhongghibansannha1, DoiNha_SoTran_Khong_GhiBan_SanNha);
						PhongDodoiItemView1.setText(R.id.sotrankhongghibansannha2, DoiNha_SoTran_Khong_GhiBan_SanKhach);
						
						PhongDodoiItemView1.setText(R.id.sotransachluoisannha1, DoiNha_SoTran_SachLuoi_SanNha);
						PhongDodoiItemView1.setText(R.id.sotransachluoisannha2, DoiNha_SoTran_SachLuoi_SanKhach);
						
						PhongDodoiItemView1.setText(R.id.sotranlotluonsannha1, DoiNha_SoTran_LotLuoi_SanNha);
						PhongDodoiItemView1.setText(R.id.sotranlotluonsannha2, DoiNha_SoTran_LotLuoi_SanKhach);
						
						PhongDodoiItemView1.setText(R.id.tylelotluonsannhatrungbinh1, DoiNha_TyLe_LotLuoi_SanNha);
						PhongDodoiItemView1.setText(R.id.tylelotluonsannhatrungbinh2, DoiNha_TyLe_LotLuoi_SanKhach);
						
						//doikhach
						PhongDodoiItemView2.setText(R.id.tendoi, TenDoiKhach);
						PhongDodoiItemView2.setText(R.id.sotranghibansannha1,DoiKhach_SoTran_GhiBan_SanNha);
						PhongDodoiItemView2.setText(R.id.sotranghibansannha2,DoiKhach_SoTran_GhiBan_SanKhach);
						
						PhongDodoiItemView2.setText(R.id.tileghibansannhatrungbinh1, DoiKhach_TyLe_GhiBan_SanNha);
						PhongDodoiItemView2.setText(R.id.tileghibansannhatrungbinh2, DoiKhach_TyLe_GhiBan_SanKhach);
						
						PhongDodoiItemView2.setText(R.id.sotrankhongghibansannha1, DoiKhach_SoTran_Khong_GhiBan_SanNha);
						PhongDodoiItemView2.setText(R.id.sotrankhongghibansannha2, DoiKhach_SoTran_Khong_GhiBan_SanKhach);
						
						PhongDodoiItemView2.setText(R.id.sotransachluoisannha1, DoiKhach_SoTran_SachLuoi_SanNha);
						PhongDodoiItemView2.setText(R.id.sotransachluoisannha2, DoiKhach_SoTran_SachLuoi_SanKhach);
						
						PhongDodoiItemView2.setText(R.id.sotranlotluonsannha1, DoiKhach_SoTran_LotLuoi_SanNha);
						PhongDodoiItemView2.setText(R.id.sotranlotluonsannha2, DoiKhach_SoTran_LotLuoi_SanKhach);
						
						PhongDodoiItemView2.setText(R.id.tylelotluonsannhatrungbinh1, DoiKhach_TyLe_LotLuoi_SanNha);
						PhongDodoiItemView2.setText(R.id.tylelotluonsannhatrungbinh2, DoiKhach_TyLe_LotLuoi_SanKhach);
						
						PhongDodoiItemTanCongPhongThuView1.setText(R.id.tendoi, TenDoiNha);
						PhongDodoiItemTanCongPhongThuView1.setText(R.id.tyleghibantrungbinh1, DoiNha_TyLe_GhiBan_TrungBinh);
						PhongDodoiItemTanCongPhongThuView1.setText(R.id.sotrankhongghiban1 , DoiNha_SoTran_Khong_GhiBan);
						PhongDodoiItemTanCongPhongThuView1.setText(R.id.sotranghiban1 , DoiNha_SoTran_GhiBan);
						PhongDodoiItemTanCongPhongThuView1.setText(R.id.hieusobanthang1 , DoiNha_Hieu_So_Ban_Thang);
						PhongDodoiItemTanCongPhongThuView1.setText(R.id.tylelotluoitrungbinh1 , DoiNha_TyLe_LotLuoi_TrungBinh);
						PhongDodoiItemTanCongPhongThuView1.setText(R.id.sotrankhonglotluoi1 , DoiNha_SoTran_Khong_LotLuoi);
						PhongDodoiItemTanCongPhongThuView1.setText(R.id.sotranlotluoi1 , DoiNha_SoTran_LotLuoi);
						PhongDodoiItemTanCongPhongThuView1.setText(R.id.hieusobanthua1 , DoiNha_Hieu_So_Ban_Thua);
						
						PhongDodoiItemTanCongPhongThuView2.setText(R.id.tendoi, TenDoiKhach);
						PhongDodoiItemTanCongPhongThuView2.setText(R.id.tyleghibantrungbinh1, DoiKhach_TyLe_GhiBan_TrungBinh);
						PhongDodoiItemTanCongPhongThuView2.setText(R.id.sotrankhongghiban1 , DoiKhach_SoTran_Khong_GhiBan);
						PhongDodoiItemTanCongPhongThuView2.setText(R.id.sotranghiban1 , DoiKhach_SoTran_GhiBan);
						PhongDodoiItemTanCongPhongThuView2.setText(R.id.hieusobanthang1 , DoiKhach_Hieu_So_Ban_Thang);
						PhongDodoiItemTanCongPhongThuView2.setText(R.id.tylelotluoitrungbinh1 , DoiKhach_TyLe_LotLuoi_TrungBinh);
						PhongDodoiItemTanCongPhongThuView2.setText(R.id.sotrankhonglotluoi1 , DoiKhach_SoTran_Khong_LotLuoi);
						PhongDodoiItemTanCongPhongThuView2.setText(R.id.sotranlotluoi1 , DoiKhach_SoTran_LotLuoi);
						PhongDodoiItemTanCongPhongThuView2.setText(R.id.hieusobanthua1 , DoiKhach_Hieu_So_Ban_Thua);
						countryAdapter.notifyDataSetChanged();
					} catch (JSONException e) {
//						CommonAndroid.showDialog(getActivity(), "data2json:" + e.getMessage() , null);
					}
					
				}
				
			}

			@Override
			public void onError(String message) {
				CommonAndroid.showDialog(getActivity(), "data3err:" + message , null);
				Log.e("ERR",message);
			}
		};
		
        
        String iID_MaTran = giaidau.getId();
		Log.e("KKKKKKKKKKKKK", "===" + giaidau.magiai() + "::" + giaidau.madoinha() + ":" + giaidau.madoikhach());
		Object aobj[] = new Object[1];
        aobj[0] = Integer.valueOf(iID_MaTran);
        String param = String.format(ByUtils.wsFootBall_Phong_Do, aobj);
        
		new APICaller(getActivity()).callApi("", true,
					callbackAPI, param);
		for(int i = 0; i < 6; i ++){
			phongdodoidau_bangephang_listitem.addView(new BangXepHangItemView(getActivity()));
		}
		
//		iID_MaGiai = CommonUtil.getdata(getActivity(),"iID_MaGiai");
//		iID_MaDoiNha = CommonUtil.getdata(getActivity(),"iID_MaDoiNha");
//		iID_MaDoiKhach = CommonUtil.getdata(getActivity(),"iID_MaDoiKhach");
		
		//laythongtinchitiet
		String param2 = String.format(ByUtils.wsFootBall_MatchDetail, aobj);
		new APICaller(getActivity()).callApi("", true,
					callbackAPI_tuongthuat, param2);
	}
	
	private void PhongDoChiTiet(){
        String magiai 		= iID_MaGiai;
        String madoinha 	= iID_MaDoiNha;
        String madoikhach 	= iID_MaDoiKhach;
        Log.e("Tag", iID_MaGiai + ":" + iID_MaDoiNha + ":" + iID_MaDoiKhach);
        String param2 = (ByUtils.wsFootBall_Phong_Do_ChiTiet).replace("magiai",
        		magiai);
        param2 = param2.replace("madoinha",madoinha);
        param2 = param2.replace("madoikhach",madoikhach);
        new APICaller(getActivity()).callApi("", true,
        		callbackAPI_Chitiet, param2);
	}
}
