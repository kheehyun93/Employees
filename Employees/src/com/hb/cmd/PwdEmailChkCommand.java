package com.hb.cmd;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hb.dao.EmployeesDAO;

public class PwdEmailChkCommand implements Command{

	@Override
	public String exec(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		String email = request.getParameter("email");
		EmployeesDAO dao = new EmployeesDAO();
		String result = dao.getPwdEmail(id,email);
		return result;
	}

}
