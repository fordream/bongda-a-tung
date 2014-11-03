package com.app.bongda;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.RotateAnimation;

import com.app.bongda.base.BaseActivtiy;
import com.app.bongda.service.BongDaServiceManager;
import com.app.bongda.service.BongDaServiceManager.BongDaServiceManagerListener;
import com.app.bongda.util.CommonAndroid;

public class MainSplashActivity extends BaseActivtiy {
	private View ic_logo;
	private Handler handler = new Handler() {
		@Override
		public void dispatchMessage(Message msg) {
			super.dispatchMessage(msg);

			if (!isFinishing()) {
				int start = rotate;
				rotate = rotate + 10;
				float pivotX = ic_logo.getWidth() / 2;
				float pivotY = ic_logo.getHeight() / 2;
				RotateAnimation animation = new RotateAnimation(start, rotate,
						pivotX, pivotY);
				animation.setDuration(10);

				ic_logo.startAnimation(animation);

				handler.sendEmptyMessageDelayed(0, 11);
			}
		}

		int rotate = 0;
	};

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		overridePendingTransition(R.anim.bot_to_top, R.anim.nothing);
		setContentView(R.layout.mainsplash);
		ic_logo = (View) findViewById(R.id.ic_logo);

		handler.sendEmptyMessage(0);
		BongDaServiceManager.getInstance().onResume(
				new BongDaServiceManagerListener() {

					@Override
					public void onSuccess() {
						onCheckForNetwork();
					}

					@Override
					public void onFail() {

					}

					@Override
					public void onDisconnected() {

					}
				});

	}

	private void onCheckForNetwork() {
		if (!CommonAndroid.NETWORK.haveConnectTed(this)) {
			Builder builder = new Builder(this);
			builder.setMessage(R.string.check_network);
			builder.setCancelable(false);
			builder.setPositiveButton(R.string.ok, new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					onCheckForNetwork();
				}
			});
			
			builder.show();
		} else {
			BongDaServiceManager.getInstance().getBongDaService().callApi(currentTime, callbackAPI, data);
		}
	}

}