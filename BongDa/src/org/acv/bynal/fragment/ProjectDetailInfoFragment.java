package org.acv.bynal.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.acv.bynal.activity.ProjectDetailActivity;
import org.acv.bynal.message.MessageActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import app.bynal.woman.R;

import com.acv.libs.base.BaseActivity;
import com.acv.libs.base.BaseFragment;
import com.acv.libs.base.BaseItem;
import com.acv.libs.base.CommonAndroid;
import com.acv.libs.base.HeaderOption;
import com.acv.libs.base.ImageLoaderUtils;
import com.acv.libs.base.HeaderOption.TYPEHEADER;
import com.acv.libs.base.callback.APICaller;
import com.acv.libs.base.callback.APICaller.ICallbackAPI;
import com.acv.libs.base.db.AccountDB;
import com.acv.libs.base.util.ByUtils;

public class ProjectDetailInfoFragment extends BaseFragment {
	Map<String, String> sendData = new HashMap<String, String>();
	ICallbackAPI callbackAPI;
	View view1;
	String user_id;
	public ProjectDetailInfoFragment() {
		
	}

	@Override
	public void setUpFragment(View view) {
		view1 = view;
		callbackAPI = new ICallbackAPI() {
			@Override
			public void onSuccess(String response) {
				try {
					JSONObject jsonObject = new JSONObject(response);
					if (jsonObject != null) {
						if(jsonObject.getString("user_id") != null){
							user_id = jsonObject.getString("user_id");
							setText(jsonObject.getString("user_name"),
									R.id.project_info_name);
							ImageLoaderUtils.getInstance(getActivity()).DisplayImage( jsonObject.getString("user_img"),
									(ImageView) view1.findViewById(R.id.image_project_profile));
							String tempString	=  getResources().getString(R.string.project_info_sendmessage_txt);
							TextView text_send_message =(TextView)view1.findViewById(R.id.project_info_sendmessage);
//							text_send_message.setPaintFlags(text_send_message.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
							SpannableString spanString = new SpannableString(tempString);
							spanString.setSpan(new UnderlineSpan(), 0, spanString.length(), 0);
							text_send_message.setText(spanString);
						}
					}
					
				} catch (JSONException e) {
					Log.e("PD","err::" + e.getMessage());
				}
			}

			@Override
			public void onError(String message) {
				
			}
		};
		sendData = new HashMap<String, String>();
		sendData.put("project_id", Integer.toString(ProjectDetailActivity.projectId));
		new APICaller(getActivity()).callApi("/project/info", true,
				callbackAPI, sendData);
		view.findViewById(R.id.project_info_sendmessage).setOnClickListener(this);
		view.findViewById(R.id.send_message).setOnClickListener(this);
	}

	@Override
	public int layoytResurce() {
		return R.layout.projectdetail_info;
	}
	
	@Override
	public HeaderOption getHeaderOption() {
		TYPEHEADER type = TYPEHEADER.NORMAL;
		HeaderOption headerOption = new HeaderOption(getActivity(), type) {
			@Override
			public void onClickButtonLeft() {
				((BaseActivity)getActivity()).changeFragemt(new ProjectDetailFragment());
			}

			@Override
			public void onClickButtonRight() {
				getActivity().setResult(Activity.RESULT_OK);
				getActivity().finish();
			}
		};
		headerOption.setShowButtonLeft(true);
		headerOption.setShowButtonRight(true);
		headerOption.setResHeader(R.string.header_project_detail_info);
		headerOption.setResDrawableLeft(R.drawable.back_xml);
		headerOption.setResDrawableRight(R.drawable.home_xml);
		return headerOption;
	}

	@Override
	public void onClick(View v) {
		int option = v.getId();
		switch (option) {
		case R.id.send_message:
		case R.id.project_info_sendmessage:
			AccountDB accountDB = new AccountDB(getActivity());
			if (accountDB.getToken() == null) {
				CommonAndroid.showDialog(getActivity(),  getResources()
						.getString(R.string.error_message_pleaselogin) , null);
			}else{
				String user_id_login = accountDB.getUserId();
//				CommonAndroid.showDialog(getActivity(), "user_id:" + user_id + "::user_id_login:" + user_id_login , null);
				if(!user_id.equalsIgnoreCase(user_id_login)){
					Intent mIntent = new Intent(getActivity(),
							MessageActivity.class);
					mIntent.putExtra("id", user_id);
			        getActivity().startActivityForResult(mIntent, ByUtils.REQUEST_PROJECTMANAGER);
				}else{
					CommonAndroid.showDialog(getActivity(),  getResources()
							.getString(R.string.err_sendmessage_userid_invalid) , null);
				}
				
			}
				break;
			default:
				break;
		}
	}
}
