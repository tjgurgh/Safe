package org.board.Android;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;

import javax.sql.DataSource;

public class FindPw_Write {
	
	/*
	 * String DB_URL =
	 * "jdbc:oracle:thin:@mydb.cqcoya9ktimf.ap-northeast-2.rds.amazonaws.com:1521:ORCL";
	 * String DB_USER = "khs9628"; String DB_PASSWORD= "1q2w3e4r";
	 */
	String DB_URL = "jdbc:log4jdbc:oracle:thin:@localhost:1521:orcl";
	String DB_USER = "hyeokho";
	String DB_PASSWORD= "hjseo687"; 
		
	private static FindPw_Write findpw = new FindPw_Write();

	public static FindPw_Write getWrite() {
		return findpw;
	}
	private String returns = "";
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	DataSource ds;
	
	public String write(String pw, String id) {
	    try {
	    	
	    	 Class.forName("oracle.jdbc.driver.OracleDriver");
	    	 conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD); 

	String SQL = "update go_member set mem_pwd='"+pw+"' where mem_id='"+id+"'";
	pstmt = conn.prepareStatement(SQL);
	pstmt.executeUpdate();
	returns = "success";
	

} catch (Exception e) {
	System.out.println("error");
	e.printStackTrace();
} finally {
	 try {
		 System.out.println("final");
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
	public static void main(String[] args) {
	
	}
}
