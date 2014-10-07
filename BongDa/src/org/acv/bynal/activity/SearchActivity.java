package org.acv.bynal.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.acv.bynal.fragment.SearchFragment;
import org.acv.bynal.views.CateAdapter;

import android.R.integer;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.acv.libs.base.BaseActivity;
import com.acv.libs.base.CommonAndroid;
import com.acv.libs.base.menu.MenuItem;
import app.bynal.woman.R;

public class SearchActivity extends BaseActivity {
	public static String TypeSearch = "1";
	public static String ValueSearch = "1";
	SearchFragment project_search;
	public static List<String> listhot = new ArrayList<String>();
	public static List<String> listcat = new ArrayList<String>();
	public static List<String> listtag = new ArrayList<String>();
	public static List<String> listhot_v = new ArrayList<String>();
	public static List<String> listcat_v = new ArrayList<String>();
	public static List<String> listtag_v = new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		configMenuRight();
		project_search = new SearchFragment();
		changeFragemt(project_search);
	}

	ExpandableListAdapter listAdapter;
	ExpandableListView expListView;
	static List<String> listDataHeader;
	static HashMap<String, List<String>> listDataChild;
	public int menu1 = 1;
	public int menu2 = 0;
	public int menu3 = 0;

	@Override
	public void configMenuleft() {
		// addmenu(new MenuItem("cate 1", 0, false));
		findViewById(R.id.menuslide_header).setVisibility(View.GONE);
		findViewById(R.id.menu_main_content).setBackgroundResource(R.drawable.bg_menuright);
		addmenu(new MenuItem(getResources().getString(R.string.menu_search_hot), R.drawable.menu_search_hot , true));
		if(menu1 == 1){
			for(int i = 0; i < listhot.size(); i++){
				addmenu(new MenuItem(listhot.get(i), R.drawable.menu_search_hotsub, false));
			}
		}
		addmenu(new MenuItem(getResources().getString(R.string.menu_search_cat), R.drawable.menu_search_cat , true));
		if(menu2 == 1){
			for(int i = 0; i < listcat.size(); i++){
				addmenu(new MenuItem(listcat.get(i), R.drawable.menu_search_catsub, false));
			}
		}
		addmenu(new MenuItem(getResources().getString(R.string.menu_search_tag), R.drawable.menu_search_tag , true));
		if(menu3 == 1){
			for(int i = 0; i < listtag.size(); i++){
				addmenu(new MenuItem(listtag.get(i), R.drawable.menu_search_tagsub, false));
			}
		}

	}

	@Override
	public void refresh() {
		super.refresh();
		 clearMenu();
		 configMenuleft();
		 refreshMenu();
		 showMenuSubRight(true);
		// changeFragemt(new HomeFragment());
	}

	@Override
	public void onItemClick(int position, MenuItem item) {
		super.onItemClick(position, item);
//		Toast.makeText(this, item.getName() + "item", Toast.LENGTH_SHORT).show();
		String item_click = item.getName(); 
		boolean clicksub = false;
		int position_array = 0;
		if(menu1 == 1){
			if(position != 0 && position != (listhot.size() + 1) && position !=  (listhot.size() + 2)){
				clicksub = true;
				TypeSearch = "1";
				position_array = position - 1;
				if(listhot_v.get(position_array) != null){
					ValueSearch = listhot_v.get(position_array);
				}
//				Toast.makeText(this, ValueSearch + ":value", Toast.LENGTH_SHORT).show();
				
			}
		}else if(menu2 == 1){
			if(position != 0 && position != 1 && position !=  (listcat.size() + 2)){
				clicksub = true;
				TypeSearch = "2";
				position_array = position - 2;
				if(listcat_v.get(position_array) != null){
					ValueSearch = listcat_v.get(position_array);
				}
//				Toast.makeText(this, ValueSearch + ":value", Toast.LENGTH_SHORT).show();
			}
		}else if(menu3 == 1){
			if(position != 0 && position != 1 && position !=  2){
				clicksub = true;
				TypeSearch = "3";
				position_array = position - 3;
				if(listtag_v.get(position_array) != null){
					ValueSearch = listtag_v.get(position_array);
				}
//				Toast.makeText(this, ValueSearch + ":value", Toast.LENGTH_SHORT).show();
			}
		}
		if(!clicksub){
			if( item_click.equalsIgnoreCase(getResources().getString(R.string.menu_search_hot))){
				TypeSearch = "1";
				menu1 = 1;
				menu2 = 0;
				menu3 = 0;
			}else if(item_click.equalsIgnoreCase(getResources().getString(R.string.menu_search_cat))){
				TypeSearch = "2";
				menu1 = 0;
				menu2 = 1;
				menu3 = 0;
			}else if(item_click.equalsIgnoreCase(getResources().getString(R.string.menu_search_tag))){
				TypeSearch = "3";
				menu1 = 0;
				menu2 = 0;
				menu3 = 1;
			}
			clearMenu();
			configMenuleft();
			refreshMenu();
			showMenuSubRight(true);
		}else{
			showMenuSubRight(false);
			project_search.CallAPISearch();
		}
		
	}

}
