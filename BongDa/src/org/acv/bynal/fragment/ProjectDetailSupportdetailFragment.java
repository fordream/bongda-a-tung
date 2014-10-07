package org.acv.bynal.fragment;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;
import app.bynal.woman.R;

import com.acv.libs.base.BaseActivity;
import com.acv.libs.base.BaseFragment;
import com.acv.libs.base.CommonAndroid;
import com.acv.libs.base.HeaderOption;
import com.acv.libs.base.HeaderOption.TYPEHEADER;
import com.acv.libs.base.db.AccountDB;

public class ProjectDetailSupportdetailFragment extends BaseFragment {

	public ProjectDetailSupportdetailFragment() {
	}

	@Override
	public void setUpFragment(View view) {
		try {
			AccountDB support_info = new AccountDB(getActivity());
			String support_info_value	=	support_info.getData("support_info_value");
			String support_info_desc	=	support_info.getData("support_info_desc");
			String support_info_offer_date	=	support_info.getData("support_info_offer_date");
			((TextView) view.findViewById(R.id.support_detail_value)).setText(support_info_value);
			((TextView) view.findViewById(R.id.support_detail_desc)).setText(support_info_desc);
			((TextView) view.findViewById(R.id.support_detail_date)).setText(support_info_offer_date);

			view.findViewById(R.id.support_detail_btnok).setOnClickListener(this);
			view.findViewById(R.id.support_detail_btcancel).setOnClickListener(this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public int layoytResurce() {
		return R.layout.projectdetail_support_detail;
	}
	
	@Override
	public HeaderOption getHeaderOption() {
		TYPEHEADER type = TYPEHEADER.NORMAL;
		HeaderOption headerOption = new HeaderOption(getActivity(), type) {
			@Override
			public void onClickButtonLeft() {
				((BaseActivity)getActivity()).changeFragemt(new ProjectDetailSupportFragment());
			}

			@Override
			public void onClickButtonRight() {
				getActivity().setResult(Activity.RESULT_OK);
				getActivity().finish();
			}
		};
		headerOption.setShowButtonLeft(true);
		headerOption.setShowButtonRight(true);
		headerOption.setResHeader(R.string.header_project_detail_support_confirm);
		headerOption.setResDrawableLeft(R.drawable.back_xml);
		headerOption.setResDrawableRight(R.drawable.home_xml);
		return headerOption;
	}

	@Override
	public void onClick(View v) {
		int option = v.getId();
		switch (option) {
			case R.id.support_detail_btnok:
				CommonAndroid.showDialog(getActivity(), getResources().getString(R.string.error_message_payment_not_support) , null);
				break;
			case R.id.support_detail_btcancel:
				((BaseActivity)getActivity()).changeFragemt(new ProjectDetailFragment());
				break;
		}
	}
}
