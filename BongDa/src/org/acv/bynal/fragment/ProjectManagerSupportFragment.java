package org.acv.bynal.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.acv.bynal.activity.ProjectmanagerActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import app.bynal.woman.R;

import com.acv.libs.base.BaseActivity;
import com.acv.libs.base.BaseFragment;
import com.acv.libs.base.BaseItem;
import com.acv.libs.base.HeaderOption;
import com.acv.libs.base.HeaderOption.TYPEHEADER;
import com.acv.libs.base.callback.APICaller;
import com.acv.libs.base.callback.APICaller.ICallbackAPI;
import com.acv.libs.base.db.AccountDB;

public class ProjectManagerSupportFragment extends BaseFragment {
	Map<String, String> sendData = new HashMap<String, String>();
	ICallbackAPI callbackAPI;
	String token_user;
	public ProjectManagerSupportFragment() {
	}

	@Override
	public void setUpFragment(View view) {
		AccountDB accountDB = new AccountDB(getActivity());
		token_user = accountDB.getToken();
		callbackAPI = new ICallbackAPI() {
			@Override
			public void onSuccess(String response) {
				try {
					JSONObject jsonObject = new JSONObject(response);
					if (jsonObject != null) {
						if(jsonObject.getString("now_support_point") != null){
							String monny = " " + getResources().getString(
									R.string.header_project_support_detail_monny);
							String now_support_point = jsonObject.getString("now_support_point") + monny;
							String pending = jsonObject.getString("pending") + monny;
							String system_per = jsonObject.getString("system_per") + monny;
							String calc_paid = jsonObject.getString("calc_paid") + monny;
							setText(now_support_point,R.id.now_support_point);
							setText(pending,R.id.pending);
							setText(system_per,R.id.system_per);
							setText(calc_paid,R.id.calc_paid);
						}else{
							
						}
					}
					
				} catch (JSONException e) {
					Log.e("PM","err::" + e.getMessage());
				}
			}

			@Override
			public void onError(String message) {
				
			}
		};
		sendData = new HashMap<String, String>();
		sendData.put("project_id", ProjectmanagerActivity.project_id);
		sendData.put("token", token_user);
		new APICaller(getActivity()).callApi("/project/supportDetail", true,
				callbackAPI, sendData);
	}

	@Override
	public int layoytResurce() {
		return R.layout.projectmanager_support;
	}
	
	@Override
	public HeaderOption getHeaderOption() {
		TYPEHEADER type = TYPEHEADER.NORMAL;
		HeaderOption headerOption = new HeaderOption(getActivity(), type) {
			@Override
			public void onClickButtonLeft() {
				((BaseActivity)getActivity()).changeFragemt(ProjectmanagerActivity.projectManagerPage);
			}

			@Override
			public void onClickButtonRight() {
				ProjectmanagerActivity.typeProject = "0";
				getActivity().setResult(Activity.RESULT_OK);
				getActivity().finish();
			}
		};
		headerOption.setShowButtonLeft(true);
		headerOption.setShowButtonRight(true);
		headerOption.setResHeader(R.string.header_project_support_detail);
		headerOption.setResDrawableLeft(R.drawable.back_xml);
		headerOption.setResDrawableRight(R.drawable.home_xml);
		return headerOption;
	}

}
