package com.app.bongda.model;

public class Country extends BaseItem {
	private String LogoCountry;
	public Country(String id, String name, String sLogoCountry) {
		super(id, name);
		this.LogoCountry = sLogoCountry;
	}
	
	public String logoCountry(){
		return LogoCountry;
	}
}
