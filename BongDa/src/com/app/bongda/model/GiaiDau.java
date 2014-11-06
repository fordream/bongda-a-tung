package com.app.bongda.model;

public class GiaiDau extends BaseItem {
	private String sMagiai;
	private String sMadoinha;
	private String sMadoikhach;
	private String iID_MaGiai;

	public GiaiDau(String id, String name) {
		super(id, name);
	}

	public GiaiDau(String id, String name, String magiai, String madoinha,
			String madoikhach, String idmagiai) {
		super(id, name);
		this.sMagiai = magiai;
		this.sMadoinha = madoinha;
		this.sMadoikhach = madoikhach;
		this.iID_MaGiai = idmagiai;
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
}