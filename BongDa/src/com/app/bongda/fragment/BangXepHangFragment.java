package com.app.bongda.fragment;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.bongda.R;
import com.app.bongda.base.BaseFragment;
import com.app.bongda.base.ImageLoaderUtils;
import com.app.bongda.callback.APICaller.ICallbackAPI;
import com.app.bongda.model.BangXepHang;
import com.app.bongda.model.GiaiDau;
import com.app.bongda.service.BongDaServiceManager;
import com.app.bongda.util.ByUtils;
import com.app.bongda.util.CommonAndroid;
import com.app.bongda.util.CommonUtil;
import com.app.bongda.view.HeaderView;
import com.app.bongda.view.adapter.BangXepHangAdapter;
import com.vnp.core.datastore.database.CountryTable;

public class BangXepHangFragment extends BaseFragment {
	private BangXepHangAdapter countryAdapter = new BangXepHangAdapter();
	OnItemClickListener onItemClickListener;
	GiaiDau dau;

	public BangXepHangFragment(GiaiDau dau,
			OnItemClickListener onItemClickListener) {
		super();
		this.onItemClickListener = onItemClickListener;
		this.dau = dau;
	}

	@Override
	public int getLayout() {
		return R.layout.bangxephang;
	}

	View mHeader;
	private ProgressBar progressBar;

	@Override
	public void onInitCreateView(View view) {
		progressBar = (ProgressBar) view.findViewById(R.id.progressBar1);
		/**
		 * init header view
		 */
		HeaderView headerView = (HeaderView) view
				.findViewById(R.id.headerView1);
		headerView.setTextHeader(R.string.bangxephang);

		/** init data */
		ListView listView = (ListView) view
				.findViewById(R.id.bangxephang_listview);
		if (mHeader != null) {
			listView.removeHeaderView(mHeader);
		} else {
			mHeader = ((LayoutInflater) view.getContext().getSystemService(
					Context.LAYOUT_INFLATER_SERVICE)).inflate(
					R.layout.bangxephang_item, null);
		}

		listView.addHeaderView(mHeader);

		listView.setOnItemClickListener(onItemClickListener);
		listView.setAdapter(countryAdapter);
		((TextView) view.findViewById(R.id.danhsachgiaidau_txtname))
				.setText(dau.getName());

		String where = String.format("iID_MaQuocGia ='%s'",
				dau.getiID_MaQuocGia());
		String sLogo = null;
		Cursor cursor = BongDaServiceManager.getInstance().getBongDaService()
				.query(new CountryTable().getTableName(), where);
		if (cursor != null && cursor.moveToNext()) {
			sLogo = cursor.getString(cursor.getColumnIndex("sLogo"));
		}

		if (sLogo == null)
			sLogo = dau.sLogoGiai();

		ImageLoaderUtils.getInstance(null).DisplayImage(sLogo,
				(ImageView) view.findViewById(R.id.bangxephang_img_country));
		progressBar.setVisibility(View.VISIBLE);
		view.postDelayed(new Runnable() {

			@Override
			public void run() {
				loadData();
			}
		}, 20);
	}

	private void loadData() {
		ICallbackAPI callbackAPI = new ICallbackAPI() {
			@Override
			public void onSuccess(String response) {
				String string_temp = CommonAndroid.parseXMLAction(response);
				if (!string_temp.equalsIgnoreCase("")) {

					try {
						JSONArray jsonarray = new JSONArray(string_temp);
						for (int i = 0; i < jsonarray.length(); i++) {
							countryAdapter.addItem(new BangXepHang(jsonarray
									.getJSONObject(i).getString("sViTri"),
									jsonarray.getJSONObject(i).getString(
											"sTenDoi"), jsonarray
											.getJSONObject(i).getString(
													"sSoTranDau"), jsonarray
											.getJSONObject(i)
											.getString("sDiem"), jsonarray
											.getJSONObject(i).getString(
													"sSoTranThang"), jsonarray
											.getJSONObject(i).getString(
													"sSoTranHoa"), jsonarray
											.getJSONObject(i).getString(
													"sSoTranThua"), jsonarray
											.getJSONObject(i).getString(
													"sBanThang"), jsonarray
											.getJSONObject(i).getString(
													"sBanThua"), jsonarray
											.getJSONObject(i)
											.getString("sHeSo")));
						}
						countryAdapter.notifyDataSetChanged();
					} catch (JSONException e) {
					}
				}

				progressBar.setVisibility(View.GONE);
			}

			@Override
			public void onError(String message) {
			}
		};
		String maGiaiDau = dau.getId();
		if (maGiaiDau == null) {
			maGiaiDau = "";
		}
		BongDaServiceManager
				.getInstance()
				.getBongDaService()
				.callApi(
						System.currentTimeMillis(),
						callbackAPI,
						ByUtils.wsFootBall_BangXepHang.replace("bangxephangId",
								maGiaiDau));
		countryAdapter.notifyDataSetChanged();
	}

	@Override
	public void onInitData() {

	}
}
