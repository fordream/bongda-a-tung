package com.app.bongda.fragment;

import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;

import com.app.bongda.R;
import com.app.bongda.base.BaseFragment;
import com.app.bongda.base.BongDaBaseAdapter;
import com.app.bongda.view.BangXepHangItemView;
import com.app.bongda.view.HeaderView;

public class PhongDoDoiDauFragment extends BaseFragment {
	OnItemClickListener onItemClickListener;

	public PhongDoDoiDauFragment(OnItemClickListener onItemClickListener) {
		super();
		this.onItemClickListener = onItemClickListener;
	}

	private CountryAdapter countryAdapter = new CountryAdapter();

	private class CountryAdapter extends BongDaBaseAdapter {

		@Override
		public int getLayout() {
			return R.layout.phongdodoidau_item;
		}

		@Override
		public void showData(Object item, View convertView) {

		}

	}

	@Override
	public int getLayout() {
		return R.layout.phongdodoidau;
	}
	LinearLayout phongdodoidau_bangephang_listitem;
	@Override
	public void onInitCreateView(View view) {
		/**
		 * init header view
		 */
		HeaderView headerView = (HeaderView) view
				.findViewById(R.id.headerView1);
		headerView.setTextHeader(R.string.xemphongdo);
	
		phongdodoidau_bangephang_listitem = (LinearLayout)view.findViewById(R.id.phongdodoidau_bangephang_listitem);
		phongdodoidau_bangephang_listitem.removeAllViews();
		
	}

	@Override
	public void onInitData() {
		for(int i = 0; i < 6; i ++){
			phongdodoidau_bangephang_listitem.addView(new BangXepHangItemView(getActivity()));
		}
	}
}
