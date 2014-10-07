package com.acv.libs.base;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public abstract class MbaseAdialog extends Dialog {
	public DialogInterface.OnClickListener clickListener;
	private VNPResize vnpResize = VNPResize.getInstance();

	public void setText(int registerfacebooktwitterEdt1, String name) {
		((TextView) findViewById(registerfacebooktwitterEdt1)).setText(name);
	}

	public String getTextStr(int res) {
		return ((TextView) findViewById(res)).getText().toString().trim();
	}

	public void resizeAndTextSize(View v, int wi, int height, int textHeight) {
		resize(v, wi, height);
		setTextSize(v, textHeight);
	}

	public void resize(View v, int wi, int height) {
		vnpResize.resizeSacle(v, wi, height);
	}

	public void setTextSize(View view, int height) {
		vnpResize.setTextsize(view, height);
	}

	public MbaseAdialog(Context context,
			DialogInterface.OnClickListener clickListener) {
		// super(context);
		super(context, android.R.style.Theme_Black_NoTitleBar);
		this.clickListener = clickListener;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setCancelable(false);
		setContentView(getLayout());
	}

	public abstract int getLayout();

	public void showDialogMessage(String message) {
		CommonAndroid.showDialog(getContext(), message, null);
	}
}