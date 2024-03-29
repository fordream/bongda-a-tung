package com.app.bongda.base;

import java.util.ArrayList;
import java.util.List;

import com.app.bongda.R;
import com.app.bongda.util.CommonAndroid;

import android.app.Activity;
import android.app.ActivityGroup;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

public class BaseGroupActivity extends ActivityGroup {
	private boolean canFinish = true;

	// Keep this in a static variable to make it accessible for all the nested
	// activities, lets them manipulate the view
	// public static FirstGroup group;

	public boolean isCanFinish() {
		return canFinish;
	}

	private FrameLayout basegroup_frame;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.basefragmentactivity);

		basegroup_frame = (FrameLayout) findViewById(R.id.basefragmentactivity_main);
	}

	public void setCanFinish(boolean canFinish) {
		this.canFinish = canFinish;
	}

	// Need to keep track of the history if you want the back-button to work
	// properly, don't use this if your activities requires a lot of memory.
	private List<View> history = new ArrayList<View>();

	public void addView(String name, Class<?> activity, Bundle extras) {
		replaceView(createView(name, activity, extras));
	}

	private View createView(String name, Class<?> activity, Bundle extras) {
		Intent intent = new Intent(this, activity);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		if (extras != null) {
			intent.putExtras(extras);
		}

		View view = getLocalActivityManager().startActivity(name, intent)
				.getDecorView();
		return view;
	}

	private void replaceView(View v) {
		// Adds the old one to history
		// Changes this Groups View to the new View.
		// setContentView(v);
		if (basegroup_frame == null) {
			history.add(v);
			setContentView(v);
			CommonAndroid.toast(getContext(), "j1");
		} else {
			if (basegroup_frame.getChildCount() == 0) {
				basegroup_frame.addView(v);
			} else {
				CommonAndroid.toast(getContext(), "jo");
				View parent = basegroup_frame.getChildAt(basegroup_frame.getChildCount() - 1);
				basegroup_frame.addView(v);
				Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.center_to_left);
				Animation animation2 = AnimationUtils.loadAnimation(getContext(), R.anim.right_to_center);
				parent.startAnimation(animation);
				v.startAnimation(animation2);
			}
		}
	}

	public void onBackPressed() {
		if (history.size() > 1) {
			history.remove(history.size() - 1);
			View view = history.get(history.size() - 1);
			if (basegroup_frame == null) {
				setContentView(view);
			} else {
				basegroup_frame
						.removeViewAt(basegroup_frame.getChildCount() - 1);
				// basegroup_frame.addView(view);
			}
		} else {
			if (canFinish) {
				finish();
			} else {
				Activity activity = getParent();
				if (activity != null) {
					activity.onBackPressed();
				}
			}
		}
	}

	protected Context getContext() {
		return this;
	}

	protected Activity getActivity() {
		return this;
	}
}