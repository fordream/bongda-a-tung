package com.app.bongda;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.app.bongda.R;
import com.app.bongda.callback.APICaller;
import com.app.bongda.callback.APICaller.ICallbackAPI;
import com.app.bongda.service.BongDaServiceManager;
import com.app.bongda.util.ByUtils;
import com.app.bongda.util.CommonAndroid;
import com.app.bongda.util.CommonUtil;
import com.app.bongda.view.HeaderView;
import com.app.bongda.view.ThecaoView;

public class SettingActivity extends Activity {
	private ThecaoView thecaoView;
	private ListView tinnhanview;
	private GridView appstoreview;
	private RadioGroup mRadioGroup1;

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

	private View settingdialog_main;

	private Context getContext() {
		return this;
	}

	private String numberphone, sothe, soseri;
	private EditText edit, edit2, edit3;
	private String tennhamang = "VTEL";//VMS: Mobile; VTEL: Viettel; GPC: Vina
	private RadioGroup radioGroup_nhamang;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		overridePendingTransition(R.anim.bot_to_top, R.anim.nothing);
		settingdialog_main = findViewById(R.id.settingdialog_main);

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

				CommonAndroid.hiddenKeyBoard(SettingActivity.this);
				thecaoView.setVisibility(id == 0 ? View.VISIBLE : View.GONE);
				tinnhanview.setVisibility(id == 1 ? View.VISIBLE : View.GONE);
				appstoreview.setVisibility(id == 2 ? View.VISIBLE : View.GONE);
				actionForm(id);
			}
		};

		mRadioGroup1.setOnCheckedChangeListener(onCheckedChangeListener);

		showData();
		actionForm(0);
		/**
		 * init header view
		 */
		HeaderView headerView = (HeaderView) findViewById(R.id.headerView1);
		headerView.setTextHeader(R.string.nap_tien);
		headerView.hiddenProgressbar();
		headerView.hiddenSetting();

		headerView.setOnClickListenerX(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(R.anim.nothing, R.anim.top_to_bot);
	}
	
	ICallbackAPI callbackAPI;
	private void actionForm(int id){
		if(id == 0){
			radioGroup_nhamang = (RadioGroup) thecaoView.findViewById(R.id.radioGroup_nhamang);
			android.widget.RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new android.widget.RadioGroup.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					if (R.id.radio0 == checkedId) {
						tennhamang = "VTEL";//VMS: Mobile; VTEL: Viettel; GPC: Vina
					}
					if (checkedId == R.id.radio1) {
						tennhamang = "VMS";
					}
					if (checkedId == R.id.radio2) {
						tennhamang = "GPC";
					}
				}
			};
			radioGroup_nhamang.setOnCheckedChangeListener(onCheckedChangeListener);
			
			edit = (EditText) thecaoView.findViewById(R.id.edit_sdt);
			String numberphone_temp = CommonUtil.getdata((Activity) thecaoView.getContext(), "numberphone");
			numberphone = numberphone_temp == null ? "" : numberphone_temp;
			if(!"".equalsIgnoreCase(numberphone)){
				((TextView) thecaoView.findViewById(R.id.edit_sdt)).setText(numberphone);
			}
			
			edit2 = (EditText) thecaoView.findViewById(R.id.edit_sothe);
			edit3 = (EditText) thecaoView.findViewById(R.id.edit_soseri);
			thecaoView.findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					numberphone = edit.getText().toString().trim();
					sothe = edit2.getText().toString().trim();
					soseri = edit3.getText().toString().trim();;
					if(numberphone.equalsIgnoreCase("")){
						Builder builder = new Builder(thecaoView.getContext());
						builder.setMessage(R.string.chuanhapsodienthoai);
						builder.setCancelable(false);
						builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
							}
						});
						builder.create().show();
					}else if(sothe.equalsIgnoreCase("")){
						Builder builder = new Builder(thecaoView.getContext());
						builder.setMessage(R.string.chuanhapthedienthoai);
						builder.setCancelable(false);
						builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
							}
						});
						builder.create().show();
					}else if(soseri.equalsIgnoreCase("")){
						Builder builder = new Builder(thecaoView.getContext());
						builder.setMessage(R.string.chuanhapseridienthoai);
						builder.setCancelable(false);
						builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
							}
						});
						builder.create().show();
					}else{
						String param = (ByUtils.wsUsers_NapThe).replace("nophone",numberphone);
						param = param.replace("tennhamang",tennhamang);
						param = param.replace("mathenap",sothe);
						param = param.replace("serithe",soseri);
						Log.e("param", param);
						new APICaller(thecaoView.getContext()).callApi("user", true, callbackAPI,
											param);
					}
				}
			});
			callbackAPI = new ICallbackAPI() {
				@Override
				public void onSuccess(String response) {
					Log.e("data22", response);
					 String string_temp = response == null ? "" : CommonAndroid.parseXMLAction(response);
						if (!string_temp.equalsIgnoreCase("")) {
							Log.e("data", string_temp);
							if(string_temp.equalsIgnoreCase("1")){
								Builder builder = new Builder(thecaoView.getContext());
								builder.setMessage(R.string.chuc_mung_napthe_thanh_cong);
								builder.setCancelable(false);
								builder.setPositiveButton(R.string.tiep_tuc, new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {
										finish();
									}
								});
								builder.create().show();
							}else if(string_temp.equalsIgnoreCase("-4")){
								Builder builder = new Builder(thecaoView.getContext());
								builder.setMessage(R.string.user_napthe_error4);
								builder.setCancelable(false);
								builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {
									}
								});
								builder.create().show();
							}else if(string_temp.equalsIgnoreCase("-1")){
								Builder builder = new Builder(thecaoView.getContext());
								builder.setMessage(R.string.user_napthe_error3);
								builder.setCancelable(false);
								builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {
									}
								});
								builder.create().show();
							}else if(string_temp.equalsIgnoreCase("2")){
								Builder builder = new Builder(thecaoView.getContext());
								builder.setMessage(R.string.user_napthe_error2);
								builder.setCancelable(false);
								builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {
									}
								});
								builder.create().show();
							}else {
								//0:user_register_error
								Builder builder = new Builder(thecaoView.getContext());
								builder.setMessage(R.string.user_napthe_error);
								builder.setCancelable(false);
								builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {
									}
								});
								builder.create().show();
							}
						}	
				}

				@Override
				public void onError(String message) {
					Log.e("Err", "" + message);
				}
			};
		}
	}
}