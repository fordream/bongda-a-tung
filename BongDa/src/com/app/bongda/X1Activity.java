package com.app.bongda;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.app.bongda.base.BaseActivtiy;
import com.app.bongda.fragment.DuDoanKetQuaFragment;
import com.app.bongda.fragment.LiveScoreFragment;
import com.app.bongda.fragment.PhongDoDoiDauFragment;
import com.app.bongda.fragment.TuongThuatTranLiveScoreFragment;
import com.app.bongda.fragment.TyLeDuDoanFragment;

public class X1Activity extends BaseActivtiy {

	// live score
	// live cac tran -> xem truong thuat -> ty le du doan
	//									 -> du doan ket qua
	//                                   -> phong do doi dau
	//               -> phong do doi dau
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		showFragment(new LiveScoreFragment(liveScoreOnItemClickListener));
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
