package com.hb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.hb.vo.CareerVO;
import com.hb.vo.MasterVO;
import com.hb.vo.SchoolVO;

public class MasterDAO {
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	DataSource ds;
	
	public MasterDAO() {
		try {
			Context init = new InitialContext();
			ds = (DataSource) init.lookup("java:comp/env/jdbc/OracleDB");
			conn = ds.getConnection();
		} catch (Exception e) {
			System.out.println("DB 연결 실패 : "+e);
			return;
		}
	}
	// p_code로 정보 삭제
	public String masterDel(int p_code){
		int result = 0;
		try {
			String sql = "delete from master where p_code = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, p_code);
			
			result = pstmt.executeUpdate();
			
			if(result==0){
				return "fail";
			}
			
			return "success";
		} catch (Exception e) {
			System.out.println("masterDel 에러 : "+e);
		} finally {
			if(rs!=null)try {rs.close();} catch (SQLException sql) {}
			if(pstmt!=null)try {pstmt.close();} catch (SQLException sql) {}
			if(conn!=null)try {conn.close();} catch (SQLException sql) {}
		}
		return "fail";
	}
	// 등록
	public boolean MasterInsert(MasterVO mvo){
		int result = 0;
		try {
			String sql = "insert into master values(?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mvo.getP_code());
			pstmt.setString(2, mvo.getE_id());
			pstmt.setString(3, mvo.getM_start());
			pstmt.setString(4, mvo.getM_end());
			pstmt.setString(5, mvo.getM_task());
			pstmt.setString(6, mvo.getM_etc());
			result = pstmt.executeUpdate();
			
			if(result==0){
				return false;
			}
			
			return true;
		} catch (Exception e) {
			System.out.println("MasterInsert 에러 : "+e);
		}
		return false;
	}
	public void close(){
		if(rs!=null)try {rs.close();} catch (SQLException sql) {}
		if(pstmt!=null)try {pstmt.close();} catch (SQLException sql) {}
		if(conn!=null)try {conn.close();} catch (SQLException sql) {}
	}
	
	// 수정
	public boolean MasterUpdate(MasterVO mvo){
		int result = 0;
		try {
			String sql = "update master set m_start=?,m_end=?,m_task=?,m_etc=? where e_id=? and p_code=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, mvo.getM_start());
			pstmt.setString(2, mvo.getM_end());
			pstmt.setString(3, mvo.getM_task());
			pstmt.setString(4, mvo.getM_etc());
			pstmt.setString(5, mvo.getE_id());
			pstmt.setInt(6, mvo.getP_code());
			
			result = pstmt.executeUpdate();
			
			if(result==0){
				return false;
			}
			
			return true;
		} catch (Exception e) {
			System.out.println("MasterUpdate 에러 : "+e);
		}
		return false;
	}
	// 삭제
	public boolean empMasterDel(int p_code,String e_id){
		int result = 0;
		try {
			String sql = "delete from master where p_code = ? and e_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, p_code);
			pstmt.setString(2, e_id);
			
			result = pstmt.executeUpdate();
			
			if(result==0){
				return false;
			}
			
			return true;
		} catch (Exception e) {
			System.out.println("empMasterDel 에러 : "+e);
		}
		return false;
	}
}
