package com.acv.libs.base.util;

import org.acv.bynal.activity.SendContactActivity;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;

import com.acv.libs.base.BaseFragment;
import com.acv.libs.base.BaseItem;

public class ByUtils {
	public static final String BASESERVER = "http://bynalapi.acvdev.com";
	public static final int REQUEST_HOME = 1000;
	public static final int RESPONSE_RELEASETOKEN = 1001;
	public static final int REQUEST_FORGOTPASSWORD = 1002;
	public static final int REQUEST_PROJECTMANAGER = 1003;
	public static final int MESSAGE_GOTOHOME = 1004;
	public static final int RESPONSE_MESSAGE_GOTOHOME = 2005;
	public static final String ACTION_HOME_BROADCAST = "ACTION_HOME_BROADCAST";
	public static final String ACTION_REFRESH_SCREEN_MESSAGE = "ACTION_REFRESH_SCREEN_MESSAGE";

	public static final int RESPONSE_SHARE_CONTACT_GOTOHOME = 2006;
	public static final int REQUEST_SHARE_CONTACT_GOTOHOME = 2007;
	public static final String BASESERVER_REGISTER_PUSH = "http://acvdev.com";
	public static final int REQUEST_PROJECT_DETAIL = 2008;

	public static final String BASEDOMAINSERVER_SHARE_PROJECT = "bynal2.acvdev.com";

	public static boolean isBlank(String str) {
		return str == null || str != null && str.trim().equals("");
	}

	public static boolean isLogin(JSONObject jsonObject) {
		return new BaseItem(jsonObject).getString("is_login").equalsIgnoreCase("true");
	}

	public static final void addCalendar(Context context, long beginTime, long endTime, String title, String desctipmtion) {
		Intent intent = new Intent(Intent.ACTION_EDIT);
		intent.setType("vnd.android.cursor.item/event");

		intent.putExtra("beginTime", beginTime);
		intent.putExtra("endTime", endTime);

		intent.putExtra("allDay", true);
		intent.putExtra("rrule", "FREQ=YEARLY");

		intent.putExtra("title", title);
		intent.putExtra("description", desctipmtion);
		if (context instanceof FragmentActivity)
			((FragmentActivity) context).startActivityForResult(intent, BaseFragment.CALENDAR);
		else if (context instanceof Activity)
			((Activity) context).startActivityForResult(intent, BaseFragment.CALENDAR);
	}

	public static void sendContact(Context context, String id, String titleProject, String url) {
		// CommonAndroid.toast(context, "send contact");
		Intent intent = new Intent(context, SendContactActivity.class);
		intent.putExtra("title", titleProject);
		intent.putExtra("id", id);
		intent.putExtra("url", url);
		if (context instanceof FragmentActivity)
			((FragmentActivity) context).startActivityForResult(intent, REQUEST_SHARE_CONTACT_GOTOHOME);
		else if (context instanceof Activity)
			((Activity) context).startActivityForResult(intent, REQUEST_SHARE_CONTACT_GOTOHOME);
	}

	public static boolean isEmail(String email) {
		String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		Boolean b = email.matches(EMAIL_REGEX);
		return b;
	}
}
