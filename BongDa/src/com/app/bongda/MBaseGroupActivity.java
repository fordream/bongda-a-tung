package com.app.bongda;

import android.os.Bundle;

import com.app.bongda.base.BaseGroupActivity;
import com.app.bongda.group.MActivity;
import com.app.bongda.model.BaseItem;

public class MBaseGroupActivity extends BaseGroupActivity {
	public enum FRAGMENT{
		BANGXEMHANG,
		COUNTRY,
		DANHSACHGIAIDAU,
		DUDOANKETQUA,
		LIVESCORE,
		PHONGDODOIDAU,
		PHONGDO,
		TUONGTHUATTRAN,
		TUOGNTHUATTRANLIVESCORE,
		TYLEDUDOAN
	}
	
	public void startActivity(FRAGMENT typeFragment, BaseItem object){
		Bundle extras = new Bundle();
		extras.putSerializable("FRAGMENT", typeFragment);
		extras.putParcelable("BaseItem", object);
		addView(typeFragment.name(), MActivity.class, extras);
		
	}
}