package org.acv.bynal.fragment;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import app.bynal.woman.R;

import com.acv.libs.base.BaseFragment;

public class X1Fragment extends BaseFragment {

	public X1Fragment() {
	}

	@Override
	public void setUpFragment(View view) {
		WebView webView = (WebView) view.findViewById(R.id.moew_m1_web);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebChromeClient(new WebChromeClient());
		webView.setWebViewClient(new WebViewClient());
		webView.loadUrl("file:///android_asset/unknow1.html");

		view.findViewById(R.id.moew_m1_email).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent emailIntent = new Intent(Intent.ACTION_SEND);
				emailIntent.setData(Uri.parse("mailto:info@bynal.com"));
				emailIntent.setType("text/plain");
				emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { "info@bynal.com" });
				emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
				emailIntent.putExtra(Intent.EXTRA_TEXT, "");
				getActivity().startActivity(emailIntent);
			}
		});
	}

	@Override
	public int layoytResurce() {
		return R.layout.more_m1;
	}

}
