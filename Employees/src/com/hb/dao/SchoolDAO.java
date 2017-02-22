package com.hb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.hb.vo.EmployeesVO;
import com.hb.vo.LicenseVO;
import com.hb.vo.SchoolVO;

public class SchoolDAO {
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	DataSource ds;
	
	public SchoolDAO() {
		try {
			Context init = new InitialContext();
			ds = (DataSource) init.lookup("java:comp/env/jdbc/OracleDB");
			conn = ds.getConnection();
		} catch (Exception e) {
			System.out.println("DB 연결 실패 : "+e);
			return;
		}
	}
	
	public List<SchoolVO> getSchoolInfo(String id){
		List<SchoolVO> list = new ArrayList<>();
		SchoolVO svo = null;
		try {
			String sql = "select * from school where e_id = ? order by s_start";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			while(rs.next()){
				svo = new SchoolVO();
				svo.setS_idx(rs.getInt(1));
				svo.setE_id(rs.getString(2));
				svo.setS_start(rs.getString(3));
				svo.setS_end(rs.getString(4));
				svo.setS_name(rs.getString(5));
				svo.setS_major(rs.getString(6));
				list.add(svo);				
			}
		} catch (Exception e) {
			System.out.println("getSchoolInfo 에러 : "+e);
		}finally {
			if(rs!=null)try {rs.close();} catch (SQLException sql) {}
			if(pstmt!=null)try {pstmt.close();} catch (SQLException sql) {}
			if(conn!=null)try {conn.close();} catch (SQLException sql) {}
		}
		return list;
	}
	
	// school 추가
	public boolean SchoolInsert(SchoolVO svo){
		int result = 0;
		try {
			String sql = "insert into school values(sch_seq.nextval,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, svo.getE_id());
			pstmt.setString(2, svo.getS_start());
			pstmt.setString(3, svo.getS_end());
			pstmt.setString(4, svo.getS_name());
			pstmt.setString(5, svo.getS_major());
			result = pstmt.executeUpdate();
			
			if(result==0){
				return false;
			}
			
			return true;
		} catch (Exception e) {
			System.out.println("SchoolInsert 에러 : "+e);
		}
		return false;
	}
	public void close(){
		if(rs!=null)try {rs.close();} catch (SQLException sql) {}
		if(pstmt!=null)try {pstmt.close();} catch (SQLException sql) {}
		if(conn!=null)try {conn.close();} catch (SQLException sql) {}
	}
	
	// 수정
	public boolean SchoolUpdate(SchoolVO svo){
		int result = 0;
		try {
			String sql = "update school set s_start=?,s_end=?,s_name=?,s_major=? where e_id=? and s_idx=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, svo.getS_start());
			pstmt.setString(2, svo.getS_end());
			pstmt.setString(3, svo.getS_name());
			pstmt.setString(4, svo.getS_major());
			pstmt.setString(5, svo.getE_id());
			pstmt.setInt(6, svo.getS_idx());
			result = pstmt.executeUpdate();
			
			if(result==0){
				return false;
			}
			
			return true;
		} catch (Exception e) {
			System.out.println("SchoolUpdate 에러 : "+e);
		}
		return false;
	}
	
	// 삭제
	public boolean SchoolDel(int s_idx){
		int result = 0;
		try {
			String sql = "delete from school where s_idx = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, s_idx);
			
			result = pstmt.executeUpdate();
			
			if(result==0){
				return false;
			}
			
			return true;
		} catch (Exception e) {
			System.out.println("SchoolDel 에러 : "+e);
		}
		return false;
	}
}
