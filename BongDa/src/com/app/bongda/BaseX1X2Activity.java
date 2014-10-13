package com.app.bongda;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.app.bongda.base.BaseActivtiy;
import com.app.bongda.fragment.BangXepHangFragment;
import com.app.bongda.fragment.DuDoanKetQuaFragment;
import com.app.bongda.fragment.LiveScoreFragment;
import com.app.bongda.fragment.PhongDoDoiDauFragment;
import com.app.bongda.fragment.TuongThuatTranLiveScoreFragment;
import com.app.bongda.fragment.TyLeDuDoanFragment;
import com.app.bongda.inter.CallBackListenner;
import com.app.bongda.model.GiaiDau;
import com.app.bongda.model.LiveScore;

public class BaseX1X2Activity   extends BaseActivtiy{
	
	public void showLiveScore(Object data){
		showFragment(new LiveScoreFragment(liveScoreOnItemClickListener, new CallBackListenner() {
			@Override
			public void onCallBackListenner(int position, Object data) {
				LiveScore liveScore = (LiveScore)data;
				if(position == 0){
					GiaiDau dau = new GiaiDau(liveScore.getId(),liveScore.getName());
					showFragment(new BangXepHangFragment(dau, null));
				}else if(position == 2){
					showFragment(new PhongDoDoiDauFragment(null));
				}
			}
		}));
	}
	OnItemClickListener liveScoreOnItemClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			LiveScore liveScore = (LiveScore)parent.getItemAtPosition(position);
			
			// phong do doi dau
			
			// xem tuong thuat a
			if(!liveScore.isHeader()){
				showFragment(new TuongThuatTranLiveScoreFragment(liveScoreTuongThuatOnItemClickListener, new CallBackListenner() {
					
					@Override
					public void onCallBackListenner(int position, Object data) {
						if(position == 1){
							showBangXemHang((GiaiDau)data);
						}else if(position == 2){
							showFragment(new PhongDoDoiDauFragment(null));
						}else if(position == 3){
							showFragment(new DuDoanKetQuaFragment(null));
						}else if(position == 4){
							showFragment(new TyLeDuDoanFragment(null));
						}
					}
				}));
			}
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
	
	public void showBangXemHang(GiaiDau dau ){
		showFragment(new BangXepHangFragment(dau, null));
	}
}
