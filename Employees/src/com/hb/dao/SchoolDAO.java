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
}
