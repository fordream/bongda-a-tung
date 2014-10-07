package org.acv.bynal.fragment;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.acv.bynal.activity.HomeActivity;
import org.acv.bynal.activity.ProfileActivity;
import org.acv.bynal.activity.ProjectDetailActivity;
import org.acv.bynal.activity.ProjectmanagerActivity;
import org.acv.bynal.activity.SearchActivity;
import org.acv.bynal.camera.VideoUtil;
import org.acv.bynal.views.HeaderView;
import org.acv.bynal.views.ProjectListItemView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

import com.acv.libs.base.BaseActivity;
import com.acv.libs.base.BaseAdapter;
import com.acv.libs.base.BaseFragment;
import com.acv.libs.base.BaseItem;
import com.acv.libs.base.BaseView;
import com.acv.libs.base.CommonAndroid;
import com.acv.libs.base.HeaderOption;
import com.acv.libs.base.LogUtils;
import com.acv.libs.base.HeaderOption.TYPEHEADER;
import com.acv.libs.base.callback.APICaller;
import com.acv.libs.base.callback.APICaller.ICallbackAPI;
import com.acv.libs.base.db.AccountDB;
import com.acv.libs.base.util.ByUtils;

import app.bynal.woman.R;

public class ProjectManagerFragment extends BaseFragment{
	Map<String, String> sendData = new HashMap<String, String>();
	Map<String, String> sendDataUploadVideo = new HashMap<String, String>();
	ICallbackAPI callbackAPI;
	ICallbackAPI callbackAPIUploadVideo;
	ICallbackAPI callbackAPIDelete;
	Context context = null;
	String token_user;
	ListView projectlistview;
	
