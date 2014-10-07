package org.acv.bynal.message;

import android.os.Bundle;

import com.acv.libs.base.BaseActivity;
import com.acv.libs.base.db.AccountDB;
import com.acv.libs.base.util.ByUtils;

public class MessageActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);

		AccountDB accountDB = new AccountDB(this);

		if (accountDB.getToken() == null) {
			finish();
		} else {
			String id = getIntent().getStringExtra("id");

			int type_message = getIntent().getIntExtra("type_message", 0);
			MessagePostFragment messagePostFragment = new MessagePostFragment();
			messagePostFragment.setMessageType(type_message);
			messagePostFragment.setData(id);
			changeFragemt(messagePostFragment);
		}
	}

	@Override
	public void configMenuleft() {

	}

	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}

	public void gotoHome() {
		setResult(ByUtils.RESPONSE_MESSAGE_GOTOHOME);
		finish();
	}
}