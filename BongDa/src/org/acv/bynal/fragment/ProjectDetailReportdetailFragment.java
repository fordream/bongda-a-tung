package org.acv.bynal.fragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.acv.bynal.activity.ProjectDetailActivity;
import org.acv.bynal.views.ProjectDetailReportdetailItemView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
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

public class ProjectDetailReportdetailFragment extends BaseFragment {
	Map<String, String> sendData = new HashMap<String, String>();
	ICallbackAPI callbackAPI;
	ListView pDetailReportdetailview;
	String projectId = "0";
	String project_report_no = "";
	private WebView credit;
	/** Layout of credit. */
	private RelativeLayout rl;
	private boolean actionAddComment = false;
	public ProjectDetailReportdetailFragment() {
	}

	private BaseAdapter baseAdapter;
	@Override
	public void setUpFragment(View view) {
		credit = new WebView(getActivity()); 
		rl = (RelativeLayout) view.findViewById(R.id.reportdetail_desc_txt);
		rl.addView(credit);
		projectId = Integer.toString(ProjectDetailActivity.projectId);
		project_report_no = ProjectDetailActivity.project_report_no;
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
							if(actionAddComment){
								actionAddComment = false;
								if(jsonObject.getString("is_login").equalsIgnoreCase("false")){
									CommonAndroid.showDialog(getActivity(), getResources()
											.getString(R.string.error_message_pleaselogin), null);
								}
							}
							setText(jsonObject.getString("report_title"),
									R.id.reportdetail_title_txt);
							setText(getResources().getString(R.string.report_postdate) + jsonObject.getString("post_date"),
									R.id.reportdetail_postdate_txt);
							updataDetail(jsonObject.getString("report_body"));
							JSONArray array = jsonObject.getJSONArray("comment_data");
							for (int i = 0; i < array.length(); i++) {
								if( (i+1) < 10){
									array.getJSONObject(i).put("key_value", "0"+(i+1));
								}else{
									array.getJSONObject(i).put("key_value", i+1);
								}
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
		sendData.put("p_id", projectId);
		sendData.put("r_no", project_report_no);
		new APICaller(getActivity()).callApi("/project/detailRepost", true,
				callbackAPI, sendData);
		pDetailReportdetailview = (ListView) view.findViewById(R.id.listview_reportdetail);
		baseAdapter = new BaseAdapter(getActivity(), new ArrayList<Object>()) {
			@Override
			public BaseView getView(Context context, Object data) {
				final ProjectDetailReportdetailItemView projectDetailReportdetailItemView = new ProjectDetailReportdetailItemView(
							context);
				projectDetailReportdetailItemView.addProjectDetailFragment(ProjectDetailReportdetailFragment.this);
				return projectDetailReportdetailItemView;
			}
		};
		pDetailReportdetailview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
	        	
			}
		});
		pDetailReportdetailview.setAdapter(baseAdapter);
		view.findViewById(R.id.reportdetail_footer_btn).setOnClickListener(this);
	}

	@Override
	public int layoytResurce() {
		return R.layout.projectdetail_reportdetail;
	}
	
	@Override
	public HeaderOption getHeaderOption() {
		TYPEHEADER type = TYPEHEADER.NORMAL;
		HeaderOption headerOption = new HeaderOption(getActivity(), type) {
			@Override
			public void onClickButtonLeft() {
				ProjectDetailActivity.project_report_no = "";
				((BaseActivity)getActivity()).changeFragemt(new ProjectDetailReportFragment());
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
	
	private void updataDetail(String data){
		try{
			String str = "";
			str += loadResouceHTMLFromAssets("project_detail_header.html");
			str += data;
			str += loadResouceHTMLFromAssets("project_detail_footer.html");
			credit.loadDataWithBaseURL("file:///android_asset/", str,
					"text/html", "UTF-8", "");
		    
		} catch (Exception e) {
		} catch (OutOfMemoryError e){
		}
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
	public void onClick(View v) {
		AccountDB accountDB = new AccountDB(getActivity());
		String token_user = accountDB.getToken();
		if (ByUtils.isBlank(token_user)) {
				CommonAndroid.showDialog(getActivity(), getResources()
						.getString(R.string.error_message_pleaselogin), null);
		} else {
			if(getTextStr(R.id.reportdetail_footer_text).equalsIgnoreCase("")){
				CommonAndroid.showDialog(getActivity(), getResources()
						.getString(R.string.report_addcomment_err), null);
			}else{
				//add comment
				actionAddComment = true;
				sendData = new HashMap<String, String>();
				sendData.put("p_id", projectId);
				sendData.put("r_no", project_report_no);
				sendData.put("token", token_user);
				sendData.put("add_comment", getTextStr(R.id.reportdetail_footer_text));
				new APICaller(getActivity()).callApi("/project/detailRepost", true,
						callbackAPI, sendData);
				setTextStr(R.id.reportdetail_footer_text, "");
			}
		}
		
	}


}
