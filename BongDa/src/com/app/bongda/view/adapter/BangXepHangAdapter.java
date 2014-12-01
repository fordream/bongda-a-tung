package com.app.bongda.view.adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.bongda.R;
import com.app.bongda.base.BongDaBaseAdapter;
import com.app.bongda.model.BangXepHang;

public class BangXepHangAdapter extends BongDaBaseAdapter {
	RelativeLayout bangxephang_row2;
	@Override
	public int getLayout() {
		return R.layout.bangxephang_item;
	}

	@Override
	public void showData(int position, Object item, View convertView) {
		super.showData(position, item, convertView);
		setText(convertView, R.id.stt, (position + 1) + "");
		bangxephang_row2 = (RelativeLayout) convertView.findViewById(R.id.bangxephang_row);
		if(position % 2 == 0){
			//trang
			bangxephang_row2.setBackgroundColor(Color.WHITE);
		}else{
			//xam
			bangxephang_row2.setBackgroundColor(Color.GRAY);
		}
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