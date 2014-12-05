package com.app.bongda.fragment.create;

import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;

import com.app.bongda.R;
import com.app.bongda.base.BaseFragment;
import com.app.bongda.view.HeaderView;

public class CreateAccountConfirmFragment extends BaseFragment {
	private EditText edit;

	public CreateAccountConfirmFragment() {
	}

	@Override
	public void onInitCreateView(View view) {
		view.findViewById(R.id.mbutton2).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Builder builder = new Builder(v.getContext());
				builder.setMessage(R.string.chuc_mung_dang_ky_thanh_cong);
				builder.setCancelable(false);
				builder.setPositiveButton(R.string.tiep_tuc, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						getActivity().finish();
					}
				});
				builder.create().show();
			
			}
		});
		edit = (EditText) view.findViewById(R.id.edit);
		/**
		 * init header view
		 */
		HeaderView headerView = (HeaderView) view.findViewById(R.id.headerView1);
		headerView.setTextHeader(R.string.create_new_account);
		headerView.hiddenProgressbar();
		headerView.hiddenSetting();

		headerView.setOnClickListenerX(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				getActivity().finish();
			}
		});
	}

	@Override
	public void onInitData() {

	}

	@Override
	public int getLayout() {
		return R.layout.create_new_account_confirm;
	}
}