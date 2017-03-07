package com.hb.cmd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hb.dao.ProjectDAO;
import com.hb.vo.Paging;
import com.hb.vo.ProjectVO;

public class ProjectListCommand implements Command{

	@Override
	public String exec(HttpServletRequest request, HttpServletResponse response) {
		ProjectDAO dao = new ProjectDAO();
		Paging pvo = new Paging();
		
		String cPage = request.getParameter("cPage");
		if(cPage != null){ // null 이면 현재페이지가 기본값 1이 된다.
			pvo.setNowPage(Integer.parseInt(cPage));
		}
		
		// 전체 게시물의 수
		pvo.setTotalRecord(dao.proTotalCount());
		pvo.setTotalPage();
		
		// begin 과 end
		pvo.setBegin((pvo.getNowPage()-1)*pvo.getNumPerPage()+1);
		pvo.setEnd((pvo.getBegin()-1)+pvo.getNumPerPage());
		
		Map<String, Integer> map = new HashMap<>();
		map.put("begin", pvo.getBegin());
		map.put("end", pvo.getEnd());
		
		// 프로젝트 정보들 불러오기
		List<ProjectVO> list = dao.getProjectList(map);
		request.setAttribute("proList", list);
		
		for (ProjectVO k : list) {
			System.out.println(k.getP_code()+" : pcode");
		}
		
		//  블록의 시작번호 끝번호 구하기
		pvo.setBeginPage((int)((pvo.getNowPage()-1)/pvo.getPagePerBlock())*pvo.getPagePerBlock()+1);
		pvo.setEndPage(pvo.getBeginPage() + pvo.getPagePerBlock()-1);
			 
		if(pvo.getEndPage() > pvo.getTotalPage()){
			pvo.setEndPage(pvo.getTotalPage());
		}
		
		// jsp에서 페이징 처리할때 필요하므로
		request.setAttribute("pvo", pvo);
		request.setAttribute("cPage", cPage);
		
		return "[project]/ProjectList.jsp";
	}

}
