package org.acv.bynal.activity;

import org.acv.bynal.fragment.ForgotPaswordFragment;

import android.os.Bundle;

import com.acv.libs.base.BaseActivity;

public class ForgotpasswordActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		changeFragemt(new ForgotPaswordFragment());
	}

	@Override
	public void configMenuleft() {

	}
}