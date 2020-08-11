package org.board.Android;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import javax.sql.DataSource;

public class Comment_Write {
	
	/*
	 * String DB_URL =
	 * "jdbc:oracle:thin:@mydb.cqcoya9ktimf.ap-northeast-2.rds.amazonaws.com:1521:ORCL";
	 * String DB_USER = "khs9628"; String DB_PASSWORD= "1q2w3e4r";
	 */
	String DB_URL = "jdbc:log4jdbc:oracle:thin:@localhost:1521:orcl";
	String DB_USER = "hyeokho";
	String DB_PASSWORD= "hjseo687"; 
	private static Comment_Write Comment = new Comment_Write();

public static Comment_Write getWrite() {
	return Comment;
}

private String returns = "";
private Connection conn = null;
private PreparedStatement pstmt = null;
private ResultSet rs = null;
DataSource ds;

public String write(String brd_id, String cmt_writer, String content) {
	String writerName="";
	String sResult="";
	String cmt_cnt="";
	try {
		 Class.forName("oracle.jdbc.driver.OracleDriver");
	   	 conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD); 
		
		Statement stmt = conn.createStatement();
		String seq = "select max(CMT_ID) from GO_COMMENT";
		ResultSet rs = stmt.executeQuery(seq);
	
		int cmt_id = -1;
		if (rs.next()) {
			cmt_id = rs.getInt(1);
		}
		cmt_id++;
			
		String query = "SELECT mem_name FROM GO_MEMBER WHERE MEM_ID = '"+cmt_writer+"'";
	        pstmt = conn.prepareStatement(query);
	        rs = pstmt.executeQuery();
	        
	       while(rs.next()) {
	        	 writerName = rs.getString("mem_name");
	       }
	       
	       query = "SELECT cmt_cnt FROM GO_BOARD WHERE BRD_ID = '"+brd_id+"'";
	        pstmt = conn.prepareStatement(query);
	        rs = pstmt.executeQuery();
	        
	       while(rs.next()) {
	        	 cmt_cnt = rs.getString("cmt_cnt");
	       }  	
		
		String nowTime = getCurrentTime("YYYY/MM/dd hh:mm:ss"); 
		
	
		String SQL = "insert into go_comment(cmt_id, cmt_writer, cmt_date, cmt_content, cmt_board) values(	'"+cmt_id+"',	'"+writerName+"',	to_date(	'"+nowTime+"',	'YYYY-MM-DD HH:MI:SS'),	'"+content+"',	'"+brd_id+"')" ;
		pstmt = conn.prepareStatement(SQL);
		pstmt.executeUpdate();
		
		sResult = cmt_id +"_"+writerName +"_"+nowTime+"_"+content;
		returns = sResult;
		int cmt = Integer.parseInt(cmt_cnt)+1;
		
		SQL = "update go_board set cmt_cnt ='"+cmt+"' where brd_id ='"+brd_id+"'";
		pstmt = conn.prepareStatement(SQL);
		pstmt.executeUpdate();
	
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

