package com.hb.vo;

public class LicenseVO {
	public int l_idx;
	public String e_id,l_name,l_date;
	public LicenseVO() {
		// TODO Auto-generated constructor stub
	}
	public LicenseVO(int l_idx, String e_id, String l_name, String l_date) {
		super();
		this.l_idx = l_idx;
		this.e_id = e_id;
		this.l_name = l_name;
		this.l_date = l_date;
	}
	public int getL_idx() {
		return l_idx;
	}
	public void setL_idx(int l_idx) {
		this.l_idx = l_idx;
	}
	public String getE_id() {
		return e_id;
	}
	public void setE_id(String e_id) {
		this.e_id = e_id;
	}
	public String getL_name() {
		return l_name;
	}
	public void setL_name(String l_name) {
		this.l_name = l_name;
	}
	public String getL_date() {
		return l_date;
	}
	public void setL_date(String l_date) {
		this.l_date = l_date;
	}
	
}
