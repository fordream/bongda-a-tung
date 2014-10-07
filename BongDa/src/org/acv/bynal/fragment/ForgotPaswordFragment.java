package org.acv.bynal.fragment;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;
import app.bynal.woman.R;

import com.acv.libs.base.BaseFragment;
import com.acv.libs.base.CommonAndroid;
import com.acv.libs.base.HeaderOption;
import com.acv.libs.base.callback.APICaller;
import com.acv.libs.base.callback.APICaller.ICallbackAPI;

public class ForgotPaswordFragment extends BaseFragment implements
		OnClickListener {

	public ForgotPaswordFragment() {
		super();
	}

	@Override
	public void setUpFragment(final View view) {
		view.findViewById(R.id.forgotpassword_btn).setOnClickListener(this);
	}

	@Override
	public int layoytResurce() {
		return R.layout.forgotpassword;
	}

	@Override
	public void onClick(View v) {
		String email = getTextStr(R.id.forgotpassword_edit);

		if (email.equals("")) {
			CommonAndroid.showDialog(getActivity(),
					getResources()
							.getString(R.string.error_message_input_email),
					null);
		} else {
			APICaller apiCaller = new APICaller(getActivity());
			Map<String, String> sendData = new HashMap<String, String>();
			sendData.put("email", email);
			apiCaller.callApi("/user/forgotpassword", true, new ICallbackAPI() {

				@Override
				public void onSuccess(String response) {
					try {
						JSONObject jsonObject = new JSONObject(response);
						if (jsonObject.getString("status").equals("1")) {
							CommonAndroid.showDialog(getActivity(),
									jsonObject.getString("message"),
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											getActivity().finish();
										}
									});
						} else {
							CommonAndroid.showDialog(getActivity(),
									jsonObject.getString("message"), null);
						}
					} catch (Exception e) {
						CommonAndroid
								.showDialog(
										getActivity(),
										getResources()
												.getString(
														R.string.error_message_connect_server_fail),
										null);
					}
				}

				@Override
				public void onError(String message) {
					CommonAndroid
							.showDialog(
									getActivity(),
									getResources()
											.getString(
													R.string.error_message_connect_server_fail),
									null);
				}
			}, sendData);
		}
	}

	@Override
	public HeaderOption getHeaderOption() {
		HeaderOption headerOption = super.getHeaderOption();
		headerOption.setResHeader(R.string.forgot_header);
		return headerOption;
	}
}
