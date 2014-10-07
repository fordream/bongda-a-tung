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
import com.acv.libs.base.callback.APICaller;
import com.acv.libs.base.callback.APICaller.ICallbackAPI;
import com.acv.libs.base.util.ByUtils;

public class ConactUsFragment extends BaseFragment implements ICallbackAPI {

	public ConactUsFragment() {
	}

	@Override
	public void setUpFragment(View view) {
		view.findViewById(R.id.contactus_btn).setOnClickListener(this);
	}

	@Override
	public void onResume() {
		super.onResume();

		setText("", R.id.contactus_email);
		setText("", R.id.contactus_title);
		setText("", R.id.contactus_descripntion);
	}

	@Override
	public void onClick(View v) {
		String email = getTextStr(R.id.contactus_email);
		String title = getTextStr(R.id.contactus_title);
		String description = getTextStr(R.id.contactus_descripntion);
		String message = null;

		if (ByUtils.isBlank(email)) {
			message = getString(R.string.error_message_input_email);
		}

		if (ByUtils.isBlank(title)) {
			message = getString(R.string.error_message_input_title);
		}

		if (ByUtils.isBlank(description)) {
			message = getString(R.string.error_message_input_description);
		}

		if (message != null) {
			CommonAndroid.showDialog(getActivity(), message, null);
			return;
		}
		
		if(!ByUtils.isEmail(email)){
			message = getString(R.string.error_message_input_email_check);
			CommonAndroid.showDialog(getActivity(), message, null);
			return;
		}

		APICaller apiCaller = new APICaller(getActivity());
		Map<String, String> sendData = new HashMap<String, String>();
		sendData.put("title", title);
		sendData.put("email", email);
		sendData.put("body", description);
		apiCaller.callApi("/user/contact", true, this, sendData);
	}

	@Override
	public int layoytResurce() {
		return R.layout.contactus;
	}

	@Override
	public void onError(String message) {
		//CommonAndroid.showDialog(getActivity(), "" + message, null);
		showDialogMessage(getString(R.string.error_message_connect_server_fail));
	}

	@Override
	public void onSuccess(String response) {
		try {
			JSONObject jsonObject = new JSONObject(response);
			if (jsonObject.getString("status").equals("1")) {
				setText("", R.id.contactus_email);
				setText("", R.id.contactus_title);
				setText("", R.id.contactus_descripntion);
				
				CommonAndroid.showDialog(getActivity(), jsonObject.getString("message"), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// goto home
					}
				});
			} else {
				showDialogMessage(jsonObject.getString("message"));
//				onError(jsonObject.getString("message"));
			}
		} catch (Exception e) {
//			onError("Can not send message");
			showDialogMessage(getString(R.string.error_message_connect_server_fail));
		}
	}
}
