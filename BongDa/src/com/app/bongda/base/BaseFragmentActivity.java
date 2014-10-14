/**
 * 
 */
package com.app.bongda.base;

import com.app.bongda.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;

/**
 * class base for all activity
 * 
 * @author tvuong1pc
 * 
 */
public class BaseFragmentActivity extends FragmentActivity {
	public static final int DIALOG_LOGIN_FAIL = 1000;

	/**
	 * 
	 * @param v
	 * @param width
	 * @param height
	 * @param textSize
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mactivity);
	}

	/**
	 * convert view from resource
	 * 
	 * @param res
	 * @return
	 */
	public <T extends View> T getView(int res) {
		@SuppressWarnings("unchecked")
		T view = (T) findViewById(res);
		return view;
	}

	protected Context getContext() {
		return this;
	}

	protected Activity getActivity() {
		return this;
	}

	public void changeFragemt(int res, Fragment fragment) {
		res = R.id.mactivity_main;
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction().replace(res, fragment).commit();
	}
}