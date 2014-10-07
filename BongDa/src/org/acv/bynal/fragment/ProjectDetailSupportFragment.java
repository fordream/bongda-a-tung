package org.acv.bynal.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.acv.bynal.activity.ProjectDetailActivity;
import org.acv.bynal.views.ProjectDetailSupportItemView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
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
import com.acv.libs.base.HeaderOption;
import com.acv.libs.base.HeaderOption.TYPEHEADER;
import com.acv.libs.base.callback.APICaller;
import com.acv.libs.base.callback.APICaller.ICallbackAPI;
import com.acv.libs.base.db.AccountDB;

public class ProjectDetailSupportFragment extends BaseFragment {
	Map<String, String> sendData = new HashMap<String, String>();
	ICallbackAPI callbackAPI;
	ListView pDetailSupportview;
	public ProjectDetailSupportFragment() {
	}

	private BaseAdapter baseAdapter;
	@Override
	public void setUpFragment(View view) {
		callbackAPI = new ICallbackAPI() {
			@Override
			public void onSuccess(String response) {
				List<BaseItem> baseItems = new ArrayList<BaseItem>();
				try {
					JSONObject jsonObject = new JSONObject(response);
					if (jsonObject != null) {
//						CommonAndroid.showDialog(getActivity(), "data:" + jsonObject.toString() , null);	
						if(jsonObject.getString("status").equalsIgnoreCase("1")){
							JSONArray array = jsonObject.getJSONArray("array_data");
							for (int i = 0; i < array.length(); i++) {
								baseItems.add(new BaseItem(array.getJSONObject(i)));
							}
							
							String project_info = jsonObject.getString("project");
							JSONObject jsonObject_pjInfo = new JSONObject(project_info);
							setText(jsonObject_pjInfo.getString("supported"),R.id.support_point_txtname);
							setText(jsonObject_pjInfo.getString("total_money_support"),R.id.support_flag_txtname);
							setText(jsonObject_pjInfo.getString("percent"),R.id.support_percent_txtname);
							setText(jsonObject_pjInfo.getString("total_date"),R.id.support_timer_txtname);
							setText(jsonObject_pjInfo.getString("person"),R.id.support_number_txtname);
						}
					}
					baseAdapter.clear();
					baseAdapter.addAllItems(baseItems);
					baseAdapter.notifyDataSetChanged();
					
				} catch (JSONException e) {
					Log.e("PD","err::" + e.getMessage());
				}
			}

			@Override
			public void onError(String message) {
				
			}
		};
		sendData = new HashMap<String, String>();
		sendData.put("id", Integer.toString(ProjectDetailActivity.projectId));
		new APICaller(getActivity()).callApi("/project/listSupport", true,
				callbackAPI, sendData);
		pDetailSupportview = (ListView) view.findViewById(R.id.listview_support);
		baseAdapter = new BaseAdapter(getActivity(), new ArrayList<Object>()) {
			@Override
			public BaseView getView(Context context, Object data) {
				final ProjectDetailSupportItemView projectDetailSupportItemView = new ProjectDetailSupportItemView(
							context);
				projectDetailSupportItemView.addProjectManagerFragment(ProjectDetailSupportFragment.this);
				return projectDetailSupportItemView;
			}
		};
		pDetailSupportview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
//				BaseItem baseItem = (BaseItem) parent
//						.getItemAtPosition(position);
			}
		});
		pDetailSupportview.setAdapter(baseAdapter);
		
	}

	@Override
	public int layoytResurce() {
		return R.layout.projectdetail_support;
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
		headerOption.setResHeader(R.string.header_project_detail_support);
		headerOption.setResDrawableLeft(R.drawable.back_xml);
		headerOption.setResDrawableRight(R.drawable.home_xml);
		return headerOption;
	}
	
	public void viewSupportDetail(View v, BaseItem data){
		AccountDB support_info = new AccountDB(getActivity());
		support_info.save("support_info_value", data.getString("value"));
		support_info.save("support_info_desc", data.getString("desc"));
		support_info.save("support_info_offer_date", data.getString("offer_date"));
		((BaseActivity)getActivity()).changeFragemt(new ProjectDetailSupportdetailFragment());
		
	}


}
