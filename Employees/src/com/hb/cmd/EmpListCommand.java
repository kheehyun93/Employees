package com.hb.cmd;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hb.dao.EmployeesDAO;
import com.hb.vo.EmployeesVO;

public class EmpListCommand implements Command{

	@Override
	public String exec(HttpServletRequest request, HttpServletResponse response) {
		EmployeesDAO dao = new EmployeesDAO();
		
		List<EmployeesVO> list = new ArrayList<>();
		/*
		list = dao.getEmpList();*/
		
		return null;
	}

}
