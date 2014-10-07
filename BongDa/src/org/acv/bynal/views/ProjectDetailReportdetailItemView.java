package org.acv.bynal.views;

import org.acv.bynal.fragment.ProjectDetailReportdetailFragment;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import app.bynal.woman.R;

import com.acv.libs.base.BaseItem;
import com.acv.libs.base.BaseView;

public class ProjectDetailReportdetailItemView extends BaseView implements OnClickListener {
	public ProjectDetailReportdetailItemView(Context context) {
		super(context);
		init(R.layout.projectdetail_reportdetail_item);
		
	}

	public ProjectDetailReportdetailItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(R.layout.projectdetail_reportdetail_item);
	}

	@Override
	public void init(int res) {
		super.init(res);
//			findViewById(R.id.report_commentcount_txt).setOnClickListener(this);
	}

	@Override
	public void refresh() {
		super.refresh();
		setText(((BaseItem) getData()).getString("key_value") , R.id.report_no_txt);
		setText(((BaseItem) getData()).getString("comment_body"),
				R.id.report_comment_body_txt);
		setText(((BaseItem) getData()).getString("user_name"),
				R.id.report_user_name_txt);
		setText(((BaseItem) getData()).getString("created"),
				R.id.reportdetail_created_txt);
	}

	@Override
	public void onClick(View v) {
		 if (projectDetailReportdetailFragment != null) {
//			 projectDetailReportdetailFragment.actionDeleteTag( v , (BaseItem)getData());
		 }
	}

	private ProjectDetailReportdetailFragment projectDetailReportdetailFragment;

	public void addProjectDetailFragment(ProjectDetailReportdetailFragment projectDetailReportdetailFragment) {
		this.projectDetailReportdetailFragment = projectDetailReportdetailFragment;
	}
}