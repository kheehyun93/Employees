package com.hb.cmd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hb.dao.EmployeesDAO;
import com.hb.vo.EmployeesVO;
import com.hb.vo.Paging;

import oracle.net.aso.h;
import oracle.security.o3logon.a;

public class EmpSearchCommand implements Command{

	@Override
	public String exec(HttpServletRequest request, HttpServletResponse response) {
		String e_rank = request.getParameter("rankCategory");
		String e_dept = request.getParameter("deptCategory");
		String e_name = request.getParameter("e_name");
		String e_tel = request.getParameter("e_tel");
		
		EmployeesDAO edao = new EmployeesDAO();
		Paging pvo = new Paging();
		
		// list 무조건 cPage 부터 받기.
		// cPage 는 현재 페이지를 변경함. null 이면 현재페이지가 기본값 1이 된다.
		String cPage = request.getParameter("cPage");
		if(cPage != null){
			pvo.setNowPage(Integer.parseInt(cPage));
		}
		
		Map<String, String> map = new HashMap<>();
		map.put("e_rank", e_rank);
		map.put("e_dept", e_dept);
		map.put("e_tel", e_tel);
		map.put("e_name", e_name);
		
		//전체 게시물의 수
		pvo.setTotalRecord(edao.empTotalSearchCount(map));
		pvo.setTotalPage();
		
		// begin 과 end 구하기
		pvo.setBegin((pvo.getNowPage()-1)*pvo.getNumPerPage()+1);
		pvo.setEnd((pvo.getBegin()-1)+pvo.getNumPerPage());
		
		Map<String, Integer> map2 = new HashMap<>();
		map2.put("begin", pvo.getBegin());
		map2.put("end", pvo.getEnd());
		
		List<EmployeesVO> list = new ArrayList<>();
		
		
		list = edao.empSearch(map,map2);
		
		//  블록의 시작번호 끝번호 구하기
		pvo.setBeginPage((int)((pvo.getNowPage()-1)/pvo.getPagePerBlock())*pvo.getPagePerBlock()+1);
		pvo.setEndPage(pvo.getBeginPage() + pvo.getPagePerBlock()-1);
			 
		if(pvo.getEndPage() > pvo.getTotalPage()){
			pvo.setEndPage(pvo.getTotalPage());
		}
		
		// jsp에서 페이징 처리할때 필요하므로
		request.setAttribute("pvo", pvo);
		request.setAttribute("cPage", cPage);
		
		request.setAttribute("empSearchList", list);
		request.setAttribute("e_rank", e_rank);
		request.setAttribute("e_dept", e_dept);
		request.setAttribute("e_name", e_name);
		request.setAttribute("e_tel", e_tel);
		
		
		return "[employees]/EmpList.jsp";
	}

}
