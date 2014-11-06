//package com.app.bongda;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import android.content.Context;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//
//import com.app.bongda.base.BaseActivtiy;
//import com.app.bongda.callback.APICaller;
//import com.app.bongda.callback.APICaller.ICallbackAPI;
//import com.app.bongda.fragment.BangXepHangFragment;
//import com.app.bongda.fragment.GameDuDoanFragment;
//import com.app.bongda.fragment.LiveScoreFragment;
//import com.app.bongda.fragment.PhongDoDoiDauFragment;
//import com.app.bongda.fragment.TuongThuatTranLiveScoreFragment;
//import com.app.bongda.fragment.TyLeDuDoanFragment;
//import com.app.bongda.inter.CallBackListenner;
//import com.app.bongda.model.GiaiDau;
//import com.app.bongda.model.LiveScore;
//import com.app.bongda.util.ByUtils;
//import com.app.bongda.util.CommonAndroid;
//
//public class X1Activity extends BaseX1X2Activity {
//
//	// live score
//	// live cac tran -> xem truong thuat -> ty le du doan
//	// -> du doan ket qua
//	// -> phong do doi dau
//	// -> phong do doi dau
//	private int count = 0;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		showLiveScore(null,null);
//	}
//
//}
