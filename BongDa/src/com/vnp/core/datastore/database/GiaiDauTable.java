package com.vnp.core.datastore.database;

public class GiaiDauTable extends Table {

	public GiaiDauTable() {
		addColumns("iID_MaGiai");
		addColumns("sTenGiai");
	}

	@Override
	public String getTableName() {
		return "GiaiDauTable";
	}
}