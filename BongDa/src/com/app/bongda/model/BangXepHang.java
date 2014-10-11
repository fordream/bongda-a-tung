package com.app.bongda.model;

public class BangXepHang extends BaseItem {
	private String pts, gp, w, d, l, gf, ga, _contru;

	// public BangXepHang(String id, String name) {
	// super(id, name);
	// }

	public BangXepHang(String id, String name, String pts, String gp, String w,
			String d, String l, String gf, String ga, String _contru) {
		super(id, name);
		this.pts = pts;
		this.gp = gp;
		this.w = w;
		this.d = d;
		this.l = l;
		this.gf = gf;
		this.ga = ga;
		this._contru = _contru;
	}

	public String getPts() {
		return pts;
	}

	public void setPts(String pts) {
		this.pts = pts;
	}

	public String getGp() {
		return gp;
	}

	public void setGp(String gp) {
		this.gp = gp;
	}

	public String getW() {
		return w;
	}

	public void setW(String w) {
		this.w = w;
	}

	public String getD() {
		return d;
	}

	public void setD(String d) {
		this.d = d;
	}

	public String getL() {
		return l;
	}

	public void setL(String l) {
		this.l = l;
	}

	public String getGf() {
		return gf;
	}

	public void setGf(String gf) {
		this.gf = gf;
	}

	public String getGa() {
		return ga;
	}

	public void setGa(String ga) {
		this.ga = ga;
	}

	public String get_contru() {
		return _contru;
	}

	public void set_contru(String _contru) {
		this._contru = _contru;
	}

}
