package com.hb.cmd;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hb.dao.ProjectDAO;
import com.hb.vo.ProEmpJoinVO;

public class ProEmpListCommand implements Command{

	@Override
	public String exec(HttpServletRequest request, HttpServletResponse response) {
		int p_code = Integer.parseInt(request.getParameter("p_code"));
		
		ProjectDAO pdao = new ProjectDAO();
		List<ProEmpJoinVO> list = pdao.getProEmpList(p_code);
		/*for (ProEmpJoinVO k : list) {
			System.out.println(k.getE_id());
			System.out.println(k.getE_name());
		}
		request.setAttribute("ProEmpList", list);*/
		String result = "[";
		
		int idx = 0;
		if(list.size()>0){
			
			for (ProEmpJoinVO k : list) {
				idx++;
				result += "{";
				result += "\"e_id\" : \""+k.getE_id()+ "\",";
				result += "\"e_name\" : \""+k.getE_name()+ "\"";
				result += "}";
				if(idx != list.size()){
					result += ",";
				}
				System.out.println(k.getE_id()+"--"+k.getE_name());
			}
			
		}
		result += "]";
		
		
		System.out.println(result);
		return result;
	}




}
