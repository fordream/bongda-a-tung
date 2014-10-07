package com.acv.libs.base;

import org.acv.bynal.activity.HomeActivity;
import org.acv.bynal.activity.SearchActivity;
import org.acv.bynal.activity.SendContactActivity;
import org.acv.bynal.fragment.ForgotPaswordFragment;
import org.acv.bynal.fragment.HomeFragment;
import org.acv.bynal.fragment.LoginFragment;
import org.acv.bynal.fragment.SearchFragment;
import org.acv.bynal.fragment.ShareContactFragment;
import org.acv.bynal.message.MessageActivity;
import org.acv.bynal.message.MessagePostFragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import app.bynal.woman.R;

import com.acv.libs.base.HeaderOption.TYPEHEADER;
import com.acv.libs.base.util.ByUtils;

public abstract class BaseFragment extends Fragment implements OnClickListener {
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	public void showDialogMessage(String message) {
		CommonAndroid.showDialog(getActivity(), message, null);
	}

	private BaseFragment mparentFragment;
	private VNPResize vnpResize = VNPResize.getInstance();

	public void resizeAndTextSize(View v, int wi, int height, int textHeight) {
		resize(v, wi, height);
		setTextSize(v, textHeight);
	}

	public void resize(View v, int wi, int height) {
		vnpResize.resizeSacle(v, wi, height);
	}

	public void setTextSize(View view, int height) {
		vnpResize.setTextsize(view, height);
	}

	public void setMParentFragment(BaseFragment parentFragment) {
		this.mparentFragment = parentFragment;
	}

	public BaseFragment getMParentFragment() {
		return mparentFragment;
	}

	public void setTextStrHtml(int messagepostFooterText, String string) {
		((TextView) getView().findViewById(messagepostFooterText)).setText(Html
				.fromHtml(string));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (layoytResurce() != 0) {
			View view = inflater.inflate(layoytResurce(), container, false);
			setUpFragment(view);
			return view;
		}

		return super.onCreateView(inflater, container, savedInstanceState);
	}

	public abstract void setUpFragment(View view);

	public abstract int layoytResurce();

	private Object data;

	public void setData(Object object) {
		data = object;
	}

	public Object getData() {
		return data;
	}

	public void setText(String str, int res) {
		((TextView) getView().findViewById(res)).setText(str);
	}

