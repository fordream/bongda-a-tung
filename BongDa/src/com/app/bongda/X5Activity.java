package com.app.bongda;

import com.app.bongda.view.SettingItemView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class X5Activity extends Activity {

	private int res[] = new int[]{
			R.drawable.setting_s1,
			R.drawable.setting_s2,
			R.drawable.setting_s3,
			R.drawable.setting_s4,
			R.drawable.setting_s5,
			R.drawable.setting_s6,
			R.drawable.setting_s7,
			R.drawable.setting_s8
	};

	private int resStr[] = new int[]{
			R.string.setting_txt1,
			R.string.setting_txt2,
			R.string.setting_txt3,
			R.string.setting_txt4,
			R.string.setting_txt5,
			R.string.setting_txt6,
			R.string.setting_txt7,
			R.string.setting_txt8
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.x5);
		
		ListView listView  =(ListView)findViewById(R.id.x5listview);
		
		listView.setAdapter(new BaseAdapter() {
			
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				
				if(convertView == null) convertView = new SettingItemView(parent.getContext());
				((SettingItemView)convertView).updateContent(res[position],resStr[position]);
				return convertView;
			}
			
			@Override
			public long getItemId(int position) {
				return res[position];
			}
			
			@Override
			public Object getItem(int position) {
				return null;
			}
			
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return res.length;
			}
		});
		
	}
}