	private int total_pages = 1;
	private int currentPage = 1;
	private boolean isLoadMore = false; 
	List<BaseItem> baseItems = new ArrayList<BaseItem>();
	@Override
	public void setUpFragment(View view) {
		projectlistview = (ListView) view.findViewById(R.id.listview_project_manager);
		AccountDB accountDB = new AccountDB(getActivity());
		token_user = accountDB.getToken();
		callbackAPI = new ICallbackAPI() {
			@Override
			public void onSuccess(String response) {
				try {
					JSONObject jsonObject = new JSONObject(response);
					if (jsonObject != null) {
						//"is_login":true
						if(jsonObject.getBoolean("is_login") == true){
							total_pages = jsonObject.getInt("total_pages");
							currentPage = jsonObject.getInt("page");
							if(total_pages > 1 && total_pages > currentPage){
								isLoadMore = true;
							}
							JSONArray array = jsonObject.getJSONArray("array_data");
//							Log.e("array_data_length","length==========::" + array.length());
							for (int i = 0; i < array.length(); i++) {
								baseItems.add(new BaseItem(array.getJSONObject(i)));
							}
						}else{
							// token timeout
							CommonAndroid.showDialog(getActivity(), getResources().getString(R.string.error_message_session_login), new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									getActivity().setResult(ByUtils.RESPONSE_RELEASETOKEN);
									getActivity().finish();
								}
							});
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
		
		callbackAPIUploadVideo = new ICallbackAPI() {
			@Override
			public void onSuccess(String response) {
				try {
					JSONObject jsonObject = new JSONObject(response);
//					CommonAndroid.showDialog(getActivity(), jsonObject.toString(), null);
					if (jsonObject.getString("status").equals("true")) {
//						Log.e("aaaaaa","video========OKOKOK");
						CommonAndroid.showDialog(getActivity(),getResources().getString(
								R.string.video_up_server_done) , null);
					}
				} catch (JSONException e) {
					CommonAndroid.showDialog(getActivity(), e.toString(), null);
				}
			}

			@Override
			public void onError(String message) {
				CommonAndroid.showDialog(getActivity(), message, null);
			}
		};
		CallAPIProjectList();
	}

	private BaseAdapter baseAdapter;
	
	@Override
	public int layoytResurce() {
		return R.layout.projectmanager;
	}
	
	@Override
	public void onClick(View v) {
	}
	
	@Override
	public HeaderOption getHeaderOption() {
		TYPEHEADER type = TYPEHEADER.NORMAL;
		HeaderOption headerOption = new HeaderOption(getActivity(), type) {
			@Override
			public void onClickButtonLeft() {
				ProjectmanagerActivity.typeProject = "0";
				getActivity().setResult(Activity.RESULT_OK);
				getActivity().finish();
			}

			@Override
			public void onClickButtonRight() {
				((BaseActivity) getActivity()).showMenuLeft(true);
				((BaseActivity) getActivity()).showMenuRight(true);
			}
		};
		headerOption.setShowButtonLeft(true);
		headerOption.setShowButtonRight(true);
		headerOption.setResHeader(R.string.header_project_favorite);
		headerOption.setResDrawableLeft(R.drawable.back_xml);
		headerOption.setResDrawableRight(R.drawable.menu_xml);
		return headerOption;
	}
	
	public void CallAPIProjectList(){
		int txtHeader = 0;
		int type = Integer.parseInt(ProjectmanagerActivity.typeProject);
		switch (type) {
			case 0:
				txtHeader = R.string.header_project_favorite;
				break;
			case 1:
				txtHeader = R.string.header_project_support;
				break;
			case 2:
				txtHeader = R.string.header_project_post;
				break;
		}
		((BaseActivity)getActivity()).setTextheader(txtHeader);
		baseAdapter = new BaseAdapter(getActivity(), new ArrayList<Object>()) {
			@Override
			public BaseView getView(Context context, Object data) {
				final ProjectListItemView ProjectManagerItemView = new ProjectListItemView(
							context);
				ProjectManagerItemView.addProjectManagerFragment(ProjectManagerFragment.this);
				return ProjectManagerItemView;
			}
		};
		projectlistview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				BaseItem baseItem = (BaseItem) parent
						.getItemAtPosition(position);
				String projectID = baseItem.getString("id");
				String public_flg = "0";
				try {
					public_flg = baseItem.getString("public_flg");
					if(public_flg.equalsIgnoreCase("1")){
						Intent PageDetail = new Intent(getActivity(), ProjectDetailActivity.class);
			        	PageDetail.putExtra("projectIdDetail", Integer.parseInt(projectID));
			        	startActivityForResult(PageDetail,
								ByUtils.REQUEST_PROJECTMANAGER);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					Intent PageDetail = new Intent(getActivity(), ProjectDetailActivity.class);
		        	PageDetail.putExtra("projectIdDetail", Integer.parseInt(projectID));
		        	startActivityForResult(PageDetail,
							ByUtils.REQUEST_PROJECTMANAGER);
				}
//				CommonAndroid.showDialog(getActivity(), "projectID::" + public_flg,null);
	        	
			}
		});
		
		projectlistview.setOnScrollListener(new OnScrollListener(){

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				if(totalItemCount > 0 && isLoadMore){
					if(totalItemCount - 1 <= firstVisibleItem + visibleItemCount){
//		    			CommonAndroid.showDialog(getActivity(), "load more:" + (currentPage + 1), null);
		    			sendData = new HashMap<String, String>();
		    			sendData.put("type", ProjectmanagerActivity.typeProject);
		    			sendData.put("token", token_user);
		    			sendData.put("page", "" +(currentPage + 1));
		    			new APICaller(getActivity()).callApi("/project/list", true,
		    					callbackAPI, sendData);
		    			isLoadMore = false;
			    	}
				}
		    	
			}
			
		});
		
		baseItems.clear();
		total_pages = 1;
		currentPage = 1;
		projectlistview.setAdapter(baseAdapter);
		sendData = new HashMap<String, String>();
		sendData.put("type", ProjectmanagerActivity.typeProject);
		sendData.put("token", token_user);
		new APICaller(getActivity()).callApi("/project/list", true,
				callbackAPI, sendData);
		
	}
	
