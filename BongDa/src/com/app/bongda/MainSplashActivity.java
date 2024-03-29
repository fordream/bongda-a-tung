package com.app.bongda;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.RotateAnimation;

import com.app.bongda.callback.progress.CountryProgressExecute;
import com.app.bongda.service.BongDaServiceManager;
import com.app.bongda.service.BongDaServiceManager.BongDaServiceManagerListener;
import com.app.bongda.util.ByUtils;
import com.app.bongda.util.CommonAndroid;

public class MainSplashActivity extends Activity {
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
				RotateAnimation animation = new RotateAnimation(start, rotate, pivotX, pivotY);
				animation.setDuration(10);
				ic_logo.startAnimation(animation);
				handler.sendEmptyMessageDelayed(0, 10);
			}
		}

		int rotate = 0;
	};

	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			overridePendingTransition(R.anim.nothing, R.anim.top_to_bot);
			return true;
		}

		return super.onKeyDown(keyCode, event);
	};

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		overridePendingTransition(R.anim.bot_to_top, R.anim.nothing);
		setContentView(R.layout.mainsplash);
		ic_logo = (View) findViewById(R.id.ic_logo);
		handler.sendEmptyMessage(0);

		BongDaServiceManager.getInstance().onResume(new BongDaServiceManagerListener() {

			@Override
			public void onSuccess() {
				BongDaServiceManager.getInstance().getBongDaService().getDBManager().liveScoreHiddenAll();
				onCheckForNetwork();
			}

			@Override
			public void onFail() {

			}

			@Override
			public void onDisconnected() {

			}
		});
		GcmBroadcastReceiver.register(MainSplashActivity.this);
	}
	CountryProgressExecute countryProgressExecute2, countryProgressExecute;
	private void onCheckForNetwork() {
		if (isFinishing()) {
			return;
		}

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

			builder.setNegativeButton(R.string.cancel, new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					finish();
					overridePendingTransition(R.anim.nothing, R.anim.top_to_bot);
				}
			});

			builder.show();
		} else {

			countryProgressExecute = new CountryProgressExecute(null, MainSplashActivity.this, "live") {
				@Override
				public void onProgressStartFail() {
					super.onProgressStartFail();
					if (isFinishing()) {
						return;
					}
					// show dialog and cancel
					Builder builder = new Builder(MainSplashActivity.this);
					builder.setCancelable(false);
					builder.setMessage(R.string.cannotconnecttoserverr);
					builder.setPositiveButton(R.string.ok, new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							finish();
							overridePendingTransition(R.anim.bot_to_top, R.anim.nothing);
						}
					});
					builder.show();
				}

				@Override
				public void onProgressSucess() {
					super.onProgressSucess();

					if (isFinishing()) {
						return;
					}

//					Intent intent = new Intent(MainSplashActivity.this, SplashActivity.class);
//
//					startActivity(intent);
//					finish();
					BongDaServiceManager.getInstance().getBongDaService().callApi(System.currentTimeMillis(), countryProgressExecute2, ByUtils.wsFootBall_Quocgia);
//					overridePendingTransition(R.anim.bot_to_top, R.anim.nothing);

				}
			};
			
			countryProgressExecute2 = new CountryProgressExecute(null, MainSplashActivity.this,"new") {
				@Override
				public void onProgressStartFail() {
					super.onProgressStartFail();
					if (isFinishing()) {
						return;
					}
					// show dialog and cancel
					Builder builder = new Builder(MainSplashActivity.this);
					builder.setCancelable(false);
					builder.setMessage(R.string.cannotconnecttoserverr);
					builder.setPositiveButton(R.string.ok, new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							finish();
							overridePendingTransition(R.anim.bot_to_top, R.anim.nothing);
						}
					});
					builder.show();
				}

				@Override
				public void onProgressSucess() {
					super.onProgressSucess();

					if (isFinishing()) {
						return;
					}

					Intent intent = new Intent(MainSplashActivity.this, SplashActivity.class);

					startActivity(intent);
					finish();
					overridePendingTransition(R.anim.bot_to_top, R.anim.nothing);

				}
			};
			BongDaServiceManager.getInstance().getBongDaService().callApi(System.currentTimeMillis(), countryProgressExecute, ByUtils.wsFootBall_Quocgia_Live);

		}
	}
}