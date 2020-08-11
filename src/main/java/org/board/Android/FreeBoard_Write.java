package org.board.Android;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;

public class FreeBoard_Write {
	
	/*
	 * String DB_URL =
	 * "jdbc:oracle:thin:@mydb.cqcoya9ktimf.ap-northeast-2.rds.amazonaws.com:1521:ORCL";
	 * String DB_USER = "khs9628"; String DB_PASSWORD= "1q2w3e4r";
	 */
	String DB_URL = "jdbc:log4jdbc:oracle:thin:@localhost:1521:orcl";
	String DB_USER = "hyeokho";
	String DB_PASSWORD= "hjseo687"; 
	
	private static FreeBoard_Write FreeBoard = new FreeBoard_Write();

public static FreeBoard_Write getWrite() {
	return FreeBoard;
}
private String returns = "";
private String sResult = "";
private String writerName="";
private Connection conn = null;
private PreparedStatement pstmt = null;
private ResultSet rs = null;

public String write(String id, String subject, String content) {
    try {
    

    	 Class.forName("oracle.jdbc.driver.OracleDriver");
    	 conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD); 
		
		Statement stmt = conn.createStatement();
		String seq = "select max(BRD_ID) from GO_BOARD";
		ResultSet rs = stmt.executeQuery(seq);
	
		int BRD_id = -1;
		if (rs.next()) {
			BRD_id = rs.getInt(1);
		}
		BRD_id++;
			
		String query = "SELECT mem_name FROM GO_MEMBER WHERE MEM_ID = '"+id+"'";
	        pstmt = conn.prepareStatement(query);
	        rs = pstmt.executeQuery();
	        
	       while(rs.next()) {
	        	 writerName = rs.getString("mem_name");
	       }  	
		
		String nowTime = getCurrentTime("YYYY/MM/dd hh:mm:ss"); 
		
	
		String SQL = "insert into go_board(brd_id, brd_writer, brd_date, brd_sub, brd_content, brd_cnt, brd_type) values(	'"+BRD_id+"',	'"+writerName+"',	to_date(	'"+nowTime+"',	'YYYY-MM-DD HH:MI:SS'),	'"+subject+"',	'"+content+"', 0,'F')" ;
		pstmt = conn.prepareStatement(SQL);
		pstmt.executeUpdate();
		
		sResult = subject + "_" + writerName+ "_" + nowTime+ "_" +0+ "_" + content+"_" + BRD_id;
		
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

