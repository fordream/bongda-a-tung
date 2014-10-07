package org.acv.bynal.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import app.bynal.woman.R;

import com.acv.libs.base.BaseAdapter;
import com.acv.libs.base.BaseFragment;
import com.acv.libs.base.BaseItem;
import com.acv.libs.base.BaseView;
import com.acv.libs.base.CommonAndroid;
import com.acv.libs.base.callback.APICaller;
import com.acv.libs.base.callback.APICaller.ICallbackAPI;
import com.google.android.gms.internal.em;

public class ShareContactFragment extends BaseFragment implements ICallbackAPI {

	public ShareContactFragment() {
		super();
	}

	@Override
	public void setUpFragment(View view) {

		view.findViewById(R.id.sharecontact_btn).setOnClickListener(this);

		((ListView) view.findViewById(R.id.sharecontact_list)).setAdapter((baseAdapter = new BaseAdapter(getActivity(), new ArrayList<Object>()) {
			@Override
			public BaseView getView(Context context, Object data) {
				return new ShareContactitemView(context);
			}
		}));

		baseAdapter.addAllItems(getNameEmailDetails());
		baseAdapter.notifyDataSetChanged();
	}

	@Override
	public void onResume() {
		super.onResume();
		String title = ((String[]) getData())[1];
		String url = ((String[]) getData())[2];
		String fotmat = getString(R.string.share_contact_format);
		String str = String.format(fotmat, title, url);

		setTextStr(R.id.sharecontact_title, str);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		String emails = "";
		for (int i = 0; i < baseAdapter.getCount(); i++) {
			ShareContactitem contactitem = (ShareContactitem) baseAdapter.getItem(i);
			if (contactitem.isChecked()) {
				if (emails.equals("")) {
					emails = contactitem.getEmail();
				} else {
					emails = emails + "," + contactitem.getEmail();
				}
			}
		}
		String ortherEmail = getTextStr(R.id.sharecontact_email);
		String ortherTitle = getTextStr(R.id.sharecontact_title);

		if (!ortherEmail.equals("")) {
			if (!emails.equals(""))
				emails = emails + "," + ortherEmail;
			else {
				emails = ortherEmail;
			}
		}

		APICaller apiCaller = new APICaller(getActivity());
		Map<String, String> sendData = new HashMap<String, String>();
		sendData.put("id_project", ((String[]) getData())[0]);

		sendData.put("emails", emails);
		sendData.put("description", ortherTitle);
		apiCaller.callApi("/user/shareContact", true, this, sendData);
	}

	@Override
	public int layoytResurce() {
		return R.layout.sharecontact;
	}

	private BaseAdapter baseAdapter;

	private class ShareContactitemView extends BaseView implements OnCheckedChangeListener, View.OnClickListener {
		@Override
		public void onClick(View v) {
			((CheckBox) findViewById(R.id.itemsharecontact_checkbox)).setChecked(!((CheckBox) findViewById(R.id.itemsharecontact_checkbox)).isChecked());
		}

		public ShareContactitemView(Context context) {
			super(context);
			init(R.layout.itemsharecontact);
			// resizeAndTextSize(findViewById(R.id.itemsharecontact_checkbox),
			// 30,
			// 30, 0);
			// resizeAndTextSize(findViewById(R.id.itemsharecontact_email), 270,
			// 40, 20);
			// resizeAndTextSize(findViewById(R.id.itemsharecontact_main), 270,
			// 40, LayoutParams.WRAP_CONTENT);

			((CheckBox) findViewById(R.id.itemsharecontact_checkbox)).setOnCheckedChangeListener(this);
			findViewById(R.id.itemsharecontact_name).setOnClickListener(this);

			findViewById(R.id.itemsharecontact_email).setOnClickListener(this);

		}

		@Override
		public void refresh() {
			super.refresh();
			((CheckBox) findViewById(R.id.itemsharecontact_checkbox)).setChecked(((ShareContactitem) getData()).isChecked());
			setTextStr(R.id.itemsharecontact_email, ((ShareContactitem) getData()).getEmail());
			setTextStr(R.id.itemsharecontact_name, ((ShareContactitem) getData()).getName());
		}

		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			boolean checked = ((CheckBox) findViewById(R.id.itemsharecontact_checkbox)).isChecked();
			((ShareContactitem) getData()).setChecked(checked);
		}
	}

	private class ShareContactitem {
		private boolean checked;
		private String email;

		public ShareContactitem(String name2, String email2) {
			name = name2;
			email = email2;
		}

		public boolean isChecked() {
			return checked;
		}

		public void setChecked(boolean checked) {
			this.checked = checked;
		}

		public String name;

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	public ArrayList<Object> getNameEmailDetails() {
		ArrayList<Object> names = new ArrayList<Object>();
		ContentResolver cr = getActivity().getContentResolver();
		Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
		if (cur.getCount() > 0) {
			while (cur.moveToNext()) {
				String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
				Cursor cur1 = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", new String[] { id }, null);
				while (cur1.moveToNext()) {
					String name = cur1.getString(cur1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
					Log.e("Name :", name);
					String email = cur1.getString(cur1.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
					Log.e("Email", email);
					if (email != null) {
						names.add(new ShareContactitem(name, email));
					}
				}
				cur1.close();
			}
		}
		return names;
	}

	@Override
	public void onError(String message) {
		CommonAndroid.showDialog(getActivity(), message, null);
	}

	@Override
	public void onSuccess(String response) {
		try {
			BaseItem baseItem = new BaseItem(new JSONObject(response));
			if (baseItem.getString("status").equals("1")) {
				CommonAndroid.showDialog(getActivity(), getString(R.string.send_message_success), new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						getActivity().finish();

					}
				});
			} else {
				onError(baseItem.getString("message"));
			}
		} catch (Exception exception) {
			onError(getString(R.string.error_message_connect_server_fail));
		}
	}
}