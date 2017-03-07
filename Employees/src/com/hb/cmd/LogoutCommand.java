package com.hb.cmd;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hb.vo.EmployeesVO;

public class LogoutCommand implements Command{

	@Override
	public String exec(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		EmployeesVO evo = new EmployeesVO();
		evo = (EmployeesVO) session.getAttribute("empInfo");
		System.out.println(evo.getE_name());
		session.invalidate();
		
		return "/[login]/Login.jsp";
	}

}
