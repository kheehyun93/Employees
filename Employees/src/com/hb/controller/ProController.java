package com.hb.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hb.cmd.Command;
import com.hb.cmd.EmpListCommand;
import com.hb.cmd.EmpOnelistCommand;
import com.hb.cmd.FinalIdChkCommand;
import com.hb.cmd.InputCheckCommand;
import com.hb.cmd.LoginCommand;
import com.hb.cmd.ProEmpListCommand;
import com.hb.cmd.ProjectAddCommand;
import com.hb.cmd.ProjectDeleteCommand;
import com.hb.cmd.ProjectListCommand;
import com.hb.cmd.ProjectModifyCommand;
import com.hb.cmd.PwdEmailChkCommand;
import com.hb.cmd.PwdIdChkCommand;
import com.hb.cmd.SendEmailCommand;
import com.hb.cmd.proFindCommand;
import com.hb.cmd.proFindSearchCommand;
import com.hb.cmd.proSearchCommand;
import com.hb.vo.ProEmpJoinVO;

/**
 * Servlet implementation class Controller
 */
@WebServlet("/ProController")
public class ProController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		String type = request.getParameter("type");
		Command cmd = null;
		String path = null;
		
		System.out.println("ProController : "+type);
		
		if(type.equals("proAdd")){
			cmd = new ProjectAddCommand();
			String result = cmd.exec(request, response);
			out.println(result);
			return;
		}else if(type.equals("proList")){
			cmd = new ProjectListCommand();
		}else if(type.equals("proModify")){
			cmd = new ProjectModifyCommand();
			String result = cmd.exec(request, response);
			out.println(result);
			return;
		}else if(type.equals("proDel")){
			cmd = new ProjectDeleteCommand();
			String result = cmd.exec(request, response);
			out.println(result);
			return;
		}else if(type.equals("proEmpList")){
			cmd = new ProEmpListCommand();
			String result = cmd.exec(request, response);
			out.println(result);
			return;
		}else if(("proSearch").equals(type)){
			cmd = new proSearchCommand();

		}else if(("proFind").equals(type)){
			cmd = new proFindCommand();
		}else if(("proFindSearch").equals(type)){
			cmd = new proFindSearchCommand();
		}
		

		path = cmd.exec(request, response);
		request.getRequestDispatcher(path).forward(request, response);	
		
		
	}


}
