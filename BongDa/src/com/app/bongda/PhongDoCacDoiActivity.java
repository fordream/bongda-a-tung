package com.app.bongda;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.app.bongda.base.BaseActivtiy;
import com.app.bongda.callback.APICaller.ICallbackAPI;
import com.app.bongda.fragment.CountryFragment;
import com.app.bongda.fragment.DanhSachGiaiDauFragment;
import com.app.bongda.fragment.TySoFragment;

public class PhongDoCacDoiActivity extends BaseActivtiy {
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
		showFragment(new CountryFragment(countryOnItemClickListener));
	}

	private OnItemClickListener countryOnItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

			showFragment(new DanhSachGiaiDauFragment(giaidauOnItemClickListener));

		}
	};

	private OnItemClickListener giaidauOnItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

			showFragment(new TySoFragment(null));

		}
	};
}
