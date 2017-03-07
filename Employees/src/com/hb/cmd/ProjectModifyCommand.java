package com.hb.cmd;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hb.dao.ProjectDAO;
import com.hb.vo.ProjectVO;

public class ProjectModifyCommand implements Command{

	@Override
	public String exec(HttpServletRequest request, HttpServletResponse response) {
		ProjectDAO pdao = new ProjectDAO();
		ProjectVO pvo = new ProjectVO();
		
		System.out.println("p_code"+Integer.parseInt(request.getParameter("p_code")));
		pvo.setP_code(Integer.parseInt(request.getParameter("p_code")));
		pvo.setP_name(request.getParameter("p_name"));
		pvo.setP_content(request.getParameter("p_content"));
		pvo.setP_start(request.getParameter("p_start"));
		pvo.setP_end(request.getParameter("p_end"));
		pvo.setP_order(request.getParameter("p_order"));
		
		String result = pdao.projectUpdate(pvo);
		
		return result;
	}

}
