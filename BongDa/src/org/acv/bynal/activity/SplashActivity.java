package org.acv.bynal.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.acv.libs.base.VNPResize;
import com.acv.libs.base.VNPResize.ICompleteInit;
import app.bynal.woman.R;

public class SplashActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);

		if (!getIntent().getBooleanExtra("aa", false))
			VNPResize.getInstance().init(this, 320, 0, new ICompleteInit() {

				@Override
				public void complete() {
					if (!isFinishing()) {
						startActivity(new Intent(SplashActivity.this,
								HomeActivity.class));
						finish();
					}
				}
			}, (TextView) findViewById(R.id.splash));
	}

	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}

}
