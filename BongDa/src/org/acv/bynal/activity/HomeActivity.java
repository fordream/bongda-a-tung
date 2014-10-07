package org.acv.bynal.activity;

import org.acv.bynal.main.activity.MainHomeActivity;
import org.acv.bynal.message.MessageActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import app.bynal.woman.R;

import com.acv.libs.base.CommonAndroid;
import com.acv.libs.base.db.AccountDB;
import com.acv.libs.base.menu.MenuItem;
import com.acv.libs.base.tab.BaseTabActivity;
import com.acv.libs.base.test.PushTestActivity;
import com.acv.libs.base.util.ByUtils;

public class HomeActivity extends BaseTabActivity {
	private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String id = intent.getStringExtra("id");
			Intent mIntent = new Intent(HomeActivity.this, MessageActivity.class);
			mIntent.putExtra("id", id);
			startActivityForResult(mIntent, ByUtils.REQUEST_HOME);
		}
	};

	protected void onResume() {
		super.onResume();

		refresh();
		registerReceiver(broadcastReceiver, new IntentFilter(ByUtils.ACTION_HOME_BROADCAST));
	}

	@Override
	protected void onPause() {
		super.onPause();
		unregisterReceiver(broadcastReceiver);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 0
		addTab(MainHomeActivity.class, "Home", "Home", MainHomeActivity.HOME);
		// 1
		addTab(MainHomeActivity.class, "Login", "Login", MainHomeActivity.LOGIN);
		// 2
		addTab(MainHomeActivity.class, "MESSAGE", "MESSAGE", MainHomeActivity.MESSAGE);
		// 3
		addTab(MainHomeActivity.class, "CONTACTUS", "CONTACTUS", MainHomeActivity.CONTACTUS);
		// 4
		addTab(MainHomeActivity.class, "ABOUT", "ABOUT", MainHomeActivity.ABOUT);
		// 5
		addTab(MainHomeActivity.class, "HELP", "HELP", MainHomeActivity.HELP);
		// 6
		addTab(MainHomeActivity.class, "X1", "X1", MainHomeActivity.X1);
		// 7
		addTab(MainHomeActivity.class, "X2", "X2", MainHomeActivity.X2);
		// 8
		addTab(MainHomeActivity.class, "X3" + "", "X3", MainHomeActivity.X3);
		// 9
		addTab(MainHomeActivity.class, "NEWANDALERTS", "NEWANDALERTS", MainHomeActivity.NEWANDALERTS);

		if (GcmBroadcastReceiver.getStatusPush((Context) this) == GcmBroadcastReceiver.NORMAl) {
			CommonAndroid.showDialog(this, getString(R.string.message_push), new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					GcmBroadcastReceiver.saveStatusPush(HomeActivity.this, GcmBroadcastReceiver.ENABLE);
					GcmBroadcastReceiver.register(HomeActivity.this);
				}
			}, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					GcmBroadcastReceiver.saveStatusPush(HomeActivity.this, GcmBroadcastReceiver.CANCEL);
				}
			});
		} else if (GcmBroadcastReceiver.getStatusPush((Context) this) == GcmBroadcastReceiver.ENABLE) {
			GcmBroadcastReceiver.register(HomeActivity.this);
		}

		// new Handler().postDelayed(new Runnable() {
		//
		// @Override
		// public void run() {
		// // ByUtils.sendContact(HomeActivity.this, "111", "Heelo");
		// startActivity(new Intent(HomeActivity.this,
		// PushTestActivity.class));
		//
		// }
		// }, 3000);

		// Intent intent = new Intent("com.google.android.c2dm.intent.RECEIVE");
		// intent.addCategory("app.bynal.woman");
		// intent.putExtra("message",
		// "{\"title\":\"message\",\"description\":\"description\",\"project_id\":\"11\"}");
		// sendBroadcast(intent);
	}

	@Override
	public void refresh() {
		super.refresh();
		clearMenu();
		configMenuleft();
		refreshMenu();
	}

	@Override
	public void configMenuleft() {
		AccountDB accountDB = new AccountDB(this);
		if (ByUtils.isBlank(accountDB.getToken())) {
			addmenu(new MenuItem(getResources().getString((R.string.menu_home_Login)), R.drawable.menu_home_m1, true));
		} else {
			addmenu(new MenuItem(getResources().getString((R.string.menu_home_profile)), R.drawable.menu_home_m1, true));
		}

		addmenu(new MenuItem(getResources().getString((R.string.menu_home_project_manager)), R.drawable.menu_home_m2, true));
		addmenu(new MenuItem(getResources().getString(R.string.menu_home_message), R.drawable.menu_home_m3, true));
		addmenu(new MenuItem(getResources().getString(R.string.menu_home_news_event), R.drawable.menu_home_m4, true));

		addmenu(new MenuItem(getResources().getString(R.string.menu_home_more), R.drawable.menu_home_m5, true));
		addmenu(new MenuItem(getResources().getString(R.string.menu_home_contact_us), R.drawable.menu_home_m6, false));
		addmenu(new MenuItem(getResources().getString(R.string.menu_home_about), R.drawable.menu_home_m6, false));
		addmenu(new MenuItem(getResources().getString(R.string.menu_home_help), R.drawable.menu_home_m6, false));
		addmenu(new MenuItem(getResources().getString(R.string.menu_home_x1), R.drawable.menu_home_m6, false));
		addmenu(new MenuItem(getResources().getString(R.string.menu_home_x2), R.drawable.menu_home_m6, false));
		addmenu(new MenuItem(getResources().getString(R.string.menu_home_x3), R.drawable.menu_home_m6, false));

		if (!ByUtils.isBlank(accountDB.getToken())) {
			addmenu(new MenuItem(getResources().getString(R.string.menu_home_logout), R.drawable.menu_home_m7, true));
		}
	}

	@Override
	public void onItemClick(int position, MenuItem item) {
		super.onItemClick(position, item);
	}
}