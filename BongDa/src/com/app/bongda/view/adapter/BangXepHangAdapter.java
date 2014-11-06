package com.app.bongda.view.adapter;

import android.view.View;

import com.app.bongda.R;
import com.app.bongda.base.BongDaBaseAdapter;
import com.app.bongda.model.BangXepHang;

public class BangXepHangAdapter extends BongDaBaseAdapter {

	@Override
	public int getLayout() {
		return R.layout.bangxephang_item;
	}

	@Override
	public void showData(int position, Object item, View convertView) {
		super.showData(position, item, convertView);
		setText(convertView, R.id.stt, position + "");
	}

	@Override
	public void showData(Object item, View convertView) {
		BangXepHang bangxephang = (BangXepHang) item;
		setText(convertView, R.id.tendoi, bangxephang.getName());
		setText(convertView, R.id.pts, bangxephang.getPts());
		setText(convertView, R.id._congtru, bangxephang.get_contru());
		setText(convertView, R.id.d, bangxephang.getD());
		setText(convertView, R.id.ga, bangxephang.getGa());
		setText(convertView, R.id.gf, bangxephang.getGf());
		setText(convertView, R.id.gp, bangxephang.getGp());
		setText(convertView, R.id.l, bangxephang.getL());
		setText(convertView, R.id.w, bangxephang.getW());
	}
}