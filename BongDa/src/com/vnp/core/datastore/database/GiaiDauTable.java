package com.vnp.core.datastore.database;

public class GiaiDauTable extends Table {

	public GiaiDauTable() {
		addColumns("iID_MaGiai");
		addColumns("iID_MaQuocGia");
		addColumns("sMaGiai");
		addColumns("sTenGiai");
		addColumns("sLogo");

		addColumns("sTinNhan_DoanTinCuoi");
		addColumns("iTinNhan_DoanTinCuoi_ThoiGian");
		addColumns("iBXH_ThoiGianCapNhap");
		addColumns("sBXH_Url_Futbal24");
		addColumns("sBXH_TieuDe");
		addColumns("bBXH_BatBuocLay");
		addColumns("sThongTinLoi");
		addColumns("iC0");
		addColumns("iSoGio");
		addColumns("iMucUuTien");
		addColumns("bDaCoThayDoi");
		addColumns("sGoalServe_XML");
		addColumns("iThoiGian_TranDauCuoi");
		addColumns("sCaMuaGiai_7MSport_URL");
		addColumns("bCaMuaGiai_7MSport_BatBuocLay");
		addColumns("iCaMuaGiai_7MSport_LoaiGiai");
		addColumns("bBatBuocLayChiTiet_7MSport");
		addColumns("sMaGiai_7MSport");
		addColumns("sTenGiai_7MSport");
		addColumns("iRunning");
		addColumns("bDungNhieu");
	}

	@Override
	public String getTableName() {
		return "GiaiDauTable";
	}
}