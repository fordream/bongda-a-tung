package org.acv.bynal.main.activity;

import java.util.HashMap;
import java.util.Map;

import org.acv.bynal.activity.GcmBroadcastReceiver;
import org.acv.bynal.dialog.RegisterFacebookTwitterDialog;
import org.acv.bynal.fragment.AboutFragment;
import org.acv.bynal.fragment.ConactUsFragment;
import org.acv.bynal.fragment.HelpFragment;
import org.acv.bynal.fragment.HomeFragment;
import org.acv.bynal.fragment.LoginFragment;
import org.acv.bynal.fragment.MessageFragment;
import org.acv.bynal.fragment.NewAndEventFragment;
import org.acv.bynal.fragment.X1Fragment;
import org.acv.bynal.fragment.X2Fragment;
import org.acv.bynal.fragment.X3Fragment;
import org.acv.bynal.message.MessageActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import app.bynal.woman.R;

import com.acv.libs.base.BaseItem;
import com.acv.libs.base.CommonAndroid;
import com.acv.libs.base.MBaseActivity;
import com.acv.libs.base.callback.APICaller;
import com.acv.libs.base.callback.APICaller.ICallbackAPI;
import com.acv.libs.base.comunicate.FacebookUtils;
import com.acv.libs.base.db.AccountDB;
import com.acv.libs.base.tab.BaseTabActivity;
import com.acv.libs.base.util.ByUtils;
import com.facebook.model.GraphUser;
import com.org.social.twitter.TwitterJS;

public class MainHomeActivity extends MBaseActivity {
	public static final int HOME = 1000;
	public static final int LOGIN = 1001;
	public static final int MESSAGE = 1002;
	public static final int MORE = 1003;
	public static final int CONTACTUS = 1004;
	public static final int ABOUT = 1005;
	public static final int HELP = 1006;
	public static final int X1 = 1007;
	public static final int X2 = 1008;
	public static final int X3 = 1009;
	public static final int NEWANDALERTS = 1010;

	// public static final int REGISTERFACEBOOK = 1011;
	// public static final int REGISTERTWITTER = 1012;

	/**
	 * ORTHER
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		facebookUtils.onActivityResult(requestCode, resultCode, data);
	}
	
	@Override
	public void onBackPressed() {
		getParent().onBackPressed();
		//		super.onBackPressed();
	}

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);

		facebookUtils = new FacebookUtils(this) {
			@Override
			public void loginFaceSucess(final GraphUser user) {
				String id = user.getId();
				APICaller apiCaller = new APICaller(MainHomeActivity.this);
				Map<String, String> sendData = new HashMap<String, String>();
				sendData.put("type", "2");
				sendData.put("id", id);

				ICallbackAPI callbackAPI = new ICallbackAPI() {
					@Override
					public void onSuccess(String response) {
						BaseItem baseItem = new BaseItem(response);
						if (baseItem.getString("status") == null) {
							onError("Can not connect to server");
						} else if ("1".equals(baseItem.getString("status"))) {
							AccountDB accountDB = new AccountDB(
									MainHomeActivity.this);
							accountDB.save(response);
							GcmBroadcastReceiver
									.register(MainHomeActivity.this);
							((BaseTabActivity) getParent())
									.refreshMenuAndgotoHome();
						} else {
							RegisterFacebookTwitterDialog dialog = new RegisterFacebookTwitterDialog(
									MainHomeActivity.this,
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											if (which == 0)
												((BaseTabActivity) getParent())
														.refreshMenuAndgotoHome();
											if (which == 1)
												((BaseTabActivity) getParent())
														.refreshMenuAndgotoHome();
										}
									}, user);
							dialog.show();
						}
					}

					@Override
					public void onError(String message) {
						CommonAndroid.showDialog(MainHomeActivity.this,
								message, null);
					}
				};
				apiCaller.callApi("/user/login", true, callbackAPI, sendData);
			}
		};
		twitterJS = new TwitterJS(this) {
			@Override
			public void loginTwiterSucess() {

				String id = twitterJS.getId();
				APICaller apiCaller = new APICaller(MainHomeActivity.this);
				Map<String, String> sendData = new HashMap<String, String>();
				sendData.put("type", "1");
				sendData.put("id", id);

				ICallbackAPI callbackAPI = new ICallbackAPI() {
					@Override
					public void onSuccess(String response) {
						BaseItem baseItem = new BaseItem(response);
						if (baseItem.getString("status") == null) {
							onError("Can not connect to server");
						} else if ("1".equals(baseItem.getString("status"))) {
							AccountDB accountDB = new AccountDB(
									MainHomeActivity.this);
							accountDB.save(response);
							GcmBroadcastReceiver
									.register(MainHomeActivity.this);
							((BaseTabActivity) getParent())
									.refreshMenuAndgotoHome();
						} else {
							RegisterFacebookTwitterDialog dialog = new RegisterFacebookTwitterDialog(
									MainHomeActivity.this,
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											if (which == 0)
												((BaseTabActivity) getParent())
														.refreshMenuAndgotoHome();
											if (which == 1)
												((BaseTabActivity) getParent())
														.refreshMenuAndgotoHome();
										}
									}, twitterJS.getId(), twitterJS.getName());
							dialog.show();
						}
					}

					@Override
					public void onError(String message) {
						CommonAndroid.showDialog(MainHomeActivity.this,
								message, null);
					}
				};
				apiCaller.callApi("/user/login", true, callbackAPI, sendData);
			}
		};
		int type = getIntent().getIntExtra("type", -1);
		setContentView(R.layout.mainhome);
		if (type == HOME) {
			changeFragemt(new HomeFragment());
		} else if (LOGIN == type) {
			changeFragemt(new LoginFragment());
		} else if (MESSAGE == type) {
			changeFragemt(new MessageFragment());
		} else if (MORE == type) {
			changeFragemt(new ConactUsFragment());
		} else if (type == CONTACTUS) {
			changeFragemt(new ConactUsFragment());
		} else if (type == ABOUT) {
			changeFragemt(new AboutFragment());
		} else if (type == HELP) {
			changeFragemt(new HelpFragment());
		} else if (type == X1) {
			changeFragemt(new X1Fragment());
		} else if (type == X2) {
			changeFragemt(new X2Fragment());
		} else if (type == X3) {
			changeFragemt(new X3Fragment());
		} else if (type == NEWANDALERTS) {
			changeFragemt(new NewAndEventFragment());
		}

	}

	/**
	 * facebook
	 */
	public void loginFacebook() {
		facebookUtils.login();
	}

	/**
	 * twitter
	 */

	public void loginTwitter() {
		twitterJS.login();
	}

	private FacebookUtils facebookUtils;
	private TwitterJS twitterJS;

}