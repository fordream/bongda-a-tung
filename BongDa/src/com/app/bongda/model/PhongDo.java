package com.app.bongda.model;

public class PhongDo extends BaseItem {
	private String time;
	private String name2;
	private String date;

	public PhongDo(String id, String name, String name2, String date,
			String time) {
		super(id, name);
		this.name2 = name2;
		this.date = date;
		this.time = time;
	}
	
	private String sViTri;
	private String sSoTranDau;
	private String sDiem;
	private String sSoTranThang;
	private String sSoTranHoa;
	private String sSoTranThua;
	private String sBanThang;
	private String sBanThua;
	private String sHeSo;
	public PhongDo(String id, String name, String sViTri_, String sSoTranDau_, String sDiem_, String sSoTranThang_, String sSoTranHoa_, String sSoTranThua_, String sBanThang_, String sBanThua_, String sHeSo_) {
		super(id, name);
		this.sViTri = sViTri_;
		this.sSoTranDau = sSoTranDau_;
		this.sDiem = sDiem_;
		this.sSoTranThang = sSoTranThang_;
		this.sSoTranHoa = sSoTranHoa_;
		this.sSoTranThua = sSoTranThua_;
		this.sBanThang = sBanThang_;
		this.sBanThua = sBanThua_;
		this.sHeSo = sHeSo_;
	}

	public String sViTri() {
		return sViTri;
	}
	
	public String sSoTranDau() {
		return sSoTranDau;
	}
	
	public String sDiem() {
		return sDiem;
	}
	
	public String sSoTranThang() {
		return sSoTranThang;
	}
	
	public String sSoTranHoa() {
		return sSoTranHoa;
	}
	
	public String sSoTranThua() {
		return sSoTranThua;
	}
	
	public String sBanThang() {
		return sBanThang;
	}
	
	public String sBanThua() {
		return sBanThua;
	}
	
	public String sHeSo() {
		return sHeSo;
	}
	
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getName2() {
		return name2;
	}

	public void setName2(String name2) {
		this.name2 = name2;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
