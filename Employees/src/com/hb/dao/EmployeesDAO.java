package com.hb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.hb.vo.EmployeesVO;

public class EmployeesDAO {
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	
	public EmployeesDAO() {
		try {
			Context init = new InitialContext();
			DataSource ds = (DataSource) init.lookup("java:comp/env/jdbc/OracleDB");
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
		}finally {
			if(rs!=null)try {rs.close();} catch (SQLException sql) {}
			if(pstmt!=null)try {pstmt.close();} catch (SQLException sql) {}
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
		}
		return result;
	}
	
	// 사원 목록 가져오기 보류
	/*public List<EmployeesVO> getEmpList(){
		int e_state = 0;
		try {
			String sql = "select * from employees where e_state = ? order by e_hire_date";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, e_state);
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}*/
}	
