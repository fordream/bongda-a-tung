package com.app.bongda.model;

public class GiaiDau extends BaseItem {
	private String sMagiai;
	private String sMadoinha;
	private String sMadoikhach;
	private String iID_MaGiai;
	private String iID_MaTran;
	private String sLogoDoiNha;
	private String sLogoDoiKhach;
	private String iID_MaDoiNha;
	private String iID_MaDoiKhach;

	public GiaiDau(String id, String name) {
		super(id, name);
	}

	public GiaiDau(String id, String name, String magiai, String madoinha,
			String madoikhach, String idmagiai, String iID_MaTran_ , String sLogoDoiNha_, String sLogoDoiKhach_ ,String iID_MaDoiNha_, String iID_MaDoiKhach_) {
		super(id, name);
		this.sMagiai = magiai;
		this.sMadoinha = madoinha;
		this.sMadoikhach = madoikhach;
		this.iID_MaGiai = idmagiai;
		this.iID_MaTran = iID_MaTran_;
		this.sLogoDoiNha = sLogoDoiNha_;
		this.sLogoDoiKhach = sLogoDoiKhach_;
		this.iID_MaDoiNha = iID_MaDoiNha_;
		this.iID_MaDoiKhach = iID_MaDoiKhach_;
	}

	public String magiai() {
		return sMagiai;
	}

	public String madoinha() {
		return sMadoinha;
	}

	public String madoikhach() {
		return sMadoikhach;
	}

	public String idmagiai() {
		return iID_MaGiai;
	}

	private String iID_MaQuocGia;
	private String sLogoGiai;
	private String sTenDoiNha;
	private String sTenDoiKhach;

	public String getiID_MaQuocGia() {
		return iID_MaQuocGia;
	}

	public void setiID_MaQuocGia(String iID_MaQuocGia) {
		this.iID_MaQuocGia = iID_MaQuocGia;
	}

	public void sLogoGiai(String sLogoGiai) {
		this.sLogoGiai = sLogoGiai;
	}

	public String sLogoGiai() {
		return sLogoGiai;
	}
	
	public String iID_MaTran(){
		return iID_MaTran;
	}

	public String sLogoDoiNha() {
		// TODO Auto-generated method stub
		return sLogoDoiNha;
	}

	public String sLogoDoiKhach() {
		// TODO Auto-generated method stub
		return sLogoDoiKhach;
	}

	public String iID_MaDoiNha() {
		// TODO Auto-generated method stub
		return iID_MaDoiNha;
	}

	public String iID_MaDoiKhach() {
		// TODO Auto-generated method stub
		return iID_MaDoiKhach;
	}

	public void iID_MaDoiNha(String iID_MaDoiNha2) {
		// TODO Auto-generated method stub
		this.iID_MaDoiNha = iID_MaDoiNha2;
	}

	public void iID_MaDoiKhach(String iID_MaDoiKhach2) {
		// TODO Auto-generated method stub
		this.iID_MaDoiKhach = iID_MaDoiKhach2;
	}

	public void sTenDoiNha(String sTenDoiNha2) {
		// TODO Auto-generated method stub
		this.sTenDoiNha = sTenDoiNha2;
	}

	public void sTenDoiKhach(String sTenDoiKhach2) {
		// TODO Auto-generated method stub
		this.sTenDoiKhach = sTenDoiKhach2;
	}

	public String sTenDoiNha() {
		// TODO Auto-generated method stub
		return sTenDoiNha;
	}

	public String sTenDoiKhach() {
		// TODO Auto-generated method stub
		return sTenDoiKhach;
	}
}