	public void actionProjectPost(View v, final BaseItem data) {
		String id = data.getString("id");
		ProjectmanagerActivity.project_id = id;
		int type = v.getId();
		int action = 0;
		switch (type) {
			case R.id.pm_button_video:
				action = 0;
				final String [] items			= new String [] { getResources().getString(
						R.string.project_changevideo_from_camera) , getResources().getString(
								R.string.project_changevideo_from_gallery)};				
				ArrayAdapter<String> adapter	= new ArrayAdapter<String> (getActivity(), android.R.layout.select_dialog_item,items);
				AlertDialog.Builder builder		= new AlertDialog.Builder(getActivity());
				
//				builder.setTitle("Select Image");
				builder.setAdapter( adapter, new DialogInterface.OnClickListener() {
					public void onClick( DialogInterface dialog, int item ) { //pick from camera
						if (item == 0) {
							VideoUtil util = new VideoUtil(getActivity());
					    	if (util.checkCameraAvaible()) {
					    		Intent i = new Intent();
					            i.setAction("android.media.action.VIDEO_CAPTURE");
					            getActivity().startActivityForResult(i, ProjectmanagerActivity.CAPTURE_RETURN);
							}else{
								util.showAlertError(getResources().getString(
										R.string.video_camera_err), getResources().getString(
										R.string.video_title_err));
							}
						}else{
							Intent intent = new Intent();
					        intent.setAction(Intent.ACTION_PICK);
					        intent.setType("video/*");

					        List<ResolveInfo> list = getActivity().getPackageManager().queryIntentActivities(intent,
					            PackageManager.MATCH_DEFAULT_ONLY);
					        if (list.size() <= 0) {
//					          Log.d(LOG_TAG, "no video picker intent on this hardware");
					        	CommonAndroid.showDialog(getActivity(), getResources().getString(
										R.string.project_novideo_gallery), null);
					          return;
					        }

					        getActivity().startActivityForResult(intent, ProjectmanagerActivity.GALLERY_RETURN);
						}
					}
				} );
				
				final AlertDialog dialog = builder.create();
				dialog.show();
				break;
			case R.id.pm_button_preview:
				action = 1;
				Intent PageDetail = new Intent(getActivity(), ProjectDetailActivity.class);
	        	PageDetail.putExtra("projectIdDetail", Integer.parseInt(id));
	        	PageDetail.putExtra("projectIdDetailPreview","1");
	        	startActivityForResult(PageDetail,
						ByUtils.REQUEST_PROJECTMANAGER);
				break;
			case R.id.pm_button_support:
				action = 2;
				((BaseActivity)getActivity()).changeFragemt(new ProjectManagerSupportFragment());
				break;
			case R.id.pm_button_delivery:
				action = 3;
				((BaseActivity)getActivity()).changeFragemt(new ProjectManagerDeliveryFragment());
				break;	
			case R.id.pm_button_delete:
				action = 4;
				callbackAPIDelete = new ICallbackAPI() {
					@Override
					public void onSuccess(String response) {
						try {
							JSONObject jsonObject = new JSONObject(response);
							if (jsonObject.getString("status").equals("1")) {
								CallAPIProjectList();
							} else {
								CommonAndroid.showDialog(getActivity(),
										jsonObject.getString("message"), null);
							}
						} catch (Exception e) {
							LogUtils.e("ABC", e);
							CommonAndroid.showDialog(getActivity(),
									"Can not delete", null);
						}
					}

					@Override
					public void onError(String message) {
						CommonAndroid.showDialog(getActivity(), message + "",
								null);
					}
				};
				CommonAndroid.showDialog(getActivity(),
						getResources().getString(R.string.project_post_delete_confirm), new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								//delete project
								sendData = new HashMap<String, String>();
								sendData.put("project_id", ProjectmanagerActivity.project_id);
								sendData.put("token", token_user);
								new APICaller(getActivity()).callApi("/project/delete", true, callbackAPIDelete, sendData);
							}
						} , new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								//Cancel
							}
						});
				break;
		}
//		CommonAndroid.showDialog(getActivity(),action + "==action::projectID==" + id, null);
	}
	
	public void CallAPIUploadVideo(String youtubeID){
		String movie_url = "<iframe width='100%' height='315' src='http://www.youtube.com/embed/"+ youtubeID +"' frameborder='0' allowfullscreen></iframe>";
		sendDataUploadVideo = new HashMap<String, String>();
		sendDataUploadVideo.put("token", token_user);
		sendDataUploadVideo.put("project_id", ProjectmanagerActivity.project_id);
		sendDataUploadVideo.put("site_name", "Youtube");
		sendDataUploadVideo.put("movie_url", movie_url);
		new APICaller(getActivity()).callApi("/project/movieUpload", true,
				callbackAPIUploadVideo, sendDataUploadVideo);
	}
}