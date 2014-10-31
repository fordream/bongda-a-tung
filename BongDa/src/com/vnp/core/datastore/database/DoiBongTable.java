package com.vnp.core.datastore.database;

public class DoiBongTable extends Table {

	public DoiBongTable() {
		addColumns("iID_MaBXH_ChiTiet");
		addColumns("iID_MaBXH");
		addColumns("iID_MaGiai");
		addColumns("iID_MaDoi");
		
		addColumns("iChiSoBXH");
		addColumns("sTieuDeBXH");
		addColumns("sMaGiai");
		addColumns("sTenGiai");
		addColumns("sMaDoi");
		addColumns("sTenDoi");
		addColumns("sViTri");
		addColumns("sSoTranDau");
		addColumns("sDiem");
		addColumns("sSoTranThang");
		addColumns("sSoTranHoa");
		addColumns("sSoTranThua");
		addColumns("sBanThang");
		addColumns("sBanThua");
		addColumns("sHeSo");
		addColumns("sLast5Match");
		addColumns("sTongHop");
		addColumns("iSTT");
		addColumns("bDangActive");
	}

	@Override
	public String getTableName() {
		return "DoiBongTable";
	}
}