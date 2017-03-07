package com.hb.cmd;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hb.dao.MasterDAO;
import com.hb.dao.ProjectDAO;

public class ProjectDeleteCommand implements Command{

	@Override
	public String exec(HttpServletRequest request, HttpServletResponse response) {
		int p_code = Integer.parseInt(request.getParameter("p_code"));
		System.out.println("p_code"+request.getParameter("p_code"));
		ProjectDAO pdao = new ProjectDAO();
		MasterDAO mdao = new MasterDAO();
		
		// master 테이블 사원 먼저 삭제
		String result1 = mdao.masterDel(p_code);
		
		// 프로젝트 테이블 삭제
		String result2 = pdao.tabelDel(p_code);
		
		String result = null;
		if(result1.substring(0,4)!="fail" || result2.substring(0,4)!="fail" ||(  result1.substring(0,4)!="fail" && result2.substring(0,4)!="fail")){
			result = "success"; 
		}else{
			result = "fail";
		}
		return result;
	}

}
