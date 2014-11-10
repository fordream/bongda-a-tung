package com.app.bongda.view.adapter.cursor;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.app.bongda.R;
import com.app.bongda.base.ImageLoaderUtils;
import com.app.bongda.inter.CallBackListenner;

public class LiveScoreLikeCusorAdapter extends CursorAdapter {
	private OnItemClickListener onItemClickListener;
	private CallBackListenner callBackListenner;

	public LiveScoreLikeCusorAdapter(Context context, Cursor c, boolean autoRequery, OnItemClickListener onItemClickListener, CallBackListenner callBackListenner) {
		super(context, c, autoRequery);
		this.callBackListenner = callBackListenner;
		this.onItemClickListener = onItemClickListener;
	}

	public LiveScoreLikeCusorAdapter(Context context, Cursor c, int flags, OnItemClickListener onItemClickListener, CallBackListenner callBackListenner) {
		super(context, c, flags);
		this.callBackListenner = callBackListenner;
		this.onItemClickListener = onItemClickListener;
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
		String bdposition = arg1.getString(arg1.getColumnIndex("bdposition"));
		boolean isHeader = "0".equals(bdposition);
		view.findViewById(R.id.livescore_header).setVisibility(isHeader ? View.VISIBLE : View.GONE);
		view.findViewById(R.id.livescore_main).setVisibility(isHeader ? View.GONE : View.VISIBLE);
		if (isHeader) {
			String sLogoQuocGia = arg1.getString(arg1.getColumnIndex("sLogoQuocGia"));
			String sTenGiai = arg1.getString(arg1.getColumnIndex("sTenGiai"));
			((TextView) view.findViewById(R.id.textView1)).setText(sTenGiai);
			ImageLoaderUtils.getInstance(null).DisplayImage(sLogoQuocGia, (ImageView) view.findViewById(R.id.logogiai));
		} else {
			int status = 0;
			try {
				status = Integer.parseInt(arg1.getString(arg1.getColumnIndex("iTrangThai")));
			} catch (Exception ex) {
			}

			if (status >= 2) {
				view.findViewById(R.id.TextView03).setVisibility(View.VISIBLE);// live
				view.findViewById(R.id.TextView02_ketqua).setVisibility(View.VISIBLE);
				setText(view, R.id.TextView02_ketqua, arg1.getString(arg1.getColumnIndex("iTiso")));// tiso
				view.findViewById(R.id.ImageView031).setVisibility(View.GONE);
				setText(view, R.id.tv1, arg1.getString(arg1.getColumnIndex("HT")));

				if (status == 5) {
					setText(view, R.id.TextView01, "FT");// time
					view.findViewById(R.id.TextView03).setVisibility(View.GONE);// live
				} else if (status == 3) {
					setText(view, R.id.TextView01, "HT");// time
				} else if (status >= 10) {
					setText(view, R.id.TextView01, view.getContext().getResources().getString(R.string.hoanthidau));
					view.findViewById(R.id.TextView02_ketqua).setVisibility(View.GONE);
					view.findViewById(R.id.TextView03).setVisibility(View.GONE);// live
					view.findViewById(R.id.ImageView031).setVisibility(View.VISIBLE);
					// iC0
					String iC0 = arg1.getString(arg1.getColumnIndex("iC0"));
					java.util.Date localDate1 = new java.util.Date(1000L * Integer.valueOf(iC0));
					Object[] arrayOfObject1 = new Object[2];
					arrayOfObject1[0] = Integer.valueOf(localDate1.getDate());
					arrayOfObject1[1] = Integer.valueOf(1 + localDate1.getMonth());
					setText(view, R.id.tv1, String.format("%d/%d", arrayOfObject1));
				} else {

					// iPhut
					String iPhut = arg1.getString(arg1.getColumnIndex("iPhut"));
					setText(view, R.id.TextView01, iPhut + " '");// time
				}
			} else {
				view.findViewById(R.id.TextView03).setVisibility(View.GONE);// live
				view.findViewById(R.id.ImageView031).setVisibility(View.VISIBLE);
				view.findViewById(R.id.TextView02_ketqua).setVisibility(View.GONE);

				// sThoiGian
				String sThoiGian = arg1.getString(arg1.getColumnIndex("sThoiGian"));
				setText(view, R.id.TextView01, sThoiGian);// time

				// iC0
				String iC0 = arg1.getString(arg1.getColumnIndex("iC0"));
				int j = Integer.valueOf(iC0);
				java.util.Date localDate2 = new java.util.Date(1000L * j);
				System.currentTimeMillis();
				new java.sql.Date(j * 1000);
				Object[] arrayOfObject2 = new Object[2];
				arrayOfObject2[0] = Integer.valueOf(localDate2.getDate());
				arrayOfObject2[1] = Integer.valueOf(1 + localDate2.getMonth());
				setText(view, R.id.tv1, String.format("%d/%d", arrayOfObject2));
			}

			// setText(convertView, R.id.TextView01, liveScore.getTime());
			// sTenDoiNha
			String sTenDoiNha = arg1.getString(arg1.getColumnIndex("sTenDoiNha"));
			setText(view, R.id.TextView02, sTenDoiNha);

			// sTenDoiKhach
			String sTenDoiKhach = arg1.getString(arg1.getColumnIndex("sTenDoiKhach"));
			setText(view, R.id.TextView023, sTenDoiKhach);

			// sLogoGiai
			String sLogoGiai = arg1.getString(arg1.getColumnIndex("sLogoGiai"));
			ImageLoaderUtils.getInstance(null).DisplayImage(sLogoGiai, (ImageView) view.findViewById(R.id.logogiai));
			view.findViewById(R.id.image_bangxephang).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// callBackListenner.onCallBackListenner(2, liveScore);
				}
			});
			// convertView.findViewById(R.id.bangxephang_icon).setVisibility(View.VISIBLE);
			view.findViewById(R.id.phongdo_icon).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// callBackListenner.onCallBackListenner(0, liveScore);
				}
			});
		}
	}

	private void setText(View view, int res, String string) {
		TextView text = (TextView) view.findViewById(res);
		text.setText(string);
	}
}