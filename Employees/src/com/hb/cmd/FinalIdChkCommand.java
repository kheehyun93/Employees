package com.hb.cmd;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hb.dao.EmployeesDAO;
import com.hb.vo.EmployeesVO;

public class FinalIdChkCommand implements Command{

	@Override
	public String exec(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String jumin = request.getParameter("jumin");

		EmployeesDAO dao = new EmployeesDAO();
		
		Map<String, String> map = new HashMap<String,String>();
		map.put("id", id);
		map.put("name", name);
		map.put("jumin",jumin);
		
		String result = dao.getFinalId(map);
		return result;
	}

}
