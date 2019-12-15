package org.board.Android;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

public class Record_Write {
	
	/*
	 * String DB_URL =
	 * "jdbc:oracle:thin:@mydb.cqcoya9ktimf.ap-northeast-2.rds.amazonaws.com:1521:ORCL";
	 * String DB_USER = "khs9628"; String DB_PASSWORD= "1q2w3e4r";
	 */
	String DB_URL = "jdbc:log4jdbc:oracle:thin:@localhost:1521:orcl";
	String DB_USER = "hyeokho";
	String DB_PASSWORD= "hjseo687"; 
	
	private static Record_Write Record = new Record_Write();

public static Record_Write getWrite() {
	return Record;
}
private String returns = "";
private Connection conn = null;
private PreparedStatement pstmt = null;
private ResultSet rs = null;

public String write(String app_no) {
    try {
    	

    	 Class.forName("oracle.jdbc.driver.OracleDriver");
    	 conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD); 
    			
	
    		String app_mem = "";
    		String query = "select app_mem from go_application where app_no='"+app_no+"'";
    		pstmt = conn.prepareStatement(query);
    		rs = pstmt.executeQuery();
    		while(rs.next()) {
    			app_mem = rs.getString("app_mem");
    		}
    		
    		query = "select a.app_stat from go_application a inner join go_member m on a.app_mem = m.mem_no where a.app_mem ='"+app_mem+"'";
    		pstmt = conn.prepareStatement(query);
    		rs = pstmt.executeQuery();
    		while(rs.next()) {
    			if(rs.getString("app_stat").trim().equals("W")) {
    				return "fail";
    			}
    		}
    		
    		
    		String SQL = "update go_application set app_stat='W' where app_no='"+app_no+"'";
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
    	}
