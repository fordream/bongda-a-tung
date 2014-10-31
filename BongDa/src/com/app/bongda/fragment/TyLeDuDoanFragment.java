package com.app.bongda.fragment;

import org.json.JSONArray;
import org.json.JSONException;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.bongda.R;
import com.app.bongda.base.BaseFragment;
import com.app.bongda.base.ImageLoaderUtils;
import com.app.bongda.callback.APICaller;
import com.app.bongda.callback.APICaller.ICallbackAPI;
import com.app.bongda.model.GiaiDau;
import com.app.bongda.util.ByUtils;
import com.app.bongda.util.CommonAndroid;
import com.app.bongda.view.HeaderView;
import com.app.bongda.view.TyLeView;

public class TyLeDuDoanFragment extends BaseFragment {
	private OnItemClickListener onItemClickListener;
	private GiaiDau giaidau;
	private TyLeView tyLeView1, tyLeView2;
	private TextView TextView01, TextView02;
	/**
	 * name doi nha name doi khach
	 */
	private TextView tyledudoan_name1, tyledudoan_name2;

	/**
	 * image giaidau
	 */

	private ImageView tyledudoan_img;

	public TyLeDuDoanFragment(GiaiDau giaiDau, OnItemClickListener onItemClickListener) {
		super();
		this.onItemClickListener = onItemClickListener;
		this.giaidau = giaiDau;
	}

	@Override
	public int getLayout() {
		return R.layout.tyledudoan;
	}

	@Override
	public void onInitCreateView(View view) {
		/**
		 * init header view
		 */
		HeaderView headerView = (HeaderView) view.findViewById(R.id.headerView1);
		headerView.setTextHeader(R.string.tyledudoan);
		/** init data */

		tyLeView1 = (TyLeView) view.findViewById(R.id.tyLeView1);
		tyLeView2 = (TyLeView) view.findViewById(R.id.tyLeView2);

		TextView01 = (TextView) view.findViewById(R.id.TextView01);
		TextView02 = (TextView) view.findViewById(R.id.TextView02);

		tyledudoan_name1 = (TextView) view.findViewById(R.id.tyledudoan_name1);
		tyledudoan_name2 = (TextView) view.findViewById(R.id.tyledudoan_name2);

		tyledudoan_img = (ImageView) view.findViewById(R.id.tyledudoan_img);

		TextView01.setText("");
		TextView02.setText("");

		tyLeView1.setPer(0);
		tyLeView2.setPer(0);

		tyledudoan_name1.setText("");
		tyledudoan_name2.setText("");
		if (giaidau != null) {
			// ImageLoaderUtils.getInstance(getActivity()).DisplayImage(giaidau.,
			// imageView, bitmap);
		}
	}

	private ICallbackAPI callbackAPI;
	private String rTyLe_DoiNha;
	private String rTyLe_DoiKhach;

	@Override
	public void onInitData() {
		callbackAPI = new ICallbackAPI() {
			@Override
			public void onSuccess(String response) {
				String string_temp = CommonAndroid.parseXMLAction(response);
				if (!string_temp.equalsIgnoreCase("")) {
					try {
						JSONArray jsonarray = new JSONArray(string_temp);
						for (int i = 0; i < jsonarray.length(); i++) {
							// parse
							rTyLe_DoiNha = jsonarray.getJSONObject(i).getString("rTyLe_DoiNha");
							rTyLe_DoiKhach = jsonarray.getJSONObject(i).getString("rTyLe_DoiKhach");

						}

						//CommonAndroid.showDialog(getActivity(), "rTyLe_DoiNha=" + rTyLe_DoiNha + ":rTyLe_DoiKhach=" + rTyLe_DoiKhach, null);
						tyLeView1.setPer(Float.parseFloat(rTyLe_DoiNha) / 100f);
						tyLeView2.setPer(Float.parseFloat(rTyLe_DoiKhach) / 100f);

						TextView01.setText(rTyLe_DoiNha + "%");
						TextView02.setText(rTyLe_DoiKhach + "%");
					} catch (JSONException e) {
					}
				}
			}

			@Override
			public void onError(String message) {
				CommonAndroid.showDialog(getActivity(), "data3err:" + message, null);
				Log.e("ERR", message);
			}
		};
		String matran = giaidau.getId();
		String param = (ByUtils.wsFootBall_Lives_TyLeDuDoan).replace("matran", matran);
		new APICaller(getActivity()).callApi("", true, callbackAPI, param);
	}
}
