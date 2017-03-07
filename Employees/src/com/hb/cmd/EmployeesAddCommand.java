package com.hb.cmd;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hb.dao.CareerDAO;
import com.hb.dao.EmployeesDAO;
import com.hb.dao.LicenseDAO;
import com.hb.dao.MasterDAO;
import com.hb.dao.ProjectDAO;
import com.hb.dao.SchoolDAO;
import com.hb.vo.CareerVO;
import com.hb.vo.EmployeesVO;
import com.hb.vo.LicenseVO;
import com.hb.vo.MasterVO;
import com.hb.vo.ProjectVO;
import com.hb.vo.SchoolVO;
import com.oreilly.servlet.MultipartFilter;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class EmployeesAddCommand implements Command{

	@Override
	public String exec(HttpServletRequest request, HttpServletResponse response){
		String path = null;
		try {
			
			// 파일처리
			String savePath = request.getServletContext().getRealPath("/upload/");
			// 파일 크기 20mb 로 제한
			int sizeLimit = 1024*1024*20;
			
			MultipartRequest mr = new MultipartRequest(request, savePath,sizeLimit,"utf-8", new DefaultFileRenamePolicy());
			String fileName = mr.getFilesystemName("photoSel");
			String e_img = "/Employees/upload/"+fileName;
			System.out.println(fileName);
			
			if(fileName==null){
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('파일을 등록해주세요.');");
				out.println("history.go(-1);");
				out.println("</script>");
				out.close();
				return null;
			}
			boolean chk = true;
			String filechk = fileName.substring(fileName.lastIndexOf(".")+1);
			System.out.println(filechk);
			switch (filechk.toLowerCase()) {
			case "jpg":
			case "jpeg":
			case "bmp":
			case "gif":
			case "png":
			break;

			default:
				chk=false;
				break;
			}
			
			if(chk==false){
				response.setContentType("text/html; charset=utf-8");
				
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('이미지 파일만 업로드할 수 있습니다.');");
				out.println("location.href='/Employees/EmpController?type=empAdd_go';");
				out.println("</script>");
				out.close();
				return null;
			}else{
			
				EmployeesDAO edao = new EmployeesDAO();
				EmployeesVO evo = new EmployeesVO();
				
				String e_id = mr.getParameter("e_id");
				
				// 1. 기본사항
				evo.setE_id(e_id);
				evo.setE_name(mr.getParameter("e_name"));
				evo.setE_jumin(mr.getParameter("e_jumin"));
				evo.setE_email(mr.getParameter("e_email")+"@"+mr.getParameter("e_email2"));
				evo.setE_tel(mr.getParameter("e_tel"));
				System.out.println("e_addr : "+mr.getParameter("e_addr"));
				System.out.println("e_post : "+mr.getParameter("e_post"));
				evo.setE_addr(mr.getParameter("e_addr")+"-"+mr.getParameter("e_addr2"));
				evo.setE_post(mr.getParameter("e_post"));
				evo.setE_rank(mr.getParameter("e_rankCategory"));
				evo.setE_dept(mr.getParameter("e_deptCategory"));
				evo.setE_hire_date(mr.getParameter("e_hire_date"));
				evo.setE_img(e_img);
				evo.setE_pwd(mr.getParameter("e_jumin"));
				
				
				boolean eResult = edao.EmployeesInsert(evo);
				
				// 2. 학력
				String[] s_start = mr.getParameterValues("s_start");
				String[] s_end = mr.getParameterValues("s_end");
				String[] s_name = mr.getParameterValues("s_name");
				String[] s_major = mr.getParameterValues("s_major");
				
				boolean sResult = true;
				SchoolDAO sdao = new SchoolDAO();
				SchoolVO svo = new SchoolVO();
				
				for (int i = 0; i < s_start.length; i++) {
					if(s_start[i].length()!=0){
						svo.setE_id(e_id);
						svo.setS_start(s_start[i]);
						svo.setS_end(s_end[i]);
						svo.setS_name(s_name[i]);
						svo.setS_major(s_major[i]);
						sResult = sdao.SchoolInsert(svo);
					}
				}
				sdao.close();
				
				
				// 3.경력사항
				
				int cidx = Integer.parseInt(request.getParameter("cidx"));
				boolean cResult = true;
				String[] c_start = mr.getParameterValues("c_start");
				String[] c_end = mr.getParameterValues("c_end");
				String[] c_name = mr.getParameterValues("c_name");
				String[] rankCategory = mr.getParameterValues("c_rankCategory");
				String[] c_task = mr.getParameterValues("c_task");
				
				CareerDAO cdao = new CareerDAO();
				CareerVO cvo = new CareerVO();
				
				
				for (int i = 0; i < c_start.length; i++) {
					if(c_start[i].length()!=0){
						cvo.setE_id(e_id);
						cvo.setC_start(c_start[i]);
						cvo.setC_end(c_end[i]);
						cvo.setC_name(c_name[i]);
						cvo.setC_rank(rankCategory[i]);
						cvo.setC_task(c_task[i]);
						cResult = cdao.CareerInsert(cvo);
					}
				}
				cdao.close();
				
				// 4. 자격증
				int lidx = Integer.parseInt(request.getParameter("lidx"));
				boolean lResult = true;
				String[] l_name = mr.getParameterValues("l_name");
				String[] l_date = mr.getParameterValues("l_date");
					
				LicenseDAO ldao = new LicenseDAO();
				LicenseVO lvo = new LicenseVO();
				
				
				for (int i = 0; i < l_name.length; i++) {
					if(l_name[i].length()!=0){
						lvo.setE_id(e_id);
						lvo.setL_name(l_name[i]);
						lvo.setL_date(l_date[i]);
						lResult = ldao.LicenseInsert(lvo);
					}
				}
				ldao.close();
				
				// 5. 프로젝트 수행경력
				int pidx = Integer.parseInt(request.getParameter("pidx"));
				
				ProjectDAO pdao = new ProjectDAO();
				MasterDAO mdao = new MasterDAO();
				
				boolean findResult = true;
				boolean findUpResult = true;
				
				String[] p_code = mr.getParameterValues("p_code");
				String[] m_start = mr.getParameterValues("m_start");
				String[] m_end = mr.getParameterValues("m_end");
				String[] m_task = mr.getParameterValues("m_task");
				String[] m_etc = mr.getParameterValues("m_etc");
					
				MasterVO mvo = new MasterVO();
				
				if(p_code!=null){
					for (int i = 0; i < p_code.length; i++) {
						if(p_code[i].length()!=0){
							mvo.setP_code(Integer.parseInt(p_code[i]));
							System.out.println("p_code"+p_code[i]);
							mvo.setE_id(e_id);
							mvo.setM_start(m_start[i]);
							mvo.setM_end(m_end[i]);
							mvo.setM_task(m_task[i]);
							mvo.setM_etc(m_etc[i]);
							findResult = mdao.MasterInsert(mvo);
							findUpResult = pdao.projectNumUpdate(Integer.parseInt(p_code[i]));
						}
					}
				}
				
				
					
				// 직접입력
				boolean directResult = true;
				boolean MdirectResult = true;
				String[] dp_name = mr.getParameterValues("dp_name");
				String[] dp_content = mr.getParameterValues("dp_content");
				String[] dm_start = mr.getParameterValues("dm_start");
				String[] dm_end = mr.getParameterValues("dm_end");
				String[] dm_task = mr.getParameterValues("dm_task");
				String[] dp_order = mr.getParameterValues("dp_order");
				String[] dm_etc = mr.getParameterValues("dm_etc");
				String[] dp_start = mr.getParameterValues("dp_start");
				String[] dp_end = mr.getParameterValues("dp_end");
					
				ProjectVO pvo = new ProjectVO();
				MasterVO dmvo = new MasterVO();
				
				if(dp_name!=null){
					for (int i = 0; i < dp_name.length; i++) {
						if(dp_name[i].length()!=0){
							pvo.setP_name(dp_name[i]);
							pvo.setP_content(dp_content[i]);
							pvo.setP_start(dp_start[i]);
							pvo.setP_end(dp_end[i]);
							pvo.setP_order(dp_order[i]);
							
							directResult = pdao.projectDirectInsert(pvo);
							int dp_code = pdao.getP_code();
							System.out.println("dp_code"+dp_code);
							
							dmvo.setP_code(dp_code);
							dmvo.setE_id(e_id);
							dmvo.setM_start(dm_start[i]);
							dmvo.setM_end(dm_end[i]);
							dmvo.setM_task(dm_task[i]);
							dmvo.setM_etc(dm_etc[i]);
							MdirectResult = mdao.MasterInsert(dmvo);
						}
						
					}
				}
					
				
				mdao.close();
				pdao.close();
				
				if(cResult==true&&eResult==true&&lResult==true&&sResult==true&&findResult==true&&findUpResult==true&&directResult==true&&MdirectResult==true&&chk==true){
					response.setContentType("text/html; charset=utf-8");
					
					PrintWriter out = response.getWriter();
					out.println("<script>");
					out.println("alert('등록 성공');");
					out.println("location.href='/Employees/EmpController?type=empAdd_go';");
					out.println("</script>");
					out.close();
					return null;
				}else{
					response.setContentType("text/html; charset=utf-8");
	
					PrintWriter out = response.getWriter();
					out.println("<script>");
					out.println("alert('등록 실패');");
					out.println("location.href='/Employees/EmpController?type=empAdd_go';");
					out.println("</script>");
					out.close();
					return null;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "Employees/EmpController";
	}

}
