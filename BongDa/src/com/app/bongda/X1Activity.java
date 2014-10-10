package com.app.bongda;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.app.bongda.base.BaseActivtiy;
import com.app.bongda.callback.APICaller;
import com.app.bongda.callback.APICaller.ICallbackAPI;
import com.app.bongda.fragment.DuDoanKetQuaFragment;
import com.app.bongda.fragment.LiveScoreFragment;
import com.app.bongda.fragment.PhongDoDoiDauFragment;
import com.app.bongda.fragment.TuongThuatTranLiveScoreFragment;
import com.app.bongda.fragment.TyLeDuDoanFragment;
import com.app.bongda.util.ByUtils;
import com.app.bongda.util.CommonAndroid;

public class X1Activity extends BaseActivtiy {
	Context context;
	ICallbackAPI callbackAPI;
	// live score
	// live cac tran -> xem truong thuat -> ty le du doan
	//									 -> du doan ket qua
	//                                   -> phong do doi dau
	//               -> phong do doi dau
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		callbackAPI = new ICallbackAPI() {
			@Override
			public void onSuccess(String response) {
				String string_temp = CommonAndroid.parseXMLAction(response);
				CommonAndroid.showDialog(context, "data:" + string_temp , null);
				
				try {
					JSONObject jsonObject = new JSONObject(string_temp);
					
				} catch (JSONException e) {
//					CommonAndroid.showDialog(context, e.getMessage() , null);
				}
				
			}

			@Override
			public void onError(String message) {
			}
		};
		
//		CallAPI();
		showFragment(new LiveScoreFragment(liveScoreOnItemClickListener));
	}
	
	public void CallAPI(){
		new APICaller(this).callApi("", true,
				callbackAPI, ByUtils.wsFootBall_Quocgia);
		
	}
	
	OnItemClickListener liveScoreOnItemClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			
			// phong do doi dau
			
			// xem tuong thuat a
			showFragment(new TuongThuatTranLiveScoreFragment(liveScoreTuongThuatOnItemClickListener));
		}
	};
	
	OnItemClickListener liveScoreTuongThuatOnItemClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			//									-> ty le du doan
			//			showFragment(new TyLeDuDoanFragment(null));
			//									-> du doan ket qua
			//			showFragment(new DuDoanKetQuaFragment(null));
			//                                  -> phong do doi dau
			showFragment(new PhongDoDoiDauFragment(null));
		}
	};


}
