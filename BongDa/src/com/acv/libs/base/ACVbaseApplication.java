package com.acv.libs.base;

import android.app.Application;
import android.content.res.AssetManager;
import android.graphics.Typeface;

public class ACVbaseApplication extends Application {
	private ImageLoaderUtils imageLoaderUtils;
	private DataStore dataStore = DataStore.getInstance();
	VNPResize vnpResize = VNPResize.getInstance();

	@Override
	public void onCreate() {
		super.onCreate();
		imageLoaderUtils = ImageLoaderUtils.getInstance(this);
		dataStore.init(this);
		vnpResize.init(this, 320, 0, null, null);

	}

	public void CallAPI() {

	}

	private Typeface typeface, typefaceb;

	public Typeface getTypeface() {
		if (typeface == null) {
			AssetManager assertManager = getAssets();
			typeface = Typeface.createFromAsset(assertManager, "meiryo.ttc");
		}
		return typeface;
	}

	public Typeface getTypefaceBold() {
		if (typefaceb == null) {
			AssetManager assertManager = getAssets();
			typefaceb = Typeface.createFromAsset(assertManager, "meiryob.ttc");
		}
		return typefaceb;
	}
}