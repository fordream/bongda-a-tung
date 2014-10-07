package org.acv.bynal.fragment;

import org.acv.bynal.main.activity.MainHomeActivity;

import android.view.View;
import app.bynal.woman.R;

import com.acv.libs.base.BaseFragment;
import com.acv.libs.base.tab.BaseTabActivity;

public class HelpFragment extends BaseFragment {

	public HelpFragment() {
	}

	@Override
	public void setUpFragment(View view) {
		view.findViewById(R.id.helptocontact).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// goto contact
				
				((BaseTabActivity)((MainHomeActivity)getActivity()).getParent()).openContactUs();
			}
		});
	}

	@Override
	public int layoytResurce() {
		return R.layout.help;
	}

}
