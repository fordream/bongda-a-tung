package com.app.bongda.model;

public class LiveScore extends PhongDo {
	public LiveScore(boolean isHeader, String id, String giai, String name, String name2,
			String ht, String phut ,String thoigian, String iC0, String tiso, int trangthai, String magiai, String madoinha, String madoikhach, String IDMagiai, boolean bNhanDinhChuyenGia_, boolean bGameDuDoan_, boolean bDaCapNhapVaoBXH_ , String sLogoQuocGia_, String sLogoGiai_, String sLogoDoiNha_, String sLogoDoiKhach_,String iID_MaDoiNha_, String iID_MaDoiKhach_
) {
		super(id, name, name2, iC0, thoigian);
		this.isHeader = isHeader;
		this.sTenGiai = giai;
		this.iTrangThai = trangthai;
		this.iPhut = phut;
		this.iTiso = tiso;
		this.HT = ht;
		this.sThoiGian = thoigian;
		this.sMaGiai = magiai;
		this.sMaDoiNha = madoinha;
		this.sMaDoiKhach = madoikhach;
		this.iID_MaGiai = IDMagiai;
		this.bNhanDinhChuyenGia = bNhanDinhChuyenGia_;
		this.bDaCapNhapVaoBXH = bDaCapNhapVaoBXH_;
		this.bGameDuDoan = bGameDuDoan_;
		this.sLogoQuocGia = sLogoQuocGia_;
		this.sLogoGiai = sLogoGiai_;
		this.sLogoDoiNha = sLogoDoiNha_;
		this.sLogoDoiKhach = sLogoDoiKhach_;
		this.iID_MaTran = id;
		this.iID_MaDoiNha = iID_MaDoiNha_;
		this.iID_MaDoiKhach = iID_MaDoiKhach_;
	}
	public LiveScore(boolean isHeader, String sTenGiai,String sTenDoiNha,String sTenDoiKhach,int iCN_BanThang_DoiNha,int iCN_BanThang_DoiKhach,int iCN_BanThang_DoiNha_HT,int iCN_BanThang_DoiKhach_HT) {
		super(null,sTenDoiNha,sTenDoiKhach,null,null);
		this.isHeader = isHeader;
		this.sTenGiai = sTenGiai;
		this.sTenDoiNha = sTenDoiNha;
		this.sTenDoiKhach = sTenDoiKhach;
		this.iCN_BanThang_DoiNha = iCN_BanThang_DoiNha;
		this.iCN_BanThang_DoiKhach = iCN_BanThang_DoiKhach;
		this.iCN_BanThang_DoiNha_HT = iCN_BanThang_DoiNha_HT;
		this.iCN_BanThang_DoiKhach_HT = iCN_BanThang_DoiKhach_HT;
	}

	private boolean isHeader;
	private String sTenGiai;
	private String sTenDoiNha;
	private String sTenDoiKhach;
	private int iCN_BanThang_DoiNha;
	private int iCN_BanThang_DoiKhach;
	private int iCN_BanThang_DoiNha_HT;
	private int iCN_BanThang_DoiKhach_HT;
	private int iTrangThai;
	private String iPhut;
	private String iTiso;
	private String HT;
	private String sThoiGian;
	
	private String sMaGiai;
	private String sMaDoiNha;
	private String sMaDoiKhach;
	
	private String iID_MaGiai;

	private boolean bNhanDinhChuyenGia;
	private boolean bGameDuDoan;
	private boolean bDaCapNhapVaoBXH;
	private String sLogoGiai;
	private String iID_MaTran;
	private String sLogoQuocGia;
	private String sLogoDoiNha;
	private String sLogoDoiKhach;
	private String iID_MaDoiNha;
	private String iID_MaDoiKhach;
	public boolean isHeader() {
		return isHeader;
	}
	
	public String sTenGiai() {
		return sTenGiai;
	}
	
	public String sTenDoiNha() {
		return sTenDoiNha;
	}
	
	public String sTenDoiKhach() {
		return sTenDoiKhach;
	}
	
	public int iCN_BanThang_DoiNha() {
		return iCN_BanThang_DoiNha;
	}
	
	public int iCN_BanThang_DoiKhach() {
		return iCN_BanThang_DoiKhach;
	}
	
	public int iCN_BanThang_DoiNha_HT() {
		return iCN_BanThang_DoiNha_HT;
	}
	
	public int iCN_BanThang_DoiKhach_HT() {
		return iCN_BanThang_DoiKhach_HT;
	}
	
	public int iTrangThai() {
		return iTrangThai;
	}
	
	public String iPhut() {
		return iPhut;
	}
	
	public String iTiso() {
		return iTiso;
	}
	public String iHT() {
		return HT;
	}
	public String sThoiGian(){
		return sThoiGian;
	}
	
	public String sMaGiai(){
		return sMaGiai;
	}
	
	public String madoinha(){
		return sMaDoiNha;
	}
	
	public String madoikhach(){
		return sMaDoiKhach;
	}
	
	public String idmagiai(){
		return iID_MaGiai;
	}
	
	public boolean isNhanDinhChuyenGia(){
		return bNhanDinhChuyenGia;
	}
	
	public boolean isGameDuDoan(){
		return bGameDuDoan;
	}
	
	public boolean isDaCapNhapVaoBXH(){
		return bDaCapNhapVaoBXH;
	}
	
	public String sLogoGiai(){
		return sLogoGiai;
	}
	public String iID_MaGiai() {
		// TODO Auto-generated method stub
		return iID_MaGiai;
	}
	public String iID_MaTran() {
		// TODO Auto-generated method stub
		return iID_MaTran;
	}
	public String sLogoDoiKhach() {
		// TODO Auto-generated method stub
		return sLogoDoiKhach;
	}
	public String sLogoDoiNha() {
		// TODO Auto-generated method stub
		return sLogoDoiNha;
	}
	public String iID_MaDoiKhach() {
		// TODO Auto-generated method stub
		return iID_MaDoiKhach;
	}
	public String iID_MaDoiNha() {
		// TODO Auto-generated method stub
		return iID_MaDoiNha;
	}
}
