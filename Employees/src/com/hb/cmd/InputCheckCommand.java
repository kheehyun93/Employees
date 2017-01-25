package com.hb.cmd;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hb.dao.EmployeesDAO;

public class InputCheckCommand implements Command{

	@Override
	public String exec(HttpServletRequest request, HttpServletResponse response) {
		String input = request.getParameter("input");
		
		System.out.println(input);
		
		String tel,email = null;
		String result = null;
		
		EmployeesDAO dao = new EmployeesDAO();
		
		if(input.contains("@")){
			// 이메일로 받음.
			email = input;
			result = dao.getFindEmail(email);
		}else{
			// 전화번호로 받음.
			tel = input;
			result = dao.getFindTel(tel);
		}
		
		System.out.println(result);
		
		return result;
	}

}
