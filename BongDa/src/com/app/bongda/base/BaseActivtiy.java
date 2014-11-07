package com.app.bongda.base;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.app.bongda.R;

public class BaseActivtiy extends FragmentActivity {
	private List<BaseFragment> lBaseFragments = new ArrayList<BaseFragment>();

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.basefragmentactivity);
	}

	public void showFragment(BaseFragment baseFragment) {
		changeFragemt(baseFragment);
		lBaseFragments.add(baseFragment);
	}

	public void showXFragment(BaseFragment baseFragment) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager//
				.beginTransaction()//
				.replace(R.id.basefragmentactivity_main, baseFragment)//
				.setCustomAnimations(R.anim.bot_to_top, R.anim.nothing, R.anim.nothing, R.anim.top_to_bot)//
				.commit();//
		lBaseFragments = null;
	}

	@Override
	public void onBackPressed() {

		if (lBaseFragments == null) {
			super.onBackPressed();
			return;
		}
		if (lBaseFragments.size() == 0 || lBaseFragments.size() == 1) {
			super.onBackPressed();
		} else {
			lBaseFragments.remove(lBaseFragments.size() - 1);
			changeFragemt(lBaseFragments.get(lBaseFragments.size() - 1));
		}
	}

	private void changeFragemt(BaseFragment fragment) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.basefragmentactivity_main, fragment).commit();
	}
}
