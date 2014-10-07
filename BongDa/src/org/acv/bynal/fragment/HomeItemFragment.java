package org.acv.bynal.fragment;

import org.json.JSONObject;

import android.view.View;
import android.widget.ImageView;

import com.acv.libs.base.BaseFragment;
import com.acv.libs.base.BaseItem;
import com.acv.libs.base.ImageLoaderUtils;
import app.bynal.woman.R;

public class HomeItemFragment extends BaseFragment {

	@Override
	public void setUpFragment(final View view) {

	}

	@Override
	public int layoytResurce() {
		return R.layout.homeitem;
	}

	@Override
	public void onResume() {
		super.onResume();

		if (getData() instanceof JSONObject) {
			BaseItem baseItem = new BaseItem((JSONObject) getData());
			String img = baseItem.getString("img");
			String title = baseItem.getString("title");
			String total_money_support = baseItem
					.getString("total_money_support");
			String total_date = baseItem.getString("total_date");
			String percent = baseItem.getString("percent");
			ImageLoaderUtils.getInstance(getActivity()).DisplayImage(img, (ImageView)getView().findViewById(R.id.homeitem_img));

			setText(title, R.id.homeitem_txtname);
			
			setText(total_money_support, R.id.homeitem_txtname_1);
			setText(percent, R.id.homeitem_txtname_2);
			setText(total_date, R.id.homeitem_txtname_3);
		}
	}
}