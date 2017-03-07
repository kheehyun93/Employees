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

public class proFindSearchCommand implements Command{

	@Override
	public String exec(HttpServletRequest request, HttpServletResponse response) {
		String p_name = request.getParameter("p_name");
		String p_order = request.getParameter("p_order");
		String p_start = request.getParameter("p_start");
		String p_end = request.getParameter("p_end");
		
		ProjectDAO pdao = new ProjectDAO();
		Paging pvo = new Paging();
		List<ProjectVO> list = new ArrayList<>();
		
		String cPage = request.getParameter("cPage");
		if(cPage != null){
			pvo.setNowPage(Integer.parseInt(cPage));
		}
		
		Map<String, String> map = new HashMap<>();
		Map<String, Integer> map2 = new HashMap<>();
		
		map.put("p_name", p_name);
		map.put("p_order", p_order);
		
		if((p_start=="") && (p_end=="")){
			//전체 게시물의 수
			pvo.setTotalRecord(pdao.proSearchNullCount(map));
			pvo.setTotalPage();
			
			// begin 과 end 구하기
			pvo.setBegin((pvo.getNowPage()-1)*pvo.getNumPerPage()+1);
			pvo.setEnd((pvo.getBegin()-1)+pvo.getNumPerPage());
			
			map2.put("begin", pvo.getBegin());
			map2.put("end", pvo.getEnd());
			
			list = pdao.proSearchNull(map,map2);
		}else if((p_start!="") && (p_end!="")){
			map.put("p_end", p_end);
			map.put("p_start", p_start);
			
			//전체 게시물의 수
			pvo.setTotalRecord(pdao.proSearchCount(map));
			pvo.setTotalPage();
			
			// begin 과 end 구하기
			pvo.setBegin((pvo.getNowPage()-1)*pvo.getNumPerPage()+1);
			pvo.setEnd((pvo.getBegin()-1)+pvo.getNumPerPage());
			
			map2.put("begin", pvo.getBegin());
			map2.put("end", pvo.getEnd());
			
			list = pdao.proSearch(map,map2);
		}else{
			map.put("p_end", p_end);
			map.put("p_start", p_start);
			
			//전체 게시물의 수
			pvo.setTotalRecord(pdao.proSearchOrCount(map));
			pvo.setTotalPage();
			
			// begin 과 end 구하기
			pvo.setBegin((pvo.getNowPage()-1)*pvo.getNumPerPage()+1);
			pvo.setEnd((pvo.getBegin()-1)+pvo.getNumPerPage());
			
			map2.put("begin", pvo.getBegin());
			map2.put("end", pvo.getEnd());
			
			list = pdao.proSearchOr(map,map2);
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
		
		request.setAttribute("proSearchList", list);
		request.setAttribute("p_name", p_name);
		request.setAttribute("p_order", p_order);
		request.setAttribute("p_start", p_start);
		request.setAttribute("p_end", p_end);
		
		String prow = request.getParameter("prow");
		request.setAttribute("prow", prow);
		
		return "[employees]/ProjectListPopup.jsp";
	}

}
