package org.acv.bynal.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.view.View;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.acv.libs.base.BaseFragment;
import com.acv.libs.base.VNPResize;
import com.acv.libs.base.callback.APICaller;
import com.acv.libs.base.callback.APICaller.ICallbackAPI;

import app.bynal.woman.R;

public class HomeFragment extends BaseFragment implements Runnable {
	@Override
	public void run() {

	}

	private com.acv.libs.base.BaseViewPager pagerhostproject;
	private com.acv.libs.base.BaseViewPager2 pagernewproject;
	private TextView textviewhostproject, textviewnewproject;

	@Override
	public void setUpFragment(final View view) {

		pagerhostproject = (com.acv.libs.base.BaseViewPager) view.findViewById(R.id.pagerhostproject);
		pagernewproject = (com.acv.libs.base.BaseViewPager2) view.findViewById(R.id.pagernewproject);
		textviewhostproject = (TextView) view.findViewById(R.id.textviewhostproject);
		textviewnewproject = (TextView) view.findViewById(R.id.textviewnewproject);

		pagerhostproject.post(this);
		Map<String, String> sendData = new HashMap<String, String>();

		APICaller apiCaller = new APICaller(getActivity());
		ICallbackAPI callbackHotAPI = new ICallbackAPI() {
			@Override
			public void onSuccess(String response) {
				List<Object> lfragments = new ArrayList<Object>();
				try {
					JSONObject jsonObject = new JSONObject(response);
					if (jsonObject.getString("status").equals("1")) {
						JSONArray array = jsonObject.getJSONArray("array_data");
						for (int i = 0; i < array.length(); i++) {
							lfragments.add(array.get(i));
						}
					}
				} catch (Exception e) {
				}

				pagerhostproject.setListData(lfragments);

			}

			@Override
			public void onError(String message) {

			}
		};

		apiCaller.callApi("/project/hot", false, callbackHotAPI, sendData);

		ICallbackAPI callbackNewAPI = new ICallbackAPI() {
			@Override
			public void onSuccess(String response) {
				List<Object> lfragments = new ArrayList<Object>();
				try {
					JSONObject jsonObject = new JSONObject(response);
					if (jsonObject.getString("status").equals("1")) {
						JSONArray array = jsonObject.getJSONArray("array_data");
						for (int i = 0; i < array.length(); i++) {
							lfragments.add(array.get(i));
						}
					}
				} catch (Exception e) {
				}
				pagernewproject.setListData(lfragments);
			}

			@Override
			public void onError(String message) {

			}
		};
		sendData = new HashMap<String, String>();
		sendData.put("type", "1");
		sendData.put("value", "1");
		apiCaller.callApi("/project/listByFilter", false, callbackNewAPI, sendData);

	}

	@Override
	public int layoytResurce() {
		return R.layout.home;
	}
}