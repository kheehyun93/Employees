package com.hb.cmd;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hb.dao.EmployeesDAO;

public class EmployeesAddGoCommand implements Command{

	@Override
	public String exec(HttpServletRequest request, HttpServletResponse response) {
		EmployeesDAO edao = new EmployeesDAO();
		String e_idx = String.valueOf(edao.getEmpIdx()+1);
		System.out.println(e_idx);
		request.setAttribute("e_idx", e_idx);
		return "[employees]/EmployeesAdd.jsp";
	}

}
