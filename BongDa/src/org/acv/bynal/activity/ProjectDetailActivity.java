package org.acv.bynal.activity;

import org.acv.bynal.fragment.ProjectDetailAngelFragment;
import org.acv.bynal.fragment.ProjectDetailCommentFragment;
import org.acv.bynal.fragment.ProjectDetailFragment;
import org.acv.bynal.fragment.ProjectDetailInfoFragment;
import org.acv.bynal.fragment.ProjectDetailReportFragment;
import org.acv.bynal.fragment.ProjectDetailTagFragment;
import org.acv.bynal.fragment.SearchFragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.acv.libs.base.BaseActivity;
import com.acv.libs.base.menu.MenuItem;
import app.bynal.woman.R;

public class ProjectDetailActivity extends BaseActivity {
	public static int projectId = 125;
	public static String projectTitle = "";
	public static String projectDesc = "";
	public static int projectTotalDate = -1;
	public static String project_report_no = "";
	public static String projectIdDetailPreview = "0";
	ProjectDetailFragment project_detail;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			//TODO: get projectid
		    projectId = extras.getInt("projectIdDetail");
		    if(extras.getString("projectIdDetailPreview") != null){
		    	projectIdDetailPreview = extras.getString("projectIdDetailPreview");
		    }
		}
		configMenuRight();
		project_detail = new ProjectDetailFragment();
		changeFragemt(project_detail);
//		Toast.makeText(this, "projectId==" + projectId, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void configMenuleft() {
		findViewById(R.id.menuslide_header).setVisibility(View.GONE);
		findViewById(R.id.menu_main_content).setBackgroundResource(
				R.drawable.bg_menuright);
		addmenu(new MenuItem(getResources().getString(
				R.string.menu_project_detail_info), R.drawable.menu_pj_detail_info,
				true));
		addmenu(new MenuItem(getResources().getString(
				R.string.menu_project_detail_tag), R.drawable.menu_pj_detail_tag,
				true));
		addmenu(new MenuItem(getResources().getString(
				R.string.menu_project_detail_report), R.drawable.menu_pj_detail_report,
				true));
		addmenu(new MenuItem(getResources().getString(
				R.string.menu_project_detail_angel), R.drawable.menu_pj_detail_angel,
				true));
		addmenu(new MenuItem(getResources().getString(
				R.string.menu_project_detail_comment), R.drawable.menu_pj_detail_comment,
				true));
	}
	
	/*@Override
	public void refresh() {
		super.refresh();
		 clearMenu();
		 configMenuleft();
		 refreshMenu();
		// changeFragemt(new HomeFragment());
	}*/

	@Override
	public void onItemClick(int position, MenuItem item) {
		super.onItemClick(position, item);
		if ((item.getName()).equalsIgnoreCase(getResources().getString(R.string.menu_project_detail_info))) {
			changeFragemt(new ProjectDetailInfoFragment());
		}else if ((item.getName()).equalsIgnoreCase(getResources().getString(R.string.menu_project_detail_tag))) {
			changeFragemt(new ProjectDetailTagFragment());
		}else if ((item.getName()).equalsIgnoreCase(getResources().getString(R.string.menu_project_detail_report))) {
			changeFragemt(new ProjectDetailReportFragment());
		}else if ((item.getName()).equalsIgnoreCase(getResources().getString(R.string.menu_project_detail_angel))) {
			changeFragemt(new ProjectDetailAngelFragment());
		}else if ((item.getName()).equalsIgnoreCase(getResources().getString(R.string.menu_project_detail_comment))) {
			changeFragemt(new ProjectDetailCommentFragment());
		}
	}

}
