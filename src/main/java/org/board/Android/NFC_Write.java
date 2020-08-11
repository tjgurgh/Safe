package org.board.Android;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.sql.DataSource;


public class NFC_Write {
	
	/*
	 * String DB_URL =
	 * "jdbc:oracle:thin:@mydb.cqcoya9ktimf.ap-northeast-2.rds.amazonaws.com:1521:ORCL";
	 * String DB_USER = "khs9628"; String DB_PASSWORD= "1q2w3e4r";
	 */
	String DB_URL = "jdbc:log4jdbc:oracle:thin:@localhost:1521:orcl";
	String DB_USER = "hyeokho";
	String DB_PASSWORD= "hjseo687"; 
	private static NFC_Write Record = new NFC_Write();

public static NFC_Write getWrite() {
	return Record;
}
private String returns = "";
private Connection conn = null;
private PreparedStatement pstmt = null;
private ResultSet rs = null;
DataSource ds;

public String write(String id) {
    try {
    	
    	 Class.forName("oracle.jdbc.driver.OracleDriver");
    	 conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD); 
		
 		
 		String safe_no = "";
 		String app_no = "";
 		String query = "select a.app_safe_no, a.app_no from go_application a inner join go_member m on a.app_mem = m.mem_no where m.mem_id='"+id+"' and a.app_stat ='W'";
 		pstmt = conn.prepareStatement(query);
 		rs = pstmt.executeQuery();
 		while(rs.next()) {
 			safe_no = rs.getString("app_safe_no");
 			app_no = rs.getString("app_no");
 		}
 		System.out.println(" "+app_no.length()+"  "+app_no);
 		if(app_no.length()==0) return "fail";
 		
 		String SQL = "update go_application set app_stat='C' where app_no='"+app_no+"'";
 		pstmt = conn.prepareStatement(SQL);
 		pstmt.executeUpdate();
 		
 		returns = ("success"+"_"+safe_no);
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
 	}