	public HeaderOption getHeaderOption() {
		TYPEHEADER type = TYPEHEADER.NORMAL;
		if (this instanceof LoginFragment) {
			type = TYPEHEADER.CHECKBOX;
		}

		HeaderOption headerOption = new HeaderOption(getActivity(), type) {
			@Override
			public void onClickButtonLeft() {

				CommonAndroid.hiddenKeyBoard(getActivity());
				if (BaseFragment.this instanceof SearchFragment) {
					getActivity().finish();
				} else if (BaseFragment.this instanceof LoginFragment) {
					((BaseActivity) getActivity())
							.changeFragemt(getMParentFragment());
				} else if (BaseFragment.this instanceof ForgotPaswordFragment) {
					getActivity().finish();
				} else if (BaseFragment.this instanceof MessagePostFragment) {
					getActivity().finish();
				}
				if (BaseFragment.this instanceof ShareContactFragment) {
					getActivity().finish();
				} else {
					((BaseActivity) getActivity()).showMenuLeft(true);
				}
			}

			@Override
			public void onClickButtonRight() {
				CommonAndroid.hiddenKeyBoard(getActivity());
				if (BaseFragment.this instanceof SearchFragment) {
					((BaseActivity) getActivity()).showMenuLeft(true);
				} else if (BaseFragment.this instanceof HomeFragment) {
					getActivity().startActivity(
							new Intent(getActivity(), SearchActivity.class));
				} else if (BaseFragment.this instanceof ForgotPaswordFragment) {
					// finish to home
					getActivity().setResult(Activity.RESULT_OK);
					getActivity().finish();
				} else if (BaseFragment.this instanceof MessagePostFragment) {
					//
					((MessageActivity) getActivity()).gotoHome();
				} else if (BaseFragment.this instanceof ShareContactFragment) {
					((SendContactActivity) getActivity()).gotoHome();
				}
			}
		};

		headerOption.setResHeader(R.string.blank);
		headerOption.setResDrawableLeft(R.drawable.menu_xml);
		headerOption.setResDrawableRight(R.drawable.search_xml);
		headerOption.setShowButtonLeft(true);
		headerOption.setShowButtonRight(true);

		if (BaseFragment.this instanceof SearchFragment) {
			headerOption.setResHeader(R.string.search);
			headerOption.setResDrawableLeft(R.drawable.back_xml);
			headerOption.setResDrawableRight(R.drawable.menu_xml);
		}

		if (BaseFragment.this instanceof LoginFragment) {
			headerOption.setShowButtonRight(false);
			headerOption.setResDrawableLeft(R.drawable.back_xml);
		}
		if (BaseFragment.this instanceof HomeFragment) {
			headerOption.setResHeader(R.string.home);
		}
		if (BaseFragment.this instanceof ForgotPaswordFragment) {
			headerOption.setResHeader(R.string.home);
			headerOption.setShowButtonRight(true);
			headerOption.setResDrawableLeft(R.drawable.back_xml);
			headerOption.setResDrawableRight(R.drawable.home_xml);
		}

		if (BaseFragment.this instanceof MessagePostFragment) {
			headerOption.setResHeader(R.string.message);
			headerOption.setShowButtonRight(true);
			headerOption.setResDrawableLeft(R.drawable.back_xml);
			headerOption.setResDrawableRight(R.drawable.home_xml);
		}
		if (this instanceof ShareContactFragment) {
			headerOption.setResHeader(R.string.sharecontact);
			headerOption.setShowButtonRight(true);
			headerOption.setResDrawableLeft(R.drawable.back_xml);
			headerOption.setResDrawableRight(R.drawable.home_xml);
		}

		return headerOption;
	}

	public String getTextStr(int forgotpasswordEdit) {
		return ((TextView) getView().findViewById(forgotpasswordEdit))
				.getText().toString().trim();
	}

	public void setTextStr(int messagepostFooterText, String string) {
		((TextView) getView().findViewById(messagepostFooterText))
				.setText(string);
	}

	public void setChoosePosition(int index) {

	}

	/**
	 * Calendar
	 */

	public static final int CALENDAR = 10212;

	/**
	 * 
	 * @param beginTime
	 * @param endTime
	 * @param title
	 * @param desctipmtion
	 */
	public final void addCalendar(long beginTime, long endTime, String title,
			String desctipmtion) {
		ByUtils.addCalendar(getActivity(), beginTime, endTime, title,
				desctipmtion);
		// Intent intent = new Intent(Intent.ACTION_EDIT);
		// intent.setType("vnd.android.cursor.item/event");
		//
		// intent.putExtra("beginTime", beginTime);
		// intent.putExtra("endTime", endTime);
		//
		// intent.putExtra("allDay", true);
		// intent.putExtra("rrule", "FREQ=YEARLY");
		//
		// intent.putExtra("title", title);
		// intent.putExtra("description", desctipmtion);
		// startActivityForResult(intent, CALENDAR);
	}

	// public void sendContact(String id, String titleProject) {
	// Intent intent = new Intent(getActivity(), SendContactActivity.class);
	// intent.putExtra("title", titleProject);
	// intent.putExtra("id", id);
	// startActivity(intent);
	// }

	public void relaseTooken() {
		if (getActivity().getParent() instanceof HomeActivity) {
			((HomeActivity) getActivity().getParent()).relaseTooken();
		} else if (getActivity() instanceof BaseActivity) {
			getActivity().setResult(ByUtils.RESPONSE_RELEASETOKEN);
			getActivity().finish();
		}
	}
}