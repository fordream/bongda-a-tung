package com.app.bongda.fragment.create;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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

public class CreatePhoneFragment extends BaseFragment {
	private EditText edit;
	private View views;
	public CreatePhoneFragment() {
	}

	ICallbackAPI callbackAPI;
	private String numberphone;
	@Override
	public void onInitCreateView(View view) {
		views = view;
		callbackAPI = new ICallbackAPI() {
			@Override
			public void onSuccess(String response) {
				Log.e("data22", response);
				 String string_temp = response == null ? "" : CommonAndroid.parseXMLAction(response);
					if (!string_temp.equalsIgnoreCase("")) {
						Log.e("data", string_temp);
						if(string_temp.equalsIgnoreCase("1")){
							CommonUtil.savedata((Activity) views.getContext(), "numberphone" , numberphone);
							Builder builder = new Builder(views.getContext());
							builder.setMessage(R.string.chuc_mung_dang_ky_thanh_cong);
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
							builder.setMessage(R.string.user_register_error);
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
				if(numberphone.equalsIgnoreCase("")){
					Builder builder = new Builder(views.getContext());
					builder.setMessage(R.string.chuanhapsodienthoai);
					builder.setCancelable(false);
					builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					});
					builder.create().show();
				}else{
					Builder builder = new Builder(v.getContext());
					builder.setTitle(R.string.xac_nhan_so_dien_thoai);
					builder.setMessage(String.format(v.getContext().getString(R.string.xacnhansodienthoai_comfirm), edit.getText().toString().trim()));
					builder.setCancelable(false);
					builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
	//						((CreateAccountActivity)getActivity()).showXFragment(new CreateAccountConfirmFragment());
							String param = (ByUtils.wsUsers_Register).replace("nophone",
									numberphone);
							new APICaller(views.getContext()).callApi("user", true, callbackAPI,
										param);
						}
					});
					builder.setNegativeButton(R.string.thaydoi, null);
					builder.create().show();
				}
			}
		});
		edit = (EditText) view.findViewById(R.id.edit);
		/**
		 * init header view
		 */
		HeaderView headerView = (HeaderView) view.findViewById(R.id.headerView1);
		headerView.setTextHeader(R.string.create_new_account);
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
		return R.layout.create_new_account;
	}


}
