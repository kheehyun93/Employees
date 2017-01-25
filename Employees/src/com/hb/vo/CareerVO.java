package com.hb.vo;

public class CareerVO {
	public int c_idx;
	public String e_id,c_start,c_end,c_name,c_rank,c_task;
	
	public CareerVO() {
		// TODO Auto-generated constructor stub
	}

	public CareerVO(int c_idx, String e_id, String c_start, String c_end, String c_name, String c_rank, String c_task) {
		super();
		this.c_idx = c_idx;
		this.e_id = e_id;
		this.c_start = c_start;
		this.c_end = c_end;
		this.c_name = c_name;
		this.c_rank = c_rank;
		this.c_task = c_task;
	}

	public int getC_idx() {
		return c_idx;
	}

	public void setC_idx(int c_idx) {
		this.c_idx = c_idx;
	}

	public String getE_id() {
		return e_id;
	}

	public void setE_id(String e_id) {
		this.e_id = e_id;
	}

	public String getC_start() {
		return c_start;
	}

	public void setC_start(String c_start) {
		this.c_start = c_start;
	}

	public String getC_end() {
		return c_end;
	}

	public void setC_end(String c_end) {
		this.c_end = c_end;
	}

	public String getC_name() {
		return c_name;
	}

	public void setC_name(String c_name) {
		this.c_name = c_name;
	}

	public String getC_rank() {
		return c_rank;
	}

	public void setC_rank(String c_rank) {
		this.c_rank = c_rank;
	}

	public String getC_task() {
		return c_task;
	}

	public void setC_task(String c_task) {
		this.c_task = c_task;
	}
	
}
