package com.app.bongda.model;

import android.os.Parcel;
import android.os.Parcelable;

public class BaseItem implements Parcelable {
	private String id;
	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BaseItem(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(id);
		dest.writeString(name);
	}

	public static final Parcelable.Creator<BaseItem> CREATOR = new Parcelable.Creator<BaseItem>() {
		public BaseItem createFromParcel(Parcel in) {
			return new BaseItem(in);
		}

		public BaseItem[] newArray(int size) {
			return new BaseItem[size];
		}
	};

	private BaseItem(Parcel in) {
		id = in.readString();
		name = in.readString();
	}

}