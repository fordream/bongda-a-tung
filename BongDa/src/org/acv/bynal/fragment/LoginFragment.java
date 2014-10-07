package org.acv.bynal.fragment;

import java.util.HashMap;
import java.util.Map;

import org.acv.bynal.activity.GcmBroadcastReceiver;
import org.acv.bynal.dialog.ForgotPasswordDialog;
import org.acv.bynal.main.activity.MainHomeActivity;
import org.acv.bynal.views.HeaderView;
import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import app.bynal.woman.R;

import com.acv.libs.base.BaseFragment;
import com.acv.libs.base.CommonAndroid;
import com.acv.libs.base.callback.APICaller;
import com.acv.libs.base.callback.APICaller.ICallbackAPI;
import com.acv.libs.base.db.AccountDB;
import com.acv.libs.base.tab.BaseTabActivity;
import com.acv.libs.base.util.ByUtils;

public class LoginFragment extends BaseFragment implements OnClickListener {

	public LoginFragment() {
	}

	private View login_main_login;

	@Override
	public void setUpFragment(final View view) {
		view.findViewById(R.id.login_main_login_forgotpassword)
				.setOnClickListener(this);
		view.findViewById(R.id.login_main_login_btnlogin).setOnClickListener(
				this);
		view.findViewById(R.id.login_main_login_facebook).setOnClickListener(
				this);
		view.findViewById(R.id.login_main_login_twitter).setOnClickListener(
				this);

		view.findViewById(R.id.login_main_register_btn1_1).setOnClickListener(
				this);
		view.findViewById(R.id.login_main_register_btn2).setOnClickListener(
				this);
		view.findViewById(R.id.login_main_register_btn3).setOnClickListener(
				this);
		view.findViewById(R.id.login_main_login_text5).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						CheckBox checkBox = (CheckBox) view
								.findViewById(R.id.login_main_login_checkbox);
						checkBox.setChecked(!checkBox.isChecked());
					}
				});

		login_main_login = view.findViewById(R.id.login_main_login);

	}

	@Override
	public void onResume() {
		super.onResume();
		clearData();
		IntentFilter filter = new IntentFilter(HeaderView.KEYCHOOSER);
		getActivity().registerReceiver(receiver, filter);
	}

	private void clearData() {
		setText("", R.id.login_main_login_email);
		setText("", R.id.login_main_login_password);
		setText("", R.id.login_main_register_edt1);
		setText("", R.id.login_main_register_edt2);
		setText("", R.id.login_main_register_edt3);
		setText("", R.id.login_main_register_edt4);
	}

	private BroadcastReceiver receiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			int index = intent.getIntExtra(HeaderView.KEYCHOOSER, 0);

			setChoosePosition(index);
		}
	};

	public void onStop() {
		super.onStop();
		getActivity().unregisterReceiver(receiver);
	};

	@Override
	public void setChoosePosition(int index) {
		super.setChoosePosition(index);
		CommonAndroid.hiddenKeyBoard(getActivity());
		if (login_main_login != null)
			if (index == 0) {
				login_main_login.setVisibility(View.VISIBLE);
			} else if (index == 1) {
				login_main_login.setVisibility(View.GONE);
			}
	}

	@Override
	public int layoytResurce() {
		return R.layout.login;
	}

	@Override
	public void onClick(View v) {
		CommonAndroid.hiddenKeyBoard(getActivity());
		if (v.getId() == R.id.login_main_login_forgotpassword) {

			new ForgotPasswordDialog(getActivity(),
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							((BaseTabActivity) ((MainHomeActivity) getActivity())
									.getParent()).refreshMenuAndgotoHome();
						}
					}).show();
		} else if (v.getId() == R.id.login_main_login_btnlogin) {
			final String email = getTextStr(R.id.login_main_login_email);
			final String password = getTextStr(R.id.login_main_login_password);

			if (ByUtils.isBlank(email) || ByUtils.isBlank(password)) {
				String message = getResources().getString(
						R.string.error_message_input_email);
				if (ByUtils.isBlank(email)) {
					message = getResources().getString(
							R.string.error_message_input_email);
				} else {
					message = getResources().getString(
							R.string.error_message_input_password);
				}
				showDialogMessage(message);
			} else {
				Map<String, String> sendData = new HashMap<String, String>();
				sendData.put("type", "0");
				sendData.put("id", "xxx");
				sendData.put("user", email);
				sendData.put("password", password);

				APICaller apiCaller = new APICaller(getActivity());

				ICallbackAPI callbackAPI = new ICallbackAPI() {

					@Override
					public void onSuccess(String response) {
						try {
							JSONObject jsonObject = new JSONObject(response);
							if (jsonObject.getString("status").equals("1")) {
								AccountDB accountDB = new AccountDB(
										getActivity());
								accountDB.save("email", email);
								accountDB.save("password", password);
								accountDB.save(response);
								GcmBroadcastReceiver.register(getActivity());
								((BaseTabActivity) ((MainHomeActivity) getActivity())
										.getParent()).refreshMenuAndgotoHome();
							} else {
								showDialogMessage(jsonObject
										.getString("message"));
							}
						} catch (Exception e) {
							showDialogMessage(getResources().getString(
									R.string.error_message_login_fail));
						}
					}

					@Override
					public void onError(String message) {
						showDialogMessage(getResources().getString(
								R.string.error_message_login_fail));
					}
				};
				apiCaller.callApi("/user/login", true, callbackAPI, sendData);
			}
		} else if (v.getId() == R.id.login_main_login_facebook) {
			((MainHomeActivity) getActivity()).loginFacebook();
		} else if (v.getId() == R.id.login_main_login_twitter) {
			((MainHomeActivity) getActivity()).loginTwitter();
		}

		// register

		if (R.id.login_main_register_btn1_1 == v.getId()) {
			String name = getTextStr(R.id.login_main_register_edt1);
			final String email = getTextStr(R.id.login_main_register_edt2);
			final String password = getTextStr(R.id.login_main_register_edt3);
			String repassword = getTextStr(R.id.login_main_register_edt4);

			if (ByUtils.isBlank(name) || ByUtils.isBlank(email)) {

				String message = getResources().getString(
						R.string.error_message_input_name);
				if (ByUtils.isBlank(name)) {
					message = getResources().getString(
							R.string.error_message_input_name);
				} else {
					message = getResources().getString(
							R.string.error_message_input_email);
				}
				showDialogMessage(message);

			} else {
				if (ByUtils.isBlank(password)) {
					String message = getResources().getString(
							R.string.error_message_input_password);
					showDialogMessage(message);
				} else if (!password.equals(repassword)) {
					String message = getResources().getString(
							R.string.error_message_check_passwordandrepassword);
					showDialogMessage(message);
				} else {
					Map<String, String> sendData = new HashMap<String, String>();
					sendData.put("type", "0");
					sendData.put("id", "xxx");
					sendData.put("user", name);
					sendData.put("email", email);
					sendData.put("name", name);
					sendData.put("password", password);
					sendData.put("repassword", repassword);

					APICaller apiCaller = new APICaller(getActivity());
					ICallbackAPI callbackAPI = new ICallbackAPI() {

						@Override
						public void onSuccess(String response) {
							try {
								JSONObject jsonObject = new JSONObject(response);
								if (jsonObject.getString("status").equals("1")) {
									AccountDB accountDB = new AccountDB(
											getActivity());
									accountDB.save("email", email);
									accountDB.save("password", password);
									accountDB.save(response);
									GcmBroadcastReceiver
											.register(getActivity());
									((BaseTabActivity) ((MainHomeActivity) getActivity())
											.getParent())
											.refreshMenuAndgotoHome();
								} else {
									showDialogMessage(jsonObject
											.getString("message"));
								}
							} catch (Exception e) {
								showDialogMessage(getResources().getString(
										R.string.error_message_register_fail));
							}
						}

						@Override
						public void onError(String message) {
							showDialogMessage(getResources().getString(
									R.string.error_message_register_fail));
						}
					};
					apiCaller.callApi("/user/register", true, callbackAPI,
							sendData);

				}
			}
		} else if (R.id.login_main_register_btn2 == v.getId()) {
			((MainHomeActivity) getActivity()).loginFacebook();
		} else if (R.id.login_main_register_btn3 == v.getId()) {
			((MainHomeActivity) getActivity()).loginTwitter();
		}
	}
}