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

public class CareerDAO {
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	DataSource ds;
	
	public CareerDAO() {
		try {
			Context init = new InitialContext();
			ds = (DataSource) init.lookup("java:comp/env/jdbc/OracleDB");
			conn = ds.getConnection();
		} catch (Exception e) {
			System.out.println("DB 연결 실패 : "+e);
			return;
		}
	}
	
	// 2. 경력 사항
	public List<CareerVO> getCareerInfo(String id){
		List<CareerVO> list = new ArrayList<>();
		CareerVO cvo = null;
		try {
			String sql = "select * from career where e_id = ? order by c_start";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				cvo = new CareerVO();
				cvo.setC_idx(rs.getInt(1));
				cvo.setE_id(rs.getString(2));
				cvo.setC_start(rs.getString(3));
				cvo.setC_end(rs.getString(4));
				cvo.setC_name(rs.getString(5));
				cvo.setC_rank(rs.getString(6));
				cvo.setC_task(rs.getString(7));
				list.add(cvo);
			}
			
		} catch (Exception e) {
			System.out.println("getCareerInfo 에러"+e);
			
		} finally {
			if(rs!=null)try {rs.close();} catch (SQLException sql) {}
			if(pstmt!=null)try {pstmt.close();} catch (SQLException sql) {}
			if(conn!=null)try {conn.close();} catch (SQLException sql) {}
		}
		return list;
	}
}
