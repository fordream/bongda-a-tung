package com.app.bongda.model;

public class GameDuDoan extends PhongDo {
	public GameDuDoan(boolean isHeader, String sTenDoiNha_, String sTenDoiKhach_ ,String iCN_BanThang_DoiNha_,String iCN_BanThang_DoiKhach_, String iC0_,String sThoiGian_,String sTyLe_ChauAu_ ,String sTyLe_ChapBong_, String sTyLe_TaiSuu_,String sTenGiai_, String sLogoQuocGia_, String sLogoGiai_,String iID_MaTran_,String iID_MaDoiNha_ ,String iID_MaDoiKhach_) {
		super(null,sTenDoiNha_,sTenDoiKhach_,null,null);
		this.isHeader = isHeader;
		this.sTenDoiNha = sTenDoiNha_;
		this.sTenDoiKhach = sTenDoiKhach_;
		this.iCN_BanThang_DoiNha = iCN_BanThang_DoiNha_;
		this.iCN_BanThang_DoiKhach = iCN_BanThang_DoiKhach_;
		this.iC0 = iC0_;
		this.sThoiGian = sThoiGian_;
		this.sTyLe_ChapBong = sTyLe_ChapBong_;
		this.sTyLe_ChauAu = sTyLe_ChauAu_;
		this.sTyLe_TaiSuu = sTyLe_TaiSuu_;
		this.sTenGiai = sTenGiai_;
		this.sLogoGiai = sLogoGiai_;
		this.sLogoQuocGia = sLogoQuocGia_;
		this.iID_MaTran = iID_MaTran_;
		this.iID_MaDoiNha = iID_MaDoiNha_;
		this.iID_MaDoiKhach = iID_MaDoiKhach_;
	}

	private boolean isHeader;
	private String sTenDoiNha;
	private String sTenDoiKhach;
	private String iCN_BanThang_DoiNha;
	private String iCN_BanThang_DoiKhach;
	private String iC0;
	private String sThoiGian;
	private String sTyLe_ChauAu;
	private String sTyLe_ChapBong;
	private String sTyLe_TaiSuu;
	private String sTenGiai;
	private String sLogoQuocGia;
	private String sLogoGiai;
	private String iID_MaTran;
	private String iID_MaDoiNha;
	private String iID_MaDoiKhach;
	public boolean isHeader() {
		return isHeader;
	}
	
	public String sTenGiai() {
		return sTenGiai;
	}
	
	public String sLogoQuocGia() {
		return sLogoQuocGia;
	}
	
	public String sLogoGiai() {
		return sLogoGiai;
	}
	
	public String sTenDoiNha() {
		return sTenDoiNha;
	}
	
	public String sTenDoiKhach() {
		return sTenDoiKhach;
	}
	
	public String iCN_BanThang_DoiNha() {
		return iCN_BanThang_DoiNha;
	}
	
	public String iCN_BanThang_DoiKhach(){
		return iCN_BanThang_DoiKhach;
	}
	
	public String iC0(){
		return iC0;
	}
	
	public String sThoiGian(){
		return sThoiGian;
	}
	
	public String sTyLe_ChauAu(){
		return sTyLe_ChauAu;
	}
	
	public String sTyLe_ChapBong(){
		return sTyLe_ChapBong;
	}
	
	public String sTyLe_TaiSuu(){
		return sTyLe_TaiSuu;
	}

	public String iID_MaTran() {
		// TODO Auto-generated method stub
		return iID_MaTran;
	}

	public String iID_MaDoiNha() {
		// TODO Auto-generated method stub
		return iID_MaDoiNha;
	}

	public String iID_MaDoiKhach() {
		// TODO Auto-generated method stub
		return iID_MaDoiKhach;
	}
	
}
