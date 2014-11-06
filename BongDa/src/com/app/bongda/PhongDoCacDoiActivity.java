package com.app.bongda;

import android.app.Activity;
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
import com.app.bongda.view.BongDaViewPager;

public class PhongDoCacDoiActivity extends Activity {

	private BongDaViewPager bongDaViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vlayout);
		overridePendingTransition(R.anim.bot_to_top, R.anim.nothing);
		bongDaViewPager = (BongDaViewPager) findViewById(R.id.bongdaviewpager);
	}

	@Override
	public void onBackPressed() {

		if (bongDaViewPager.onBackPressed()) {
			return;
		}
		super.onBackPressed();
	}

	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(R.anim.nothing, R.anim.top_to_bot);
	}
}