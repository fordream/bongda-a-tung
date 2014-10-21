package com.app.bongda.model;

public class GiaiDau extends BaseItem {
	private String sMagiai;
	private String sMadoinha;
	private String sMadoikhach;
	private String iID_MaGiai;
	public GiaiDau(String id, String name) {
		super(id, name);
	}
	
	public GiaiDau(String id, String name, String magiai, String madoinha, String madoikhach, String idmagiai) {
		super(id, name);
		this.sMagiai = magiai;
		this.sMadoinha = madoinha;
		this.sMadoikhach = madoikhach;
		this.iID_MaGiai = idmagiai;
	}
	
	public String magiai(){
		return sMagiai;
	}
	
	public String madoinha(){
		return sMadoinha;
	}
	
	public String madoikhach(){
		return sMadoikhach;
	}
	
	public String idmagiai(){
		return iID_MaGiai;
	}
}
