package org.board.Android;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
public class ReviseInfo_Write {
	
	/*
	 * String DB_URL =
	 * "jdbc:oracle:thin:@mydb.cqcoya9ktimf.ap-northeast-2.rds.amazonaws.com:1521:ORCL";
	 * String DB_USER = "khs9628"; String DB_PASSWORD= "1q2w3e4r";
	 */
	String DB_URL = "jdbc:log4jdbc:oracle:thin:@localhost:1521:orcl";
	String DB_USER = "hyeokho";
	String DB_PASSWORD= "hjseo687"; 
	
	private static ReviseInfo_Write reviseinfo = new ReviseInfo_Write();

	public static ReviseInfo_Write getWrite() {
		return reviseinfo;
	}
	private String returns = "";
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	public String write(String value, String index, String mem_no) {
	    try {

	    	Class.forName("oracle.jdbc.driver.OracleDriver");
	    	 conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
		
		if(index.equals("0")) {
			String SQL = "Update go_member set mem_name='"+value+"' where mem_no='"+mem_no+"'";
			pstmt = conn.prepareStatement(SQL);
			pstmt.executeUpdate();
			returns = "success";
		}else if(index.equals("1")) {
			String SQL = "Update go_member set mem_id='"+value+"' where mem_no='"+mem_no+"'";
			pstmt = conn.prepareStatement(SQL);
			pstmt.executeUpdate();
			returns = "success";
		}else if(index.equals("2")) {
			String SQL = "Update go_member set mem_email='"+value+"' where mem_no='"+mem_no+"'";
			pstmt = conn.prepareStatement(SQL);
			pstmt.executeUpdate();
			returns = "success";
		}else if(index.equals("3")) {
			String SQL = "Update go_member set mem_pwd='"+value+"' where mem_no='"+mem_no+"'";
			pstmt = conn.prepareStatement(SQL);
			pstmt.executeUpdate();
			returns = "success";
		}else if(index.equals("4")) {
			String SQL = "Update go_member set mem_phone='"+value+"' where mem_no='"+mem_no+"'";
			pstmt = conn.prepareStatement(SQL);
			pstmt.executeUpdate();
			returns = "success";
		}else {
			returns = "";
		}

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