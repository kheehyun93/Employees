package com.hb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class SchoolDAO {
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	
	public SchoolDAO() {
		try {
			Context init = new InitialContext();
			DataSource ds = (DataSource) init.lookup("java:comp/env/jdbc/OracleDB");
			conn = ds.getConnection();
		} catch (Exception e) {
			System.out.println("DB 연결 실패 : "+e);
			return;
		}
	}
}
