package com.hb.vo;

public class ProjectVO {
	public int p_code,p_num;
	public String p_name,p_content,p_start,p_end,p_order;
	public ProjectVO() {
		// TODO Auto-generated constructor stub
	}
	public ProjectVO(int p_code, int p_num, String p_name, String p_content, String p_start, String p_end,
			String p_order) {
		super();
		this.p_code = p_code;
		this.p_num = p_num;
		this.p_name = p_name;
		this.p_content = p_content;
		this.p_start = p_start;
		this.p_end = p_end;
		this.p_order = p_order;
	}
	public int getP_code() {
		return p_code;
	}
	public void setP_code(int p_code) {
		this.p_code = p_code;
	}
	public int getP_num() {
		return p_num;
	}
	public void setP_num(int p_num) {
		this.p_num = p_num;
	}
	public String getP_name() {
		return p_name;
	}
	public void setP_name(String p_name) {
		this.p_name = p_name;
	}
	public String getP_content() {
		return p_content;
	}
	public void setP_content(String p_content) {
		this.p_content = p_content;
	}
	public String getP_start() {
		return p_start;
	}
	public void setP_start(String p_start) {
		this.p_start = p_start;
	}
	public String getP_end() {
		return p_end;
	}
	public void setP_end(String p_end) {
		this.p_end = p_end;
	}
	public String getP_order() {
		return p_order;
	}
	public void setP_order(String p_order) {
		this.p_order = p_order;
	}
	
}
