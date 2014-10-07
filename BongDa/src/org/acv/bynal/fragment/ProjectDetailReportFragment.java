package org.acv.bynal.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.acv.bynal.activity.ProjectDetailActivity;
import org.acv.bynal.views.ProjectDetailReportItemView;
import org.acv.bynal.views.ProjectTagItemView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
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

public class ProjectDetailReportFragment extends BaseFragment {
	Map<String, String> sendData = new HashMap<String, String>();
	ICallbackAPI callbackAPI;
	ListView pDetailReportview;
	String projectId = "0";
	public ProjectDetailReportFragment() {
	}

	private BaseAdapter baseAdapter;
	@Override
	public void setUpFragment(View view) {
		projectId = Integer.toString(ProjectDetailActivity.projectId);
		callbackAPI = new ICallbackAPI() {
			@Override
			public void onSuccess(String response) {
				List<BaseItem> baseItems = new ArrayList<BaseItem>();
				baseItems.clear();
				try {
					JSONObject jsonObject = new JSONObject(response);
					if (jsonObject != null) {
//						CommonAndroid.showDialog(getActivity(), "data:" + jsonObject.toString() , null);	
						if (jsonObject != null) {
							JSONArray array = jsonObject.getJSONArray("array_data");
							for (int i = 0; i < array.length(); i++) {
								baseItems.add(new BaseItem(array.getJSONObject(i)));
							}
							
						}
						baseAdapter.clear();
						baseAdapter.addAllItems(baseItems);
						baseAdapter.notifyDataSetChanged();
						
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
		sendData.put("id", projectId);
		new APICaller(getActivity()).callApi("/project/listRepost", true,
				callbackAPI, sendData);
		pDetailReportview = (ListView) view.findViewById(R.id.listview_report);
		baseAdapter = new BaseAdapter(getActivity(), new ArrayList<Object>()) {
			@Override
			public BaseView getView(Context context, Object data) {
				final ProjectDetailReportItemView projectDetailReportItemView = new ProjectDetailReportItemView(
							context);
				projectDetailReportItemView.addProjectManagerFragment(ProjectDetailReportFragment.this);
				return projectDetailReportItemView;
			}
		};
		pDetailReportview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
	        	//view list comment
				BaseItem baseItem = (BaseItem) parent
						.getItemAtPosition(position);
				String project_report_no = baseItem.getString("project_report_no");
				ProjectDetailActivity.project_report_no = project_report_no;
				((BaseActivity)getActivity()).changeFragemt(new ProjectDetailReportdetailFragment());
			}
		});
		pDetailReportview.setAdapter(baseAdapter);
	}

	@Override
	public int layoytResurce() {
		return R.layout.projectdetail_report;
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
		headerOption.setResHeader(R.string.header_project_detail_report);
		headerOption.setResDrawableLeft(R.drawable.back_xml);
		headerOption.setResDrawableRight(R.drawable.home_xml);
		return headerOption;
	}

}
