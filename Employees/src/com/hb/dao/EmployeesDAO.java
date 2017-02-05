package com.hb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.catalina.connector.Request;

import com.hb.vo.CareerVO;
import com.hb.vo.EmployeesVO;
import com.hb.vo.ProjectJoinVO;

public class EmployeesDAO {
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	DataSource ds;
	
	public EmployeesDAO() {
		try {
			Context init = new InitialContext();
			ds = (DataSource) init.lookup("java:comp/env/jdbc/OracleDB");
			conn = ds.getConnection();
		} catch (Exception e) {
			System.out.println("DB 연결 실패 : "+e);
			return;
		}
	}
	
	// 로그인 정보 모두 저장
	public EmployeesVO loginCheck(String id,String pwd){
		EmployeesVO evo = null;
		try {
			String sql = "select * from employees where e_id=? and e_pwd=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2,pwd);
			rs = pstmt.executeQuery();
			if(rs.next()){
				evo = new EmployeesVO();
				evo.setE_id(rs.getString(1));
				evo.setE_idx(rs.getInt(2));
				evo.setE_name(rs.getString(3));
				evo.setE_jumin(rs.getString(4));
				evo.setE_email(rs.getString(5));
				evo.setE_tel(rs.getString(6));
				evo.setE_addr(rs.getString(7));
				evo.setE_post(rs.getString(8));
				evo.setE_rank(rs.getString(9));
				evo.setE_dept(rs.getString(10));
				evo.setE_hire_date(rs.getString(11));
				evo.setE_img(rs.getString(12));
				evo.setE_pwd(rs.getString(13));
				evo.setE_state(rs.getInt(14));
			}
			
		} catch (Exception e) {
			System.out.println("loginCheck 에러 : "+e);
		}finally {
			if(rs!=null)try {rs.close();} catch (SQLException sql) {}
			if(pstmt!=null)try {pstmt.close();} catch (SQLException sql) {}
			if(conn!=null)try {conn.close();} catch (SQLException sql) {}
		}
		return evo;
	}
	
