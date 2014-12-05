package com.app.bongda.view.adapter;

import android.view.View;
import android.widget.TextView;

import com.app.bongda.R;
import com.app.bongda.base.BongDaBaseAdapter;
import com.app.bongda.model.GiaiDau;

public class DanhSachGiaiDauAdapter extends BongDaBaseAdapter {

	@Override
	public int getLayout() {
		return R.layout.danhsachgiaidau_item;
	}

	@Override
	public void showData(Object item, View convertView) {
		GiaiDau giaiDau = (GiaiDau) item;
		TextView textView = (TextView) convertView.findViewById(R.id.textView1);
		textView.setText(giaiDau.getName());
	}
}