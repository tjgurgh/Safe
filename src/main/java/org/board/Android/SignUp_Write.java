package org.board.Android;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

public class SignUp_Write {
	
	
	private static SignUp_Write signup = new SignUp_Write();

	public static SignUp_Write getWrite() {
		return signup;
	}
	private String returns = "";
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	public String write(String content, String id, String email, String pw, String phone) {
	    try {

	    	/*
	    	 * String DB_URL =
	    	 * "jdbc:oracle:thin:@mydb.cqcoya9ktimf.ap-northeast-2.rds.amazonaws.com:1521:ORCL";
	    	 * String DB_USER = "khs9628"; String DB_PASSWORD= "1q2w3e4r";
	    	 */
	    	String DB_URL = "jdbc:log4jdbc:oracle:thin:@localhost:1521:orcl";
	    	String DB_USER = "hyeokho";
	    	String DB_PASSWORD= "hjseo687"; 

	    	 Class.forName("oracle.jdbc.driver.OracleDriver");
	         conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD); 
	 		
		Statement stmt = conn.createStatement();
		String seq = "select max(mem_no) from go_member";
		ResultSet rs = stmt.executeQuery(seq);

		int mem_no = -1;
		if (rs.next()) 
			mem_no = rs.getInt(1);
			mem_no++;

		String SQL = "INSERT INTO go_member(mem_no,mem_stat,mem_name,mem_id,mem_email,mem_phone, mem_pwd) VALUES(seq_member.nextval ,'" + 'W' + "','" + content + "','" + id + "','" + email+ "','" + phone + "','" + pw + "')";
		pstmt = conn.prepareStatement(SQL);
		pstmt.executeUpdate();
		returns = "success";

	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		 try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	return returns;
       }
        public static String getCurrentTime(String timeFormat) {
	       return new SimpleDateFormat(timeFormat).format(System.currentTimeMillis());
        }
}
