package com.hb.vo;

public class SchoolVO {
	public int s_idx;
	public String e_id,s_start,s_end,s_name,s_major;
	public SchoolVO() {
		// TODO Auto-generated constructor stub
	}
	public SchoolVO(int s_idx, String e_id, String s_start, String s_end, String s_name, String s_major) {
		super();
		this.s_idx = s_idx;
		this.e_id = e_id;
		this.s_start = s_start;
		this.s_end = s_end;
		this.s_name = s_name;
		this.s_major = s_major;
	}
	public int getS_idx() {
		return s_idx;
	}
	public void setS_idx(int s_idx) {
		this.s_idx = s_idx;
	}
	public String getE_id() {
		return e_id;
	}
	public void setE_id(String e_id) {
		this.e_id = e_id;
	}
	public String getS_start() {
		return s_start;
	}
	public void setS_start(String s_start) {
		this.s_start = s_start;
	}
	public String getS_end() {
		return s_end;
	}
	public void setS_end(String s_end) {
		this.s_end = s_end;
	}
	public String getS_name() {
		return s_name;
	}
	public void setS_name(String s_name) {
		this.s_name = s_name;
	}
	public String getS_major() {
		return s_major;
	}
	public void setS_major(String s_major) {
		this.s_major = s_major;
	}
	
	
}
