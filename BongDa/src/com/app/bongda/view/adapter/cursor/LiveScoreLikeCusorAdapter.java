package com.app.bongda.view.adapter.cursor;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.bongda.R;

public class LiveScoreLikeCusorAdapter extends CursorAdapter {

	public LiveScoreLikeCusorAdapter(Context context, Cursor c, boolean autoRequery) {
		super(context, c, autoRequery);
	}

	public LiveScoreLikeCusorAdapter(Context context, Cursor c, int flags) {
		super(context, c, flags);
	}

	@Override
	public void bindView(View arg0, Context arg1, Cursor arg2) {
		showData(arg2, arg0);
	}

	@Override
	public View newView(Context arg0, Cursor arg1, ViewGroup arg2) {
		View view = (View) ((LayoutInflater) arg2.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.livescore_item, null);
		showData(arg1, view);
		return view;
	}

	private void showData(Cursor arg1, View view) {
		// TextView textView = (TextView) view.findViewById(R.id.textView1);
		// textView.setText( arg1.getString(arg1.getColumnIndex("sTenGiai")));
	}

}