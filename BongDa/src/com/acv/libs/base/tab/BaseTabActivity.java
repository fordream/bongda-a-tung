/**
 * 
 */
package com.acv.libs.base.tab;

import java.util.ArrayList;
import java.util.List;

import org.acv.bynal.activity.ProfileActivity;
import org.acv.bynal.activity.ProjectmanagerActivity;
import org.acv.bynal.activity.SearchActivity;
import org.acv.bynal.activity.SplashActivity;
import org.acv.bynal.views.HeaderView;
import org.acv.bynal.views.MenuSlideView.OnItemChooser;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;
import app.bynal.woman.R;

import com.acv.libs.base.BaseFragment;
import com.acv.libs.base.CommonAndroid;
import com.acv.libs.base.HeaderOption;
import com.acv.libs.base.HeaderOption.TYPEHEADER;
import com.acv.libs.base.VNPResize;
import com.acv.libs.base.VNPResize.ICompleteInit;
import com.acv.libs.base.comunicate.FacebookUtils;
import com.acv.libs.base.db.AccountDB;
import com.acv.libs.base.menu.MenuItem;
import com.acv.libs.base.menu.MenuItemView;
import com.acv.libs.base.util.ByUtils;
import com.facebook.model.GraphUser;

/**
 * class base for all activity
 * 
 * @author tvuong1pc
 * 
 */
public abstract class BaseTabActivity extends TabActivity implements OnTabChangeListener, OnItemChooser {
	private class PaneListener implements SlidingPaneLayout.PanelSlideListener {

		@Override
		public void onPanelClosed(View view) {

			// getFragmentManager().findFragmentById(R.id.leftpane)
			// .setHasOptionsMenu(false);
			// getFragmentManager().findFragmentById(R.id.rightpane)
			// .setHasOptionsMenu(true);
		}

		@Override
		public void onPanelOpened(View view) {
			// getFragmentManager().findFragmentById(R.id.leftpane)
			// .setHasOptionsMenu(true);
			// getFragmentManager().findFragmentById(R.id.rightpane)
			// .setHasOptionsMenu(false);
		}

		@Override
		public void onPanelSlide(View view, float arg1) {
		}
	}

	@Override
	public void onBackPressed() {

		// CommonAndroid.toast(this, "DKM");

		if (getTabHost().getCurrentTab() == 0) {
			super.onBackPressed();
		} else {
			getTabHost().setCurrentTab(0);
		}
	}

	private HeaderOption headerOption;
	private DrawerLayout dLayout;
	// SlidingPaneLayout pane;
	private View dList;
	private ListView dListView;
	private MenuAdapter adapter = new MenuAdapter();

	private class MenuAdapter extends BaseAdapter {
		List<MenuItem> items = new ArrayList<MenuItem>();

		@Override
		public int getCount() {
			return items.size();
		}

		public void clear() {
			items.clear();
		}

		public void add(MenuItem item) {
			items.add(item);
		}

		@Override
		public Object getItem(int position) {
			return items.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = new MenuItemView(parent.getContext());
			}

			((MenuItemView) convertView).setData(items.get(position));

