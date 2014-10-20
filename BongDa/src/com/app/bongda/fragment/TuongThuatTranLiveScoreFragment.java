package com.app.bongda.fragment;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import com.app.bongda.callback.APICaller;
import com.app.bongda.callback.APICaller.ICallbackAPI;
import com.app.bongda.inter.CallBackListenner;
import com.app.bongda.model.GiaiDau;
import com.app.bongda.model.LiveScore;
import com.app.bongda.model.TuongThuatTran;
import com.app.bongda.util.ByUtils;
import com.app.bongda.util.CommonAndroid;
import com.app.bongda.view.HeaderView;

public class TuongThuatTranLiveScoreFragment extends BaseFragment {
	OnItemClickListener onItemClickListener;
	CallBackListenner backListenner;
	GiaiDau dau;
	View view;
	public TuongThuatTranLiveScoreFragment(GiaiDau dau,
			OnItemClickListener onItemClickListener,
			CallBackListenner backListenner) {
		super();
		this.onItemClickListener = onItemClickListener;
		this.backListenner = backListenner;
		this.dau = dau;
	}

	private CountryAdapter countryAdapter = new CountryAdapter();

	private class CountryAdapter extends BongDaBaseAdapter {

		@Override
		public int getLayout() {
			return R.layout.tuongthuattructiep_item;
		}

		@Override
		public void showData(Object item, View convertView) {
			final TuongThuatTran tuongthuattran = (TuongThuatTran) item;
			if(tuongthuattran.isDoi() == 1){
				convertView.findViewById(R.id.doi1).setVisibility(View.VISIBLE);
				convertView.findViewById(R.id.doi2).setVisibility(View.GONE);
				setText(convertView, R.id.time1,tuongthuattran.isThoigian() + "'");
				setText(convertView, R.id.name1,tuongthuattran.getName());
				ImageView localImageView1 = (ImageView)convertView.findViewById(R.id.icon_tuongthuat1);
				if(tuongthuattran.isTrangthai() == 1){
					localImageView1.setImageResource(R.drawable.chitiettrandau_32);
				}else if(tuongthuattran.isTrangthai() == 3){
					localImageView1.setImageResource(R.drawable.chitiettrandau_40);
				}
			}else{
				convertView.findViewById(R.id.doi1).setVisibility(View.GONE);
				convertView.findViewById(R.id.doi2).setVisibility(View.VISIBLE);
				setText(convertView, R.id.time2,tuongthuattran.isThoigian() + "'");
				setText(convertView, R.id.name2,tuongthuattran.getName());
				ImageView localImageView2 = (ImageView)convertView.findViewById(R.id.icon_tuongthuat2);
				if(tuongthuattran.isTrangthai() == 2){
					localImageView2.setImageResource(R.drawable.chitiettrandau_32);
				}else if(tuongthuattran.isTrangthai() == 4){
					localImageView2.setImageResource(R.drawable.chitiettrandau_40);
				}
			}
			
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
		headerView.findViewById(R.id.Button02).setOnClickListener(clickListener);
		headerView.findViewById(R.id.Button03).setOnClickListener(clickListener);
		headerView.findViewById(R.id.Button04).setOnClickListener(clickListener);

		/** init data */
		ListView listView = (ListView) view.findViewById(R.id.listView1);
		listView.setAdapter(countryAdapter);
		listView.setOnItemClickListener(onItemClickListener);

		this.view = view;
//		((TextView) view.findViewById(R.id.textTenTran)).setText(dau.getName());

	}
	
	View.OnClickListener clickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {

			if(v.getId() == R.id.imageView1s){
				backListenner.onCallBackListenner(1, dau /*new GiaiDau("1", "aaaa")*/);

			}else if(v.getId() == R.id.Button02){
				backListenner.onCallBackListenner(2, dau/*null*/);
			}else if(v.getId() == R.id.Button03){
				backListenner.onCallBackListenner(3, dau/*null*/);
			}else if(v.getId() == R.id.Button04){
				backListenner.onCallBackListenner(4, dau/*null*/);
			}
		}
	};

