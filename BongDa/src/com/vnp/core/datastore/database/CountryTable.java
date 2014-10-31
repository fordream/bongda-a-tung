package com.vnp.core.datastore.database;

public class CountryTable extends Table {

	public CountryTable() {
		addColumns("iID_MaQuocGia");
		addColumns("sTenQuocGia");
		addColumns("sLogo");
	}

	@Override
	public String getTableName() {
		return "CountryTable";
	}
}