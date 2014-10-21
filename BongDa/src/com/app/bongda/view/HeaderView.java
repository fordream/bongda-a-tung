package com.app.bongda.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.bongda.R;
import com.app.bongda.dialog.SettingDialog;

public class HeaderView extends LinearLayout {

	public HeaderView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public HeaderView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		LayoutInflater inflater = (LayoutInflater) getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.header, this);

		findViewById(R.id.button2).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new SettingDialog(getContext()).show();
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