package com.app.bongda.model;

public class LiveScore extends PhongDo {
	public LiveScore(boolean isHeader, String id, String giai, String name, String name2,
			String date, String time) {
		super(id, name, name2, date, time);
		this.isHeader = isHeader;
		this.sTenGiai = giai;
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
	
}
