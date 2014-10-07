package org.acv.bynal.views;

import org.acv.bynal.activity.HomeActivity;
import org.acv.bynal.activity.ProjectmanagerActivity;
import org.acv.bynal.fragment.MessageFragment;
import org.acv.bynal.fragment.ProjectManagerDeliveryFragment;
import org.acv.bynal.fragment.ProjectManagerFragment;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import app.bynal.woman.R;

import com.acv.libs.base.BaseItem;
import com.acv.libs.base.BaseView;
import com.acv.libs.base.CommonAndroid;
import com.acv.libs.base.ImageLoaderUtils;
import com.acv.libs.base.ProjectItem;
import com.acv.libs.base.VNPResize;
import com.acv.libs.base.util.ByUtils;

public class DeliveryListItemView extends BaseView implements OnClickListener {
	public DeliveryListItemView(Context context) {
		super(context);
		init(R.layout.delivery_list_item);
		
	}

	public DeliveryListItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(R.layout.delivery_list_item);
	}

	@Override
	public void init(int res) {
		super.init(res);
		findViewById(R.id.send_message).setOnClickListener(this);
	}

	@Override
	public void refresh() {
		super.refresh();
		String amount_temp = getResources().getString(
				R.string.project_delivery_monny) ;
		String zip_temp = getResources().getString(
				R.string.project_delivery_zip) ;
		if(!((BaseItem) getData()).getString("amount").equalsIgnoreCase("null")){
			setText( amount_temp +(((BaseItem) getData()).getString("amount")),
					R.id.amount);
		}else{
			setText(amount_temp + "0", R.id.amount);
		}
		if(!((BaseItem) getData()).getString("name").equalsIgnoreCase("null")){
			setText( ((BaseItem) getData()).getString("name"),
					R.id.delivery_name);
		}else{
			setText("", R.id.delivery_name);
		}
		if(!((BaseItem) getData()).getString("phone").equalsIgnoreCase("null")){
			setText( ((BaseItem) getData()).getString("phone"),
					R.id.delivery_phone);
		}else{
			setText("", R.id.delivery_phone);
		}
		if(!((BaseItem) getData()).getString("zip").equalsIgnoreCase("null")){
			setText( zip_temp +(((BaseItem) getData()).getString("zip")),
					R.id.delivery_zip);
		}else{
			setText(zip_temp + "", R.id.delivery_zip);
		}
		if(!((BaseItem) getData()).getString("address").equalsIgnoreCase("null")){
			setText( ((BaseItem) getData()).getString("address"),
					R.id.delivery_address);
		}else{
			setText("", R.id.delivery_address);
		}
	}

	@Override
	public void onClick(View v) {
//		int id = v.getId();
//		CommonAndroid.showDialog(getContext(), "send message", null);
		 if (projectManagerDeliveryFragment != null) {
			 projectManagerDeliveryFragment.actionProjectDeliverySendMessage( v , (BaseItem)getData());
			 
		 }
	}

	private ProjectManagerDeliveryFragment projectManagerDeliveryFragment;

	public void addProjectManagerFragment(ProjectManagerDeliveryFragment projectManagerDeliveryFragment) {
		this.projectManagerDeliveryFragment = projectManagerDeliveryFragment;
	}
}