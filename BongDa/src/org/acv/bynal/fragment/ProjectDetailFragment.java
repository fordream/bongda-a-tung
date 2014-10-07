package org.acv.bynal.fragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.acv.bynal.activity.ProjectDetailActivity;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.PluginState;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.acv.libs.base.BaseActivity;
import com.acv.libs.base.BaseFragment;
import com.acv.libs.base.CommonAndroid;
import com.acv.libs.base.HeaderOption;
import com.acv.libs.base.ImageLoaderUtils;
import com.acv.libs.base.HeaderOption.TYPEHEADER;
import com.acv.libs.base.callback.APICaller;
import com.acv.libs.base.callback.APICaller.ICallbackAPI;
import com.acv.libs.base.db.AccountDB;
import com.acv.libs.base.util.ByUtils;

import app.bynal.woman.R;

public class ProjectDetailFragment extends BaseFragment implements
OnClickListener{
	Map<String, String> sendData = new HashMap<String, String>();
	ICallbackAPI callbackAPI;
	ICallbackAPI callbackAPIFavorite;
	Context context = null;
	private WebView credit;
	/** Layout of credit. */
	private RelativeLayout rl;
	String time_start_temp = "";
	String time_end_temp = "";
	String set_staus_favorite = "";
	String token = null;
	
	//load page
    private ProgressDialog progressDialog;
    
	@Override
	public void setUpFragment(View view) {
		AccountDB accountDB = new AccountDB(getActivity());
		token = accountDB.getToken();
		credit = new WebView(getActivity());
		credit.getSettings().setJavaScriptEnabled(true);
		credit.getSettings().setPluginState(PluginState.ON);
		credit.getSettings().setAllowFileAccess(true);
		rl = (RelativeLayout) view.findViewById(R.id.detail_display);
		rl.addView(credit);
		callbackAPI = new ICallbackAPI() {
			@Override
			public void onSuccess(String response) {
				try {
					JSONObject jsonObject = new JSONObject(response);
					if (jsonObject.getString("status").equals("1")) {
						time_start_temp = jsonObject.getString("public_date");//"2014-09-12";
					    time_end_temp = jsonObject.getString("end_date");//"2014-09-12";
					    String project_image = jsonObject.getString("project_image");
						String div_block =  jsonObject.getString("div_block");
						String project_movie =  jsonObject.getString("project_movie");
						
						ProjectDetailActivity.projectTitle = jsonObject.getString("title");
						ProjectDetailActivity.projectDesc = jsonObject.getString("desc");
						ProjectDetailActivity.projectTotalDate = jsonObject.getInt("total_date");
						
						updataDetail(project_image,project_movie,div_block);
						
						ImageView img= (ImageView) getActivity().findViewById(R.id.project_detail_setlike);
						if(jsonObject.getString("favorite").equalsIgnoreCase("1")){
							img.setImageResource(R.drawable.project_detail_setunlike);
							set_staus_favorite = "0";
						}else if(jsonObject.getString("favorite").equalsIgnoreCase("0")){
							img.setImageResource(R.drawable.project_detail_setlike);
							set_staus_favorite = "1";
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
		sendData.put("token", token);
		sendData.put("id", "" + ProjectDetailActivity.projectId);
		new APICaller(getActivity()).callApi("/project/detail", true, callbackAPI,
				sendData);
		
		//call back favorite
		callbackAPIFavorite = new ICallbackAPI() {
			@Override
			public void onSuccess(String response) {
				try {
					JSONObject jsonObject = new JSONObject(response);
					if (jsonObject.getString("status").equals("1")) {
//						sendData = new HashMap<String, String>();
//						sendData.put("token", token);
//						sendData.put("id", "" + ProjectDetailActivity.projectId);
//						new APICaller(getActivity()).callApi("/project/detail", true, callbackAPI,
//								sendData);
						ImageView img= (ImageView) getActivity().findViewById(R.id.project_detail_setlike);
						if(set_staus_favorite.equalsIgnoreCase("0")){
							img.setImageResource(R.drawable.project_detail_setlike);
							set_staus_favorite = "1";
						}else if(set_staus_favorite.equalsIgnoreCase("1")){
							img.setImageResource(R.drawable.project_detail_setunlike);
							set_staus_favorite = "0";
						}
					}else if(!jsonObject.getBoolean("is_login")){
						CommonAndroid.showDialog(getActivity(), getResources()
								.getString(R.string.error_message_session_login), null);
					}	
				} catch (JSONException e) {
					Log.e("PD","err::" + e.getMessage());
				}
			}

			@Override
			public void onError(String message) {
				
			}
		};
		
		view.findViewById(R.id.project_detail_share).setOnClickListener(this);
		view.findViewById(R.id.project_detail_money).setOnClickListener(this);
		view.findViewById(R.id.project_detail_setlike).setOnClickListener(this);
		view.findViewById(R.id.project_detail_calendar).setOnClickListener(this);
	}

	@Override
	public int layoytResurce() {
		return R.layout.projectdetail;
	}
	
	@Override
	public void onClick(View v) {
		int type = v.getId();
		switch (type) {
			case R.id.project_detail_share:
				if((ProjectDetailActivity.projectIdDetailPreview).equalsIgnoreCase("0")){
					final String [] items			= new String [] { getResources().getString(
							R.string.project_detail_share_facebook) , getResources().getString(
									R.string.project_detail_share_twitter) , getResources().getString(
											R.string.project_detail_share_bookmark), getResources().getString(
													R.string.project_detail_share_contact) };				
					ArrayAdapter<String> adapter	= new ArrayAdapter<String> (getActivity(), android.R.layout.select_dialog_item,items);
					AlertDialog.Builder builder		= new AlertDialog.Builder(getActivity());
					builder.setAdapter( adapter, new DialogInterface.OnClickListener() {
						public void onClick( DialogInterface dialog, int item ) {
							if (item == 0) {
								//share face
								String URL = "http://www.facebook.com/sharer/sharer.php?u=http://"+ByUtils.BASEDOMAINSERVER_SHARE_PROJECT+"/projects/detail/" + ProjectDetailActivity.projectId;
								Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URL));
								getActivity().startActivityForResult(browserIntent, ByUtils.REQUEST_PROJECT_DETAIL);
							}else if(item ==1){
								//share twitter
//								https://twitter.com/share?url=http://bynal.acvdev.com/projects/detail/" + pid ;
								String title = ProjectDetailActivity.projectTitle;
								String fotmat = getString(R.string.share_twitter_title);
								String str = String.format(fotmat, title, "");
								String URL = "https://twitter.com/share?url=http://"+ByUtils.BASEDOMAINSERVER_SHARE_PROJECT+"/projects/detail/" + ProjectDetailActivity.projectId + "&text=" + str;
								Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URL));
								getActivity().startActivityForResult(browserIntent, ByUtils.REQUEST_PROJECT_DETAIL);
							}else if(item ==2){
								//share bookmark
//								http://b.hatena.ne.jp/entry/bynal.acvdev.com/projects/detail/"  + pid;
								String URL = "http://b.hatena.ne.jp/entry/"+ByUtils.BASEDOMAINSERVER_SHARE_PROJECT+"/projects/detail/" + ProjectDetailActivity.projectId;
								Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URL));
								getActivity().startActivityForResult(browserIntent, ByUtils.REQUEST_PROJECT_DETAIL);
							}else if(item ==3){
								//share contact
//								Intent PageDetail = new Intent(getActivity(), SendContactActivity.class);
//					        	PageDetail.putExtra("id", ProjectDetailActivity.projectId);
//					        	PageDetail.putExtra("title", "title");
//					        	getActivity().startActivityForResult(PageDetail, ByUtils.REQUEST_PROJECT_DETAIL);
								String URL = "http://"+ByUtils.BASEDOMAINSERVER_SHARE_PROJECT+"/projects/detail/" + ProjectDetailActivity.projectId;
					        	ByUtils.sendContact(getActivity(), ""+ProjectDetailActivity.projectId, ProjectDetailActivity.projectTitle , URL);
							}
							 
						}
					} );
					
					final AlertDialog dialog = builder.create();
					dialog.show();
				}
				
				break;
			case R.id.project_detail_money:
				((BaseActivity)getActivity()).changeFragemt(new ProjectDetailSupportFragment());
				break;
			case R.id.project_detail_setlike:
				AccountDB accountDB = new AccountDB(getActivity());
				if(!set_staus_favorite.equalsIgnoreCase("") && accountDB.getToken() != null){
					sendData = new HashMap<String, String>();
					sendData.put("id", Integer.toString(ProjectDetailActivity.projectId));
					sendData.put("token", token);
					sendData.put("value", set_staus_favorite);
					new APICaller(getActivity()).callApi("/project/setFavorite", true,
							callbackAPIFavorite, sendData);
				}else{
					//login
					CommonAndroid.showDialog(getActivity(),  getResources()
							.getString(R.string.error_message_pleaselogin) , null);
				}
				break;
			case R.id.project_detail_calendar:
				try {
					if(ProjectDetailActivity.projectTotalDate > 0){
						Calendar cal_start = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
						Calendar cal_end = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
				        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//HHmmss
				        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));       
				        cal_start.setTime(sdf.parse(time_start_temp));
				        cal_end.setTime(sdf.parse(time_end_temp));
				        
				        long time_start = cal_start.getTimeInMillis();
				        long time_end	= cal_end.getTimeInMillis();
						
//						CommonAndroid.showDialog(getActivity(), ":time_start:" + time_start + ":time_end:" + time_end, null);
						ByUtils.addCalendar(getActivity(),time_start, time_end , ProjectDetailActivity.projectTitle, ProjectDetailActivity.projectDesc);
					}else{
						CommonAndroid.showDialog(getActivity(), getResources()
								.getString(R.string.project_detail_support_err_projectended), null);
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			default:
				break;
		}
	}
	
	private void updataDetail(String project_image, String project_movie,String div_block){
		try{
			progressDialog = ProgressDialog.show(getActivity(), "", "Loading...",true);
			progressDialog.dismiss();
			String str = "";
			str += loadResouceHTMLFromAssets("project_detail_header.html");
			if(!project_movie.equalsIgnoreCase("")){
				str += (project_movie  + "<br />");
			}else if(!project_image.equalsIgnoreCase("")){
				getView().findViewById(R.id.project_detail_image).setVisibility(View.VISIBLE);
				ImageLoaderUtils.getInstance(getActivity()).DisplayImage(project_image,
	 					(ImageView) getView().findViewById(R.id.project_detail_image));
//				str += (project_image  + "<br />");
			}
			str += div_block;
			str += loadResouceHTMLFromAssets("project_detail_footer.html");
			credit.loadDataWithBaseURL("file:///android_asset/", str,
					"text/html", "UTF-8", "");
//	        webView.loadUrl("file:///android_asset/test.html");
			credit.setWebChromeClient(new WebChromeClient() {
	        });
	            
			credit.setWebViewClient(new WebViewClient() {
	                 public boolean shouldOverrideUrlLoading(WebView view, String url) {
	                     return false;
	                }
	                public void onPageFinished(WebView view, String url) {
	                   progressDialog.dismiss();
	                   //Toast.makeText(context, "Page Load Finished", Toast.LENGTH_SHORT).show();
	                }
	          });       
		    
		} catch (Exception e) {
			progressDialog.dismiss();
		} catch (OutOfMemoryError e){
			progressDialog.dismiss();
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
	public HeaderOption getHeaderOption() {
		TYPEHEADER type = TYPEHEADER.NORMAL;
		HeaderOption headerOption = new HeaderOption(getActivity(), type) {
			@Override
			public void onClickButtonLeft() {
				ProjectDetailActivity.projectIdDetailPreview = "0";
				getActivity().setResult(Activity.RESULT_OK);
				getActivity().finish();
			}

			@Override
			public void onClickButtonRight() {
				((BaseActivity) getActivity()).showMenuLeft(true);
			}
		};
		if((ProjectDetailActivity.projectIdDetailPreview).equalsIgnoreCase("1")){
			headerOption.setShowButtonLeft(true);
			headerOption.setResHeader(R.string.header_project_preview);
			headerOption.setResDrawableLeft(R.drawable.back_xml);
		}else{
			headerOption.setShowButtonLeft(true);
			headerOption.setShowButtonRight(true);
			headerOption.setResHeader(R.string.header_project_detail);
			headerOption.setResDrawableLeft(R.drawable.back_xml);
			headerOption.setResDrawableRight(R.drawable.menu_xml);
		}
		return headerOption;
	}
	
}