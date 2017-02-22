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
import com.hb.vo.SchoolVO;

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
	
	// 사원등록 경력사항 insert
	public boolean CareerInsert(CareerVO cvo){
		//System.out.println(cvo.getE_id()+cvo.getC_start()+cvo.getC_end()+cvo.getC_name()+cvo.getC_rank()+cvo.getC_task());
		int result = 0;
		try {
			String sql = "insert into career values(car_seq.nextval,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, cvo.getE_id());
			pstmt.setString(2, cvo.getC_start());
			pstmt.setString(3, cvo.getC_end());
			pstmt.setString(4, cvo.getC_name());
			pstmt.setString(5, cvo.getC_rank());
			pstmt.setString(6, cvo.getC_task());
			result = pstmt.executeUpdate();
			
			if(result==0){
				return false;
			}
			
			return true;
		} catch (Exception e) {
			System.out.println("CareerInsert 에러 : "+e);
		}
		return false;
	}
	
	// 경력 수정
	public boolean CareerUpdate(CareerVO cvo){
		int result = 0;
		try {
			String sql = "update career set c_start=?,c_end=?,c_name=?,c_rank=?,c_task=? where e_id=? and c_idx=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, cvo.getC_start());
			pstmt.setString(2, cvo.getC_end());
			pstmt.setString(3, cvo.getC_name());
			pstmt.setString(4, cvo.getC_rank());
			pstmt.setString(5, cvo.getC_task());
			pstmt.setString(6, cvo.getE_id());
			pstmt.setInt(7, cvo.getC_idx());
			result = pstmt.executeUpdate();
			
			if(result==0){
				return false;
			}
			
			return true;
		} catch (Exception e) {
			System.out.println("CareerUpdate 에러 : "+e);
		}
		return false;
	}
	
	public void close(){
		if(rs!=null)try {rs.close();} catch (SQLException sql) {}
		if(pstmt!=null)try {pstmt.close();} catch (SQLException sql) {}
		if(conn!=null)try {conn.close();} catch (SQLException sql) {}
	}
	
	// 삭제 CareerDel
	public boolean CareerDel(int delc_idx){
		int result = 0;
		try {
			String sql = "delete from career where c_idx = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, delc_idx);
			
			result = pstmt.executeUpdate();
			
			if(result==0){
				return false;
			}
			
			return true;
		} catch (Exception e) {
			System.out.println("CareerDel 에러 : "+e);
		}
		return false;
	}
}
