package com.app.bongda.model;

public class LiveScore extends PhongDo {
	public LiveScore(boolean isHeader, String id, String giai, String name, String name2,
			String ht, String phut ,String thoigian, String iC0, String tiso, int trangthai, String magiai, String madoinha, String madoikhach) {
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
	
	public String magiai(){
		return sMaGiai;
	}
	
	public String madoinha(){
		return sMaDoiNha;
	}
	
	public String madoikhach(){
		return sMaDoiKhach;
	}
}
