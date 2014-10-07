package org.acv.bynal.views;

import org.acv.bynal.fragment.ProjectDetailReportFragment;

import android.content.Context;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import app.bynal.woman.R;

import com.acv.libs.base.BaseItem;
import com.acv.libs.base.BaseView;

public class ProjectDetailReportItemView extends BaseView implements OnClickListener {
	public ProjectDetailReportItemView(Context context) {
		super(context);
		init(R.layout.projectdetail_report_item);
		
	}

	public ProjectDetailReportItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(R.layout.projectdetail_report_item);
	}

	@Override
	public void init(int res) {
		super.init(res);
			findViewById(R.id.report_commentcount_txt).setOnClickListener(this);
	}

	@Override
	public void refresh() {
		super.refresh();
		setText(((BaseItem) getData()).getString("report_title"),
				R.id.report_title_txt);
		setText(((BaseItem) getData()).getString("report_body"),
				R.id.report_desc_txt);
		setText(getResources().getString(R.string.report_postdate) + ((BaseItem) getData()).getString("post_date"),
				R.id.report_datepost_txt);
		setText(((BaseItem) getData()).getString("created"),
				R.id.report_datecreate_txt);
		String comment_count_temp = getResources().getString(R.string.report_commentcount) + "(" +((BaseItem) getData()).getString("comment_count") + ")";
//		setText(comment_count_temp,
//				R.id.report_commentcount_txt);
		TextView report_commentcount_txt =(TextView)findViewById(R.id.report_commentcount_txt);
		SpannableString spanString = new SpannableString(comment_count_temp);
		spanString.setSpan(new UnderlineSpan(), 0, spanString.length(), 0);
		report_commentcount_txt.setText(spanString);
		
	}

	@Override
	public void onClick(View v) {
		 if (projectDetailReportFragment != null) {
//			 projectDetailReportFragment.actionDeleteTag( v , (BaseItem)getData());
		 }
	}

	private ProjectDetailReportFragment projectDetailReportFragment;

	public void addProjectManagerFragment(ProjectDetailReportFragment projectDetailReportFragment) {
		this.projectDetailReportFragment = projectDetailReportFragment;
	}
}