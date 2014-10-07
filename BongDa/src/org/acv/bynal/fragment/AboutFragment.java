package org.acv.bynal.fragment;

import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import app.bynal.woman.R;

import com.acv.libs.base.BaseFragment;

public class AboutFragment extends BaseFragment {

	public AboutFragment() {
	}

	@Override
	public void setUpFragment(View view) {
//		WebView webView = (WebView) view.findViewById(R.id.scroll5);
//		webView.getSettings().setJavaScriptEnabled(true);
//		webView.setWebChromeClient(new WebChromeClient());
//		webView.setWebViewClient(new WebViewClient());
//		webView.loadUrl("file:///android_asset/abount.html");
//
//		view.findViewById(R.id.abount_m1_email).setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//
//			}
//		});
	}

	@Override
	public int layoytResurce() {
		return R.layout.abount2;
	}

}
