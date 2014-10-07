package org.acv.bynal.views;

import org.acv.bynal.fragment.ProjectDetailAngelFragment;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import app.bynal.woman.R;

import com.acv.libs.base.BaseItem;
import com.acv.libs.base.BaseView;
import com.acv.libs.base.ImageLoaderUtils;

public class ProjectDetailAngelItemView extends BaseView implements OnClickListener {
	public ProjectDetailAngelItemView(Context context) {
		super(context);
		init(R.layout.projectdetail_angel_item);
		
	}

	public ProjectDetailAngelItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(R.layout.projectdetail_angel_item);
	}

	@Override
	public void init(int res) {
		super.init(res);
	}

	@Override
	public void refresh() {
		super.refresh();
		setText(((BaseItem) getData()).getString("name"),
				R.id.angel_name_txt);
		ImageLoaderUtils.getInstance(getContext()).DisplayImage(((BaseItem) getData()).getString("avatar"),
				(ImageView) findViewById(R.id.angel_img));
	}

	@Override
	public void onClick(View v) {
	}

	private ProjectDetailAngelFragment projectDetailAngelFragment;

	public void addProjectManagerFragment(ProjectDetailAngelFragment projectDetailAngelFragment) {
		this.projectDetailAngelFragment = projectDetailAngelFragment;
	}
}