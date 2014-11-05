package com.app.bongda.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
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
		inflater.inflate(R.layout.bangxephang_item, this);
	}
	
	public void showData(){
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