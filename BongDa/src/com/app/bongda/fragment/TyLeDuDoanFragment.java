package com.app.bongda.fragment;

import android.view.View;
import android.widget.AdapterView.OnItemClickListener;

import com.app.bongda.R;
import com.app.bongda.base.BaseFragment;
import com.app.bongda.view.HeaderView;

public class TyLeDuDoanFragment extends BaseFragment {
	OnItemClickListener onItemClickListener;

	public TyLeDuDoanFragment(OnItemClickListener onItemClickListener) {
		super();
		this.onItemClickListener = onItemClickListener;
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
		HeaderView headerView = (HeaderView) view
				.findViewById(R.id.headerView1);
		headerView.setTextHeader(R.string.tyledudoan);
		/** init data */

	}

	@Override
	public void onInitData() {

	}
}
