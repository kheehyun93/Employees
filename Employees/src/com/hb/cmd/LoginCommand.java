package com.hb.cmd;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hb.dao.EmployeesDAO;
import com.hb.vo.EmployeesVO;

public class LoginCommand implements Command{

	@Override
	public String exec(HttpServletRequest request, HttpServletResponse response){
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		
		System.out.println(id+pwd);
		
		HttpSession session = request.getSession();
		EmployeesVO evo = new EmployeesVO();
		
		EmployeesDAO dao = new EmployeesDAO();
		
		

		
		evo = dao.loginCheck(id,pwd);
		
		String result = null;
		
		if(evo == null){
			result="fail";
		}else{
			session.setAttribute("empInfo", evo);
			result = "../[menu]/Main.jsp";
		}
		
		return result;
	}

}
