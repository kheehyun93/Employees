package com.hb.vo;

public class ProEmpJoinVO {
	public String e_id,e_name;
	public ProEmpJoinVO() {
		// TODO Auto-generated constructor stub
	}
	public ProEmpJoinVO(String e_id, String e_name) {
		super();
		this.e_id = e_id;
		this.e_name = e_name;
	}
	public String getE_id() {
		return e_id;
	}
	public void setE_id(String e_id) {
		this.e_id = e_id;
	}
	public String getE_name() {
		return e_name;
	}
	public void setE_name(String e_name) {
		this.e_name = e_name;
	}
	
}
