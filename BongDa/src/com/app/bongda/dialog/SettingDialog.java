package com.app.bongda.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.app.bongda.R;
import com.app.bongda.service.BongDaServiceManager;
import com.app.bongda.view.HeaderView;

public class SettingDialog extends Dialog {

	public SettingDialog(Context context) {
		super(context, android.R.style.Theme_Translucent_NoTitleBar);
		// setCancelable(false);
	}

	private boolean isRunAnimation = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);

		final CheckBox checkBox = (CheckBox) findViewById(R.id.setting_reload);
		boolean isReload = BongDaServiceManager.getInstance()
				.getBongDaService().getReload();
		checkBox.setChecked(isReload);

		checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				BongDaServiceManager.getInstance().getBongDaService()
						.setReload(checkBox.isChecked());
			}
		});

		/**
		 * init header view
		 */
		HeaderView headerView = (HeaderView) findViewById(R.id.headerView1);
		headerView.setTextHeader(R.string.msetting);
		headerView.hiddenProgressbar();
		headerView.hiddenSetting();

		headerView.setOnClickListenerX(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
		Animation animation = AnimationUtils.loadAnimation(getContext(),
				R.anim.bot_to_top);
		animation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				isRunAnimation = true;
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				isRunAnimation = false;
			}
		});

		findViewById(R.id.settingdialog_main).setAnimation(animation);
	}

	@Override
	public void dismiss() {
		if (!isRunAnimation) {
			Animation animation = AnimationUtils.loadAnimation(getContext(),
					R.anim.top_to_bot);
			animation.setAnimationListener(new AnimationListener() {

				@Override
				public void onAnimationStart(Animation animation) {
					isRunAnimation = true;
				}

				@Override
				public void onAnimationRepeat(Animation animation) {

				}

				@Override
				public void onAnimationEnd(Animation animation) {
					isRunAnimation = false;
					SettingDialog.super.dismiss();
				}
			});
			findViewById(R.id.settingdialog_main).setAnimation(animation);
		}
		// super.dismiss();
	}
}