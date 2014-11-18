package com.app.bongda;

import android.os.Bundle;

import com.app.bongda.base.BaseActivtiy;
import com.app.bongda.fragment.create.CreatePhoneFragment;
import com.app.bongda.fragment.create.LoginPhoneFragment;

public class LoginAccountActivity extends BaseActivtiy {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.bot_to_top, R.anim.nothing);
		showXFragment(new LoginPhoneFragment());
		
	}

	

	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(R.anim.nothing, R.anim.top_to_bot);
	}
}