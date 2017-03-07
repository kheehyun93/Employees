package com.hb.cmd;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hb.dao.EmployeesDAO;

public class EmpJuminChkCommand implements Command{

	@Override
	public String exec(HttpServletRequest request, HttpServletResponse response) {
		String jumin = request.getParameter("jumin");
		
		EmployeesDAO edao = new EmployeesDAO();
		String juminChk = edao.getChkJumin(jumin);
		
		String result = null;
		if(juminChk != null){
			result = "중복된 사원";
		}else{
			result = "등록가능";
		}
		
		return result;
	}

}
