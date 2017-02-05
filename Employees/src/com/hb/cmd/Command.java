package com.hb.cmd;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hb.vo.ProEmpJoinVO;

public interface Command {
	public String exec(HttpServletRequest request, HttpServletResponse response);
}
