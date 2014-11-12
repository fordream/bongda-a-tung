package com.app.bongda.fragment;

import org.json.JSONArray;
import org.json.JSONException;

import android.database.Cursor;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.bongda.R;
import com.app.bongda.base.BaseFragment;
import com.app.bongda.base.ImageLoaderUtils;
import com.app.bongda.callback.APICaller;
import com.app.bongda.callback.APICaller.ICallbackAPI;
import com.app.bongda.callback.progress.DanhSachGiaiDauProgressExecute;
import com.app.bongda.model.Country;
import com.app.bongda.model.GiaiDau;
import com.app.bongda.service.BongDaServiceManager;
import com.app.bongda.util.ByUtils;
import com.app.bongda.util.CommonAndroid;
import com.app.bongda.view.HeaderView;
import com.app.bongda.view.adapter.DanhSachGiaiDauAdapter;
import com.app.bongda.view.adapter.cursor.DanhSachGiaiDauCusorAdapter;
import com.vnp.core.datastore.database.GiaiDauTable;

public class DanhSachGiaiDauFragment extends BaseFragment {
	OnItemClickListener onItemClickListener;
	Country country;

	public DanhSachGiaiDauFragment(Country country,
			OnItemClickListener onItemClickListener) {
		super();
		this.onItemClickListener = onItemClickListener;
		this.country = country;
	}

	private DanhSachGiaiDauAdapter countryAdapter = new DanhSachGiaiDauAdapter();

	@Override
	public int getLayout() {
		return R.layout.danhsachgiaidau;
	}

	private ListView listView;
	private ProgressBar progressBar;

	@Override
	public void onInitCreateView(View view) {
		HeaderView headerView = (HeaderView) view
				.findViewById(R.id.headerView1);
		headerView.setTextHeader(R.string.quocgia);
		progressBar = (ProgressBar) view.findViewById(R.id.progressBar1);

		/** init data */
		listView = (ListView) view.findViewById(R.id.danhsachgiaidau_listview);
		listView.setOnItemClickListener(onItemClickListener);
		listView.setAdapter(countryAdapter);

		((TextView) view.findViewById(R.id.danhsachgiaidau_txtname))
				.setText(country.getName());
		String image1 = country.logoCountry();
		ImageLoaderUtils.getInstance(view.getContext()).DisplayImage(image1,
				(ImageView) view.findViewById(R.id.imageView1));
		showData();

		listView.postDelayed(new Runnable() {

			@Override
			public void run() {
				loadData();
			}
		}, 20);
	}

	private DanhSachGiaiDauCusorAdapter cusorAdapter;

	private void showData() {
		String where = String.format("iID_MaQuocGia = '%s'", country.getId());
		Cursor cursor = BongDaServiceManager.getInstance().getBongDaService()
				.query(new GiaiDauTable().getTableName(), where);
		if (cursor != null) {
			progressBar.setVisibility(View.GONE);
			if (cursor.getCount() >= 1) {
				cusorAdapter = new DanhSachGiaiDauCusorAdapter(
						listView.getContext(), cursor, true);
				listView.setAdapter(cusorAdapter);
			}
			
		}else{
			progressBar.setVisibility(View.GONE);
		}
	}

	private void loadData() {
		String country_id = country.getId();
		String ws = (ByUtils.wsFootBall_Giai_Theo_QuocGia_Live).replace("quocgiaid",
				country_id);

		DanhSachGiaiDauProgressExecute danhSachGiaiDauProgressExecute = new DanhSachGiaiDauProgressExecute(
				null, null) {
			@Override
			public void onProgressSucess() {
				super.onProgressSucess();
				String where = String.format("iID_MaQuocGia = '%s'",
						country.getId());
				showData();
			}
		};

		BongDaServiceManager
				.getInstance()
				.getBongDaService()
				.callApi(System.currentTimeMillis(),
						danhSachGiaiDauProgressExecute, ws);
	}

	@Override
	public void onInitData() {

	}
}