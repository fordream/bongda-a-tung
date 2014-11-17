package com.vnp.core.datastore.database;

public class CountryTable extends Table {
	private String table2 = null;
	public CountryTable() {
		addColumns("iID_MaQuocGia");
		addColumns("sMaQuocGia");
		addColumns("sTenQuocGia");
		addColumns("sMaQuocGia_en");
		addColumns("sMaQuocGia_GoalServe");
		addColumns("sLogo");
		addColumns("stype");
	}

	@Override
	public String getTableName() {
		return "CountryTable";
	}
}