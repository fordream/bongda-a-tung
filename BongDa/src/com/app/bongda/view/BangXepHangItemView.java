package com.app.bongda.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.bongda.R;
import com.app.bongda.model.PhongDo;

public class BangXepHangItemView extends LinearLayout {

	private PhongDo phongdodoi;

	public BangXepHangItemView(Context context) {
		super(context);
		init();
	}

	public BangXepHangItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	public BangXepHangItemView(Context context, PhongDo phongdo) {
		super(context);
		this.phongdodoi = phongdo;
		init();
		showData();
	}
	 
	private void init() {
		LayoutInflater inflater = (LayoutInflater) getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.phongdobangxephang_item, this);
	}
	
	@SuppressLint("ResourceAsColor")
	public void showData(){
		int check_row = phongdodoi.getNo();
		Log.e("phongdo", "no==" + check_row);
		if(check_row == 4){ //123 456
			((TextView) findViewById(R.id.line_row)).setVisibility(View.VISIBLE);
		}else{
			((TextView) findViewById(R.id.line_row)).setVisibility(View.GONE);
			if(check_row == 2 || check_row == 5){
				((TextView) findViewById(R.id.stt)).setTextColor(Color.parseColor("#cc6601"));
				((TextView) findViewById(R.id.tendoi)).setTextColor(Color.parseColor("#cc6601"));
				((TextView) findViewById(R.id.pts)).setTextColor(Color.parseColor("#cc6601"));
				((TextView) findViewById(R.id._congtru)).setTextColor(Color.parseColor("#cc6601"));
				((TextView) findViewById(R.id.d)).setTextColor(Color.parseColor("#cc6601"));
				((TextView) findViewById(R.id.ga)).setTextColor(Color.parseColor("#cc6601"));
				((TextView) findViewById(R.id.gf)).setTextColor(Color.parseColor("#cc6601"));
				((TextView) findViewById(R.id.gp)).setTextColor(Color.parseColor("#cc6601"));
				((TextView) findViewById(R.id.l)).setTextColor(Color.parseColor("#cc6601"));
				((TextView) findViewById(R.id.w)).setTextColor(Color.parseColor("#cc6601"));
			}else{
				((TextView) findViewById(R.id.stt)).setTextColor(Color.parseColor("#000000"));
				((TextView) findViewById(R.id.tendoi)).setTextColor(Color.parseColor("#000000"));
				((TextView) findViewById(R.id.pts)).setTextColor(Color.parseColor("#000000"));
				((TextView) findViewById(R.id._congtru)).setTextColor(Color.parseColor("#000000"));
				((TextView) findViewById(R.id.d)).setTextColor(Color.parseColor("#000000"));
				((TextView) findViewById(R.id.ga)).setTextColor(Color.parseColor("#000000"));
				((TextView) findViewById(R.id.gf)).setTextColor(Color.parseColor("#000000"));
				((TextView) findViewById(R.id.gp)).setTextColor(Color.parseColor("#000000"));
				((TextView) findViewById(R.id.l)).setTextColor(Color.parseColor("#000000"));
				((TextView) findViewById(R.id.w)).setTextColor(Color.parseColor("#000000"));
			}
		}
		
		
		
		((TextView) findViewById(R.id.stt)).setText(phongdodoi.sViTri());
		((TextView) findViewById(R.id.tendoi)).setText(phongdodoi.getName());
		((TextView) findViewById(R.id.pts)).setText(phongdodoi.sSoTranDau());
		((TextView) findViewById(R.id._congtru)).setText(phongdodoi.sHeSo());
		((TextView) findViewById(R.id.d)).setText(phongdodoi.sSoTranHoa());
		((TextView) findViewById(R.id.ga)).setText(phongdodoi.sBanThua());
		((TextView) findViewById(R.id.gf)).setText(phongdodoi.sBanThang());
		((TextView) findViewById(R.id.gp)).setText(phongdodoi.sDiem());
		((TextView) findViewById(R.id.l)).setText(phongdodoi.sSoTranThua());
		((TextView) findViewById(R.id.w)).setText(phongdodoi.sSoTranThang());
	}
			

}