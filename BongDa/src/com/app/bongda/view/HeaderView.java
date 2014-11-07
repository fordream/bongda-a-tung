package com.app.bongda.view;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.bongda.CreateAccountActivity;
import com.app.bongda.R;
import com.app.bongda.SettingActivity;

public class HeaderView extends LinearLayout {

	public HeaderView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public HeaderView(Context context) {
		super(context);
		init();
	}

	private void init() {
		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.header, this);

		findViewById(R.id.button2).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String[] datas = new String[] { //
				getResources().getString(R.string.setting_mo_tai_khoan), //
						getResources().getString(R.string.setting_nap_the) //
				};//

				Builder builder = new Builder(getContext());
				builder.setItems(datas, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (which == 0) {
							getContext().startActivity(new Intent(getContext(), CreateAccountActivity.class));
						} else if (which == 1) {
							getContext().startActivity(new Intent(getContext(), SettingActivity.class));
						}
					}
				});
				builder.setNegativeButton(R.string.cancel, null);
				builder.setCancelable(false);
				builder.create().show();

				// new SettingDialog(getContext()).show();
			}
		});
	}

	public void setTextHeader(int bangxephang) {
		((TextView) findViewById(R.id.textView1)).setText(bangxephang);
	}

	public void hiddenProgressbar() {
		findViewById(R.id.Button01).setVisibility(View.GONE);
	}

	public void hiddenSetting() {
		findViewById(R.id.button2).setVisibility(View.GONE);

	}

	public void setOnClickListenerX(View.OnClickListener onClickListener) {
		findViewById(R.id.x).setVisibility(View.VISIBLE);
		findViewById(R.id.x).setOnClickListener(onClickListener);
	}
}