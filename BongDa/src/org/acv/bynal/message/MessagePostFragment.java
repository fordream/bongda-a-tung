package org.acv.bynal.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import app.bynal.woman.R;

import com.acv.libs.base.BaseAdapter;
import com.acv.libs.base.BaseFragment;
import com.acv.libs.base.BaseItem;
import com.acv.libs.base.BaseView;
import com.acv.libs.base.CommonAndroid;
import com.acv.libs.base.HeaderOption;
import com.acv.libs.base.ImageLoaderUtils;
import com.acv.libs.base.callback.APICaller;
import com.acv.libs.base.callback.APICaller.ICallbackAPI;
import com.acv.libs.base.db.AccountDB;
import com.acv.libs.base.util.ByUtils;

public class MessagePostFragment extends BaseFragment {
	private ListView messagepost_listview;
	private BaseAdapter baseAdapter;

	public MessagePostFragment() {
		super();
	}

	@Override
	public void setUpFragment(View view) {
		messagepost_listview = (ListView) view
				.findViewById(R.id.messagepost_listview);
		messagepost_listview.setAdapter((baseAdapter = new BaseAdapter(
				getActivity(), new ArrayList<Object>()) {

			@Override
			public BaseView getView(Context context, Object data) {
				return new PostMessageItemView(context);
			}
		}));

		view.findViewById(R.id.messagepost_footer_btn).setOnClickListener(this);
		loadPost();
	}

	private void loadPost() {
		String id = getData().toString();
		APICaller apiCaller = new APICaller(getActivity());
		Map<String, String> sendData = new HashMap<String, String>();
		sendData.put("user_id", id);
		sendData.put("token", new AccountDB(getActivity()).getToken());
		ICallbackAPI callbackAPI = new ICallbackAPI() {
			@Override
			public void onSuccess(String response) {
				updateScreen(false, response);
			}

			@Override
			public void onError(String message) {
				CommonAndroid.showDialog(getActivity(), message + "", null);
			}
		};
		apiCaller.callApi("/messages/post", true, callbackAPI, sendData);
	}

	@Override
	public void onResume() {
		super.onResume();

		IntentFilter filter = new IntentFilter(
				ByUtils.ACTION_REFRESH_SCREEN_MESSAGE);
		getActivity().registerReceiver(broadcastReceiver, filter);
		
	}

	private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			loadPost();
		}

	};

	@Override
	public void onPause() {
		super.onPause();
		getActivity().unregisterReceiver(broadcastReceiver);
	}

	@Override
	public int layoytResurce() {
		return R.layout.messagepost;
	}

	@Override
	public void onClick(View v) {
		APICaller apiCaller = new APICaller(getActivity());
		Map<String, String> sendData = new HashMap<String, String>();
		sendData.put("user_id", getData().toString());
		sendData.put("message", getTextStr(R.id.messagepost_footer_text));
		sendData.put("token", new AccountDB(getActivity()).getToken());
		ICallbackAPI callbackAPI = new ICallbackAPI() {
			@Override
			public void onSuccess(String response) {
				updateScreen(true, response);
			}

			@Override
			public void onError(String message) {
				CommonAndroid.showDialog(getActivity(), message + "", null);
			}
		};

		apiCaller.callApi("/messages/post", true, callbackAPI, sendData);
		setTextStr(R.id.messagepost_footer_text, "");
	}

	private void updateScreen(boolean isSendMessage, String response) {
		if (isSendMessage) {

		}

		BaseItem baseItem = new BaseItem(response);
		String status = baseItem.getString("status");

		if ("1".equals(status)) {
			if (baseItem.getObject("array_data") != null) {
				JSONArray jsonArray = (JSONArray) baseItem
						.getObject("array_data");
				List<Object> list = new ArrayList<Object>();

				for (int i = 0; i < jsonArray.length(); i++) {
					try {
						list.add(new BaseItem(jsonArray.getJSONObject(i)));
					} catch (JSONException e) {
					}
				}
				baseAdapter.clear();
				baseAdapter.addAllItems(list);
				baseAdapter.notifyDataSetChanged();
			}
		}
	}

	private class PostMessageItemView extends BaseView {

		public PostMessageItemView(Context context) {
			super(context);
			init(R.layout.postmessageitem);
		}

		@Override
		public void refresh() {
			super.refresh();
			BaseItem baseItem = (BaseItem) getData();
			setTextStr(R.id.postmesage_textview1,
					baseItem.getString("user_name"));
			setTextStr(R.id.postmesage_textview2, baseItem.getString("date"));
			setTextStr(R.id.postmesage_textview3, baseItem.getString("message"));

			ImageLoaderUtils.getInstance(getContext()).DisplayImage(
					baseItem.getString("user_img"),
					(ImageView) findViewById(R.id.postmesage_avatar_img),
					BitmapFactory.decodeResource(getResources(),
							R.drawable.noimg));
			ImageLoaderUtils.getInstance(getContext()).DisplayImage(
					baseItem.getString("user_img"),
					(ImageView) findViewById(R.id.postmesage_avatar_img2),
					BitmapFactory.decodeResource(getResources(),
							R.drawable.noimg));

			String user_id = baseItem.getString("user_id");
			String myId = new AccountDB(getContext()).getUserId();

			if (user_id.equals(myId)) {
				findViewById(R.id.postmesage_avatar1).setVisibility(
						View.VISIBLE);
				findViewById(R.id.postmesage_left).setVisibility(View.VISIBLE);
				findViewById(R.id.postmesage_avatar2).setVisibility(View.GONE);
				findViewById(R.id.postmesage_right).setVisibility(View.GONE);
			} else {
				findViewById(R.id.postmesage_avatar1).setVisibility(View.GONE);
				findViewById(R.id.postmesage_left).setVisibility(View.GONE);

				findViewById(R.id.postmesage_avatar2).setVisibility(
						View.VISIBLE);
				findViewById(R.id.postmesage_right).setVisibility(View.VISIBLE);
			}
		}
	}

	@Override
	public HeaderOption getHeaderOption() {
		HeaderOption headerOption = super.getHeaderOption();
		if (type_message == 1) {
			headerOption.setShowButtonRight(false);
		} else {
			headerOption.setShowButtonRight(true);
		}
		return headerOption;
	}

	private int type_message = 0;

	public void setMessageType(int type_message) {
		this.type_message = type_message;
	}
}