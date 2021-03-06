package com.hb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.hb.vo.EmployeesVO;
import com.hb.vo.MasterVO;
import com.hb.vo.ProEmpJoinVO;
import com.hb.vo.ProjectJoinVO;
import com.hb.vo.ProjectVO;

public class ProjectDAO {
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	Statement stmt;
	DataSource ds;
	
	public ProjectDAO() {
		try {
			Context init = new InitialContext();
			ds = (DataSource) init.lookup("java:comp/env/jdbc/OracleDB");
			conn = ds.getConnection();
		} catch (Exception e) {
			System.out.println("DB 연결 실패 : "+e);
			return;
		}
	}
	
	//5. 프로젝트 수행 경력
	public List<ProjectJoinVO> getProInfo(String id){
		List<ProjectJoinVO> list = new ArrayList<>();
		ProjectJoinVO pro = null;
		try {
			String sql = "select p.p_name,p.p_content,m.m_start,m.m_end,m.m_task,p.p_order,m.m_etc,p.p_code,p.p_start,p.p_end,p.p_state from master m, project p where m.p_code = p.p_code and e_id = ? order by m.m_end";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			while(rs.next()){
				pro = new ProjectJoinVO();
				pro.setP_name(rs.getString(1));
				pro.setP_content(rs.getString(2));
				pro.setM_start(rs.getString(3));
				pro.setM_end(rs.getString(4));
				pro.setM_task(rs.getString(5));
				pro.setP_order(rs.getString(6));				
				if(rs.getString(7)==null){
					pro.setM_etc("");
				}else{
					pro.setM_etc(rs.getString(7));
				}
				pro.setP_code(rs.getString(8));
				pro.setP_start(rs.getString(9));
				pro.setP_end(rs.getString(10));
				pro.setP_state(rs.getString(11));
				list.add(pro);
			}
			
			
		} catch (Exception e) {
			System.out.println("getProInfo 에러 : "+e);
		}finally {
			if(rs!=null)try {rs.close();} catch (SQLException sql) {}
			if(pstmt!=null)try {pstmt.close();} catch (SQLException sql) {}
			if(conn!=null)try {conn.close();} catch (SQLException sql) {}
		}
		return list;
	}
	
