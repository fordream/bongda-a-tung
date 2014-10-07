package org.acv.bynal.views;

import org.acv.bynal.activity.ProjectDetailActivity;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import app.bynal.woman.R;

import com.acv.libs.base.BaseItem;
import com.acv.libs.base.BaseView;
import com.acv.libs.base.ImageLoaderUtils;
import com.acv.libs.base.VNPResize;

public class HomeItemView extends BaseView implements Runnable, OnClickListener {

	public HomeItemView(Context context) {
		super(context);
		init(R.layout.homeitem);
	}

	public HomeItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(R.layout.homeitem);
	}

	@Override
	public void init(int res) {
		super.init(res);
		run();

		findViewById(R.id.home_item_main_mi).setOnClickListener(this);
	}

	@Override
	public void refresh() {
		super.refresh();
		if (getData() instanceof JSONObject) {
			BaseItem baseItem = new BaseItem((JSONObject) getData());
			String img = baseItem.getString("img");
			String title = baseItem.getString("title");
			String total_money_support = baseItem
					.getString("supported");
			String total_date = baseItem.getString("total_date");
			String percent = baseItem.getString("percent");
			ImageLoaderUtils.getInstance(getContext()).DisplayImage(img,
					(ImageView) findViewById(R.id.homeitem_img));

			setText(title, R.id.homeitem_txtname);

			setText(total_money_support, R.id.homeitem_txtname_1);
			setText(percent, R.id.homeitem_txtname_2);
			setText(total_date, R.id.homeitem_txtname_3);
		}
	}

	@Override
	public void run() {
//		VNPResize vnpResize = VNPResize.getInstance();
//		vnpResize.resizeSacle(this, LayoutParams.MATCH_PARENT, 230);
//		vnpResize.resizeSacle(findViewById(R.id.homeitem_img), 300, 230);
//		vnpResize.resizeSacle(findViewById(R.id.homeitem_content_1), 300, 35);
//
//		vnpResize.resizeSacle(findViewById(R.id.homeitem_content_step1), 160,
//				35);
//		vnpResize
//				.resizeSacle(findViewById(R.id.homeitem_content_step2), 70, 35);
//		vnpResize
//				.resizeSacle(findViewById(R.id.homeitem_content_step3), 70, 35);
//
//		vnpResize.resizeSacle(findViewById(R.id.homeitem_img_1), 16, 16);
//		vnpResize.resizeSacle(findViewById(R.id.homeitem_img_2), 16, 16);
//		vnpResize.resizeSacle(findViewById(R.id.homeitem_img_3), 16, 16);
//
//		vnpResize.setTextsize(findViewById(R.id.homeitem_txtname_1), 18);
//		vnpResize.setTextsize(findViewById(R.id.homeitem_txtname_2), 18);
//		vnpResize.setTextsize(findViewById(R.id.homeitem_txtname_3), 18);
//
//		resizeAndTextSize(findViewById(R.id.homeitem_txtname), 300,
//				LayoutParams.WRAP_CONTENT, 25);

	}

	@Override
	public void onClick(View v) {
		if (getData() instanceof JSONObject) {
			BaseItem baseItem = new BaseItem((JSONObject) getData());
			String id = baseItem.getString("id");
			Intent intent = new Intent(getContext(), ProjectDetailActivity.class);
			intent.putExtra("projectIdDetail", Integer.parseInt(id));
			getContext().startActivity(intent );
		}
	}
}
