package org.board.Android;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

public class Receipt_Write {
	
	/*
	 * String DB_URL =
	 * "jdbc:oracle:thin:@mydb.cqcoya9ktimf.ap-northeast-2.rds.amazonaws.com:1521:ORCL";
	 * String DB_USER = "khs9628"; String DB_PASSWORD= "1q2w3e4r";
	 */
	String DB_URL = "jdbc:log4jdbc:oracle:thin:@localhost:1521:orcl";
	String DB_USER = "hyeokho";
	String DB_PASSWORD= "hjseo687"; 
	private static Receipt_Write Receipt = new Receipt_Write();

	public static Receipt_Write getWrite() {
		return Receipt;
	}
	private String returns = "";
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	public String write(String id, String safe_no, String content) {
		String mem_no="";
		String item_no = "";
	    try {
	    	

	    	 Class.forName("oracle.jdbc.driver.OracleDriver");
	    	 conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD); 
		
		

	    		String query = "SELECT a.app_mem from go_application a inner join go_member m on a.app_mem = m.mem_no where m.mem_id ='"+id+"' and a.app_type='R'";
	    	    pstmt = conn.prepareStatement(query);
	    	    rs = pstmt.executeQuery();
	    	    
	    	   while(rs.next()) {
	    	    	 mem_no = rs.getString("app_mem");
	    	   }
	    	   /*
	    	   if(mem_no.length()>0){
	    		   return "fail";
	    	   }
	    	   */
	    	   Statement stmt = conn.createStatement();
	    		String seq = "select max(APP_NO) from GO_APPLICATION";
	    		ResultSet rs = stmt.executeQuery(seq);

	    		int app_no = -1;
	    		if (rs.next()) {
	    			app_no = rs.getInt(1);
	    		}
	    		app_no++;
	    		
	    		query = "SELECT item_no FROM go_item WHERE item_safe_no = '"+safe_no+"'";
	    	    pstmt = conn.prepareStatement(query);
	    	    rs = pstmt.executeQuery();
	    	    
	    	   while(rs.next()) {
	    	    	 item_no = rs.getString("item_no");
	    	   }
	    		
	    		
	    		query = "SELECT MEM_NO FROM GO_MEMBER WHERE MEM_ID = '"+id+"'";
	    	    pstmt = conn.prepareStatement(query);
	    	    rs = pstmt.executeQuery();
	    	    
	    	   while(rs.next()) {
	    	    	 mem_no = rs.getString("MEM_NO");
	    	   }
	    	   
	    	   String nowTime = getCurrentTime("YYYY/MM/dd hh:mm:ss"); 
	    	   
	    		String SQL = "INSERT INTO GO_APPLICATION(APP_NO, APP_MEM, APP_SAFE_NO, APP_DATE, APP_CONTENT, APP_TYPE, APP_STAT, APP_ITEM_NO) VALUES('"+app_no+"', '"+mem_no+"','"+safe_no+"', to_date('"+nowTime+"', 'YYYY/MM/DD HH:MI:SS'), '"+content+"', 'R', 'N', '"+item_no+"')";
	    		pstmt = conn.prepareStatement(SQL);
	    		pstmt.executeUpdate();
	    		//������ ���� ��, app_safe_no, item_stat ����
	    		/*
	    		SQL = "UPDATE GO_ITEM SET ITEM_STAT='E' where item_safe_no ='"+safe_no+"'";
	    		pstmt = conn.prepareStatement(SQL);
	    		pstmt.executeUpdate();
	    		*/
	    		return "success";
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
