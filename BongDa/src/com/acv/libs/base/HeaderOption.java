package com.acv.libs.base;

import android.content.Context;

public class HeaderOption {
	public enum TYPEHEADER {
		NORMAL, CHECKBOX
	}

	private boolean showButtonLeft = false;
	private boolean showButtonRight = false;
	private int resHeader = 0;
	private int resDrawableLeft = 0;
	private int resDrawableRight = 0;
	
	public int getResDrawableLeft() {
		return resDrawableLeft;
	}

	public void setResDrawableLeft(int resDrawableLeft) {
		this.resDrawableLeft = resDrawableLeft;
	}

	public int getResDrawableRight() {
		return resDrawableRight;
	}

	public void setResDrawableRight(int resDrawableRight) {
		this.resDrawableRight = resDrawableRight;
	}

	private Context context;
	private TYPEHEADER typeHeader = TYPEHEADER.NORMAL;

	public TYPEHEADER getTypeHeader() {
		return typeHeader;
	}

	public void setTypeHeader(TYPEHEADER typeHeader) {
		this.typeHeader = typeHeader;
	}

	public boolean isShowButtonLeft() {
		return showButtonLeft;
	}

	public void setShowButtonLeft(boolean showButtonLeft) {
		this.showButtonLeft = showButtonLeft;
	}

	public boolean isShowButtonRight() {
		return showButtonRight;
	}

	public void setShowButtonRight(boolean showButtonRight) {
		this.showButtonRight = showButtonRight;
	}

	public int getResHeader() {
		return resHeader;
	}

	public void setResHeader(int resHeader) {
		this.resHeader = resHeader;
	}

	public HeaderOption(Context mContext, TYPEHEADER typeHeader) {
		this.typeHeader = typeHeader;
		context = mContext;
	}

	public void onClickButtonLeft() {

	}

	public void onClickButtonRight() {

	}
}