package org.acv.bynal.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.acv.bynal.activity.HomeActivity;
import org.acv.bynal.activity.ProjectDetailActivity;
import org.acv.bynal.activity.ProjectmanagerActivity;
import org.acv.bynal.message.MessageActivity;
import org.acv.bynal.views.DeliveryListItemView;
import org.acv.bynal.views.ProjectListItemView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
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

public class ProjectManagerDeliveryFragment extends BaseFragment {
	Map<String, String> sendData = new HashMap<String, String>();
	ICallbackAPI callbackAPI;
	String token_user;
	ListView deliverylistview;
	public ProjectManagerDeliveryFragment() {
	}

	@Override
	public void setUpFragment(View view) {
		deliverylistview = (ListView) view.findViewById(R.id.listview_delivery);
		AccountDB accountDB = new AccountDB(getActivity());
		token_user = accountDB.getToken();
		callbackAPI = new ICallbackAPI() {
			@Override
			public void onSuccess(String response) {
				List<BaseItem> baseItems = new ArrayList<BaseItem>();
				baseItems.clear();
				try {
					JSONObject jsonObject = new JSONObject(response);
					if (jsonObject != null) {
						JSONArray array = jsonObject.getJSONArray("array_data");
						if(array != null){
							for (int i = 0; i < array.length(); i++) {
								baseItems.add(new BaseItem(array.getJSONObject(i)));
							}
						}
						
						
					}
					baseAdapter.clear();
					baseAdapter.addAllItems(baseItems);
					baseAdapter.notifyDataSetChanged();
					
				} catch (JSONException e) {
					Log.e("PM","err::" + e.getMessage());
				}
			}

			@Override
			public void onError(String message) {
				
			}
		};
		baseAdapter = new BaseAdapter(getActivity(), new ArrayList<Object>()) {
			@Override
			public BaseView getView(Context context, Object data) {
				final DeliveryListItemView deliveryListItemView = new DeliveryListItemView(
							context);
				deliveryListItemView.addProjectManagerFragment(ProjectManagerDeliveryFragment.this);
				return deliveryListItemView;
			}
		};
		deliverylistview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				BaseItem baseItem = (BaseItem) parent
						.getItemAtPosition(position);
	        	
			}
		});
		deliverylistview.setAdapter(baseAdapter);
		sendData = new HashMap<String, String>();
		sendData.put("project_id", ProjectmanagerActivity.project_id);
		sendData.put("token", token_user);
		new APICaller(getActivity()).callApi("/project/deliverylist", true,
				callbackAPI, sendData);
	}

	private BaseAdapter baseAdapter;
	
	@Override
	public int layoytResurce() {
		return R.layout.projectmanager_delivery;
	}
	
	@Override
	public HeaderOption getHeaderOption() {
		TYPEHEADER type = TYPEHEADER.NORMAL;
		HeaderOption headerOption = new HeaderOption(getActivity(), type) {
			@Override
			public void onClickButtonLeft() {
				ProjectmanagerActivity.project_id = "0";
				((BaseActivity)getActivity()).changeFragemt(ProjectmanagerActivity.projectManagerPage);
			}

			@Override
			public void onClickButtonRight() {
				ProjectmanagerActivity.project_id = "0";
				ProjectmanagerActivity.typeProject = "0";
				getActivity().setResult(Activity.RESULT_OK);
				getActivity().finish();
			}
		};
		headerOption.setShowButtonLeft(true);
		headerOption.setShowButtonRight(true);
		headerOption.setResHeader(R.string.header_project_delivery);
		headerOption.setResDrawableLeft(R.drawable.back_xml);
		headerOption.setResDrawableRight(R.drawable.home_xml);
		return headerOption;
	}
	
	public void actionProjectDeliverySendMessage(View v, final BaseItem data) {
		String user_id = data.getString("user_id");
//		CommonAndroid.showDialog(getActivity(), "user_id:" + user_id, null);
		AccountDB accountDB = new AccountDB(getActivity());
		String user_id_login = accountDB.getUserId();
//		CommonAndroid.showDialog(getActivity(), "user_id:" + user_id + "::user_id_login:" + user_id_login , null);
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

}
