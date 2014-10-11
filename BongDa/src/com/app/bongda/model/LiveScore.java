package com.app.bongda.model;

public class LiveScore extends PhongDo {
	public LiveScore(boolean isHeader, String id, String name, String name2,
			String date, String time) {
		super(id, name, name2, date, time);
		this.isHeader = isHeader;
	}

	private boolean isHeader;

	public boolean isHeader() {
		return isHeader;
	}

}