	ICallbackAPI callbackAPI;
	private String iID_MaTran;
	public String iCN_BanThang_DoiKhach;
    public String iCN_BanThang_DoiKhach_HT;
    public String iCN_BanThang_DoiNha;
    public String iCN_BanThang_DoiNha_HT;
    private int iID_MaDoiKhach;
    private int iID_MaDoiNha;
    public int iID_MaGiai;
    public String iPhut;
    private boolean isInFront;
    private ListView listView;
    public String sTenDoiKhach;
    public String sTenDoiNha;
    public String sTenGiai;
	@Override
	public void onInitData() {

		


		callbackAPI = new ICallbackAPI() {
			@Override
			public void onSuccess(String response) {
//				CommonAndroid.showDialog(getActivity(), "data2:" + response , null);
				String string_temp = CommonAndroid.parseXMLAction(response);
				if(!string_temp.equalsIgnoreCase("")){
					try {
						JSONArray jsonarray = new JSONArray(string_temp);
//						CommonAndroid.showDialog(getActivity(), "data2:" + string_temp , null);
						for (int i = 0; i < jsonarray.length(); i++) {
							//parse
							sTenGiai = jsonarray.getJSONObject(i).getString("sTenGiai");
							sTenDoiNha = jsonarray.getJSONObject(i).getString("sTenDoiNha");
							sTenDoiKhach = jsonarray.getJSONObject(i).getString("sTenDoiKhach");
							iCN_BanThang_DoiNha = jsonarray.getJSONObject(i).getString("iCN_BanThang_DoiNha");
							iCN_BanThang_DoiKhach = jsonarray.getJSONObject(i).getString("iCN_BanThang_DoiKhach");
							iCN_BanThang_DoiNha_HT= jsonarray.getJSONObject(i).getString("iCN_BanThang_DoiNha_HT");
							iCN_BanThang_DoiKhach_HT = jsonarray.getJSONObject(i).getString("iCN_BanThang_DoiKhach_HT");
							iPhut = jsonarray.getJSONObject(i).getString("iPhut") + "'";
							loadItem(jsonarray.getJSONObject(i),"sThongTin_DoiNha",1);//GOAL_HOME
							loadItem(jsonarray.getJSONObject(i),"sThongTin_DoiKhach",2);//GOAL_AWAY
							loadItem(jsonarray.getJSONObject(i),"sThongTinThe_DoiNha",3);//YELLOW_CARD_HOME
							loadItem(jsonarray.getJSONObject(i),"sThongTinThe_DoiKhach",4);//YELLOW_CARD_AWAY
						}
						String HT = "";
						StringBuilder stringbuilder1 = new StringBuilder("HT ");
						HT = stringbuilder1.append(iCN_BanThang_DoiNha_HT).append(" - ").append(iCN_BanThang_DoiKhach_HT).toString();
				        
						String Banthang = (new StringBuilder()).append(iCN_BanThang_DoiNha).append(" - ").append(iCN_BanThang_DoiKhach).toString();
						((TextView) view.findViewById(R.id.textTenTran)).setText(sTenGiai);
						((TextView) view.findViewById(R.id.TextView01)).setText(sTenDoiNha);
						((TextView) view.findViewById(R.id.TextView02)).setText(sTenDoiKhach);
						((TextView) view.findViewById(R.id.tuongthuat_time)).setText(iPhut);
						((TextView) view.findViewById(R.id.tuongthuat_tiso)).setText(Banthang);
						((TextView) view.findViewById(R.id.tuongthuat_ht)).setText(HT);
						countryAdapter.notifyDataSetChanged();
					} catch (JSONException e) {
					}
					
				}
				
			}

			@Override
			public void onError(String message) {
			}
		};
		iID_MaTran = dau.getId();
//		Log.e("KKKKKKKKKKKKK", "===" + dau.magiai() + "::" + dau.madoinha() + ":" + dau.madoikhach());
		Object aobj[] = new Object[1];
        aobj[0] = Integer.valueOf(iID_MaTran);
        String param = String.format(ByUtils.wsFootBall_MatchDetail, aobj);
		new APICaller(getActivity()).callApi("", true,
					callbackAPI, param);

		
//		countryAdapter.addItem(new TuongThuatTran(true, "1", "1"));
		countryAdapter.notifyDataSetChanged();
	}
	
	private void loadItem(JSONObject jsonobject, String s, int status)
    {
		int doi = 1;
		if(status == 2 || status == 4){
			doi = 2;
		}
        if (!jsonobject.has(s)) {
//        	Log.e("KKKKKKKK", "KKKKKKKKKK001");
        }else{
//        	else goto _L1
        	String s3;
			try {
				s3 = jsonobject.getString(s);
	            if (s3 == null || s3.equals("null")) {
//	            	Log.e("KKKKKKKK", "KKKKKKKKKK002");
	            }else{
	                String temp1[];
	                int j = s3.indexOf(",");
	                if(j > 0){
	                	temp1 = s3.split(",");
		                for(int i= 0; i < temp1.length;i++ ){
		                	String temp2[];
		                	int k = temp1[i].indexOf(" ");
		                	if(k > 0){
			                	temp2 = temp1[i].split(" ");
	//		                	Log.e("KKKKKKKK", "KKKKKKKKKK11" + temp2[0] + ":::" + temp2[1]);
			                	countryAdapter.addItem(new TuongThuatTran(doi, null , temp2[0] ,temp2[1], status));
		                	}
		                }
	                }else{
	                	String temp2[];
	                	int k = s3.indexOf(" ");
	                	if(k > 0){
	                		temp2 = s3.split(" ");
	                		countryAdapter.addItem(new TuongThuatTran(doi, null , temp2[0] ,temp2[1], status));
	                	}
	                }
	                
	            }    
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }	

    }
}