	// 프로젝트 등록
	public String projectInsert(ProjectVO pvo){
		int result = 0;
		int num = 0;
		try {
			String sql = "insert into project values(pro_seq.nextval,?,?,?,?,?,?,"+0+")";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, pvo.getP_name());
			pstmt.setString(2, pvo.getP_content());
			pstmt.setString(3, pvo.getP_start());
			pstmt.setString(4, pvo.getP_end());
			pstmt.setInt(5, num);
			pstmt.setString(6, pvo.getP_order());
			result = pstmt.executeUpdate();
			
			if(result==0){
				return "fail";
			}
			
			return "/Employees/ProController?type=proList";
		} catch (Exception e) {
			System.out.println("projectInsert 에러 : "+e);
		}finally {
			if(rs!=null)try {rs.close();} catch (SQLException sql) {}
			if(pstmt!=null)try {pstmt.close();} catch (SQLException sql) {}
			if(conn!=null)try {conn.close();} catch (SQLException sql) {}
		}
		return "fail";
	}
	
	// 프로젝트 전체 게시물의 수
	public int proTotalCount(){
		int count = 0;
		try {
			String sql = "select count(*) from project where p_state = "+0;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if(rs.next()){
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			System.out.println("proTotalCount 에러 : "+e);
		}
		System.out.println(count+" : proTotalCount");
		return count;
	}
	
	// 프로젝트 목록 가져오기
	public List<ProjectVO> getProjectList(Map<String, Integer> map){
		List<ProjectVO> list = new ArrayList<>();
		ProjectVO pvo = null;
		
		int begin = map.get("begin");
		int end = map.get("end");
		
		try {
			String sql = "select * from (select rownum r_num, a.* from ( select * from project where p_state = "+0+" order by p_start ) a ) where r_num between ? and ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, begin);
			pstmt.setInt(2, end);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				pvo = new ProjectVO();
				pvo.setP_code(rs.getInt(2));
				pvo.setP_name(rs.getString(3));
				pvo.setP_content(rs.getString(4));
				pvo.setP_start(rs.getString(5));
				pvo.setP_end(rs.getString(6));
				pvo.setP_num(rs.getInt(7));
				pvo.setP_order(rs.getString(8));
				list.add(pvo);
			}
		} catch (Exception e) {
			System.out.println("getProjectList 에러 : "+e);
		}finally {
			if(rs!=null)try {rs.close();} catch (SQLException sql) {}
			if(pstmt!=null)try {pstmt.close();} catch (SQLException sql) {}
			if(conn!=null)try {conn.close();} catch (SQLException sql) {}
		}

		return list;
	}
	
	// 프로젝트 수정
	public String projectUpdate(ProjectVO pvo){
		int result = 0;
		try {
			String sql = "update project set p_name=?,p_content=?,p_start=?,p_end=?,p_order=? where p_code=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, pvo.getP_name());
			pstmt.setString(2, pvo.getP_content());
			pstmt.setString(3, pvo.getP_start());
			pstmt.setString(4, pvo.getP_end());
			pstmt.setString(5, pvo.getP_order());
			pstmt.setInt(6, pvo.getP_code());
			
			result = pstmt.executeUpdate();
			System.out.println("result"+result);
			if(result==0){
				return "fail";
			}
			
			return "success";
		} catch (Exception e) {
			System.out.println("projectUpdate 에러 : "+e);
		}finally {
			if(rs!=null)try {rs.close();} catch (SQLException sql) {}
			if(pstmt!=null)try {pstmt.close();} catch (SQLException sql) {}
			if(conn!=null)try {conn.close();} catch (SQLException sql) {}
		}
		return "fail";
	}
	
	// 프로젝트 삭제
	// p_code로 정보 삭제
	public String tabelDel(int p_code){
		int result = 0;
		try {
			String sql = "delete from project where p_code = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, p_code);
			
			result = pstmt.executeUpdate();
			
			if(result==0){
				return "fail";
			}
			
			return "success";
		} catch (Exception e) {
			System.out.println("tabelDel 에러 : "+e);
		} finally {
			if(rs!=null)try {rs.close();} catch (SQLException sql) {}
			if(pstmt!=null)try {pstmt.close();} catch (SQLException sql) {}
			if(conn!=null)try {conn.close();} catch (SQLException sql) {}
		}
		return "fail";
	}
	
	// 프로젝트, 사원 테이블 조인
	public List<ProEmpJoinVO> getProEmpList(int p_code){
		List<ProEmpJoinVO> list = new ArrayList<>();
		ProEmpJoinVO pvo = null;
		try {
			String sql = "select e.e_id,e.e_name from master m,employees e where m.e_id=e.e_id and p_code=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, p_code);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				pvo = new ProEmpJoinVO();
				pvo.setE_id(rs.getString(1));
				pvo.setE_name(rs.getString(2));
				list.add(pvo);
			}
			
		} catch (Exception e) {
			System.out.println("getProEmpList 에러 : "+e);
		}finally {
			if(rs!=null)try {rs.close();} catch (SQLException sql) {}
			if(pstmt!=null)try {pstmt.close();} catch (SQLException sql) {}
			if(conn!=null)try {conn.close();} catch (SQLException sql) {}
		}
		return list;
	}
	
	// 검색
	
	//1. p_end 값이 있을때
	public List<ProjectVO> proSearchEnd(Map<String, String> map){
		List<ProjectVO> list = new ArrayList<>();
		ProjectVO pvo = null;
		String p_name = map.get("p_name");
		String p_order = map.get("p_order");
		String p_end = map.get("p_end");
		
		try {
			String sql = "select * from project where upper(p_name) like upper('%' || ? || '%') and upper(p_order) like upper('%' || ? || '%') and p_end <= ? and p_state ="+0;
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, p_name);
			pstmt.setString(2, p_order);
			pstmt.setString(3, p_end);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				pvo = new ProjectVO();
				pvo.setP_code(rs.getInt(1));
				pvo.setP_name(rs.getString(2));
				pvo.setP_content(rs.getString(3));
				pvo.setP_start(rs.getString(4));
				pvo.setP_end(rs.getString(5));
				pvo.setP_num(rs.getInt(6));
				pvo.setP_order(rs.getString(7));
				list.add(pvo);
			}
			
		} catch (Exception e) {
			System.out.println("proSearchEnd 에러 : "+e);
		}finally {
			if(rs!=null)try {rs.close();} catch (SQLException sql) {}
			if(pstmt!=null)try {pstmt.close();} catch (SQLException sql) {}
			if(conn!=null)try {conn.close();} catch (SQLException sql) {}
		}
		return list;
	}
	
	//2. p_start 값이 있을때
	public List<ProjectVO> proSearchOr(Map<String, String> map,Map<String, Integer> map2){
		List<ProjectVO> list = new ArrayList<>();
		ProjectVO pvo = null;
		String p_name = map.get("p_name");
		String p_order = map.get("p_order");
		String p_start = map.get("p_start");
		String p_end = map.get("p_end");
		
		int begin = map2.get("begin");
		int end = map2.get("end");
		//
		try {
			//String sql = "select * from project where upper(p_name) like upper('%' || ? || '%') and upper(p_order) like upper('%' || ? || '%') and (p_start >= ? or p_end <= ?) and p_state =0;
			String sql = "select * from (select rownum r_num, a.* from (select * from project where upper(p_name) like upper('%' || ? || '%') and upper(p_order) like upper('%' || ? || '%') and (p_start >= ? or p_end <= ?) and p_state =0 ) a ) where r_num between ? and ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, p_name);
			pstmt.setString(2, p_order);
			pstmt.setString(3, p_start);
			pstmt.setString(4, p_end);
			pstmt.setInt(5, begin);
			pstmt.setInt(6, end);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				pvo = new ProjectVO();
				pvo.setP_code(rs.getInt(2));
				pvo.setP_name(rs.getString(3));
				pvo.setP_content(rs.getString(4));
				pvo.setP_start(rs.getString(5));
				pvo.setP_end(rs.getString(6));
				pvo.setP_num(rs.getInt(7));
				pvo.setP_order(rs.getString(8));
				list.add(pvo);
			}
			
		} catch (Exception e) {
			System.out.println("proSearchOr 에러 : "+e);
		}finally {
			if(rs!=null)try {rs.close();} catch (SQLException sql) {}
			if(pstmt!=null)try {pstmt.close();} catch (SQLException sql) {}
			if(conn!=null)try {conn.close();} catch (SQLException sql) {}
		}
		return list;
	}
	
	//3. 둘다 null 값일때
	public List<ProjectVO> proSearchNull(Map<String, String> map,Map<String, Integer> map2){
		List<ProjectVO> list = new ArrayList<>();
		ProjectVO pvo = null;
		String p_name = map.get("p_name");
		String p_order = map.get("p_order");
		int begin = map2.get("begin");
		int end = map2.get("end");
		
		try {
			//String sql = "select * from project where upper(p_name) like upper('%' || ? || '%') and upper(p_order) like upper('%' || ? || '%') and p_state="+0;
			String sql = "select * from (select rownum r_num, a.* from (select * from project where upper(p_name) like upper('%' || ? || '%') and upper(p_order) like upper('%' || ? || '%') and p_state=0) a ) where r_num between ? and ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, p_name);
			pstmt.setString(2, p_order);
			pstmt.setInt(3, begin);
			pstmt.setInt(4, end);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				pvo = new ProjectVO();
				pvo.setP_code(rs.getInt(2));
				pvo.setP_name(rs.getString(3));
				pvo.setP_content(rs.getString(4));
				pvo.setP_start(rs.getString(5));
				pvo.setP_end(rs.getString(6));
				pvo.setP_num(rs.getInt(7));
				pvo.setP_order(rs.getString(8));
				list.add(pvo);
			}
			
		} catch (Exception e) {
			System.out.println("proSearchNull 에러 : "+e);
		}finally {
			if(rs!=null)try {rs.close();} catch (SQLException sql) {}
			if(pstmt!=null)try {pstmt.close();} catch (SQLException sql) {}
			if(conn!=null)try {conn.close();} catch (SQLException sql) {}
		}
		return list;
	}
	
	//4. 둘다 값이 있을때
	public List<ProjectVO> proSearch(Map<String, String> map,Map<String, Integer> map2){
		List<ProjectVO> list = new ArrayList<>();
		ProjectVO pvo = null;
		String p_name = map.get("p_name");
		String p_order = map.get("p_order");
		String p_start = map.get("p_start");
		String p_end = map.get("p_end");
		
		int begin = map2.get("begin");
		int end = map2.get("end");
		
		//"select * from (select rownum r_num, a.* from (select * from project where upper(p_name) like upper('%' || ? || '%') and upper(p_order) like upper('%' || ? || '%') and p_start >= ? and p_end <= ? and p_state=0) a ) where r_num between ? and ?";
		
		try {
			//String sql = "select * from project where upper(p_name) like upper('%' || ? || '%') and upper(p_order) like upper('%' || ? || '%') and p_start >= ? and p_end <= ? and p_state=0";
			String sql = "select * from (select rownum r_num, a.* from (select * from project where upper(p_name) like upper('%' || ? || '%') and upper(p_order) like upper('%' || ? || '%') and p_start >= ? and p_end <= ? and p_state=0) a ) where r_num between ? and ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, p_name);
			pstmt.setString(2, p_order);
			pstmt.setString(3, p_start);
			pstmt.setString(4, p_end);
			pstmt.setInt(5, begin);
			pstmt.setInt(6, end);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				pvo = new ProjectVO();
				pvo.setP_code(rs.getInt(2));
				pvo.setP_name(rs.getString(3));
				pvo.setP_content(rs.getString(4));
				pvo.setP_start(rs.getString(5));
				pvo.setP_end(rs.getString(6));
				pvo.setP_num(rs.getInt(7));
				pvo.setP_order(rs.getString(8));
				list.add(pvo);
			}
			
		} catch (Exception e) {
			System.out.println("proSearch 에러 : "+e);
		}finally {
			if(rs!=null)try {rs.close();} catch (SQLException sql) {}
			if(pstmt!=null)try {pstmt.close();} catch (SQLException sql) {}
			if(conn!=null)try {conn.close();} catch (SQLException sql) {}
		}
		return list;
	}
	
	//  프로젝트 인원 업데이트
	public boolean projectNumUpdate(int p_code){
		int result = 0;
		try {
			String sql = "update project set p_num = (select count(*) from master where p_code = ?) where p_code=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, p_code);
			pstmt.setInt(2, p_code);
			
			result = pstmt.executeUpdate();

			if(result==0){
				return false;
			}
			
			return true;
		} catch (Exception e) {
			System.out.println("projectNumUpdate 에러 : "+e);
		}
		return false;
	}
	
	// 프로젝트 직접 입력 등록
	public boolean projectDirectInsert(ProjectVO pvo){
		int result = 0;
		int num = 1;
		try {
			String sql = "insert into project values(pro_seq.nextval,?,?,?,?,?,?,"+1+")";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, pvo.getP_name());
			pstmt.setString(2, pvo.getP_content());
			pstmt.setString(3, pvo.getP_start());
			pstmt.setString(4, pvo.getP_end());
			pstmt.setInt(5, num);
			pstmt.setString(6, pvo.getP_order());
			result = pstmt.executeUpdate();
			
			if(result==0){
				return false;
			}
			
			return true;
		} catch (Exception e) {
			System.out.println("projectDirectInsert 에러 : "+e);
		}
		return false;
	}
	
	public int getP_code(){
		int p_code = 0;
		try {
			String sql = "select max(p_code) from project";
			//String sql = "select last_number from user_sequences where sequence_name= UPPER('emp_seq')";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if(rs.next()){
				p_code = rs.getInt(1);
			}
		} catch (Exception e) {
			System.out.println("getP_code 에러 : "+e);
		}
		return p_code;
	}
	public void close(){
		if(rs!=null)try {rs.close();} catch (SQLException sql) {}
		if(pstmt!=null)try {pstmt.close();} catch (SQLException sql) {}
		if(conn!=null)try {conn.close();} catch (SQLException sql) {}
	}
	
	// 프로젝트 수정
	public boolean projectDirectUpdate(ProjectVO pvo){
		int result = 0;
		try {
			String sql = "update project set p_name=?,p_content=?,p_start=?,p_end=?,p_order=? where p_code=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, pvo.getP_name());
			pstmt.setString(2, pvo.getP_content());
			pstmt.setString(3, pvo.getP_start());
			pstmt.setString(4, pvo.getP_end());
			pstmt.setString(5, pvo.getP_order());
			pstmt.setInt(6, pvo.getP_code());
			result = pstmt.executeUpdate();
			
			if(result==0){
				return false;
			}
			
			return true;
		} catch (Exception e) {
			System.out.println("projectDirectUpdate 에러 : "+e);
		}
		return false;
	}
	
	// 프로젝트 삭제
	public boolean empProjectDel(int p_code){
		int result = 0;
		try {
			String sql = "delete from project where p_code = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, p_code);
			
			result = pstmt.executeUpdate();
			
			if(result==0){
				return false;
			}
			
			return true;
		} catch (Exception e) {
			System.out.println("empProjectDel 에러 : "+e);
		}
		return false;
	}
	
	// 검색
	public int proSearchNullCount(Map<String, String> map){
		int count = 0;
		String p_name = map.get("p_name");
		String p_order = map.get("p_order");
		try {
			String sql = "select count(*) from project where upper(p_name) like upper('%' || ? || '%') and upper(p_order) like upper('%' || ? || '%') and p_state=0";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, p_name);
			pstmt.setString(2, p_order);
			rs = pstmt.executeQuery();
			if(rs.next()){
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			System.out.println("proSearchNullCount 에러 : "+e);
		}
		
		return count;
	}
	
	public int proSearchCount(Map<String, String> map){
		int count = 0;
		String p_name = map.get("p_name");
		String p_order = map.get("p_order");
		String p_start = map.get("p_start");
		String p_end = map.get("p_end");
		try {
			String sql = "select count(*) from project where upper(p_name) like upper('%' || ? || '%') and upper(p_order) like upper('%' || ? || '%') and p_start >= ? and p_end <= ? and p_state=0";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, p_name);
			pstmt.setString(2, p_order);
			pstmt.setString(3, p_start);
			pstmt.setString(4, p_end);
			rs = pstmt.executeQuery();
			if(rs.next()){
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			System.out.println("proSearchCount 에러 : "+e);
		}
		
		return count;
	}
	
	public int proSearchOrCount(Map<String, String> map){
		int count = 0;
		String p_name = map.get("p_name");
		String p_order = map.get("p_order");
		String p_start = map.get("p_start");
		String p_end = map.get("p_end");
		try {
			String sql = "select count(*) from project where upper(p_name) like upper('%' || ? || '%') and upper(p_order) like upper('%' || ? || '%') and (p_start >= ? or p_end <= ?) and p_state =0 ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, p_name);
			pstmt.setString(2, p_order);
			pstmt.setString(3, p_start);
			pstmt.setString(4, p_end);
			rs = pstmt.executeQuery();
			if(rs.next()){
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			System.out.println("proSearchOrCount 에러 : "+e);
		}
		
		return count;
	}
	
}
