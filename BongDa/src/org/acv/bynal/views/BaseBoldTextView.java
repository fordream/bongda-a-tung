package org.acv.bynal.views;

import com.acv.libs.base.ACVbaseApplication;
import com.acv.libs.base.CommonAndroid;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

//org.acv.bynal.views.BaseBoldTextView
public class BaseBoldTextView extends TextView {

	public BaseBoldTextView(Context context) {
		super(context);
		try {
			setTypeface(((ACVbaseApplication) getContext()
					.getApplicationContext()).getTypefaceBold());
		} catch (Exception exception) {

		} catch (Error error) {

		}
	}

	public BaseBoldTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		try {
			setTypeface(((ACVbaseApplication) getContext()
					.getApplicationContext()).getTypefaceBold());
		} catch (Exception exception) {

		} catch (Error error) {

		}
	}

	public BaseBoldTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		try {
			setTypeface(((ACVbaseApplication) getContext()
					.getApplicationContext()).getTypefaceBold());
		} catch (Exception exception) {

		} catch (Error error) {

		}
	}
}