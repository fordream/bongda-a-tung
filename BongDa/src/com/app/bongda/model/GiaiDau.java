package com.app.bongda.model;

public class GiaiDau extends BaseItem {
	private String sMagiai;
	private String sMadoinha;
	private String sMadoikhach;
	public GiaiDau(String id, String name) {
		super(id, name);
	}
	
	public GiaiDau(String id, String name, String magiai, String madoinha, String madoikhach) {
		super(id, name);
		this.sMagiai = magiai;
		this.sMadoinha = madoinha;
		this.sMadoikhach = madoikhach;
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
}
