package org.acv.bynal.fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ListView;
import app.bynal.woman.R;

import com.acv.libs.base.BaseAdapter;
import com.acv.libs.base.BaseFragment;
import com.acv.libs.base.BaseItem;
import com.acv.libs.base.BaseView;
import com.acv.libs.base.callback.APICaller;
import com.acv.libs.base.callback.APICaller.ICallbackAPI;

public class NewAndEventFragment extends BaseFragment implements ICallbackAPI {
	private ListView newandeventListView;

	public NewAndEventFragment() {
		super();
	}

	private void postCallendar(BaseItem data) {
		// CalendarUtils calendarUtils = new CalendarUtils(getActivity());
		// calendarUtils.addCalendarEvent();
		Calendar cal = Calendar.getInstance();
		long beginTime = cal.getTimeInMillis();
		long endTime = cal.getTimeInMillis() + 60 * 60 * 1000;
		addCalendar(beginTime, endTime, data.getString("title"), data.getString("message"));
	}

	@Override
	public void setUpFragment(View view) {
		newandeventListView = (ListView) view.findViewById(R.id.newandevent_listview);
		adapter = new BaseAdapter(getActivity(), new ArrayList<Object>()) {
			@Override
			public BaseView getView(Context context, Object data) {
				return new ItemEventsView(context);
			}
		};
		newandeventListView.setAdapter(adapter);
		APICaller apiCaller = new APICaller(getActivity());
		apiCaller.callApi("/user/eventnews", true, this, new HashMap<String, String>());
	}

	private BaseAdapter adapter;

	@Override
	public int layoytResurce() {
		return R.layout.newandevent;
	}

	@Override
	public void onError(String message) {

	}

	@Override
	public void onSuccess(String response) {
		try {
			List<Object> list = new ArrayList<Object>();

			JSONObject jsonObject = new JSONObject(response);
			if (jsonObject.getString("status").equals("1")) {
				JSONArray array = jsonObject.getJSONArray("data");
				for (int i = 0; i < array.length(); i++) {
					list.add(new BaseItem(array.getJSONObject(i)));
				}
			}

			adapter.clear();
			adapter.addAllItems(list);
			adapter.notifyDataSetChanged();
		} catch (Exception e) {
		}
	}

	private class ItemEventsView extends BaseView implements OnClickListener {

		public ItemEventsView(Context context) {
			super(context);
			init(R.layout.eventitem);
			findViewById(R.id.newevent_event).setOnClickListener(this);
			// resizeAndTextSize(findViewById(R.id.newevent_event), 50, 50, 0);
			// resizeAndTextSize(findViewById(R.id.newevent_news), 50, 20, 0);
			// resizeAndTextSize(findViewById(R.id.newevent_title),
			// LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 20);
			// resizeAndTextSize(findViewById(R.id.newevent_date),
			// LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 20);
			// resizeAndTextSize(findViewById(R.id.newevent_content),
			// LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 18);
		}

		@Override
		public void refresh() {
			super.refresh();
			setTextStr(R.id.newevent_title, ((BaseItem) getData()).getString("title"));
			setTextStr(R.id.newevent_date, ((BaseItem) getData()).getString("time"));
			setTextStrHtml(R.id.newevent_content, ((BaseItem) getData()).getString("message"));

			WebView web = (WebView) findViewById(R.id.newevent_content_web);
			String text = ((BaseItem) getData()).getString("message");
			web.loadDataWithBaseURL(null, text, "text/html", "UTF-8", null);

			if (((BaseItem) getData()).getString("new").equalsIgnoreCase("true")) {
				findViewById(R.id.newevent_news).setVisibility(View.VISIBLE);
			} else {
				findViewById(R.id.newevent_news).setVisibility(View.GONE);
			}
		}

		@Override
		public void onClick(View v) {
			postCallendar((BaseItem) getData());
		}

	}
}
