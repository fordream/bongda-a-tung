package org.acv.bynal.fragment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.acv.bynal.activity.ProfileActivity;
import org.acv.bynal.activity.SearchActivity;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import app.bynal.woman.R;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

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

public class ProfileFragment extends BaseFragment {
	View profile;
	String token_user;
	Boolean getProfile = false;
	String user_name_new;
	Map<String, String> sendDataUploadImg = new HashMap<String, String>();
	Map<String, String> sendData = new HashMap<String, String>();
	ICallbackAPI callbackNewAPI;
	ICallbackAPI callbackAPIUploadImg;
	private ImageView imgProfile;
	private Spinner spinner_year;
	List<String> listyear = new ArrayList<String>();
	int maxYear = 2010;
	int minYear = 1950;
	String birthday_year = "";
	public ProfileFragment() {
	}

	@Override
	public void setUpFragment(final View view) {
		profile = view;
		// ((BaseActivity)getActivity()).setTextheader(getResources().getString(R.string.menu_profile_header));
		view.findViewById(R.id.profile_user_btnupdateprofile).setOnClickListener(this);
		view.findViewById(R.id.prof_img_upload).setOnClickListener(this);
		imgProfile = (ImageView) view.findViewById(R.id.prof_img_upload);
		APICaller apiCaller = new APICaller(getActivity());
		callbackNewAPI = new ICallbackAPI() {
			@Override
			public void onSuccess(String response) {
				try {
					JSONObject jsonObject = new JSONObject(response);
					if (jsonObject.getString("status").equals("1")) {
						setData(null);
						setData(jsonObject);
						parserData();
					} else if (jsonObject.getString("status").equals("0")) {
						// token timeout
						CommonAndroid.showDialog(getActivity(), getResources().getString(R.string.error_message_session_login), new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								/*AccountDB account_clear = new AccountDB(getActivity());
								account_clear.clear();
								// getActivity().setResult(Activity.RESULT_OK);
								// getActivity().finish();
								relaseTooken();*/
								getActivity().setResult(ByUtils.RESPONSE_RELEASETOKEN);
								getActivity().finish();
							}
						});

					}
				} catch (Exception e) {
				}
			}

