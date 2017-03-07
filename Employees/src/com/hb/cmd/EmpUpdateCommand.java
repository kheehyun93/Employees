package com.hb.cmd;

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
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class EmpUpdateCommand implements Command{

	@Override
	public String exec(HttpServletRequest request, HttpServletResponse response) {
		String path = null;
		try {
			
			// 파일처리
			String savePath = request.getServletContext().getRealPath("/upload/");
			// 파일 크기 20mb 로 제한
			int sizeLimit = 1024*1024*20;
			
			MultipartRequest mr = new MultipartRequest(request, savePath,sizeLimit,"utf-8", new DefaultFileRenamePolicy());
			
			String fileName=mr.getFilesystemName("photoSel");
			String e_img = null;
			System.out.println("--"+fileName+"--");
			
			if(fileName!=null){
				e_img = "/Employees/upload/"+fileName;
			}else{
				e_img = mr.getParameter("filechk");
			}
			boolean chk = true;
			String filechk = e_img.substring(e_img.lastIndexOf(".")+1);
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
				evo.setE_name(mr.getParameter("e_name"));
				evo.setE_email(mr.getParameter("e_email")+"@"+mr.getParameter("e_email2"));
				evo.setE_tel(mr.getParameter("e_tel"));
				evo.setE_addr(mr.getParameter("e_addr")+"-"+mr.getParameter("e_addr2"));
				evo.setE_post(mr.getParameter("e_post"));
				evo.setE_rank(mr.getParameter("e_rankCategory"));
				evo.setE_dept(mr.getParameter("e_deptCategory"));
				evo.setE_img(e_img);
				evo.setE_pwd(mr.getParameter("e_jumin"));
				evo.setE_id(e_id);
				
				boolean eResult = edao.EmployeesUpdate(evo);
				
				// 2. 학력
				String[] s_idx = mr.getParameterValues("s_idx");
				String[] s_state = mr.getParameterValues("s_state");
				String[] s_start = mr.getParameterValues("s_start");
				String[] s_end = mr.getParameterValues("s_end");
				String[] s_name = mr.getParameterValues("s_name");
				String[] s_major = mr.getParameterValues("s_major");
				
				SchoolDAO sdao = new SchoolDAO();
				SchoolVO svo = new SchoolVO();
				
				// 학력 삭제
				String[] dels_idx = mr.getParameterValues("dels_idx");
				
				// 삭제
				if(dels_idx!=null){
					for (int i = 0; i < dels_idx.length; i++) {
						if(dels_idx[i].length()!=0){
							eResult = sdao.SchoolDel(Integer.parseInt(dels_idx[i]));
						}
					}
				}
				if(s_start!=null){
					for (int i = 0; i < s_start.length; i++) {
						if(s_start[i].length()!=0){
							if(("0").equals(s_state[i])){
								// 추가된 값
								svo.setE_id(e_id);
								svo.setS_start(s_start[i]);
								svo.setS_end(s_end[i]);
								svo.setS_name(s_name[i]);
								svo.setS_major(s_major[i]);
								eResult = sdao.SchoolInsert(svo);
							}else if(("1").equals(s_state[i])){
								// 원래 있던 값
								svo.setS_idx(Integer.parseInt(s_idx[i]));
								svo.setE_id(e_id);
								svo.setS_start(s_start[i]);
								svo.setS_end(s_end[i]);
								svo.setS_name(s_name[i]);
								svo.setS_major(s_major[i]);
								eResult = sdao.SchoolUpdate(svo);
							}
						}
					}
				}
				sdao.close();
				
				
				// 3.경력사항
				String[] c_idx = mr.getParameterValues("c_idx");
				String[] c_state = mr.getParameterValues("c_state");
				String[] c_start = mr.getParameterValues("c_start");
				String[] c_end = mr.getParameterValues("c_end");
				String[] c_name = mr.getParameterValues("c_name");
				String[] rankCategory = mr.getParameterValues("c_rankCategory");
				String[] c_task = mr.getParameterValues("c_task");
				
				String[] delc_idx = mr.getParameterValues("delc_idx");
				
				
				CareerDAO cdao = new CareerDAO();
				CareerVO cvo = new CareerVO();
				
				// 삭제
				if(delc_idx!=null){
					for (int i = 0; i < delc_idx.length; i++) {
						if(delc_idx[i].length()!=0){
							eResult = cdao.CareerDel(Integer.parseInt(delc_idx[i]));
						}
					}
				}
				
				
				
				for (int i = 0; i < c_start.length; i++) {
					if(c_start[i].length()!=0){
						if(("0").equals(c_state[i])){
							// 추가된 값
							cvo.setE_id(e_id);
							cvo.setC_start(c_start[i]);
							cvo.setC_end(c_end[i]);
							cvo.setC_name(c_name[i]);
							cvo.setC_rank(rankCategory[i]);
							cvo.setC_task(c_task[i]);
							eResult = cdao.CareerInsert(cvo);
						}else if(("1").equals(c_state[i])){
							// 원래 있던 값
							cvo.setE_id(e_id);
							cvo.setC_start(c_start[i]);
							cvo.setC_end(c_end[i]);
							cvo.setC_name(c_name[i]);
							cvo.setC_rank(rankCategory[i]);
							cvo.setC_task(c_task[i]);
							cvo.setC_idx(Integer.parseInt(c_idx[i]));
							eResult = cdao.CareerUpdate(cvo);
						}
					}
				}
				cdao.close();
				
				// 4. 자격증
				String[] l_idx = mr.getParameterValues("l_idx");
				String[] l_state = mr.getParameterValues("l_state");
				String[] l_name = mr.getParameterValues("l_name");
				String[] l_date = mr.getParameterValues("l_date");
					
				LicenseDAO ldao = new LicenseDAO();
				LicenseVO lvo = new LicenseVO();
				
				String[] dell_idx = mr.getParameterValues("dell_idx");
				
				// 삭제
				if(dell_idx!=null){
					for (int i = 0; i < dell_idx.length; i++) {
						if(dell_idx[i].length()!=0){
							eResult = ldao.LicenseDel(Integer.parseInt(dell_idx[i]));
						}
					}
				}
				
				
				for (int i = 0; i < l_name.length; i++) {
					if(l_name[i].length()!=0){
						if(("0").equals(l_state[i])){
							// 추가된 값
							lvo.setE_id(e_id);
							lvo.setL_name(l_name[i]);
							lvo.setL_date(l_date[i]);
							eResult = ldao.LicenseInsert(lvo);
						}else if(("1").equals(l_state[i])){
							// 원래 있던 값
							lvo.setE_id(e_id);
							lvo.setL_name(l_name[i]);
							lvo.setL_date(l_date[i]);
							lvo.setL_idx(Integer.parseInt(l_idx[i]));
							eResult = ldao.LicenseUpdate(lvo);
						}
					}
				}
				ldao.close();
				
				// 5. 프로젝트 수행경력
				ProjectDAO pdao = new ProjectDAO();
				MasterDAO mdao = new MasterDAO();
				MasterVO mvo = new MasterVO();
				ProjectVO pvo = new ProjectVO();
				
				// 삭제
				
				// 찾기 master 테이블 삭제, 프로젝트 테이블 업데이트
				String[] delmp_code = mr.getParameterValues("delmp_code");
				if(delmp_code!=null){
					for (int i = 0; i < delmp_code.length; i++) {
						eResult = mdao.empMasterDel(Integer.parseInt(delmp_code[i]),e_id);
						eResult = pdao.projectNumUpdate(Integer.parseInt(delmp_code[i]));
					}
				}
				
				// 직접입력 프로젝트,마스터 테이블 삭제
				String[] delp_code = mr.getParameterValues("delp_code");
				if(delp_code!=null){
					for (int i = 0; i < delp_code.length; i++) {
						eResult = mdao.empMasterDel(Integer.parseInt(delp_code[i]),e_id);
						eResult = pdao.empProjectDel(Integer.parseInt(delp_code[i]));
					}
				}
				
				// 수정
				String[] up_name = mr.getParameterValues("up_name");
				String[] up_content = mr.getParameterValues("up_content");
				String[] up_start = mr.getParameterValues("up_start");
				String[] up_end = mr.getParameterValues("up_end");
				String[] up_order = mr.getParameterValues("up_order");
				
				String[] upm_start = mr.getParameterValues("upm_start");
				String[] upm_end = mr.getParameterValues("upm_end");
				String[] up_task = mr.getParameterValues("up_task");
				String[] up_etc = mr.getParameterValues("up_etc");
				
				String[] up_code = mr.getParameterValues("up_code");
				String[] up_state = mr.getParameterValues("up_state");
				
				if(up_state!=null){
					for (int i = 0; i < up_state.length; i++) {
						if("0".equals(up_state[i])){
							// 찾기
							mvo.setP_code(Integer.parseInt(up_code[i]));
							mvo.setE_id(e_id);
							mvo.setM_start(upm_start[i]);
							mvo.setM_end(upm_end[i]);
							mvo.setM_task(up_task[i]);
							mvo.setM_etc(up_etc[i]);
							eResult = mdao.MasterUpdate(mvo);
							eResult = pdao.projectNumUpdate(Integer.parseInt(up_code[i]));
						}else{
							// 직접입력
							pvo.setP_name(up_name[i]);
							pvo.setP_content(up_content[i]);
							pvo.setP_start(up_start[i]);
							pvo.setP_end(up_end[i]);
							pvo.setP_order(up_order[i]);
							pvo.setP_code(Integer.parseInt(up_code[i]));
							
							eResult = pdao.projectDirectUpdate(pvo);
							
							mvo.setP_code(Integer.parseInt(up_code[i]));
							mvo.setE_id(e_id);
							mvo.setM_start(upm_start[i]);
							mvo.setM_end(upm_end[i]);
							mvo.setM_task(up_task[i]);
							mvo.setM_etc(up_etc[i]);
							eResult = mdao.MasterUpdate(mvo);
							
						}
					}
				}
				
				// 추가
				String[] p_code = mr.getParameterValues("p_code");
				String[] m_start = mr.getParameterValues("m_start");
				String[] m_end = mr.getParameterValues("m_end");
				String[] m_task = mr.getParameterValues("m_task");
				String[] m_etc = mr.getParameterValues("m_etc");
					
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
							eResult = mdao.MasterInsert(mvo);
							eResult = pdao.projectNumUpdate(Integer.parseInt(p_code[i]));
						}
					}
				}
				
				// 직접입력
				
				String[] dp_name = mr.getParameterValues("dp_name");
				String[] dp_content = mr.getParameterValues("dp_content");
				String[] dm_start = mr.getParameterValues("dm_start");
				String[] dm_end = mr.getParameterValues("dm_end");
				String[] dm_task = mr.getParameterValues("dm_task");
				String[] dp_order = mr.getParameterValues("dp_order");
				String[] dm_etc = mr.getParameterValues("dm_etc");
				String[] dp_start = mr.getParameterValues("dp_start");
				String[] dp_end = mr.getParameterValues("dp_end");
					
				if(dp_name!=null){
					for (int i = 0; i < dp_name.length; i++) {
						if(dp_name[i].length()!=0){
							pvo.setP_name(dp_name[i]);
							pvo.setP_content(dp_content[i]);
							pvo.setP_start(dp_start[i]);
							pvo.setP_end(dp_end[i]);
							pvo.setP_order(dp_order[i]);
							
							eResult = pdao.projectDirectInsert(pvo);
							int dp_code = pdao.getP_code();
							System.out.println("dp_code"+dp_code);
							
							mvo.setP_code(dp_code);
							mvo.setE_id(e_id);
							mvo.setM_start(dm_start[i]);
							mvo.setM_end(dm_end[i]);
							mvo.setM_task(dm_task[i]);
							mvo.setM_etc(dm_etc[i]);
							eResult = mdao.MasterInsert(mvo);
						}
						
					}
				}
				
				
				mdao.close();
				pdao.close();
				
				System.out.println(eResult);
				System.out.println(chk);
				if(eResult==true&&chk==true){
					response.setContentType("text/html; charset=utf-8");
					String id = String.valueOf(e_id);
					System.out.println(id);
					PrintWriter out = response.getWriter();
					out.println("<script>");
					out.println("alert('수정 성공');");
					out.println("var id='"+id+"';");
					out.println("location.href='/Employees/EmpController?type=empOnelist&id='+id;");
					out.println("</script>");
					out.close();
					return null;
				}else{
					response.setContentType("text/html; charset=utf-8");
	
					PrintWriter out = response.getWriter();
					out.println("<script>");
					out.println("alert('수정 실패');");
					out.println("location.href='/Employees/EmpController?type=empOnelist&e_id='"+e_id+";");
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
