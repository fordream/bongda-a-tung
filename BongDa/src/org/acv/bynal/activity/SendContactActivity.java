package org.acv.bynal.activity;

import org.acv.bynal.fragment.ShareContactFragment;

import android.os.Bundle;
import app.bynal.woman.R;

import com.acv.libs.base.BaseActivity;
import com.acv.libs.base.util.ByUtils;

public class SendContactActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ShareContactFragment contactFragment = new ShareContactFragment();
		// setTextheader(R.string.sharecontact);
		contactFragment.setData(new String[] {
				getIntent().getStringExtra("id"),
				getIntent().getStringExtra("title"),
				getIntent().getStringExtra("url")});
		changeFragemt(contactFragment);
	}

	@Override
	public void configMenuleft() {

	}

	public void gotoHome() {

		setResult(ByUtils.RESPONSE_SHARE_CONTACT_GOTOHOME);
		finish();
	}
}