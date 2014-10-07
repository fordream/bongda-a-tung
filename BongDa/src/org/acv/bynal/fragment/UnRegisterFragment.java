package org.acv.bynal.fragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.acv.bynal.activity.ProfileActivity;
import org.acv.bynal.main.activity.MainHomeActivity;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import app.bynal.woman.R;

import com.acv.libs.base.BaseActivity;
import com.acv.libs.base.BaseFragment;
import com.acv.libs.base.CommonAndroid;
import com.acv.libs.base.HeaderOption;
import com.acv.libs.base.LogUtils;
import com.acv.libs.base.HeaderOption.TYPEHEADER;
import com.acv.libs.base.callback.APICaller;
import com.acv.libs.base.callback.APICaller.ICallbackAPI;
import com.acv.libs.base.db.AccountDB;
import com.acv.libs.base.tab.BaseTabActivity;
import com.acv.libs.base.util.ByUtils;

public class UnRegisterFragment extends BaseFragment implements OnClickListener {
	private WebView credit;
	/** Layout of credit. */
	private RelativeLayout rl;
	APICaller apiCaller;
	ICallbackAPI callbackAPI;
	Map<String, String> sendData = new HashMap<String, String>();
	private TextView unregister_button;
	public UnRegisterFragment() {
	}

	@Override
	public void setUpFragment(final View view) {
		view.findViewById(R.id.profile_user_btnunregister).setOnClickListener(
				this);
		unregister_button = (TextView) view.findViewById(R.id.profile_user_btnunregister);
		credit = new WebView(getActivity());
		rl = (RelativeLayout) view.findViewById(R.id.credit_display);
		rl.addView(credit);
		try{
			String str = "";
			str += loadResouceHTMLFromAssets("webviewcontent.html");
			credit.loadDataWithBaseURL("file:///android_asset/", str,
					"text/html", "UTF-8", "");
			unregister_button.setVisibility(View.VISIBLE);
		} catch (Exception e) {
		} catch (OutOfMemoryError e){
		}
	}

	@Override
	public int layoytResurce() {
		return R.layout.user_unregister;
	}

	public String loadResouceHTMLFromAssets(String filename) {
		String tmp = "";
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(getActivity().getAssets().open(
					filename)));
			String word;
			while ((word = br.readLine()) != null) {
				if (!word.equalsIgnoreCase("")) {
					tmp += word;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close(); // stop reading
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return tmp;
	}
	
	@Override
	public HeaderOption getHeaderOption() {
		TYPEHEADER type = TYPEHEADER.NORMAL;
		HeaderOption headerOption = new HeaderOption(getActivity(), type) {
			@Override
			public void onClickButtonLeft() {
				((BaseActivity) getActivity()).changeFragemt(ProfileActivity.profilePage);
			}

			@Override
			public void onClickButtonRight() {
				getActivity().setResult(Activity.RESULT_OK);
				getActivity().finish();
			}
		};
		headerOption.setShowButtonLeft(true);
		headerOption.setShowButtonRight(true);
		headerOption.setResHeader(R.string.header_profile_unregister);
		headerOption.setResDrawableLeft(R.drawable.back_xml);
		headerOption.setResDrawableRight(R.drawable.home_xml);
		return headerOption;
	}
	
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.profile_user_btnunregister) {
			AccountDB accountDB = new AccountDB(getActivity());
			String token = accountDB.getToken();
			sendData.put("token", token);
			apiCaller = new APICaller(getActivity());
			callbackAPI = new ICallbackAPI() {
				@Override
				public void onSuccess(String response) {
					try {
						JSONObject jsonObject = new JSONObject(response);
//						CommonAndroid.showDialog(getActivity(),"" + jsonObject.toString(), null);
						if (jsonObject.getString("success").equals("1")) {
//							AccountDB accountDB = new AccountDB(getActivity());
//							accountDB.clear();
							getActivity().setResult(ByUtils.RESPONSE_RELEASETOKEN);
							getActivity().finish();
						} else {
							CommonAndroid.showDialog(getActivity(),
									jsonObject.getString("message"), null);
						}
					} catch (Exception e) {
						LogUtils.e("ABC", e);
						try {
							JSONObject jsonObject2 = new JSONObject(response);
							CommonAndroid.showDialog(getActivity(),
									jsonObject2.getString("message"), null);
						} catch (JSONException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
							CommonAndroid.showDialog(getActivity(), e.getMessage(), null);
						}
						e.printStackTrace();
					}
				}

				@Override
				public void onError(String message) {
					CommonAndroid.showDialog(getActivity(), message + "",
							null);
				}
			};
			CommonAndroid.showDialog(getActivity(),
					getResources().getString(R.string.unregister_confirm), new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							//leave
							apiCaller.callApi("/user/leave", true, callbackAPI, sendData);
						}
					} , new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							//Cancel
						}
					});
			
		}
	}
}