			@Override
			public void onError(String message) {
			}
		};

		callbackAPIUploadImg = new ICallbackAPI() {
			@Override
			public void onSuccess(String response) {
				try {
					JSONObject jsonObject = new JSONObject(response);
					if (jsonObject.getString("status").equals("1")) {
						imgProfile.setImageBitmap(ProfileActivity.croppedImage);

						Log.e("aaaaaa", "avata========OKOKOK123");
						// sendData.put("token", token_user );
						//
						// new
						// APICaller(getActivity()).callApi("/user/getprofile",
						// true, callbackNewAPI,
						// sendData);
					}
				} catch (JSONException e) {
				}
			}

			@Override
			public void onError(String message) {
				((SearchActivity) getActivity()).refresh();
			}
		};

		sendData = new HashMap<String, String>();
		AccountDB accountDB = new AccountDB(getActivity());
		token_user = accountDB.getToken();
		if (ByUtils.isBlank(token_user)) {
			CommonAndroid.showDialog(getActivity(), getResources().getString(R.string.error_message_pleaselogin), null);
		} else {
			sendData.put("token", token_user);
			apiCaller.callApi("/user/getprofile", true, callbackNewAPI, sendData);
		}
		
		spinner_year = (Spinner) view.findViewById(R.id.birthday_year_2);
		Calendar cal = Calendar.getInstance();
		maxYear = cal.get(Calendar.YEAR);
		for(int i = maxYear; i >= minYear; i--){
			listyear.add(""+i);
		}
		ArrayAdapter<String> adapter	= new ArrayAdapter<String> (getActivity(), android.R.layout.simple_spinner_item,listyear);
		spinner_year.setAdapter(adapter);
		spinner_year.setOnItemSelectedListener(new CustomOnItemSelectedListener());

	}
	
	public class CustomOnItemSelectedListener implements OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> parent, View view, int pos,
				long id) {
			birthday_year = parent.getItemAtPosition(pos).toString();
//			CommonAndroid.showDialog(getActivity(),"position::" + pos + "selState::" + parent.getItemAtPosition(pos).toString(), null);
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub

		}

	}

	public void updataRadioButtonClicked(View view) {

		// Is the button now checked?
		boolean checked = ((RadioButton) view).isChecked();

		// Check which radio button was clicked
		RadioButton sex_male;
		RadioButton sex_female;
		sex_male = (RadioButton) profile.findViewById(R.id.sex_male);
		sex_female = (RadioButton) profile.findViewById(R.id.sex_female);
		RadioButton mail_delivery_flg_0;
		RadioButton mail_delivery_flg_1;
		mail_delivery_flg_0 = (RadioButton) profile.findViewById(R.id.mail_delivery_flg_0);
		mail_delivery_flg_1 = (RadioButton) profile.findViewById(R.id.mail_delivery_flg_1);

		switch (view.getId()) {
		case R.id.sex_male:
			if (checked) {
				sex_male.setChecked(true);
				sex_female.setChecked(false);
				sex_male.setBackgroundResource(R.drawable.radio_button_check);
				sex_female.setBackgroundResource(R.drawable.radio_button_uncheck);
			}
			break;
		case R.id.sex_female:
			if (checked) {
				sex_male.setChecked(false);
				sex_female.setChecked(true);
				sex_male.setBackgroundResource(R.drawable.radio_button_uncheck);
				sex_female.setBackgroundResource(R.drawable.radio_button_check);
			}
			break;
		case R.id.mail_delivery_flg_0:
			if (checked) {
				mail_delivery_flg_0.setChecked(true);
				mail_delivery_flg_1.setChecked(false);
				mail_delivery_flg_0.setBackgroundResource(R.drawable.radio_button_check);
				mail_delivery_flg_1.setBackgroundResource(R.drawable.radio_button_uncheck);
			}
			break;
		case R.id.mail_delivery_flg_1:
			if (checked) {
				mail_delivery_flg_0.setChecked(false);
				mail_delivery_flg_1.setChecked(true);
				mail_delivery_flg_0.setBackgroundResource(R.drawable.radio_button_uncheck);
				mail_delivery_flg_1.setBackgroundResource(R.drawable.radio_button_check);
			}
			break;
		}
	}

	public void CallAPIUploadAvata(String avatar) {

		// Log.e("aaaaaa","1111111111111" + avatar);
		sendDataUploadImg = new HashMap<String, String>();
		sendDataUploadImg.put("token", token_user);
		sendDataUploadImg.put("file", avatar);
		new APICaller(getActivity()).callApi("/user/updateavatar", true, callbackAPIUploadImg, sendDataUploadImg);
	}

	public void parserData() {
		if (getData() instanceof JSONObject) {
//			Log.e("aaaaaa", "1111111111111");
			JSONObject jsonObject = (JSONObject) getData();
//			CommonAndroid.showDialog(getActivity(),"data::" + jsonObject.toString(), null);
			Log.e("data", "aa" + jsonObject.toString());
			try {
				/*
				 * String user_id = jsonObject.getString("user_id"); String
				 * mail_address = jsonObject.getString("mail_address");
				 */
				String user_name = jsonObject.getString("user_name");
				if (user_name.equalsIgnoreCase("null")) {
					user_name = "";
				}
				String user_name_yomi = jsonObject.getString("user_name_yomi");
				if (user_name_yomi.equalsIgnoreCase("null")) {
					user_name_yomi = "";
				}
				// String user_image_no = jsonObject.getString("user_image_no");
				String biography = jsonObject.getString("biography");
				if (biography.equalsIgnoreCase("null")) {
					biography = "";
				}
				String sex = jsonObject.getString("sex");
				String birthday_year = jsonObject.getString("birthday_year");
				if (birthday_year.equalsIgnoreCase("null")) {
					birthday_year = "";
				}
				String hometown = jsonObject.getString("hometown");
				if (hometown.equalsIgnoreCase("null")) {
					hometown = "";
				}
				String job = jsonObject.getString("job");
				if (job.equalsIgnoreCase("null")) {
					job = "";
				}
				String history1 = jsonObject.getString("history1");
				if (history1.equalsIgnoreCase("null")) {
					history1 = "";
				}
				String history2 = jsonObject.getString("history2");
				if (history2.equalsIgnoreCase("null")) {
					history2 = "";
				}
				String history3 = jsonObject.getString("history3");
				if (history3.equalsIgnoreCase("null")) {
					history3 = "";
				}
				String user_url1 = jsonObject.getString("user_url1");
				if (user_url1.equalsIgnoreCase("null")) {
					user_url1 = "";
				}
				String user_url2 = jsonObject.getString("user_url2");
				if (user_url2.equalsIgnoreCase("null")) {
					user_url2 = "";
				}
				String user_url3 = jsonObject.getString("user_url3");
				if (user_url3.equalsIgnoreCase("null")) {
					user_url3 = "";
				}
				/*
				 * String point = jsonObject.getString("point"); String
				 * permission_cd = jsonObject.getString("permission_cd"); String
				 * facebook_id = jsonObject.getString("facebook_id"); String
				 * twitter_id = jsonObject.getString("twitter_id"); String
				 * facebook_token = jsonObject.getString("facebook_token");
				 * String twitter_token = jsonObject.getString("twitter_token");
				 * String twitter_token_secret =
				 * jsonObject.getString("twitter_token_secret"); String
				 * mail_magazin_delivery_flg =
				 * jsonObject.getString("mail_magazin_delivery_flg");
				 */
				String mail_delivery_flg = jsonObject.getString("mail_delivery_flg");
				// String delete_flg = jsonObject.getString("delete_flg");
				String prof_img_upload = jsonObject.getString("prof_img_upload");
				String name = jsonObject.getString("delivery_name"); //name
				if (name.equalsIgnoreCase("null")) {
					name = "";
				}
				String zip1 = jsonObject.getString("delivery_zip1");//zip
				if (zip1.equalsIgnoreCase("null")) {
					zip1 = "";
				}
				String zip2 = jsonObject.getString("delivery_zip2");//zip
				if (zip2.equalsIgnoreCase("null")) {
					zip2 = "";
				}
				String phone = jsonObject.getString("delivery_phone");//phone
				if (phone.equalsIgnoreCase("null")) {
					phone = "";
				}
				String address = jsonObject.getString("delivery_address");
				if (address.equalsIgnoreCase("null")) {
					address = "";
				}

				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
				String currentDateandTime = sdf.format(new Date());

				ImageLoaderUtils.getInstance(getActivity()).DisplayImage(prof_img_upload + "?" + currentDateandTime, (ImageView) profile.findViewById(R.id.prof_img_upload));
				// VNPResize vnpResize = VNPResize.getInstance();
				// vnpResize.resizeSacle(profile.findViewById(R.id.prof_img_upload),
				// 150, 150);
				// TextView title = (TextView)
				// profile.findViewById(R.id.user_name);
				/*
				 * user_name user_name_yomi biography sex birthday_year hometown
				 * job history1 history2 history3 user_url1 user_url2 user_url3
				 * mail_delivery_flg delivery_name delivery_zip1
				 * delivery_address delivery_phone
				 */

				((TextView) profile.findViewById(R.id.user_name)).setText(user_name);
				((TextView) profile.findViewById(R.id.user_name_yomi)).setText(user_name_yomi);
				((TextView) profile.findViewById(R.id.biography)).setText(biography);
				// ((TextView) profile.findViewById(R.id.sex)).setText(sex);

				RadioButton sex_male;
				RadioButton sex_female;
				sex_male = (RadioButton) profile.findViewById(R.id.sex_male);
				sex_female = (RadioButton) profile.findViewById(R.id.sex_female);
				if (sex.equalsIgnoreCase(getResources().getString(R.string.profile_sex_man))) {
					sex_male.setChecked(true);
					sex_female.setChecked(false);
					sex_male.setBackgroundResource(R.drawable.radio_button_check);
					sex_female.setBackgroundResource(R.drawable.radio_button_uncheck);
				} else {
					sex_male.setChecked(false);
					sex_female.setChecked(true);
					sex_male.setBackgroundResource(R.drawable.radio_button_uncheck);
					sex_female.setBackgroundResource(R.drawable.radio_button_check);
				}

//				((TextView) profile.findViewById(R.id.birthday_year)).setText(birthday_year);
				if(!birthday_year.equalsIgnoreCase("")){
					for(int i = 0; i < listyear.size(); i++){
						if(birthday_year.equalsIgnoreCase(listyear.get(i))){
							spinner_year.setSelection(i);
						}
					}
				}
				((TextView) profile.findViewById(R.id.hometown)).setText(hometown);
				((TextView) profile.findViewById(R.id.job)).setText(job);
				((TextView) profile.findViewById(R.id.history1)).setText(history1);
				((TextView) profile.findViewById(R.id.history2)).setText(history2);
				((TextView) profile.findViewById(R.id.history3)).setText(history3);
				((TextView) profile.findViewById(R.id.user_url1)).setText(user_url1);
				((TextView) profile.findViewById(R.id.user_url2)).setText(user_url2);
				((TextView) profile.findViewById(R.id.user_url3)).setText(user_url3);
				// ((TextView)
				// profile.findViewById(R.id.mail_delivery_flg)).setText(mail_delivery_flg);
				RadioButton mail_delivery_flg_0;
				RadioButton mail_delivery_flg_1;
				mail_delivery_flg_0 = (RadioButton) profile.findViewById(R.id.mail_delivery_flg_0);
				mail_delivery_flg_1 = (RadioButton) profile.findViewById(R.id.mail_delivery_flg_1);
				if (mail_delivery_flg.equalsIgnoreCase("0")) {
					mail_delivery_flg_0.setChecked(true);
					mail_delivery_flg_1.setChecked(false);
					mail_delivery_flg_0.setBackgroundResource(R.drawable.radio_button_check);
					mail_delivery_flg_1.setBackgroundResource(R.drawable.radio_button_uncheck);
				} else {
					mail_delivery_flg_0.setChecked(false);
					mail_delivery_flg_1.setChecked(true);
					mail_delivery_flg_0.setBackgroundResource(R.drawable.radio_button_uncheck);
					mail_delivery_flg_1.setBackgroundResource(R.drawable.radio_button_check);
				}

				((TextView) profile.findViewById(R.id.delivery_name)).setText(name);
				((TextView) profile.findViewById(R.id.delivery_zip1)).setText(zip1);
				((TextView) profile.findViewById(R.id.delivery_zip2)).setText(zip2);
				((TextView) profile.findViewById(R.id.delivery_address)).setText(address);
				((TextView) profile.findViewById(R.id.delivery_phone)).setText(phone);
				getProfile = true;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			Log.e("aaaaaa", "22222222222");
		}
	}

	@Override
	public int layoytResurce() {
		return R.layout.user_profile;
	}

	@Override
	public HeaderOption getHeaderOption() {
		TYPEHEADER type = TYPEHEADER.NORMAL;
		HeaderOption headerOption = new HeaderOption(getActivity(), type) {
			@Override
			public void onClickButtonLeft() {
				getActivity().setResult(Activity.RESULT_OK);
				getActivity().finish();
			}

			@Override
			public void onClickButtonRight() {
				((BaseActivity) getActivity()).showMenuLeft(true);
			}
		};
		headerOption.setShowButtonLeft(true);
		headerOption.setShowButtonRight(true);
		headerOption.setResHeader(R.string.header_profile);
		headerOption.setResDrawableLeft(R.drawable.back_xml);
		headerOption.setResDrawableRight(R.drawable.menu_xml);
		return headerOption;
	}

	private Button button;

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.prof_img_upload && getProfile) {
			final String[] items = new String[] { getResources().getString(R.string.profile_changeimg_from_camera), getResources().getString(R.string.profile_changeimg_from_gallery) };
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_item, items);
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

			// builder.setTitle("Select Image");
			builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int item) { // pick
																		// from
																		// camera
					if (item == 0) {
						Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

						ProfileActivity.mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "tmp_avatar_" + String.valueOf(System.currentTimeMillis()) + ".jpg"));

						intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, ProfileActivity.mImageCaptureUri);
						AccountDB profile_imginfo = new AccountDB(getActivity());
						Uri fileUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "tmp_avatar_" + String.valueOf(System.currentTimeMillis()) + ".jpg"));
						profile_imginfo.save("img_profile", fileUri.toString());
						try {
							intent.putExtra("return-data", true);

							getActivity().startActivityForResult(intent, ProfileActivity.PICK_FROM_CAMERA);
						} catch (ActivityNotFoundException e) {
							e.printStackTrace();
						}

					} else {
						// Intent intent = new Intent();
						//
						// intent.setType("image/*");
						// intent.setAction(Intent.ACTION_GET_CONTENT);
						//
						// getActivity().startActivityForResult(Intent.createChooser(intent,
						// "Complete action using"),
						// ProfileActivity.PICK_FROM_FILE);

						Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
						getActivity().startActivityForResult(i, ProfileActivity.PICK_FROM_FILE);
					}
				}
			});

			final AlertDialog dialog = builder.create();
			dialog.show();
		} else if (v.getId() == R.id.profile_user_btnupdateprofile && getProfile) {
			final String token = token_user;
			final String user_name = getTextStr(R.id.user_name);
			final String user_name_yomi = getTextStr(R.id.user_name_yomi);
			final String biography = getTextStr(R.id.biography);
			String sex = "";// getTextStr(R.id.sex);
//			final String birthday_year = getTextStr(R.id.birthday_year);
			final String hometown = getTextStr(R.id.hometown);
			final String job = getTextStr(R.id.job);
			final String history1 = getTextStr(R.id.history1);
			final String history2 = getTextStr(R.id.history2);
			final String history3 = getTextStr(R.id.history3);
			final String user_url1 = getTextStr(R.id.user_url1);
			final String user_url2 = getTextStr(R.id.user_url2);
			final String user_url3 = getTextStr(R.id.user_url3);
			String mail_delivery_flg = "";// getTextStr(R.id.mail_delivery_flg);
			final String delivery_name = getTextStr(R.id.delivery_name);
			final String delivery_zip1 = getTextStr(R.id.delivery_zip1);
			final String delivery_zip2 = getTextStr(R.id.delivery_zip2);
			final String delivery_address = getTextStr(R.id.delivery_address);
			final String delivery_phone = getTextStr(R.id.delivery_phone);

			RadioButton sex_male;
			sex_male = (RadioButton) profile.findViewById(R.id.sex_male);
			RadioButton mail_delivery_flg_0;
			mail_delivery_flg_0 = (RadioButton) profile.findViewById(R.id.mail_delivery_flg_0);

			if (sex_male.isChecked()) {
				sex = getResources().getString(R.string.profile_sex_man);
			} else {
				sex = getResources().getString(R.string.profile_sex_woman);
			}
			if (mail_delivery_flg_0.isChecked()) {
				mail_delivery_flg = "0";
			} else {
				mail_delivery_flg = "1";
			}

			Map<String, String> sendData = new HashMap<String, String>();
			sendData.put("token", token);
			sendData.put("user_name", user_name);
			sendData.put("user_name_yomi", user_name_yomi);
			sendData.put("biography", biography);
			sendData.put("sex", sex);
			sendData.put("birthday_year", birthday_year);
			sendData.put("hometown", hometown);
			sendData.put("job", job);
			sendData.put("history1", history1);
			sendData.put("history2", history2);
			sendData.put("history3", history3);
			sendData.put("user_url1", user_url1);
			sendData.put("user_url2", user_url2);
			sendData.put("user_url3", user_url3);
			sendData.put("mail_delivery_flg", mail_delivery_flg);
			sendData.put("delivery_name", delivery_name);
			sendData.put("delivery_zip1", delivery_zip1);
			sendData.put("delivery_zip2", delivery_zip2);
			sendData.put("delivery_address", delivery_address);
			sendData.put("delivery_phone", delivery_phone);
			user_name_new = user_name;

			APICaller apiCallerUpload = new APICaller(getActivity());

			ICallbackAPI callbackAPIUpload = new ICallbackAPI() {

				@Override
				public void onSuccess(String response) {
					try {
						JSONObject jsonObject = new JSONObject(response);
//						CommonAndroid.showDialog(getActivity(),"::" +jsonObject.toString().indexOf("\\n") + jsonObject.toString(), null );
						if (jsonObject.getString("status").equals("1")) {
							CommonAndroid.showDialog(getActivity(), getResources().getString(R.string.profile_update_ok), new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									AccountDB accountDB = new AccountDB(getActivity());
									accountDB.saveName(accountDB.getUserId(), user_name_new);
//									accountDB.save("user_name", user_name_new);
								}
							});

						} else {
							String err = jsonObject.getString("message");
							err =  errReplace(err);
							CommonAndroid.showDialog(getActivity(), err.replaceAll("/n", "") , null);
						}
					} catch (Exception e) {
						CommonAndroid.showDialog(getActivity(), e.getMessage() , null);
					}
				}

				@Override
				public void onError(String message) {
					CommonAndroid.showDialog(getActivity(), message + "", null);
				}
			};
			apiCallerUpload.callApi("/user/updateprofile", true, callbackAPIUpload, sendData);
		}

	}
	
	
	public static String errReplace(String string) {
		String message = string;
        try {
			if (string == null || string.length() == 0) {
			    return "\"\"";
			}

			char         c = 0;
			int          i;
			int          len = string.length();
			StringBuilder sb = new StringBuilder(len + 4);
			String       t;
			String		temp = "/";
			for (i = 0; i < len; i += 1) {
			    c = string.charAt(i);
			    switch (c) {
			    case '\\':
			        sb.append('\n');
			        sb.append(temp);
			        break;
			    case '\n':
			        sb.append("\n");
			        break;
			    default:
			        if (c < ' ') {
			            t = "000" + Integer.toHexString(c);
			            sb.append("\\u" + t.substring(t.length() - 4));
			        } else {
			            sb.append(c);
			        }
			    }
			}
			message = sb.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return message;
    }
}
