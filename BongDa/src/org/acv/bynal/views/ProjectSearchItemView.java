package org.acv.bynal.views;

import org.acv.bynal.fragment.SearchFragment;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import app.bynal.woman.R;

import com.acv.libs.base.BaseItem;
import com.acv.libs.base.BaseView;
import com.acv.libs.base.ImageLoaderUtils;

public class ProjectSearchItemView extends BaseView implements OnClickListener {
	Context context;
	public ProjectSearchItemView(Context context) {
		super(context);
		if(SearchFragment.optionView == 1){
			init(R.layout.searchitem);
		}else{
			init(R.layout.searchitem2);
		}
		
	}

	public ProjectSearchItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		if(SearchFragment.optionView == 1){
			init(R.layout.searchitem);
		}else{
			init(R.layout.searchitem2);
		}
	}

	@Override
	public void init(int res) {
		super.init(res);
	}

	@Override
	public void refresh() {
		super.refresh();
        setText(((BaseItem) getData()).getString("title"),
				R.id.homeitem_txtname);
        setText(((BaseItem) getData()).getString("total_money_support"),
				R.id.homeitem_txtname_1);
        setText(((BaseItem) getData()).getString("total_date"),
				R.id.homeitem_txtname_3);
        setText(((BaseItem) getData()).getString("percent"),
				R.id.homeitem_txtname_2);
        ImageLoaderUtils.getInstance(context).DisplayImage(((BaseItem) getData()).getString("img"),
				(ImageView) findViewById(R.id.homeitem_img));

	}

	@Override
	public void onClick(View v) {
	}

	private SearchFragment searchFragment;

	public void addProjectSearchFragment(SearchFragment searchFragment) {
		this.searchFragment = searchFragment;
	}
}