package com.app.bongda;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.app.bongda.base.BaseActivtiy;
import com.app.bongda.fragment.BangXepHangFragment;
import com.app.bongda.fragment.CountryFragment;
import com.app.bongda.fragment.DanhSachGiaiDauFragment;
import com.app.bongda.model.Country;

public class X4Activity extends BaseActivtiy {
	// bang xep hang
	// country -> danh sach giai dau -> bang xep hang
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		showFragment(new CountryFragment(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Country country = (Country) parent.getItemAtPosition(position);
				showFragment(new DanhSachGiaiDauFragment(country,
						new OnItemClickListener() {
							@Override
							public void onItemClick(AdapterView<?> parent,
									View view, int position, long id) {
								showBangXephang();
							}

						}));
			}

		}));
	}

	private void showDanhSachGiaiDau() {

	}

	private void showBangXephang() {
		showFragment(new BangXepHangFragment(null));

	}
}
