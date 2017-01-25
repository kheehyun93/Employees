package com.hb.vo;

public class EmployeesVO {
	public int e_idx,e_state;
	public String e_id,e_name,e_jumin,e_email,e_tel,e_addr,e_post,e_rank,e_dept,e_hire_date,e_img,e_pwd;
	
	public EmployeesVO() {
		// TODO Auto-generated constructor stub
	}

	public EmployeesVO(int e_idx, int e_state, String e_id, String e_name, String e_jumin, String e_email, String e_tel,
			String e_addr, String e_post, String e_rank, String e_dept, String e_hire_date, String e_img,
			String e_pwd) {
		super();
		this.e_idx = e_idx;
		this.e_state = e_state;
		this.e_id = e_id;
		this.e_name = e_name;
		this.e_jumin = e_jumin;
		this.e_email = e_email;
		this.e_tel = e_tel;
		this.e_addr = e_addr;
		this.e_post = e_post;
		this.e_rank = e_rank;
		this.e_dept = e_dept;
		this.e_hire_date = e_hire_date;
		this.e_img = e_img;
		this.e_pwd = e_pwd;
	}

	public int getE_idx() {
		return e_idx;
	}

	public void setE_idx(int e_idx) {
		this.e_idx = e_idx;
	}

	public int getE_state() {
		return e_state;
	}

	public void setE_state(int e_state) {
		this.e_state = e_state;
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

	public String getE_jumin() {
		return e_jumin;
	}

	public void setE_jumin(String e_jumin) {
		this.e_jumin = e_jumin;
	}

	public String getE_email() {
		return e_email;
	}

	public void setE_email(String e_email) {
		this.e_email = e_email;
	}

	public String getE_tel() {
		return e_tel;
	}

	public void setE_tel(String e_tel) {
		this.e_tel = e_tel;
	}

	public String getE_addr() {
		return e_addr;
	}

	public void setE_addr(String e_addr) {
		this.e_addr = e_addr;
	}

	public String getE_post() {
		return e_post;
	}

	public void setE_post(String e_post) {
		this.e_post = e_post;
	}

	public String getE_rank() {
		return e_rank;
	}

	public void setE_rank(String e_rank) {
		this.e_rank = e_rank;
	}

	public String getE_dept() {
		return e_dept;
	}

	public void setE_dept(String e_dept) {
		this.e_dept = e_dept;
	}

	public String getE_hire_date() {
		return e_hire_date;
	}

	public void setE_hire_date(String e_hire_date) {
		this.e_hire_date = e_hire_date;
	}

	public String getE_img() {
		return e_img;
	}

	public void setE_img(String e_img) {
		this.e_img = e_img;
	}

	public String getE_pwd() {
		return e_pwd;
	}

	public void setE_pwd(String e_pwd) {
		this.e_pwd = e_pwd;
	}
	
}
