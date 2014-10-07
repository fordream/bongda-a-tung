package org.acv.bynal.views;

import org.acv.bynal.fragment.ProjectDetailCommentFragment;

import android.content.Context;
import android.text.Html;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import app.bynal.woman.R;

import com.acv.libs.base.BaseItem;
import com.acv.libs.base.BaseView;
import com.acv.libs.base.ImageLoaderUtils;

public class ProjectDetailCommentItemView extends BaseView implements OnClickListener {
	public ProjectDetailCommentItemView(Context context) {
		super(context);
		init(R.layout.projectdetail_comment_item);
		
	}

	public ProjectDetailCommentItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(R.layout.projectdetail_comment_item);
	}

	@Override
	public void init(int res) {
		super.init(res);
	}

	@Override
	public void refresh() {
		super.refresh();
		try {
			String s_name = "<b>"+((BaseItem) getData()).getString("name")+"</b> " + getResources().getString(R.string.project_detail_comment_san) ;
			TextView tv_name = (TextView)findViewById(R.id.comment_name);
			tv_name.setText(Html.fromHtml(s_name));
//			setText(((BaseItem) getData()).getString("name"),
//					R.id.comment_name);
			String created_temp = ((BaseItem) getData()).getString("created");
			String created = created_temp.substring(0, 10);//2014-06-20
			setText(created,
					R.id.comment_created);
			setText(((BaseItem) getData()).getString("comment"),
					R.id.comment_commenttxt);
			ImageLoaderUtils.getInstance(getContext()).DisplayImage(((BaseItem) getData()).getString("avatar"),
						(ImageView) findViewById(R.id.comment_avatar));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void onClick(View v) {
	}

	private ProjectDetailCommentFragment projectDetailCommentFragment;

	public void addProjectManagerFragment(ProjectDetailCommentFragment projectDetailCommentFragment) {
		this.projectDetailCommentFragment = projectDetailCommentFragment;
	}
}