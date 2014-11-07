package com.app.bongda;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RadioGroup;

import com.app.bongda.R;
import com.app.bongda.service.BongDaServiceManager;
import com.app.bongda.util.CommonAndroid;
import com.app.bongda.view.HeaderView;
import com.app.bongda.view.ThecaoView;

public class CreateAccountActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_new_account);
		overridePendingTransition(R.anim.bot_to_top, R.anim.nothing);

		/**
		 * init header view
		 */
		HeaderView headerView = (HeaderView) findViewById(R.id.headerView1);
		headerView.setTextHeader(R.string.create_new_account);
		headerView.hiddenProgressbar();
		headerView.hiddenSetting();

		headerView.setOnClickListenerX(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(R.anim.nothing, R.anim.top_to_bot);
	}
}