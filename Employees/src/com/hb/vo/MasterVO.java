package com.hb.vo;

public class MasterVO {
	public int p_code;
	public String e_id,m_start,m_end,m_task,m_etc;
	public MasterVO() {
		// TODO Auto-generated constructor stub
	}
	public MasterVO(int p_code, String e_id, String m_start, String m_end, String m_task, String m_etc) {
		super();
		this.p_code = p_code;
		this.e_id = e_id;
		this.m_start = m_start;
		this.m_end = m_end;
		this.m_task = m_task;
		this.m_etc = m_etc;
	}
	public int getP_code() {
		return p_code;
	}
	public void setP_code(int p_code) {
		this.p_code = p_code;
	}
	public String getE_id() {
		return e_id;
	}
	public void setE_id(String e_id) {
		this.e_id = e_id;
	}
	public String getM_start() {
		return m_start;
	}
	public void setM_start(String m_start) {
		this.m_start = m_start;
	}
	public String getM_end() {
		return m_end;
	}
	public void setM_end(String m_end) {
		this.m_end = m_end;
	}
	public String getM_task() {
		return m_task;
	}
	public void setM_task(String m_task) {
		this.m_task = m_task;
	}
	public String getM_etc() {
		return m_etc;
	}
	public void setM_etc(String m_etc) {
		this.m_etc = m_etc;
	}
	
}
