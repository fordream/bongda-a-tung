package com.app.bongda.dialog;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
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
	private final String[] DATA = new String[] { "NOT SET", "5000", "10000",
			"30000" };

	private void showData() {
		final Button checkBox = (Button) findViewById(R.id.setting_reload);
		long isReload = BongDaServiceManager.getInstance().getBongDaService()
				.getReload();

		if (isReload == -1) {
			checkBox.setText(DATA[0]);
		} else {
			checkBox.setText(isReload + "");
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		showData();
		final Button checkBox = (Button) findViewById(R.id.setting_reload);
		checkBox.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Builder builder = new Builder(getContext());
				builder.setItems(DATA, new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (which == 0) {
							BongDaServiceManager.getInstance()
									.getBongDaService().setReload(-1);
						} else {
							BongDaServiceManager.getInstance()
									.getBongDaService()
									.setReload(Long.parseLong(DATA[which]));
						}
						showData();
					}
				});

				builder.setCancelable(false);
				builder.setPositiveButton("Cancel", null);
				builder.show();
			}
		});

		// checkBox.setChecked(isReload);
		//
		// checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		// @Override
		// public void onCheckedChanged(CompoundButton buttonView,
		// boolean isChecked) {
		// BongDaServiceManager.getInstance().getBongDaService()
		// .setReload(checkBox.isChecked());
		// }
		// });

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