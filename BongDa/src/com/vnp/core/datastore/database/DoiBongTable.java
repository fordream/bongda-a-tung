package com.vnp.core.datastore.database;

public class DoiBongTable extends Table {

	public DoiBongTable() {
		addColumns("iID_MaDoi");
		addColumns("sViTri");
		addColumns("sTenDoi");
		addColumns("sSoTranDau");
		addColumns("sDiem");
		addColumns("sSoTranThang");
		addColumns("sSoTranHoa");
		addColumns("sSoTranThua");
		addColumns("sBanThang");
		addColumns("sBanThua");
		addColumns("sHeSo");
	}

	@Override
	public String getTableName() {
		return "DoiBongTable";
	}
}