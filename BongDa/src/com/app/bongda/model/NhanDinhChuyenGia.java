package com.app.bongda.model;

public class NhanDinhChuyenGia extends BaseItem {
	private String iID_MaNhanDinh_Info;
	private String iID_MaTran;
	private String sTieuDe;
	private String sKey;
	private String sAnh;
	private String sAnhChiTiet;
	private String sNoiDung;
	private String dNgayDang;

	public NhanDinhChuyenGia(String id, String matran, String tieude, String key, String anh, String anh2, String noidung, String date) {
		super(id, matran);
		this.iID_MaNhanDinh_Info = id;
		this.iID_MaTran = matran;
		this.sTieuDe = tieude;
		this.sKey = key;
		this.sAnh = anh;
		this.sAnhChiTiet = anh2;
		this.sNoiDung = noidung;
		this.dNgayDang = date;
	}

	public String manhandinh() {
		return iID_MaNhanDinh_Info;
	}
	
	public String mantran() {
		return iID_MaTran;
	}
	
	public String tieude() {
		return sTieuDe;
	}
	
	public String key() {
		return sKey;
	}
	
	public String anh() {
		return sAnh;
	}
	
	public String anh2() {
		return sAnhChiTiet;
	}
	
	public String noidung() {
		return sNoiDung;
	}

	public String date() {
		return dNgayDang;
	}
}
