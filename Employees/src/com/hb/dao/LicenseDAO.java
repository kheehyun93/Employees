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

import com.hb.vo.CareerVO;
import com.hb.vo.EmployeesVO;
import com.hb.vo.LicenseVO;

public class LicenseDAO {
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	DataSource ds;
	
	public LicenseDAO() {
		try {
			Context init = new InitialContext();
			ds = (DataSource) init.lookup("java:comp/env/jdbc/OracleDB");
			conn = ds.getConnection();
		} catch (Exception e) {
			System.out.println("DB 연결 실패 : "+e);
			return;
		}
	}
	
	//자격증 정보 가져오기
	public List<LicenseVO> getLicenseInfo(String id){
		List<LicenseVO> list = new ArrayList<>();
		LicenseVO lvo = null;
		try {
			String sql = "select * from license where e_id=? order by l_date";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				lvo = new LicenseVO();
				lvo.setL_idx(rs.getInt(1));
				lvo.setE_id(rs.getString(2));
				lvo.setL_name(rs.getString(3));
				lvo.setL_date(rs.getString(4));
				list.add(lvo);
				
			}
		} catch (Exception e) {
			System.out.println("getLicenseInfo 에러"+e);
		} finally {
			if(rs!=null)try {rs.close();} catch (SQLException sql) {}
			if(pstmt!=null)try {pstmt.close();} catch (SQLException sql) {}
			if(conn!=null)try {conn.close();} catch (SQLException sql) {}
		}
		return list;
	}
	
	// 자격증 insert LicenseInsert
	public boolean LicenseInsert(LicenseVO lvo){
		int result = 0;
		try {
			String sql = "insert into license values(lic_seq.nextval,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, lvo.getE_id());
			pstmt.setString(2, lvo.getL_name());
			pstmt.setString(3, lvo.getL_date());
			result = pstmt.executeUpdate();
			
			if(result==0){
				return false;
			}
			
			return true;
		} catch (Exception e) {
			System.out.println("LicenseInsert 에러 : "+e);
		}
		return false;
	}
	public void close(){
		if(rs!=null)try {rs.close();} catch (SQLException sql) {}
		if(pstmt!=null)try {pstmt.close();} catch (SQLException sql) {}
		if(conn!=null)try {conn.close();} catch (SQLException sql) {}
	}
	
	// 자격증 수정
	public boolean LicenseUpdate(LicenseVO lvo){
		int result = 0;
		try {
			String sql = "update license set l_name=?,l_date=? where e_id=? and l_idx=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, lvo.getL_name());
			pstmt.setString(2, lvo.getL_date());
			pstmt.setString(3, lvo.getE_id());
			pstmt.setInt(4, lvo.getL_idx());
			
			result = pstmt.executeUpdate();
			
			if(result==0){
				return false;
			}
			
			return true;
		} catch (Exception e) {
			System.out.println("LicenseUpdate 에러 : "+e);
		}
		return false;
	}
	
	// 자격증 삭제
	public boolean LicenseDel(int l_idx){
		int result = 0;
		try {
			String sql = "delete from license where l_idx = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, l_idx);
			
			result = pstmt.executeUpdate();
			
			if(result==0){
				return false;
			}
			
			return true;
		} catch (Exception e) {
			System.out.println("LicenseDel 에러 : "+e);
		}
		return false;
	}
	
}
