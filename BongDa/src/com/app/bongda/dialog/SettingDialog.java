package com.app.bongda.dialog;

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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.app.bongda.R;
import com.app.bongda.service.BongDaServiceManager;
import com.app.bongda.util.CommonAndroid;
import com.app.bongda.view.HeaderView;
import com.app.bongda.view.ThecaoView;

public class SettingDialog extends Dialog {
	private ThecaoView thecaoView;
	private ListView tinnhanview;
	private GridView appstoreview;
	private Context mContext;
	private RadioGroup mRadioGroup1;

	public SettingDialog(Context context) {
		super(context, android.R.style.Theme_Translucent_NoTitleBar);
		// setCancelable(false);

		this.mContext = context;
	}

	private boolean isRunAnimation = false;
	private final String[] DATA = new String[] { "NOT SET", "5000", "10000", "30000" };

	private void showData() {
		final Button checkBox = (Button) findViewById(R.id.setting_reload);
		long isReload = BongDaServiceManager.getInstance().getBongDaService().getReload();

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

		thecaoView = (ThecaoView) findViewById(R.id.thecaoview);
		tinnhanview = (ListView) findViewById(R.id.tinnhanview);
		appstoreview = (GridView) findViewById(R.id.appstoreview);
		mRadioGroup1 = (RadioGroup) findViewById(R.id.mRadioGroup1);

		appstoreview.setAdapter(new BaseAdapter() {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				if (convertView == null)
					convertView = ((LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.sms_item, null);
				return convertView;
			}

			@Override
			public long getItemId(int position) {
				return 0;
			}

			@Override
			public Object getItem(int position) {
				return null;
			}

			@Override
			public int getCount() {
				return 10;
			}
		});
		View view = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.tinnhanheader, null);
		tinnhanview.addHeaderView(view);
		tinnhanview.setAdapter(new BaseAdapter() {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				if (convertView == null)
					convertView = ((LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.sms_item, null);
				return convertView;
			}

			@Override
			public long getItemId(int position) {
				return 0;
			}

			@Override
			public Object getItem(int position) {
				return null;
			}

			@Override
			public int getCount() {
				return 10;
			}
		});
		appstoreview.setAdapter(new BaseAdapter() {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				if (convertView == null)
					convertView = ((LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.appstore_item, null);
				return convertView;
			}

			@Override
			public long getItemId(int position) {
				return 0;
			}

			@Override
			public Object getItem(int position) {
				return null;
			}

			@Override
			public int getCount() {
				return 10;
			}
		});

		android.widget.RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new android.widget.RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				int id = 2;
				if (R.id.radio0 == checkedId) {
					id = 0;
				}
				if (checkedId == R.id.radio1) {
					id = 1;
				}

				CommonAndroid.hiddenKeyBoard((Activity) mContext);
				thecaoView.setVisibility(id == 0 ? View.VISIBLE : View.GONE);
				tinnhanview.setVisibility(id == 1 ? View.VISIBLE : View.GONE);
				appstoreview.setVisibility(id == 2 ? View.VISIBLE : View.GONE);
			}
		};

		mRadioGroup1.setOnCheckedChangeListener(onCheckedChangeListener);
		// RadioButton.OnCheckedChangeListener changeListener = new
		// RadioButton.OnCheckedChangeListener() {
		//
		// @Override
		// public void onCheckedChanged(CompoundButton buttonView, boolean
		// isChecked) {
		// CommonAndroid.toast(mContext, "sss");
		// int id = 2;
		// if (((RadioButton) findViewById(R.id.radio0)).isChecked()) {
		// id = 0;
		// }
		// if (((RadioButton) findViewById(R.id.radio1)).isChecked()) {
		// id = 1;
		// }
		//
		// CommonAndroid.hiddenKeyBoard((Activity) mContext);
		// thecaoView.setVisibility(id == 0 ? View.VISIBLE : View.GONE);
		// tinnhanview.setVisibility(id == 1 ? View.VISIBLE : View.GONE);
		// appstoreview.setVisibility(id == 2 ? View.VISIBLE : View.GONE);
		// }
		// };

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
							BongDaServiceManager.getInstance().getBongDaService().setReload(-1);
						} else {
							BongDaServiceManager.getInstance().getBongDaService().setReload(Long.parseLong(DATA[which]));
						}
						showData();
					}
				});

				builder.setCancelable(false);
				builder.setPositiveButton("Cancel", null);
				builder.show();
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
		// Animation animation = AnimationUtils.loadAnimation(getContext(),
		// R.anim.bot_to_top);
		// animation.setAnimationListener(new AnimationListener() {
		//
		// @Override
		// public void onAnimationStart(Animation animation) {
		// isRunAnimation = true;
		// }
		//
		// @Override
		// public void onAnimationRepeat(Animation animation) {
		//
		// }
		//
		// @Override
		// public void onAnimationEnd(Animation animation) {
		// isRunAnimation = false;
		// }
		// });
		//
		// findViewById(R.id.settingdialog_main).setAnimation(animation);
	}

	// @Override
	// public void dismiss() {
	// if (!isRunAnimation) {
	// Animation animation = AnimationUtils.loadAnimation(getContext(),
	// R.anim.top_to_bot);
	// animation.setAnimationListener(new AnimationListener() {
	//
	// @Override
	// public void onAnimationStart(Animation animation) {
	// isRunAnimation = true;
	// }
	//
	// @Override
	// public void onAnimationRepeat(Animation animation) {
	//
	// }
	//
	// @Override
	// public void onAnimationEnd(Animation animation) {
	// isRunAnimation = false;
	// SettingDialog.super.dismiss();
	// }
	// });
	// findViewById(R.id.settingdialog_main).setAnimation(animation);
	// }
	// }
}