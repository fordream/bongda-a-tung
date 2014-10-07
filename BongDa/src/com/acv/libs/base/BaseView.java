package com.acv.libs.base;

import android.content.Context;
import android.text.Html;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BaseView extends LinearLayout {
	private Object data;

	public void setText(String str, int res) {
		((TextView) findViewById(res)).setText(str);
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Object getData() {
		return data;
	}

	public void refresh() {

	}

	public BaseView(Context context) {
		super(context);
	}

	public BaseView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void init(int res) {
		LayoutInflater inflater = (LayoutInflater) getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(res, this);
	}

	public void setTextStrHtml(int messagepostFooterText, String string) {
		((TextView) findViewById(messagepostFooterText)).setText(Html
				.fromHtml(string));
	}

	public void setTextStr(int messagepostFooterText, String string) {
		((TextView) findViewById(messagepostFooterText)).setText(string);
	}

	public void resizeAndTextSize(View v, int wi, int height, int textHeight) {
		resize(v, wi, height);
		setTextSize(v, textHeight);
	}

	public void resize(View v, int wi, int height) {
		VNPResize.getInstance().resizeSacle(v, wi, height);
	}

	public void setTextSize(View view, int height) {
		VNPResize.getInstance().setTextsize(view, height);
	}
}