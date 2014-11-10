package com.vnp.core.datastore.database;

public class LiveScoreLikeTable extends Table {

	public LiveScoreLikeTable() {
		addColumns("iID_MaTran");
		addColumns("iID_MaGiai");
		
		addColumns("iID_MaDoiNha");
		addColumns("iID_MaDoiKhach");
		addColumns("iID_MaMayChu");
		addColumns("sLogoQuocGia");
		addColumns("sLogoGiai");
		addColumns("sLogoDoiNha");
		addColumns("sLogoDoiKhach");
		addColumns("sThoiGian");
		addColumns("iCServer");
		addColumns("dThoiGianThiDau");
		addColumns("sMaGiai");
		addColumns("sTenGiai");
		addColumns("iC0");
		addColumns("iC1");
		addColumns("iC2");
		addColumns("iCN_Phut");
		addColumns("iCN_Phut_TrangWebCapNhap");
		addColumns("iCN_Phut_ThoiGianCapNhap");
		addColumns("iPhut");
		addColumns("iPhutThem");
		addColumns("iSoPhut1Hiep");
		addColumns("sMaDoiNha");
		addColumns("sTenDoiNha");
		addColumns("sMaDoiKhach");
		addColumns("sTenDoiKhach");
		addColumns("iTrangThai");
		addColumns("sTyLe_ChauAu_H1");
		addColumns("sTyLe_ChapBong_H1");
		addColumns("sTyLe_TaiSuu_H1");
		addColumns("sTyLe_ChauAu");
		addColumns("sTyLe_ChapBong");
		addColumns("sTyLe_TaiSuu");
		addColumns("sTyLe_TaiSuu_TrangWebCapNhap");
		addColumns("sTyLe_TaiSuu_ThoiGianCapNhap");
		addColumns("sTyLe_PhatGoc_ChauAu_H1");
		addColumns("sTyLe_PhatGoc_ChapBong_H1");
		addColumns("sTyLe_PhatGoc_TaiSuu_H1");
		addColumns("sTyLe_PhatGoc_ChauAu");
		addColumns("sTyLe_PhatGoc_ChapBong");
		addColumns("sTyLe_PhatGoc_TaiSuu");
		addColumns("sTyLe_PhatGoc_TaiSuu_TrangWebCapNhap");
		addColumns("sTyLe_PhatGoc_TaiSuu_ThoiGianCapNhap");
		addColumns("sThongTin_DoiNha");
		addColumns("sThongTin_DoiKhach");
		addColumns("sThongTin_DoiNha_TrangWebCapNhap");
		addColumns("sThongTin_DoiNha_ThoiGianCapNhap");
		addColumns("sThongTin_DoiNha_HT");
		addColumns("sThongTin_DoiKhach_HT");
		addColumns("sThongTinThe_DoiNha");
		addColumns("sThongTinThe_DoiKhach");
		addColumns("sThongTinThe_DoiNha_TrangWebCapNhap");
		addColumns("sThongTinThe_DoiNha_ThoiGianCapNhap");
		addColumns("sThongTinThe_DoiNha_HT");
		addColumns("sThongTinThe_DoiKhach_HT");
		addColumns("fPoss_DoiNha");
		addColumns("fPoss_DoiKhach");
		addColumns("fPoss_DoiNha_HT");
		addColumns("fPoss_DoiKhach_HT");
		addColumns("bKhaiCuoc_DoiNha");
		addColumns("bKhaiCuoc_DoiKhach");
		addColumns("iCN_BanThang_DoiNha");
		addColumns("iCN_BanThang_DoiKhach");
		addColumns("iCN_BanThang_DoiNha_TrangWebCapNhap");
		addColumns("iCN_BanThang_DoiNha_ThoiGianCapNhap");
		addColumns("iCN_BanThang_DoiNha_HT");
		addColumns("iCN_BanThang_DoiKhach_HT");
		addColumns("iCN_BanThang_DoiNha_FT");
		addColumns("iCN_BanThang_DoiKhach_FT");
		addColumns("iCN_BanThang_DoiNha_ET");
		addColumns("iCN_BanThang_DoiKhach_ET");
		addColumns("iCN_BanThang_DoiNha_Pen");
		addColumns("iCN_BanThang_DoiKhach_Pen");
		addColumns("iCN_Sut_DoiNha");
		addColumns("iCN_Sut_DoiKhach");
		addColumns("iCN_Sut_DoiNha_HT");
		addColumns("iCN_Sut_DoiKhach_HT");
		addColumns("iCN_SutTrung_DoiNha");
		addColumns("iCN_SutTrung_DoiKhach");
		addColumns("iCN_SutTrung_DoiNha_HT");
		addColumns("iCN_SutTrung_DoiKhach_HT");
		addColumns("iCN_SutPhat_DoiNha");
		addColumns("iCN_SutPhat_DoiKhach");
		addColumns("iCN_SutPhat_DoiNha_HT");
		addColumns("iCN_SutPhat_DoiKhach_HT");
		addColumns("iCN_PhatGoc_DoiNha");
		addColumns("iCN_PhatGoc_DoiKhach");
		addColumns("iCN_PhatGoc_DoiNha_TrangWebCapNhap");
		addColumns("iCN_PhatGoc_DoiNha_ThoiGianCapNhap");
		addColumns("iCN_PhatGoc_DoiNha_HT");
		addColumns("iCN_PhatGoc_DoiKhach_HT");
		addColumns("iCN_PhamLoi_DoiNha");
		addColumns("iCN_PhamLoi_DoiKhach");
		addColumns("iCN_PhamLoi_DoiNha_HT");
		addColumns("iCN_PhamLoi_DoiKhach_HT");
		addColumns("iCN_TheVang_DoiNha");
		addColumns("iCN_TheVang_DoiKhach");
		addColumns("iCN_TheVang_DoiNha_HT");
		addColumns("iCN_TheVang_DoiKhach_HT");
		addColumns("iCN_TheDo_DoiNha");
		addColumns("iCN_TheDo_DoiKhach");
		addColumns("iCN_TheDo_DoiNha_HT");
		addColumns("iCN_TheDo_DoiKhach_HT");
		addColumns("iCN_VietVi_DoiNha");
		addColumns("iCN_VietVi_DoiKhach");
		addColumns("iCN_VietVi_DoiNha_HT");
		addColumns("iCN_VietVi_DoiKhach_HT");
		addColumns("iCN_NemBien_DoiNha");
		addColumns("iCN_NemBien_DoiKhach");
		addColumns("iCN_NemBien_DoiNha_HT");
		addColumns("iCN_NemBien_DoiKhach_HT");
		addColumns("iRungBanThang_DoiNha");
		addColumns("iRungBanThang_DoiKhach");
		addColumns("iRungTheVang_DoiNha");
		addColumns("iRungTheVang_DoiKhach");
		addColumns("iRungTheDo_DoiNha");
		addColumns("iRungTheDo_DoiKhach");
		addColumns("iRungPhatGoc_DoiNha");
		addColumns("iRungPhatGoc_DoiKhach");
		addColumns("iRungTrangThai");
		addColumns("iRung");
		addColumns("sRung_TenTruong");
		addColumns("iVongDau");
		addColumns("dThoiGianSua");
		addColumns("sTrangWeb");
		addColumns("sLastMatches_DoiNha");
		addColumns("sLastMatches_DoiKhach");
		addColumns("sHeadToHead");
		addColumns("sHeadToHead_Thieu");
		addColumns("iHeadToHead_ThoiGianCapNhap");
		addColumns("bHeadToHead_BatBuocLay");
		addColumns("sDoiHinh_DoiNha");
		addColumns("sDoiHinh_DoiKhach");
		addColumns("iDoiHinh_ThoiGianCapNhap");
		addColumns("bDoiHinh_BatBuocLay");
		addColumns("bNhanDinhChuyenGia");
		addColumns("bGameDuDoan");
		addColumns("bDaXong");
		addColumns("bDaCapNhapVaoBXH");
		addColumns("bKhongDuaVaoTinNhan");
		addColumns("iChiSoTrenTinNhan");
		addColumns("sMaDoiKhach_ET");
		addColumns("bSanTrungLap");
		addColumns("sTip");
		addColumns("iTip_ThoiGian");

		/**
		 * add new
		 */

		// 1 show
		// 0 hidden
		addColumns("bdneedshow");
		// contry 0
		// item 1
		addColumns("bdposition");
		// 1 liked
		// 0 unlike
		addColumns("bdliked");

	}

	@Override
	public String getTableName() {
		return "LiveScoreLikeTable";
	}
}