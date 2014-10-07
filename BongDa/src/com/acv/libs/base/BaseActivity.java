package com.acv.libs.base;

import org.acv.bynal.views.HeaderView;
import org.acv.bynal.views.MenuSlideView;
import org.acv.bynal.views.MenuSlideView.OnItemChooser;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import app.bynal.woman.R;

import com.acv.libs.base.comunicate.FacebookUtils;
import com.acv.libs.base.menu.MenuItem;
import com.acv.libs.base.util.ByUtils;
import com.org.social.twitter.TwitterJS;

public abstract class BaseActivity extends FragmentActivity implements
		OnItemChooser {
	private FrameLayout mFrameLayout;
	private MenuSlideView menuSlideView;

	// private FacebookUtils facebookUtils;
	// private TwitterJS twitterJS;

	public void refresh() {

	}

	public void refreshMenu() {
	}

	public void clearMenu() {
		menuSlideView.clear();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.base);
		setContentView(R.layout.base);
		mFrameLayout = (FrameLayout) findViewById(R.id.mFrament);
		menuSlideView = new MenuSlideView(this);
		mFrameLayout.addView(menuSlideView);
		menuSlideView.setOnItemChooser(this);

		configMenuleft();

		if (menuSlideView != null) {
			menuSlideView.notifyDataSetChanged();
		}
	}

	public void enableMenuLeft() {
	}

	public void setTextheader(int str) {
		HeaderView headerView = (HeaderView) findViewById(R.id.headerView1);
		headerView.setText(str);
	}

	public void setTextheader(String str) {
		HeaderView headerView = (HeaderView) findViewById(R.id.headerView1);
		headerView.setText(str);
	}

	public HeaderView getHeaderView() {
		return (HeaderView) findViewById(R.id.headerView1);
	}

	/**
	 * add fragment
	 * 
	 */

	private BaseFragment currBaseFragment;
	private Handler handler = new Handler();

	public void changeFragemt(final BaseFragment rFragment) {
//		handler.postDelayed(new Runnable() {
//
//			@Override
//			public void run() {
				getHeaderView().reloadChooser();
				rFragment.setMParentFragment(currBaseFragment);
				currBaseFragment = rFragment;
				FragmentManager fragmentManager = getSupportFragmentManager();
				FragmentTransaction ft = fragmentManager.beginTransaction();
				ft.replace(R.id.main_content, rFragment);
				ft.commit();

				HeaderOption headerOption = rFragment.getHeaderOption();
				getHeaderView().setheaderOption(headerOption);
				getHeaderView().refresh();
//			}
//		}, MenuSlideView.TIMECALLMENU);

	}

	/*
	 * MENU LEFT
	 */
	public void onItemClickmenuLeft(AdapterView<?> parent, View view,
			int position, long id) {

	}

	public abstract void configMenuleft();

	public void addmenu(MenuItem item) {
		if (item != null) {
			if (menuSlideView != null) {
				menuSlideView.addmenu(item);
			}
		}
	}

	public void showMenuLeft(boolean show) {
		if (menuSlideView != null) {
			menuSlideView.callMenu(MenuSlideView.TIMECALLMENU);
		}
	}

	public void showMenuSubRight(boolean show) {
		menuSlideView.onSubmenu = show;
		if (!show) {
			menuSlideView.callMenu(0);
		}
	}

	public void configMenuRight() {
		menuSlideView.isRight = true;
	}
	
	public void showMenuRight(boolean show) {
		if (!show) {
			menuSlideView.menu_click.setVisibility(View.GONE);
			menuSlideView.menu_list.setVisibility(View.GONE);
			menuSlideView.menu_main.setVisibility(View.GONE);
		}else{
			menuSlideView.menu_click.setVisibility(View.VISIBLE);
			menuSlideView.menu_list.setVisibility(View.VISIBLE);
			menuSlideView.menu_main.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onItemClick(int position, MenuItem item) {

	}

	public void setChoosePosition(int i) {
		if (currBaseFragment != null) {
			currBaseFragment.setChoosePosition(i);
		}
	}

}