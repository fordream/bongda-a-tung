package com.app.bongda.fragment.create;

import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;

import com.app.bongda.CreateAccountActivity;
import com.app.bongda.R;
import com.app.bongda.base.BaseFragment;
import com.app.bongda.util.CommonAndroid;
import com.app.bongda.view.HeaderView;

public class CreatePhoneFragment extends BaseFragment {
	private EditText edit;

	public CreatePhoneFragment() {
	}

	@Override
	public void onInitCreateView(View view) {
		view.findViewById(R.id.mbutton2).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Builder builder = new Builder(v.getContext());
				builder.setTitle(R.string.xac_nhan_so_dien_thoai);
				builder.setMessage(String.format(v.getContext().getString(R.string.xacnhansodienthoai_comfirm), edit.getText().toString().trim()));
				builder.setCancelable(false);
				builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						((CreateAccountActivity)getActivity()).showXFragment(new CreateAccountConfirmFragment());
					}
				});
				builder.setNegativeButton(R.string.thaydoi, null);
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
		return R.layout.create_new_account;
	}


}
