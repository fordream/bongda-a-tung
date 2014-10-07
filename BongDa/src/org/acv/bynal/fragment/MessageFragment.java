package org.acv.bynal.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.acv.bynal.activity.MessageService;
import org.acv.bynal.activity.MessageService.STATUSSERVICE;
import org.acv.bynal.views.MessageItemView;
import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import app.bynal.woman.R;

import com.acv.libs.base.BaseAdapter;
import com.acv.libs.base.BaseFragment;
import com.acv.libs.base.BaseItem;
import com.acv.libs.base.BaseView;
import com.acv.libs.base.CommonAndroid;
import com.acv.libs.base.VNPResize;
import com.acv.libs.base.callback.APICaller;
import com.acv.libs.base.callback.APICaller.ICallbackAPI;
import com.acv.libs.base.db.AccountDB;
import com.acv.libs.base.util.ByUtils;

@SuppressLint("NewApi")
public class MessageFragment extends BaseFragment {
	ListView messagelistview;

	public MessageFragment() {
		super();
	}

	private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			
			STATUSSERVICE status = (STATUSSERVICE) intent
					.getSerializableExtra(MessageService.KEY_STATUS);
			if (status == STATUSSERVICE.ERROR) {
				onError(intent.getStringExtra(MessageService.KEY_MESSAGE));
			} else {
				List<BaseItem> baseItems = new ArrayList<BaseItem>();
				try {
					String response = intent
							.getStringExtra(MessageService.KEY_VALUES);
					JSONObject jsonObject = new JSONObject(response);

					if (jsonObject.getString("status").equals("1")) {
						JSONArray array = jsonObject.getJSONArray("array_data");
						for (int i = 0; i < array.length(); i++) {
							baseItems.add(new BaseItem(array.getJSONObject(i)));
						}
					} else {
						if (!ByUtils.isLogin(jsonObject)) {
							relaseTooken();
						} else {
							onError(jsonObject.getString("message"));
						}
					}

				} catch (Exception e) {
				}

				baseAdapter.clear();
				baseAdapter.addAllItems(baseItems);
				baseAdapter.notifyDataSetChanged();
			}
		}
	};

	private void onError(String message) {
		CommonAndroid.showDialog(getActivity(), message, null);
	}

	@Override
	public void onResume() {
		super.onResume();
		getView().findViewById(R.id.mesage_block).setVisibility(View.GONE);
		getActivity().registerReceiver(broadcastReceiver,
				new IntentFilter(MessageService.ACTION_UPDATE_ROOM_LIST));
		MessageService.updateMessageRoom(getActivity());
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	public void callMessageActivity(String id) {
		Intent intent = new Intent(ByUtils.ACTION_HOME_BROADCAST);
		intent.putExtra("id", id);
		getActivity().sendBroadcast(intent);
	}

	@Override
	public void setUpFragment(final View view) {
		view.findViewById(R.id.mesage_block).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						view.findViewById(R.id.mesage_block).setVisibility(
								View.GONE);
					}
				});
		messagelistview = (ListView) view.findViewById(R.id.messagelistview);
		baseAdapter = new BaseAdapter(getActivity(), new ArrayList<Object>()) {
			@Override
			public BaseView getView(Context context, Object data) {
				final MessageItemView messageItemView = new MessageItemView(
						context);
				messageItemView.addMessageFragment(MessageFragment.this);
				return messageItemView;
			}
		};

		messagelistview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				BaseItem baseItem = (BaseItem) parent
						.getItemAtPosition(position);
				String user_id = baseItem.getString("user_id");

				String block_flg = baseItem.getString("block_flg");
				String spam_flg = baseItem.getString("spam_flg");
				String hide_flg = baseItem.getString("hide_flg");

				if ("0".equals(block_flg) && "0".equals(spam_flg)
						&& "0".equals(hide_flg)) {
					callMessageActivity(user_id);
				}
			}
		});
		messagelistview.setAdapter(baseAdapter);

		// APICaller apiCaller = new APICaller(getActivity());
		// Map<String, String> sendData = new HashMap<String, String>();
		// sendData.put("token", new AccountDB(getActivity()).getToken());
		// ICallbackAPI callbackAPI = new ICallbackAPI() {
		// @Override
		// public void onSuccess(String response) {
		// List<BaseItem> baseItems = new ArrayList<BaseItem>();
		// try {
		// JSONObject jsonObject = new JSONObject(response);
		//
		// if (jsonObject.getString("status").equals("1")) {
		// JSONArray array = jsonObject.getJSONArray("array_data");
		// for (int i = 0; i < array.length(); i++) {
		// baseItems.add(new BaseItem(array.getJSONObject(i)));
		// }
		// } else {
		// if (!ByUtils.isLogin(jsonObject)) {
		// relaseTooken();
		// } else {
		// onError(jsonObject.getString("message"));
		// }
		// }
		//
		// } catch (Exception e) {
		// }
		//
		// baseAdapter.clear();
		// baseAdapter.addAllItems(baseItems);
		// baseAdapter.notifyDataSetChanged();
		// }
		//
		// @Override
		// public void onError(String message) {
		// CommonAndroid.showDialog(getActivity(), message + "", null);
		// }
		// };
		//
		// apiCaller.callApi("/messages/list", true, callbackAPI, sendData);

		// MessageService.updateMessageRoom(getActivity());
	}

	@Override
	public int layoytResurce() {
		return R.layout.message;
	}

	private BaseAdapter baseAdapter;

	public void showController(int top, final BaseItem data) {

		if (top < 0)
			top = 0;

		int height = (int) (25 * VNPResize.getInstance().getScale());
		top = top + height;

		getView().findViewById(R.id.mesage_block).setVisibility(View.VISIBLE);
		View view = getView().findViewById(R.id.mesage_block_content);
		RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view
				.getLayoutParams();

		layoutParams.setMargins((int) (5 * VNPResize.getInstance().getScale()),
				top, 0, 0);

		view.setLayoutParams(layoutParams);

		String block_flg = data.getString("block_flg");
		String spam_flg = data.getString("spam_flg");
		String hide_flg = data.getString("hide_flg");

		if ("0".equals(block_flg)) {
			getView().findViewById(R.id.item1_img).setBackgroundResource(
					R.drawable.message_menu_1);
		} else {
			getView().findViewById(R.id.item1_img).setBackgroundResource(
					R.drawable.message_menu_4);
		}

		if ("0".equals(hide_flg)) {
			getView().findViewById(R.id.item2_img).setBackgroundResource(
					R.drawable.message_menu_2);
		} else {
			getView().findViewById(R.id.item2_img).setBackgroundResource(
					R.drawable.message_menu_4);
		}

		if ("0".equals(spam_flg)) {
			getView().findViewById(R.id.item3_img).setBackgroundResource(
					R.drawable.message_menu_3);
		} else {
			getView().findViewById(R.id.item3_img).setBackgroundResource(
					R.drawable.message_menu_4);
		}

		setTextStr(R.id.item1,
				block_flg.equals("1") ? getString(R.string.block)
						: getString(R.string.unblock));
		setTextStr(R.id.item2, hide_flg.equals("1") ? getString(R.string.hide)
				: getString(R.string.unhide));
		setTextStr(R.id.item3, spam_flg.equals("1") ? getString(R.string.spam)
				: getString(R.string.unspam));

		getView().findViewById(R.id.item1).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						onChange(data, 0);
					}
				});

		getView().findViewById(R.id.item2).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						onChange(data, 1);
					}
				});

		getView().findViewById(R.id.item3).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						onChange(data, 2);
					}

				});
	}

	private void onChange(BaseItem data, int index) {
		getView().findViewById(R.id.mesage_block).setVisibility(View.GONE);
		String block_flg = data.getString("block_flg");
		String spam_flg = data.getString("spam_flg");
		String hide_flg = data.getString("hide_flg");

		Map<String, String> sendData = new HashMap<String, String>();
		sendData.put("token", new AccountDB(getActivity()).getToken());
		sendData.put("user_id", data.getString("user_id"));
		sendData.put("room", data.getString("room_no"));

		// block_flg
		if (index == 0) {
			sendData.put("type", "0");
			sendData.put("value", block_flg.equals("1") ? "0" : "1");
		} else if (index == 1) {
			// spam_flg
			sendData.put("type", "1");
			sendData.put("value", hide_flg.equals("1") ? "0" : "1");
		} else if (index == 2) {
			// spam_flg
			sendData.put("type", "2");
			sendData.put("value", spam_flg.equals("1") ? "0" : "1");
		}

		APICaller apiCaller = new APICaller(getActivity());

		ICallbackAPI callbackAPI = new ICallbackAPI() {
			@Override
			public void onSuccess(String response) {
				// CommonAndroid.showDialog(getActivity(), response, null);
				List<BaseItem> baseItems = new ArrayList<BaseItem>();
				try {
					JSONObject jsonObject = new JSONObject(response);
					if (jsonObject.getString("status").equals("1")) {
						JSONArray array = jsonObject.getJSONArray("array_data");
						for (int i = 0; i < array.length(); i++) {
							baseItems.add(new BaseItem(array.getJSONObject(i)));
						}
					} else {
						onError(jsonObject.getString("message"));
					}

				} catch (Exception e) {
					// onError(e.getMessage());
				}

				baseAdapter.clear();
				baseAdapter.addAllItems(baseItems);
				baseAdapter.notifyDataSetChanged();
			}

			@Override
			public void onError(String message) {
				CommonAndroid.showDialog(getActivity(), message + "", null);
			}
		};

		apiCaller.callApi("/messages/status", true, callbackAPI, sendData);
	}
}