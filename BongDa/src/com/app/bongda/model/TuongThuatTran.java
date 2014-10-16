package com.app.bongda.model;

public class TuongThuatTran extends BaseItem {
	private int isDoi;
	private String sThoigian;
	private int sTrangthai;

	public TuongThuatTran(int doi, String id, String thoigian, String name, int status) {
		super(id, name);
		this.isDoi = doi;
		this.sThoigian = thoigian;
		this.sTrangthai = status;
	}

	public int isDoi() {
		return isDoi;
	}
	
	public String isThoigian() {
		return sThoigian;
	}

	public int isTrangthai() {
		return sTrangthai;
	}
}
