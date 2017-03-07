package com.hb.cmd;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hb.dao.EmployeesDAO;
import com.hb.vo.EmployeesVO;

public class PwdUpdateCommand implements Command{

	@Override
	public String exec(HttpServletRequest request, HttpServletResponse response) {
		String e_id = request.getParameter("e_id");
		String pwd = request.getParameter("pwd");
		String newPwd = request.getParameter("newPwd");
		System.out.println(pwd+newPwd);

		EmployeesDAO edao = new EmployeesDAO();
		EmployeesVO evo = new EmployeesVO();
		
		Map<String, String> map = new HashMap<>();
		map.put("e_id", e_id);
		map.put("pwd", pwd);
		map.put("newPwd", newPwd);
		
		String result = edao.emppwdUpdate(map);
		
		return result;
	}

}
