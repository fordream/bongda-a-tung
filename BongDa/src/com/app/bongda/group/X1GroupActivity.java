package com.app.bongda.group;

import android.os.Bundle;

import com.app.bongda.MBaseGroupActivity;

public class X1GroupActivity extends MBaseGroupActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		startActivity(FRAGMENT.COUNTRY, null);
	}
}
