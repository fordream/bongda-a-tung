package com.app.bongda;

import com.app.bongda.view.HeaderView;
import com.app.bongda.view.SettingItemView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class X5Activity extends Activity implements OnItemClickListener {

	private int res[] = new int[] { R.drawable.setting_s1,
			R.drawable.setting_s2, R.drawable.setting_s3,
			R.drawable.setting_s4, R.drawable.setting_s5,
			R.drawable.setting_s6, R.drawable.setting_s7, R.drawable.setting_s8 };

	private int resStr[] = new int[] { R.string.setting_txt1,
			R.string.setting_txt2, R.string.setting_txt3,
			R.string.setting_txt4, R.string.setting_txt5,
			R.string.setting_txt6, R.string.setting_txt7, R.string.setting_txt8 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.x5);

		HeaderView headerView = (HeaderView) findViewById(R.id.headerView1);
		headerView.hiddenProgressbar();
		ListView listView = (ListView) findViewById(R.id.x5listview);

		listView.setAdapter(new BaseAdapter() {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {

				if (convertView == null)
					convertView = new SettingItemView(parent.getContext());
				((SettingItemView) convertView).updateContent(res[position],
						resStr[position]);
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
				return res.length;
			}
		});

		listView.setOnItemClickListener(this);

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		// parent.getItemIdAtPosition(position);
		if (position == 0) {
			SplashActivity.changTab(0, this);
		} else if (position == 1) {
			SplashActivity.changTab(1, this);
		} else if (position == 2) {
			SplashActivity.changTab(2, this);
		} else if (position == 3) {
			SplashActivity.changTab(3, this);
		} else if (position == 4) {
			SplashActivity.changTab(4, this);
		} else if (position == 5) {
			SplashActivity.changTab(5, this);
		} else if (position == 6) {
			SplashActivity.changTab(6, this);
		} else if (position == 7) {
			SplashActivity.changTab(7, this);
		}

	}

	
}