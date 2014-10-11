package com.app.bongda;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.app.bongda.base.BaseActivtiy;
import com.app.bongda.fragment.CountryFragment;
import com.app.bongda.fragment.DanhSachGiaiDauFragment;
import com.app.bongda.fragment.PhongDoDoiDauFragment;
import com.app.bongda.model.Country;

public class X2Activity extends BaseX1X2Activity {
//Chcon giai dau
	// coountry -> chon giai dau-> tuogn thuat(giong ben live)
	//
	//                          -> phong do doi dau   
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		
		showFragment(new CountryFragment(clickListenerCountry));
	}
	
	private OnItemClickListener clickListenerCountry = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view,
				int position, long id) {
			Country country = (Country)parent.getItemAtPosition(position);
			showFragment(new DanhSachGiaiDauFragment(country,listGiaiDau));
		}
	};
	
	private OnItemClickListener listGiaiDau = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// data
			showLiveScore(parent.getItemAtPosition(position));
			//showFragment(new PhongDoDoiDauFragment(phongdoonItemClickListener));
			
			//TuongThuatTranFragment
		}
	};
	
	private OnItemClickListener phongdoonItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			
		}
	};

}
