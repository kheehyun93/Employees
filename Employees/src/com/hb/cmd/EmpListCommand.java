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
import com.hb.vo.ProEmpJoinVO;

public class EmpListCommand implements Command{

	@Override
	public String exec(HttpServletRequest request, HttpServletResponse response) {
		EmployeesDAO dao = new EmployeesDAO();
		Paging pvo = new Paging();
		
		// list 무조건 cPage 부터 받기.
		// cPage 는 현재 페이지를 변경함. null 이면 현재페이지가 기본값 1이 된다.
		
		String cPage = request.getParameter("cPage");
		if(cPage != null){
			pvo.setNowPage(Integer.parseInt(cPage));
		}
		
		//전체 게시물의 수
		pvo.setTotalRecord(dao.empTotalCount());
		pvo.setTotalPage();
		
		// begin 과 end 구하기
		pvo.setBegin((pvo.getNowPage()-1)*pvo.getNumPerPage()+1);
		pvo.setEnd((pvo.getBegin()-1)+pvo.getNumPerPage());
		
		Map<String, Integer> map = new HashMap<>();
		map.put("begin", pvo.getBegin());
		map.put("end", pvo.getEnd());
		
		// 사원정보 불러오기		
		List<EmployeesVO> list = dao.getEmployessList(map);
		request.setAttribute("empList", list);
		
		//  블록의 시작번호 끝번호 구하기
		pvo.setBeginPage((int)((pvo.getNowPage()-1)/pvo.getPagePerBlock())*pvo.getPagePerBlock()+1);
		pvo.setEndPage(pvo.getBeginPage() + pvo.getPagePerBlock()-1);
			 
		if(pvo.getEndPage() > pvo.getTotalPage()){
			pvo.setEndPage(pvo.getTotalPage());
		}
		
		// jsp에서 페이징 처리할때 필요하므로
		request.setAttribute("pvo", pvo);
		request.setAttribute("cPage", cPage);
		
		return "[employees]/EmpList.jsp";
	}


}
