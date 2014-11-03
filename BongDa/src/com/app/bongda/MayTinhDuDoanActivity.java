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

public class MayTinhDuDoanActivity extends BaseX1X2Activity {
	ICallbackAPI callbackAPI;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		showMayTinhDuDoan(null);
	}
}
