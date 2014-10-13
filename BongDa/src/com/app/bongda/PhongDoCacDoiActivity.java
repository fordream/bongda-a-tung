package com.app.bongda;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.app.bongda.base.BaseActivtiy;
import com.app.bongda.callback.APICaller.ICallbackAPI;
import com.app.bongda.fragment.CountryFragment;
import com.app.bongda.fragment.DanhSachGiaiDauFragment;
import com.app.bongda.fragment.PhongDoFragment;
import com.app.bongda.model.Country;
import com.app.bongda.model.GiaiDau;

public class PhongDoCacDoiActivity extends BaseX1X2Activity {
	ICallbackAPI callbackAPI;

	// live score
	// country
	// ------->giai dau
	// ---------------->Ty so
	// ------------------------>tuong thuat?
	// ------------------------>??????

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// CallAPI();
//		showFragment(new CountryFragment(countryOnItemClickListener));
		showCountry();
	}

//	private OnItemClickListener countryOnItemClickListener = new OnItemClickListener() {
//
//		@Override
//		public void onItemClick(AdapterView<?> parent, View view, int position,
//				long id) {
//			Country country = (Country)parent.getItemAtPosition(position);
//			showFragment(new DanhSachGiaiDauFragment(country,giaidauOnItemClickListener));
//
//		}
//	};
//
//	private OnItemClickListener giaidauOnItemClickListener = new OnItemClickListener() {
//
//		@Override
//		public void onItemClick(AdapterView<?> parent, View view, int position,
//				long id) {
//			GiaiDau giaiDau = (GiaiDau)parent.getItemAtPosition(position);
//			
//			showFragment(new PhongDoFragment(giaiDau,null));
//
//		}
//	};
}
