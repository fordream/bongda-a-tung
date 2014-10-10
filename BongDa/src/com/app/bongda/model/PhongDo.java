package com.app.bongda.model;

public class PhongDo extends BaseItem {
	private String time;
	private String name2;
	private String date;

	public PhongDo(String id, String name, String name2, String date,
			String time) {
		super(id, name);
		this.name2 = name2;
		this.date = date;
		this.time = time;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getName2() {
		return name2;
	}

	public void setName2(String name2) {
		this.name2 = name2;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
