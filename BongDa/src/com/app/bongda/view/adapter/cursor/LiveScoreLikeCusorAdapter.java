package com.app.bongda.view.adapter.cursor;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.app.bongda.R;
import com.app.bongda.base.ImageLoaderUtils;
import com.app.bongda.fragment.LiveScoreLikeFragment;
import com.app.bongda.inter.CallBackListenner;
import com.app.bongda.model.LiveScore;
import com.app.bongda.service.BongDaServiceManager;
import com.app.bongda.util.CommonUtil;

public class LiveScoreLikeCusorAdapter extends CursorAdapter {
	private OnItemClickListener onItemClickListener;
	private CallBackListenner callBackListenner;
	private Context ctx;

	public LiveScoreLikeCusorAdapter(Context context, Cursor c, boolean autoRequery, OnItemClickListener onItemClickListener, CallBackListenner callBackListenner) {
		super(context, c, autoRequery);
		this.callBackListenner = callBackListenner;
		this.onItemClickListener = onItemClickListener;
		this.ctx = context;
	}

	public LiveScoreLikeCusorAdapter(Context context, Cursor c, int flags, OnItemClickListener onItemClickListener, CallBackListenner callBackListenner) {
		super(context, c, flags);
		this.callBackListenner = callBackListenner;
		this.onItemClickListener = onItemClickListener;
		this.ctx = context;
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

	private int action_down_x = 0;
	private int action_up_x = 0;
	public static int difference = 0;
	private LiveScore liveScore;
	private void showData(Cursor arg1, View view) {
		String bdposition = arg1.getString(arg1.getColumnIndex("bdposition"));
		boolean isHeader = "0".equals(bdposition);
		view.findViewById(R.id.livescore_header).setVisibility(isHeader ? View.VISIBLE : View.GONE);
		view.findViewById(R.id.livescore_main).setVisibility(isHeader ? View.GONE : View.VISIBLE);
		view.findViewById(R.id.traitim).setVisibility(View.VISIBLE);
		view.findViewById(R.id.gamedudoan_icon).setVisibility(View.GONE);
		view.findViewById(R.id.persion).setVisibility(View.GONE);
		view.findViewById(R.id.phongdo_icon).setVisibility(View.GONE);
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
				String tiso = arg1.getString(arg1.getColumnIndex("iCN_BanThang_DoiNha")) + " - " + arg1.getString(arg1.getColumnIndex("iCN_BanThang_DoiKhach"));
				setText(view, R.id.TextView02_ketqua, tiso);// tiso
				view.findViewById(R.id.ImageView031).setVisibility(View.GONE);
				setText(view, R.id.tv1, "HT");

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

			// sTenDoiNha
			setText(view, R.id.TextView02, arg1.getString(arg1.getColumnIndex("sTenDoiNha")));

			// sTenDoiKhach
			setText(view, R.id.TextView023, arg1.getString(arg1.getColumnIndex("sTenDoiKhach")));

			// sLogoGiai
			String sLogoGiai = arg1.getString(arg1.getColumnIndex("sLogoGiai"));
			ImageLoaderUtils.getInstance(null).DisplayImage(sLogoGiai, (ImageView) view.findViewById(R.id.logogiai));

			liveScore = new LiveScore(//
					false,//
					arg1.getString(arg1.getColumnIndex("iID_MaTran"))//
					, arg1.getString(arg1.getColumnIndex("sTenGiai"))//
					, arg1.getString(arg1.getColumnIndex("sTenDoiNha"))//
					, arg1.getString(arg1.getColumnIndex("sTenDoiKhach"))//
					, null//
					, null//
					, null//
					, null//
					, null//
					, 0//
					, arg1.getString(arg1.getColumnIndex("sMaGiai"))//
					, null//
					, null//
					, arg1.getString(arg1.getColumnIndex("iID_MaGiai"))// /
					, false//
					, false//
					, false//
					, arg1.getString(arg1.getColumnIndex("sLogoQuocGia"))//
					, arg1.getString(arg1.getColumnIndex("sLogoGiai"))//
					, arg1.getString(arg1.getColumnIndex("sLogoDoiNha"))//
					, arg1.getString(arg1.getColumnIndex("sLogoDoiKhach"))//
					, arg1.getString(arg1.getColumnIndex("iID_MaDoiNha"))//
					, arg1.getString(arg1.getColumnIndex("iID_MaDoiKhach"))//
			);//

			liveScore.iID_MaGiai();
			view.findViewById(R.id.image_bangxephang).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					callBackListenner.onCallBackListenner(2, liveScore);
				}
			});
			view.findViewById(R.id.phongdo_icon).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					callBackListenner.onCallBackListenner(0, liveScore);
				}
			});
		}
		view.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				int action = event.getAction();
				switch (action) {
				case MotionEvent.ACTION_DOWN:
//					Log.e("action", "ACTION_DOWN - " + action_down_x);
					action_down_x = (int) event.getX();
					break;
				case MotionEvent.ACTION_MOVE:
					action_up_x = (int) event.getX();
//					Log.e("action", "ACTION_MOVE :: " + action_down_x + ":::"+ action_up_x);
					
					difference = action_down_x - action_up_x;
					if(difference > delta1 || difference <  delta2){
						calcuateDifference(liveScore);
					}
					break;
				case MotionEvent.ACTION_UP:
//					Log.e("action", "ACTION_UP - ");
					 if(difference <= delta1 && difference >= delta2){
						 callBackListenner.onCallBackListenner(5, liveScore);
					 }else{
						 calcuateDifference(liveScore);
					 }
					break;
				}
				return true;
			}
			
		});
	}

	private void setText(View view, int res, String string) {
		TextView text = (TextView) view.findViewById(res);
		text.setText(string);
	}
	
	private int delta1 = 20;
	private int delta2 = -20;
	private void calcuateDifference(final LiveScore liveScore) {
		((Activity) ctx ).runOnUiThread(new Runnable() {

			@Override
			public void run() {
				if (CommonUtil.listQuanTam == null) {
					CommonUtil.listQuanTam = new ArrayList<String>();
					CommonUtil.getdata(ctx);
				}
				
				String check_quantam = liveScore.idmagiai() + "-" +  liveScore.getId() ;
//				if(TypeView == null ){
					Log.e("KKKKKKKKKK", "difference:::" + difference + ":::" + delta2);
					String check_quantam2 = liveScore.getId() ;
					if (difference > delta1) {
//						if(BongDaServiceManager.getInstance().getBongDaService().getDBManager().liveScoreLikeCheck( liveScore.getId())){
							//TODO add live score
							BongDaServiceManager.getInstance().getBongDaService().getDBManager().liveScoreLike( liveScore.getId() , "0");
							if (CommonUtil.listQuanTam.contains( check_quantam2 )) {
								CommonUtil.listQuanTam.remove( check_quantam2 );
								CommonUtil.savedata((Activity) ctx);
								CommonUtil.getdata((Activity) ctx);
							}	
//							countryAdapter.notifyDataSetChanged();
							LiveScoreLikeFragment.reloadData();
							Toast.makeText(ctx, "Remove favorite", Toast.LENGTH_LONG).show();
//						}
						
					}/*else if (difference < delta2) {
						if(!BongDaServiceManager.getInstance().getBongDaService().getDBManager().liveScoreLikeCheck( liveScore.getId())){
							Log.e("KKKKKKKKKK", "B*" + CommonUtil.listQuanTam.toString());
							//TODO add live score
							BongDaServiceManager.getInstance().getBongDaService().getDBManager().liveScoreLike( liveScore.getId(), "1");
							if (!CommonUtil.listQuanTam.contains( check_quantam2 )) {
								CommonUtil.listQuanTam.add( check_quantam2 );
								CommonUtil.savedata((Activity) ctx);
								CommonUtil.getdata((Activity) ctx);
							}
//							countryAdapter.notifyDataSetChanged();
							LiveScoreLikeFragment.reloadData();
							Toast.makeText(ctx, "Add to Favorite", Toast.LENGTH_LONG).show();
						}
						
					}*/
//				}
				
//				action_down_x = 0;
//				action_up_x = 0;
				difference = 0;

			}
		});
	}
}