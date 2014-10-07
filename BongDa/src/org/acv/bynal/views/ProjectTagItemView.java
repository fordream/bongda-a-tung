package org.acv.bynal.views;

import org.acv.bynal.fragment.ProjectDetailTagFragment;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import app.bynal.woman.R;

import com.acv.libs.base.BaseItem;
import com.acv.libs.base.BaseView;

public class ProjectTagItemView extends BaseView implements OnClickListener {
	public ProjectTagItemView(Context context) {
		super(context);
		init(R.layout.projectdetail_tag_item);
		
	}

	public ProjectTagItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(R.layout.projectdetail_tag_item);
	}

	@Override
	public void init(int res) {
		super.init(res);
			findViewById(R.id.tag_delete).setOnClickListener(this);
	}

	@Override
	public void refresh() {
		super.refresh();
		setText(((BaseItem) getData()).getString("value"),
				R.id.tag_value);
	}

	@Override
	public void onClick(View v) {
		 if (projectDetailTagFragment != null) {
			 projectDetailTagFragment.actionDeleteTag( v , (BaseItem)getData());
		 }
	}

	private ProjectDetailTagFragment projectDetailTagFragment;

	public void addProjectManagerFragment(ProjectDetailTagFragment projectDetailTagFragment) {
		this.projectDetailTagFragment = projectDetailTagFragment;
	}

}