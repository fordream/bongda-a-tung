package org.acv.bynal.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.acv.bynal.activity.ProjectDetailActivity;
import org.acv.bynal.views.ProjectDetailCommentItemView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
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

public class ProjectDetailCommentFragment extends BaseFragment {
	Map<String, String> sendData = new HashMap<String, String>();
	ICallbackAPI callbackAPI;
	ListView pDetailCommentview;
	private int total_pages = 1;
	private int more_pages = 1;
	private boolean isLoadMore = false;
	List<BaseItem> baseItems = new ArrayList<BaseItem>();
	public ProjectDetailCommentFragment() {
	}

	private BaseAdapter baseAdapter;
	@Override
	public void setUpFragment(View view) {
		callbackAPI = new ICallbackAPI() {
			@Override
			public void onSuccess(String response) {
//				List<BaseItem> baseItems = new ArrayList<BaseItem>();
				if(more_pages == 1){
					baseItems.clear();
				}
				try {
					JSONObject jsonObject = new JSONObject(response);
					if (jsonObject != null) {
						if (jsonObject != null) {
//							CommonAndroid.showDialog(getActivity(), "index_pages:" + more_pages + ":data:" + jsonObject.toString(), null);
							if(jsonObject.getString("status").equalsIgnoreCase("1")){
								total_pages = jsonObject.getInt("total_pages");
								if(total_pages > 1){
									isLoadMore = true;
									if(more_pages <= total_pages){
										more_pages++;
									}
								}
								JSONArray array = jsonObject.getJSONArray("array_data");
								for (int i = 0; i < array.length(); i++) {
									baseItems.add(new BaseItem(array.getJSONObject(i)));
								}
								
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
		sendData.put("id", Integer.toString(ProjectDetailActivity.projectId));
		sendData.put("page", "1");
		new APICaller(getActivity()).callApi("/project/listComment", true,
				callbackAPI, sendData);
		pDetailCommentview = (ListView) view.findViewById(R.id.listview_comment);
		baseAdapter = new BaseAdapter(getActivity(), new ArrayList<Object>()) {
			@Override
			public BaseView getView(Context context, Object data) {
				final ProjectDetailCommentItemView projectDetailCommentItemView = new ProjectDetailCommentItemView(
							context);
				projectDetailCommentItemView.addProjectManagerFragment(ProjectDetailCommentFragment.this);
				return projectDetailCommentItemView;
			}
		};
		pDetailCommentview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
//				BaseItem baseItem = (BaseItem) parent
//						.getItemAtPosition(position);
			}
		});
		pDetailCommentview.setOnScrollListener(new OnScrollListener(){

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				if(totalItemCount > 0 && isLoadMore){
					if(totalItemCount - 2 <= firstVisibleItem + visibleItemCount){
			    		if(more_pages <= total_pages){
//			    			CommonAndroid.showDialog(getActivity(), "load more" + more_pages, null);
			    			isLoadMore = false;
			    			sendData = new HashMap<String, String>();
			    			sendData.put("id", Integer.toString(ProjectDetailActivity.projectId));
			    			sendData.put("page", Integer.toString(more_pages));
			    			new APICaller(getActivity()).callApi("/project/listComment", true,
			    					callbackAPI, sendData);
			    		}
			    	}
				}
		    	
			}
			
		});
		pDetailCommentview.setAdapter(baseAdapter);
	}

	@Override
	public int layoytResurce() {
		return R.layout.projectdetail_comment;
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
		headerOption.setResHeader(R.string.header_project_detail_comment);
		headerOption.setResDrawableLeft(R.drawable.back_xml);
		headerOption.setResDrawableRight(R.drawable.home_xml);
		return headerOption;
	}

}
