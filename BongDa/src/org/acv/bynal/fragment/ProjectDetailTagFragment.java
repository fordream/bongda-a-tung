package org.acv.bynal.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.acv.bynal.activity.ProjectDetailActivity;
import org.acv.bynal.activity.ProjectmanagerActivity;
import org.acv.bynal.views.ProjectListItemView;
import org.acv.bynal.views.ProjectTagItemView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import app.bynal.woman.R;

import com.acv.libs.base.BaseActivity;
import com.acv.libs.base.BaseAdapter;
import com.acv.libs.base.BaseFragment;
import com.acv.libs.base.BaseItem;
import com.acv.libs.base.BaseView;
import com.acv.libs.base.CommonAndroid;
import com.acv.libs.base.HeaderOption;
import com.acv.libs.base.HeaderOption.TYPEHEADER;
import com.acv.libs.base.callback.APICaller;
import com.acv.libs.base.callback.APICaller.ICallbackAPI;
import com.acv.libs.base.db.AccountDB;
import com.acv.libs.base.util.ByUtils;

public class ProjectDetailTagFragment extends BaseFragment {
	Map<String, String> sendData = new HashMap<String, String>();
	ICallbackAPI callbackAPI;
	ListView projectlisttagview;
	boolean actionChangeTag = false;
	public ProjectDetailTagFragment() {
	}

	private BaseAdapter baseAdapter;
	@Override
	public void setUpFragment(View view) {
		projectlisttagview = (ListView) view.findViewById(R.id.listview_tag);
		callbackAPI = new ICallbackAPI() {
			@Override
			public void onSuccess(String response) {
				List<BaseItem> baseItems = new ArrayList<BaseItem>();
				baseItems.clear();
				try {
					JSONObject jsonObject = new JSONObject(response);
					if (jsonObject != null) {
//						CommonAndroid.showDialog(getActivity(), "data:" + jsonObject.toString() , null);	
						if(jsonObject.getString("status").equalsIgnoreCase("1")){
							if (jsonObject != null) {
								try {
									JSONArray array = jsonObject.getJSONArray("array_data");
									for (int i = (array.length() - 1) ; i >= 0 ; i--) {
										baseItems.add(new BaseItem(array.getJSONObject(i)));
									}
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
							}
							baseAdapter.clear();
							baseAdapter.addAllItems(baseItems);
							baseAdapter.notifyDataSetChanged();
							if(actionChangeTag){
								if(!jsonObject.getBoolean("is_login")){
									CommonAndroid.showDialog(getActivity(), getResources()
											.getString(R.string.error_message_session_login), null);
									actionChangeTag = false;
								}else{
									if(jsonObject.getString("add").equalsIgnoreCase("false")){ //add err
										CommonAndroid.showDialog(getActivity(), getResources()
												.getString(R.string.tag_add_error), null);
										actionChangeTag = false;
									}
								}
							}
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
//		"remove_tag":"",
//		"add_tag":"",
//		"token":"xxx"
		new APICaller(getActivity()).callApi("/project/tag", true,
				callbackAPI, sendData);
		baseAdapter = new BaseAdapter(getActivity(), new ArrayList<Object>()) {
			@Override
			public BaseView getView(Context context, Object data) {
				final ProjectTagItemView projectTagItemView = new ProjectTagItemView(
							context);
				projectTagItemView.addProjectManagerFragment(ProjectDetailTagFragment.this);
				return projectTagItemView;
			}
		};
		projectlisttagview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
//				BaseItem baseItem = (BaseItem) parent
//						.getItemAtPosition(position);
//				String projectID = baseItem.getString("id");
//				
//				CommonAndroid.showDialog(getActivity(), "projectID::" + projectID ,null);
	        	
			}
		});
		projectlisttagview.setAdapter(baseAdapter);
		view.findViewById(R.id.tagpost_footer_btn).setOnClickListener(this);
	}

	@Override
	public int layoytResurce() {
		return R.layout.projectdetail_tag;
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
		headerOption.setResHeader(R.string.header_project_detail_tag);
		headerOption.setResDrawableLeft(R.drawable.back_xml);
		headerOption.setResDrawableRight(R.drawable.home_xml);
		return headerOption;
	}
	
	public void actionDeleteTag(View v, final BaseItem data) {
		final String tag_id_delete = data.getString("id");
		int type = v.getId();
		switch (type) {
			case R.id.tag_delete:
				AccountDB accountDB = new AccountDB(getActivity());
				final String token_user = accountDB.getToken();
				if (ByUtils.isBlank(token_user)) {
						CommonAndroid.showDialog(getActivity(), getResources()
								.getString(R.string.error_message_pleaselogin), null);
				} else {
					CommonAndroid.showDialog(getActivity(),
							getResources().getString(R.string.tag_delete_confirm) , new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									//delete tag
									actionChangeTag = true;
									sendData = new HashMap<String, String>();
									sendData.put("project_id", Integer.toString(ProjectDetailActivity.projectId));
									sendData.put("remove_tag", tag_id_delete);
									sendData.put("add_tag", "");
									sendData.put("token", token_user);
									new APICaller(getActivity()).callApi("/project/tag", true,
											callbackAPI, sendData);
								}
							} , new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									//Cancel
								}
							});
					
				}
				
			break;	
		}
	}
	
	@Override
	public void onClick(View v) {
		AccountDB accountDB = new AccountDB(getActivity());
		String token_user = accountDB.getToken();
		if (ByUtils.isBlank(token_user)) {
				CommonAndroid.showDialog(getActivity(), getResources()
						.getString(R.string.error_message_pleaselogin), null);
		} else {
			if(getTextStr(R.id.tagpost_footer_text).equalsIgnoreCase("")){
				CommonAndroid.showDialog(getActivity(), getResources()
						.getString(R.string.tag_add_error), null);
			}else{
				//add tag
				actionChangeTag = true;
				sendData = new HashMap<String, String>();
				sendData.put("project_id", Integer.toString(ProjectDetailActivity.projectId));
				sendData.put("token", token_user);
				sendData.put("add_tag", getTextStr(R.id.tagpost_footer_text));
				new APICaller(getActivity()).callApi("/project/tag", true,
						callbackAPI, sendData);
				setTextStr(R.id.tagpost_footer_text, "");
			}
		}
		
	}

}
