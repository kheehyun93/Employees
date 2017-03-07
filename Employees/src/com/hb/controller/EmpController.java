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
import com.hb.cmd.EmpJuminChkCommand;
import com.hb.cmd.EmpListCommand;
import com.hb.cmd.EmpOnelistCommand;
import com.hb.cmd.EmpSearchCommand;
import com.hb.cmd.EmpUpdateCommand;
import com.hb.cmd.EmpUpdategoCommand;
import com.hb.cmd.EmployeesAddCommand;
import com.hb.cmd.EmployeesAddGoCommand;
import com.hb.cmd.EmployeesDelCommand;
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
import com.hb.cmd.PwdUpdateCommand;
import com.hb.cmd.SendEmailCommand;
import com.hb.vo.ProEmpJoinVO;

/**
 * Servlet implementation class Controller
 */
@WebServlet("/EmpController")
public class EmpController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmpController() {
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
		
		System.out.println("EmpController : "+type);
	
		if(type.equals("empList")){
			cmd = new EmpListCommand();
		}else if(type.equals("empOnelist")){
			cmd = new EmpOnelistCommand();
		}else if(("pwdUpdate").equals(type)){
			cmd = new PwdUpdateCommand();
			String result = cmd.exec(request, response);
			out.println(result);
			return;
		}else if(("empSearch").equals(type)){
			cmd = new EmpSearchCommand();
		}else if(("empAdd_go").equals(type)){
			cmd = new EmployeesAddGoCommand();
		}else if(("empAdd").equals(type)){
			cmd = new EmployeesAddCommand();
			cmd.exec(request, response);
			return;
		}else if(("empDel".equals(type))){
			cmd = new EmployeesDelCommand();
			String result = cmd.exec(request, response);
			out.println(result);
			return;
		}else if(("empUp_go".equals(type))){
			cmd = new EmpUpdategoCommand();
		}else if(("empUp").equals(type)){
			cmd = new EmpUpdateCommand();
			cmd.exec(request, response);
			return;
		}else if(("jumin_chk").equals(type)){
			cmd = new EmpJuminChkCommand();
			String result = cmd.exec(request, response);
			out.println(result);
			return;
		}
		
		
		path = cmd.exec(request, response);
		request.getRequestDispatcher(path).forward(request, response);	
		
		
	}


}
