package com.app.bongda.fragment.create;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.bongda.CreateAccountActivity;
import com.app.bongda.R;
import com.app.bongda.base.BaseFragment;
import com.app.bongda.callback.APICaller;
import com.app.bongda.callback.APICaller.ICallbackAPI;
import com.app.bongda.model.NhanDinhChuyenGia;
import com.app.bongda.util.ByUtils;
import com.app.bongda.util.CommonAndroid;
import com.app.bongda.util.CommonUtil;
import com.app.bongda.view.HeaderView;

public class LoginPhoneFragment extends BaseFragment {
	private EditText edit, edit2;
	private View views;
	public LoginPhoneFragment() {
	}

	ICallbackAPI callbackAPI;
	private String numberphone = "";
	private String maxacthuc = "";
	@Override
	public void onInitCreateView(View view) {
		views = view;
		try {
			String numberphone_temp = CommonUtil.getdata((Activity) views.getContext(), "numberphone");
			numberphone = numberphone_temp == null ? "" : numberphone_temp;
			Log.e("number", numberphone);
			if(!"".equalsIgnoreCase(numberphone)){
				((TextView) views.findViewById(R.id.edit)).setText(numberphone);
			}
			
			String maxacthuc_temp = CommonUtil.getdata((Activity) views.getContext(), "maxacthuc");
			maxacthuc = maxacthuc_temp == null ? "" : maxacthuc_temp;
			if(!"".equalsIgnoreCase(maxacthuc)){
				((TextView) views.findViewById(R.id.edit2)).setText(maxacthuc);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		callbackAPI = new ICallbackAPI() {
			@Override
			public void onSuccess(String response) {
				Log.e("data22", response);
				 String string_temp = response == null ? "" : CommonAndroid.parseXMLAction(response);
					if (!string_temp.equalsIgnoreCase("")) {
						Log.e("data", string_temp);
						if(string_temp.equalsIgnoreCase("1")){
							CommonUtil.savedata((Activity) views.getContext(), "numberphone" , numberphone);
							CommonUtil.savedata((Activity) views.getContext(), "maxacthuc" , maxacthuc);
							CommonUtil.savedata((Activity) views.getContext(), "numberphonelogin" , numberphone);
							Builder builder = new Builder(views.getContext());
							builder.setMessage(R.string.chuc_mung_login_thanh_cong);
							builder.setCancelable(false);
							builder.setPositiveButton(R.string.tiep_tuc, new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									getActivity().finish();
								}
							});
							builder.create().show();
						}else{
							//0:user_register_error
							Builder builder = new Builder(views.getContext());
							builder.setMessage(R.string.user_login_error);
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
		view.findViewById(R.id.mbutton2).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				numberphone = edit.getText().toString().trim();
				maxacthuc = edit2.getText().toString().trim();
				boolean check_val = true;
				int mess_err = 0;
				if(numberphone.equalsIgnoreCase("")){
					mess_err = R.string.chuanhapsodienthoai;
					check_val = false;
				}else if(maxacthuc.equalsIgnoreCase("")){
					mess_err = R.string.chuanhapmaxacthuc;
					check_val = false;
				}else if(numberphone.equalsIgnoreCase("") && maxacthuc.equalsIgnoreCase("")){
					mess_err = R.string.chuanhap_sdt_mxt;
					check_val = false;
				}
				if(check_val){
					String param = (ByUtils.wsUsers_Login).replace("nophone",
					numberphone);
					param = param.replace("maotp",
							maxacthuc);
					Log.e("param", param);
					new APICaller(views.getContext()).callApi("user", true, callbackAPI,
								param);
				}else{
					Builder builder = new Builder(views.getContext());
					builder.setMessage(mess_err);
					builder.setCancelable(false);
					builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					});
					builder.create().show();
				}
			}
		});
		edit = (EditText) view.findViewById(R.id.edit);
		edit2 = (EditText) view.findViewById(R.id.edit2);
		/**
		 * init header view
		 */
		HeaderView headerView = (HeaderView) view.findViewById(R.id.headerView1);
		headerView.setTextHeader(R.string.login_account);
		headerView.hiddenProgressbar();
		headerView.hiddenSetting();

		headerView.setOnClickListenerX(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				getActivity().finish();
			}
		});
	}

	@Override
	public void onInitData() {

	}

	@Override
	public int getLayout() {
		return R.layout.login_account;
	}


}