			return convertView;
		}
	}

	private static SharedPreferences preferences_shorcut;
	private static final String SHORCUT_DB = "shorcut_db";

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabexampleslide);
		dLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		dLayout.setDrawerListener(new DrawerListener() {

			@Override
			public void onDrawerStateChanged(int arg0) {
				CommonAndroid.hiddenKeyBoard(getActivity());
			}

			@Override
			public void onDrawerSlide(View arg0, float arg1) {
				CommonAndroid.hiddenKeyBoard(getActivity());
			}

			@Override
			public void onDrawerOpened(View arg0) {
				CommonAndroid.hiddenKeyBoard(getActivity());
			}

			@Override
			public void onDrawerClosed(View arg0) {
				CommonAndroid.hiddenKeyBoard(getActivity());
			}
		});
		// pane = (SlidingPaneLayout) findViewById(R.id.sp);
		// pane.setPanelSlideListener(new PaneListener());
		dList = (View) findViewById(R.id.left_drawer);
		dListView = (ListView) findViewById(R.id.left_drawer_list);
		dListView.setAdapter(adapter);
		dListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				MenuItem menuItem = (MenuItem) adapter.getItem(position);
				if (menuItem.getResImgge() == R.drawable.menu_home_m5) {
					return;
				}
				dLayout.closeDrawers();
				// pane.closePane();
				monItemClick(position, (MenuItem) adapter.getItem(position));
			}
		});
		getTabHost().setOnTabChangedListener(this);
		mFrameLayout = (FrameLayout) findViewById(R.id.mFrament);
		// menuSlideView = new MenuSlideView(this, true);
		// mFrameLayout.addView(menuSlideView);
		// menuSlideView.setOnItemChooser(this);

		configMenuleft();
		adapter.notifyDataSetChanged();

		// if (menuSlideView != null) {
		// menuSlideView.notifyDataSetChanged();
		// }

		headerOption = new HeaderOption(this, TYPEHEADER.NORMAL) {
			@Override
			public void onClickButtonLeft() {
				super.onClickButtonLeft();
				showMenuLeft(true);
			}

			@Override
			public void onClickButtonRight() {
				super.onClickButtonRight();
				if (getTabHost().getCurrentTab() == 0) {
					startActivity(new Intent(BaseTabActivity.this, SearchActivity.class));
				} else {
					getTabHost().setCurrentTab(0);
				}
			}
		};

		headerOption.setResHeader(R.string.home);
		headerOption.setResDrawableLeft(R.drawable.menu_xml);
		headerOption.setResDrawableRight(R.drawable.search_xml);
		headerOption.setShowButtonLeft(true);
		headerOption.setShowButtonRight(true);

		getHeaderView().setheaderOption(headerOption);
		preferences_shorcut = getSharedPreferences(SHORCUT_DB, MODE_PRIVATE);
		autoCreateShortCut(preferences_shorcut.getBoolean("create_shorcut", false), getContext());
	}

	protected void monItemClick(int position, MenuItem item) {
		onItemClick(position, item);
	}

	private VNPResize vnpResize = VNPResize.getInstance();

	/**
	 * 
	 * @param context
	 * @param baseWidth
	 * @param baseHeight
	 * @param completeInit
	 * @param textView
	 */
	public void initVNPResize(Context context, int baseWidth, int baseHeight, ICompleteInit completeInit, TextView textView) {
		vnpResize.init(this, baseWidth, baseHeight, completeInit, textView);
	}

	/**
	 * 
	 * @param v
	 * @param width
	 * @param height
	 * @param textSize
	 */
	public void resize(View v, int width, int height, int textSize) {
		vnpResize.resizeSacle(v, width, height);
		vnpResize.setTextsize(v, textSize);
	}

	/**
	 * convert view from resource
	 * 
	 * @param res
	 * @return
	 */
	public <T extends View> T getView(int res) {
		@SuppressWarnings("unchecked")
		T view = (T) findViewById(res);
		return view;
	}

	public void addTab(Class<?> activity, String tabSpect, String indicator, int type) {
		TabHost tabHost = getTabHost();
		TabSpec firstTabSpec = tabHost.newTabSpec(tabSpect);
		Intent intent = new Intent(this, activity);
		intent.putExtra("type", type);
		firstTabSpec.setIndicator(indicator).setContent(intent);
		tabHost.addTab(firstTabSpec);
	}

	public void addTab(Class<?> activity, String tabSpect, View indicator) {
		TabHost tabHost = getTabHost();
		TabSpec firstTabSpec = tabHost.newTabSpec(tabSpect);
		firstTabSpec.setIndicator(indicator).setContent(new Intent(this, activity));
		tabHost.addTab(firstTabSpec);
	}

	protected Context getContext() {
		return this;
	}

	protected Activity getActivity() {
		return this;
	}

	@Override
	public void onTabChanged(String tabId) {
		int tabCurrent = getTabHost().getCurrentTab();

		headerOption.setTypeHeader(tabCurrent == 1 ? TYPEHEADER.CHECKBOX : TYPEHEADER.NORMAL);
		if (tabCurrent == 1) {
			getHeaderView().reloadChooser();
		}

		headerOption.setResDrawableRight(R.drawable.home_xml);
		if (tabCurrent == 0) {
			headerOption.setResHeader(R.string.home);
			headerOption.setResDrawableRight(R.drawable.search_xml);
		} else if (tabCurrent == 2) {
			headerOption.setResHeader(R.string.menu_home_message);
		} else if (tabCurrent == 3) {
			headerOption.setResHeader(R.string.menu_home_contact_us);
		} else if (tabCurrent == 4) {
			headerOption.setResHeader(R.string.menu_home_about);
		} else if (tabCurrent == 5) {
			headerOption.setResHeader(R.string.menu_home_help);
		} else if (tabCurrent == 6) {
			headerOption.setResHeader(R.string.menu_home_x1);
		} else if (tabCurrent == 7) {
			headerOption.setResHeader(R.string.menu_home_x2);
		} else if (tabCurrent == 8) {
			headerOption.setResHeader(R.string.menu_home_x3);
		} else if (tabCurrent == 9) {
			headerOption.setResHeader(R.string.menu_home_news_event);
		}

		getHeaderView().setheaderOption(headerOption);
	}

	/**
	 * 
	 */

	private FrameLayout mFrameLayout;

	// private MenuSlideView menuSlideView;

	public void refresh() {

	}

	public void refreshMenu() {
	}

	public void clearMenu() {
		adapter.clear();
		adapter.notifyDataSetChanged();

		// menuSlideView.clear();
	}

	public void enableMenuLeft() {
	}

	public void setTextheader(int str) {
		HeaderView headerView = (HeaderView) findViewById(R.id.headerView1);
		headerView.setText(str);
	}

	public HeaderView getHeaderView() {
		return (HeaderView) findViewById(R.id.headerView1);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		// CommonAndroid.showDialog(this, requestCode + " : " + resultCode,
		// null);
		if (requestCode == ByUtils.REQUEST_HOME && resultCode == ByUtils.RESPONSE_RELEASETOKEN) {
			// relaseTooken();
			new AccountDB(this).clear();
			configMenuleft();
			refreshMenuAndgotoHome();
		} else if (resultCode == ByUtils.RESPONSE_MESSAGE_GOTOHOME && requestCode == ByUtils.REQUEST_HOME) {
			refreshMenuAndgotoHome();
		}
	}

	/**
	 * add fragment
	 * 
	 */

	// private BaseFragment currBaseFragment;
	// private Handler handler = new Handler();

	public void changeFragemt(final BaseFragment rFragment) {

	}

	/*
	 * MENU LEFT
	 */
	// public void onItemClickmenuLeft(AdapterView<?> parent, View view,
	// int position, long id) {
	//
	// }

	public abstract void configMenuleft();

	public void addmenu(MenuItem item) {
		// if (item != null) {
		// if (menuSlideView != null) {
		// menuSlideView.addmenu(item);
		// }
		// }

		if (item != null && adapter != null) {
			adapter.add(item);
		}
	}

	public void showMenuLeft(boolean show) {
		// if (menuSlideView != null) {
		// menuSlideView.callMenu(MenuSlideView.TIMECALLMENU);
		// }
		dLayout.openDrawer(dList);
		// pane.openPane();
	}

	public void configMenuRight() {
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (dLayout != null)
			dLayout.closeDrawers();
		// if(pane!= null) pane.openPane();
	}

	@Override
	public void onItemClick(int position, MenuItem item) {
		AccountDB accountDB = new AccountDB(getActivity());
		if ((item.getName()).equalsIgnoreCase(getResources().getString(R.string.menu_home_Login))) {
			getTabHost().setCurrentTab(1);
		} else if ((item.getName()).equalsIgnoreCase(getResources().getString(R.string.menu_home_message))) {
			if (ByUtils.isBlank(accountDB.getToken())) {
				if (getTabHost().getCurrentTab() == 1) {
					CommonAndroid.showDialog(getActivity(), getResources().getString(R.string.error_message_pleaselogin), null);
					getHeaderView().reloadChooser();
				} else
					getTabHost().setCurrentTab(1);
			} else {
				getTabHost().setCurrentTab(2);
			}
		} else if ((item.getName()).equalsIgnoreCase(getResources().getString(R.string.menu_home_contact_us))) {
			getTabHost().setCurrentTab(3);
		} else if ((item.getName()).equalsIgnoreCase(getResources().getString(R.string.menu_home_about))) {
			getTabHost().setCurrentTab(4);
		} else if ((item.getName()).equalsIgnoreCase(getResources().getString(R.string.menu_home_help))) {
			getTabHost().setCurrentTab(5);
		} else if ((item.getName()).equalsIgnoreCase(getResources().getString(R.string.menu_home_x1))) {
			getTabHost().setCurrentTab(6);
		} else if ((item.getName()).equalsIgnoreCase(getResources().getString(R.string.menu_home_x2))) {
			getTabHost().setCurrentTab(7);
		} else if ((item.getName()).equalsIgnoreCase(getResources().getString(R.string.menu_home_x3))) {
			getTabHost().setCurrentTab(8);
		} else if ((item.getName()).equalsIgnoreCase(getResources().getString(R.string.menu_home_news_event))) {
			getTabHost().setCurrentTab(9);
		} else if ((item.getName()).equalsIgnoreCase(getResources().getString(R.string.menu_home_logout))) {
			logout();
		} else if ((item.getName()).equalsIgnoreCase(getResources().getString(R.string.menu_home_project_manager))) {
			if (ByUtils.isBlank(accountDB.getToken())) {
				if (getTabHost().getCurrentTab() == 1) {
					CommonAndroid.showDialog(getActivity(), getResources().getString(R.string.error_message_pleaselogin), null);
					getHeaderView().reloadChooser();
				} else
					getTabHost().setCurrentTab(1);
			} else {
				startActivityForResult(new Intent(this, ProjectmanagerActivity.class), ByUtils.REQUEST_HOME);

				// Intent intent = new Intent(this,
				// ProjectmanagerActivity.class);
				// intent.putExtra("projectIdDelivery", "125");
				// startActivityForResult(intent, ByUtils.REQUEST_HOME);
			}
		} else if ((item.getName()).equalsIgnoreCase(getResources().getString((R.string.menu_home_profile)))) {
			startActivityForResult(new Intent(this, ProfileActivity.class), ByUtils.REQUEST_HOME);
		}
	}

	private void logout() {
		new AccountDB(this).clear();
		FacebookUtils facebookUtils = new FacebookUtils(this) {
			@Override
			public void loginFaceSucess(GraphUser user) {
			}
		};

		facebookUtils.logout();
		refreshMenuAndgotoHome();
	}

	/**
	 * facebook
	 */

	/**
	 * twitter
	 */

	public void refreshMenuAndgotoHome() {
		refresh();
		getTabHost().setCurrentTab(0);

	}

	public void relaseTooken() {

		new AccountDB(this).clear();
		refresh();
		getTabHost().setCurrentTab(1);
	}

	public void openContactUs() {
		getTabHost().setCurrentTab(3);
	}

	public static void autoCreateShortCut_old(boolean hasShorcut, Context context) {
		if (!hasShorcut) {
			// Create Shortcut
			final String title = context.getResources().getString(R.string.app_name);
			final int icon = R.drawable.icon;

			final Class<?> cls = SplashActivity.class;

			final Intent shortcutIntent = new Intent();
			shortcutIntent.setClass(context, cls);
			shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			final Intent putShortCutIntent = new Intent();
			putShortCutIntent.putExtra("duplicate", false);
			putShortCutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
			putShortCutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, title);
			putShortCutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(context, icon));
			putShortCutIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
			context.sendBroadcast(putShortCutIntent);
			preferences_shorcut.edit().putBoolean("create_shorcut", true).commit();
		}
	}

	private static void autoCreateShortCut(boolean hasShorcut, Context context) {
		if (!hasShorcut) {
			Intent intentShortcut = createIntent(SplashActivity.class, context);
			intentShortcut.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
			context.sendBroadcast(intentShortcut);
			preferences_shorcut.edit().putBoolean("create_shorcut", true).commit();
		}
	}

	private static Intent createIntent(Class<?> cls, Context context) {

		Intent shortcutIntent = new Intent(Intent.ACTION_MAIN);
		shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		// shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		// -------------------------------------------------------------
		// sam sung 2x
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB && Build.BRAND.equals("samsung")) {
			// sam sung 2.x
			// shortcutIntent.addCategory("android.intent.category.LAUNCHER");
		} else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH && Build.BRAND.equals("samsung")) {
			// sam sung 4.x
			shortcutIntent.addCategory("android.intent.category.LAUNCHER");
			shortcutIntent.setPackage(context.getPackageName());
			shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
		} else {
			// orther
			shortcutIntent.addCategory("android.intent.category.LAUNCHER");
			shortcutIntent.setPackage(context.getPackageName());
		}
		// -------------------------------------------------------
		shortcutIntent.setClass(context, cls);
		Intent intentShortcut = new Intent();
		intentShortcut.putExtra("android.intent.extra.shortcut.INTENT", shortcutIntent);
		String title = context.getResources().getString(R.string.app_name);
		intentShortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, title);
		intentShortcut.putExtra("duplicate", false);

		final int icon = R.drawable.icon;

		intentShortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(context, icon));
		return intentShortcut;
	}
}