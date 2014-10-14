package com.app.bongda.model;

public class TuongThuatTran extends BaseItem {
	private boolean isDoi1;

	public TuongThuatTran(boolean isDoi1, String id, String name) {
		super(id, name);
		this.isDoi1 = isDoi1;
	}

	public boolean isDoi1() {
		return isDoi1;
	}

}
