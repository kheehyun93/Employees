package com.hb.cmd;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hb.dao.EmployeesDAO;
import com.hb.vo.EmployeesVO;

public class EmployeesDelCommand implements Command{

	@Override
	public String exec(HttpServletRequest request, HttpServletResponse response) {
		String e_id = request.getParameter("e_id");
		EmployeesDAO edao = new EmployeesDAO();
		String result = edao.empDel(e_id);
		/*String path = null; //170228139
		
		try {
			PrintWriter out = response.getWriter();
			if(result.substring(0,4)!="fail"){
				out.println("<script>");
				out.println("alert('퇴사처리 성공');");
				out.println("</script>");
				out.close();
				path = "EmpController?type=empList";
				return path;
			}else {
				out.println("<script>");
				out.println("alert('퇴사처리 실패');");
				out.println("history.go(-1);");
				out.println("</script>");
				out.close();
				return null;
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		return result;
	}

}
