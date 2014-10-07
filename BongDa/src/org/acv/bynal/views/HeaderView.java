package org.acv.bynal.views;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.TextView;
import app.bynal.woman.R;

import com.acv.libs.base.BaseView;
import com.acv.libs.base.HeaderOption;
import com.acv.libs.base.HeaderOption.TYPEHEADER;
import com.acv.libs.base.VNPResize;

public class HeaderView extends BaseView implements Runnable {
	public interface IHeaderCallBack {
		public void onClickLeft();

		public void onClickRight();
	}

	public HeaderView(Context context) {
		super(context);
		init();
	}

	public HeaderView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		LayoutInflater inflater = (LayoutInflater) getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.header, this);
		try {
			findViewById(R.id.headerbutton1).setOnClickListener(
					new OnClickListener() {
						@Override
						public void onClick(View v) {

							if (headerOption != null)
								headerOption.onClickButtonLeft();
						}
					});

			findViewById(R.id.headerbutton2).setOnClickListener(
					new OnClickListener() {
						@Override
						public void onClick(View v) {
							if (headerOption != null)
								headerOption.onClickButtonRight();
						}
					});
			final RadioButton radioButton = (RadioButton) findViewById(R.id.headerradiogroup1);
			radioButton
					.setOnCheckedChangeListener(new OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {
							if (radioButton.isChecked()) {
								sendPosition(0);
							} else {
								sendPosition(1);
							}
						}
					});
			post(this);
		} catch (Exception exception) {

		}
	}

	public static final String KEYCHOOSER = "CHOOSER_POSITION";

	private void sendPosition(int i) {
		Intent intent = new Intent(KEYCHOOSER);
		intent.putExtra(KEYCHOOSER, i);
		getContext().sendBroadcast(intent);
	}

	public void setText(int str) {
		((TextView) findViewById(R.id.headertextView1)).setText(str);
	}

	private HeaderOption headerOption;

	public void setheaderOption(HeaderOption headerOption) {
		this.headerOption = headerOption;
		refresh();
	}

	public void refresh() {
		if (headerOption != null) {

			if (headerOption.getTypeHeader() == TYPEHEADER.CHECKBOX) {
				findViewById(R.id.headerradiogroup).setVisibility(View.VISIBLE);
				findViewById(R.id.headertextView1).setVisibility(GONE);
			} else {
				findViewById(R.id.headerradiogroup).setVisibility(View.GONE);
				findViewById(R.id.headertextView1).setVisibility(VISIBLE);
			}

			if (headerOption.getResHeader() != R.string.blank) {
				if (headerOption.getResHeader() == R.string.menu_home_message) {
					setText(R.string.header_message_room);
				} else {
					setText(headerOption.getResHeader());
				}
			}

			if (headerOption.getResHeader() == R.string.home) {
				findViewById(R.id.home_logo).setVisibility(View.VISIBLE);
				findViewById(R.id.headertextView1).setVisibility(View.GONE);
			} else {
				findViewById(R.id.home_logo).setVisibility(View.GONE);
				if (headerOption.getTypeHeader() != TYPEHEADER.CHECKBOX){
					findViewById(R.id.headertextView1).setVisibility(View.VISIBLE);
				}
			}

			findViewById(R.id.headerbutton1).setBackgroundResource(
					headerOption.getResDrawableLeft());
			findViewById(R.id.headerbutton2).setBackgroundResource(
					headerOption.getResDrawableRight());

			if (headerOption.isShowButtonLeft()) {
				findViewById(R.id.headerbutton1).setVisibility(VISIBLE);
			} else {
				findViewById(R.id.headerbutton1).setVisibility(GONE);
			}

			if (headerOption.isShowButtonRight()) {
				findViewById(R.id.headerbutton2).setVisibility(VISIBLE);
			} else {
				findViewById(R.id.headerbutton2).setVisibility(GONE);
			}
		}
	}

	@Override
	public void run() {
//		VNPResize vnpResize = VNPResize.getInstance();
//		vnpResize.resizeSacle(findViewById(R.id.header_main_content),
//				LayoutParams.MATCH_PARENT, 45);
//		vnpResize.resizeSacle(findViewById(R.id.headerbutton1), 45, 45);
//		vnpResize.resizeSacle(findViewById(R.id.headerbutton2), 45, 45);
//		vnpResize.setTextsize(findViewById(R.id.headertextView1), 25);
//		vnpResize.setTextsize(findViewById(R.id.headertextView1), 25);
//		vnpResize
//				.resizeSacle(findViewById(R.id.headerradiogroup1), 158 / 2, 25);
//		vnpResize
//				.resizeSacle(findViewById(R.id.headerradiogroup2), 158 / 2, 25);
//		resizeAndTextSize(findViewById(R.id.home_logo), 238 / 2, 15, 0);
	}

	public void reloadChooser() {
		RadioButton radioButton = (RadioButton) findViewById(R.id.headerradiogroup1);
		radioButton.setChecked(true);
	}

	public void setText(String str) {
		((TextView) findViewById(R.id.headertextView1)).setText(str);
	}
}