	// 사원번호 찾기
	public String getFindEmail(String email){
		String e_id = null;
		String result = null;
		
		try {
			String sql = "select e_id from employees where e_email=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				e_id = rs.getString(1);
			}
			
			
			
			if(e_id != null){
				result = "find"+e_id;
			}else{
				result = "notFind";
			}
			
		} catch (Exception e) {
			System.out.println("getFindEmail 에러 : "+e);
		}
		return result;
	}
	public String getFindTel(String tel){
		String e_id = null;
		String result = null;
		
		try {
			String sql = "select e_id from employees where e_tel=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, tel);
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				e_id = rs.getString(1);
			}
			
			if(e_id != null){
				result = "find"+e_id;
			}else{
				result = "notFind";
			}
			
		} catch (Exception e) {
			System.out.println("getFindTel 에러 : "+e);
		}finally {
			if(rs!=null)try {rs.close();} catch (SQLException sql) {}
			if(pstmt!=null)try {pstmt.close();} catch (SQLException sql) {}
			if(conn!=null)try {conn.close();} catch (SQLException sql) {}
		}
		return result;
	}
	public String getFinalId(Map<String, String> map){
		String id = map.get("id");
		String name = map.get("name");
		String jumin = map.get("jumin");
		System.out.println("dao:  "+id+jumin+name);
		String e_id = null;
		String result = null;
		try {
			String sql = "select e_id from employees where e_id = ? and e_name = ? and e_jumin = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, name);
			pstmt.setString(3, jumin);
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				e_id = rs.getString(1);
				System.out.println(e_id);
			}
			
			if(e_id != null){
				result = "find"+e_id;
			}else{
				result = "notFind";
			}
			
			System.out.println(result);
		}catch (Exception e) {
			System.out.println("getFinalId 에러 : "+e);
		}finally {
			if(rs!=null)try {rs.close();} catch (SQLException sql) {}
			if(pstmt!=null)try {pstmt.close();} catch (SQLException sql) {}
			if(conn!=null)try {conn.close();} catch (SQLException sql) {}
		}
		return result;
	}
	
	// 비밀번호 찾기
	public String getFindId(String id){
		String e_id = null;
		String result = null;
		
		try {
			String sql = "select e_id from employees where e_id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				e_id = rs.getString(1);
			}
			
			if(e_id != null){
				result = "find"+e_id;
			}else{
				result = "notFind";
			}
			
		} catch (Exception e) {
			System.out.println("getFindId 에러 : "+e);
		}finally {
			if(rs!=null)try {rs.close();} catch (SQLException sql) {}
			if(pstmt!=null)try {pstmt.close();} catch (SQLException sql) {}
			if(conn!=null)try {conn.close();} catch (SQLException sql) {}
		}
		return result;
	}
	
	public String getPwdEmail(String id){
		String e_email = null;
		String result = null;
		
		try {
			String sql = "select e_email from employees where e_id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				e_email = rs.getString(1);
			}
			
			if(e_email != null){
				result = "find"+e_email;
			}else{
				result = "notFind";
			}
			
		} catch (Exception e) {
			System.out.println("getPwdEmail 에러 : "+e);
		}finally {
			if(rs!=null)try {rs.close();} catch (SQLException sql) {}
			if(pstmt!=null)try {pstmt.close();} catch (SQLException sql) {}
			if(conn!=null)try {conn.close();} catch (SQLException sql) {}
		}
		return result;
	}
	
	// 전체 게시물의 수
	public int empTotalCount(){
		int count = 0;
		int e_state = 0;
		try {
			String sql = "select count(*) from employees where e_state = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, e_state);
			rs = pstmt.executeQuery();
			if(rs.next()){
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			System.out.println("empTotalCount 에러 : "+e);
		}
		
		System.out.println(count);
		return count;
	}
	
	// 사원 목록 가져오기
	public List<EmployeesVO> getEmployessList(Map<String, Integer> map){
		List<EmployeesVO> list = new ArrayList<>();
		EmployeesVO evo = null;
		
		int e_state = 0;
		int begin = map.get("begin");
		int end = map.get("end");
		
		try {
			String sql = "select * from (select rownum r_num, a.* from (select * from employees where e_state = ? "
					+ "order by e_hire_date) a ) where r_num between ? and ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, e_state);
			pstmt.setInt(2, begin);
			pstmt.setInt(3, end);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				evo = new EmployeesVO();
				evo.setE_id(rs.getString(2));
				evo.setE_idx(rs.getInt(3));
				evo.setE_name(rs.getString(4));
				evo.setE_jumin(rs.getString(5));
				evo.setE_email(rs.getString(6));
				evo.setE_tel(rs.getString(7));
				evo.setE_addr(rs.getString(8));
				evo.setE_post(rs.getString(9));
				evo.setE_rank(rs.getString(10));
				evo.setE_dept(rs.getString(11));
				evo.setE_hire_date(rs.getString(12));
				evo.setE_img(rs.getString(13));
				evo.setE_pwd(rs.getString(14));
				evo.setE_state(rs.getInt(15));
				list.add(evo);
				
			}
			return list;
		} catch (Exception e) {
			System.out.println("getEmployessList 에러 : "+e);
		}finally {
			if(rs!=null)try {rs.close();} catch (SQLException sql) {}
			if(pstmt!=null)try {pstmt.close();} catch (SQLException sql) {}
			if(conn!=null)try {conn.close();} catch (SQLException sql) {}
		}
		return null;
	}
	
	// 1. 기본정보
	public EmployeesVO getFirstInfo(String id){
		EmployeesVO evo = null;
		try {
			String sql = "select * from employees where e_id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()){
				evo = new EmployeesVO();
				evo.setE_id(rs.getString(1));
				evo.setE_idx(rs.getInt(2));
				evo.setE_name(rs.getString(3));
				evo.setE_jumin(rs.getString(4));
				evo.setE_email(rs.getString(5));
				evo.setE_tel(rs.getString(6));
				evo.setE_addr(rs.getString(7));
				evo.setE_post(rs.getString(8));
				evo.setE_rank(rs.getString(9));
				evo.setE_dept(rs.getString(10));
				evo.setE_hire_date(rs.getString(11));
				evo.setE_img(rs.getString(12));
				evo.setE_pwd(rs.getString(13));
				evo.setE_state(rs.getInt(14));
			}
			
		} catch (Exception e) {
			System.out.println("getFirstInfo 에러 : "+e);
		}finally {
			if(rs!=null)try {rs.close();} catch (SQLException sql) {}
			if(pstmt!=null)try {pstmt.close();} catch (SQLException sql) {}
			if(conn!=null)try {conn.close();} catch (SQLException sql) {}
		}
		return evo;
	}
	
	// 메일로 비밀번호 변경
	public boolean pwdUpdate(String id, String pwd){
		int result = 0;
		try {
			String sql = "update employees set e_pwd=? where e_id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, pwd);
			pstmt.setString(2, id);
			
			result = pstmt.executeUpdate();
			
			if(result==0){
				return false;
			}
			
			return true;
		} catch (Exception e) {
			System.out.println("pwdUpdate 에러 : "+e);
		}finally {
			if(rs!=null)try {rs.close();} catch (SQLException sql) {}
			if(pstmt!=null)try {pstmt.close();} catch (SQLException sql) {}
			if(conn!=null)try {conn.close();} catch (SQLException sql) {}
		}
		return false;
	}
	
	// 본인 비밀번호 재설정
	public String emppwdUpdate(Map<String, String> map){
		int result = 0;
		String e_id = map.get("e_id");
		String pwd = map.get("pwd");
		String newPwd = map.get("newPwd");
		
		try {
			String sql = "update employees set e_pwd=? where e_id=? and e_pwd=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, newPwd);
			pstmt.setString(2, e_id);
			pstmt.setString(3, pwd);
			
			result = pstmt.executeUpdate();
			System.out.println("result"+result);
			
			if(result==0){
				return "fail";
			}
			
			return "susccess";
			
		} catch (Exception e) {
			System.out.println("emppwdUpdate 에러 : "+e);
		}finally {
			if(rs!=null)try {rs.close();} catch (SQLException sql) {}
			if(pstmt!=null)try {pstmt.close();} catch (SQLException sql) {}
			if(conn!=null)try {conn.close();} catch (SQLException sql) {}
		}
		return "fail";
	}
	
	// emp search
	public List<EmployeesVO> empSearch(Map<String, String> map){
		List<EmployeesVO> list = new ArrayList<>();
		EmployeesVO evo = null;
		String e_rank = map.get("e_rank");
		String e_dept = map.get("e_dept");
		String e_name = map.get("e_name");
		String e_tel = map.get("e_tel");
		
		int e_state = 0;
		
		try {
			String sql = "select * from employees where e_rank like '%' || ? || '%' and e_dept like '%' || ? || '%' and e_name like '%' || ? || '%' and e_tel like '%' || ? || '%' and e_state=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, e_rank);
			pstmt.setString(2, e_dept);
			pstmt.setString(3, e_name);
			pstmt.setString(4, e_tel);
			pstmt.setInt(5, e_state);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				evo = new EmployeesVO();
				evo.setE_id(rs.getString(1));
				evo.setE_idx(rs.getInt(2));
				evo.setE_name(rs.getString(3));
				evo.setE_jumin(rs.getString(4));
				evo.setE_email(rs.getString(5));
				evo.setE_tel(rs.getString(6));
				evo.setE_addr(rs.getString(7));
				evo.setE_post(rs.getString(8));
				evo.setE_rank(rs.getString(9));
				evo.setE_dept(rs.getString(10));
				evo.setE_hire_date(rs.getString(11));
				evo.setE_img(rs.getString(12));
				evo.setE_pwd(rs.getString(13));
				evo.setE_state(rs.getInt(14));
				list.add(evo);
				
			}
			return list;
		} catch (Exception e) {
			System.out.println("empSearch 에러 : "+e);
		}finally {
			if(rs!=null)try {rs.close();} catch (SQLException sql) {}
			if(pstmt!=null)try {pstmt.close();} catch (SQLException sql) {}
			if(conn!=null)try {conn.close();} catch (SQLException sql) {}
		}
		return null;
	}
}	
