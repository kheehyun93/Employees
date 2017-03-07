package com.hb.vo;

public class ProjectJoinVO {
	public String p_name,p_content,m_start,m_end,m_task,p_order,m_etc,p_code,p_start,p_end,p_state;
	public ProjectJoinVO() {
		// TODO Auto-generated constructor stub
	}
	
	public ProjectJoinVO(String p_name, String p_content, String m_start, String m_end, String m_task, String p_order,
			String m_etc, String p_code, String p_start, String p_end, String p_state) {
		super();
		this.p_name = p_name;
		this.p_content = p_content;
		this.m_start = m_start;
		this.m_end = m_end;
		this.m_task = m_task;
		this.p_order = p_order;
		this.m_etc = m_etc;
		this.p_code = p_code;
		this.p_start = p_start;
		this.p_end = p_end;
		this.p_state = p_state;
	}

	public String getP_state() {
		return p_state;
	}

	public void setP_state(String p_state) {
		this.p_state = p_state;
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

	public String getP_code() {
		return p_code;
	}

	public void setP_code(String p_code) {
		this.p_code = p_code;
	}

	public String getM_etc() {
		return m_etc;
	}

	public void setM_etc(String m_etc) {
		this.m_etc = m_etc;
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
	public String getP_order() {
		return p_order;
	}
	public void setP_order(String p_order) {
		this.p_order = p_order;
	}
	
}
