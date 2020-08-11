package org.board.Android;

import java.sql.*;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

public class Notification_Board{
	
	/*
	 * String DB_URL =
	 * "jdbc:oracle:thin:@mydb.cqcoya9ktimf.ap-northeast-2.rds.amazonaws.com:1521:ORCL";
	 * String DB_USER = "khs9628"; String DB_PASSWORD= "1q2w3e4r";
	 */
	String DB_URL = "jdbc:log4jdbc:oracle:thin:@localhost:1521:orcl";
	String DB_USER = "hyeokho";
	String DB_PASSWORD= "hjseo687"; 
	
   private static Notification_Board Notification_board = new Notification_Board();

   public static Notification_Board getNotification_Board() {
      return Notification_board;
   }

   private String returns;
   private String sResult;
   private Connection con = null;
   private PreparedStatement pstmt = null;
   private ResultSet rs = null;
   
   public String select(String id) {
      try {
        returns ="";
        sResult="";
        
        Class.forName("oracle.jdbc.driver.OracleDriver");
        con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD); 
        
        String query = "SELECT COUNT(*)count FROM GO_BOARD WHERE BRD_TYPE = 'N'";
        pstmt = con.prepareStatement(query);
        rs = pstmt.executeQuery();
        
       while(rs.next()) {
        	 sResult += rs.getString("count");
        	 sResult += "_";
       }  
		
       query = "SELECT BRD_ID, BRD_SUB, BRD_WRITER, TO_CHAR(BRD_DATE,'YYYY/MM/DD')BRD_DATE, BRD_CNT, BRD_CONTENT FROM GO_BOARD WHERE BRD_TYPE = 'N' order by BRD_DATE DESC";
        pstmt = con.prepareStatement(query);
        rs = pstmt.executeQuery();
        
       while(rs.next()) {
    	   	 sResult += rs.getString("BRD_ID");
  	     	 sResult += "_";
    	     sResult += rs.getString("BRD_SUB");
    	     sResult += "_";
    	     sResult += rs.getString("BRD_WRITER");
    	     sResult += "_";
    	     sResult += rs.getString("BRD_DATE");
    	     sResult += "_";
    	     sResult += rs.getString("BRD_CNT");
    	     sResult += "_";
    	     sResult += rs.getString("BRD_CONTENT");
    	     sResult += "_";
       } 
       return sResult;
        
     } catch (Exception e) {
    	 System.out.println("error");
        e.printStackTrace();
     } // end try~catch
      
      finally {
          if (pstmt != null)
        	  try {
					if(rs != null) rs.close();
					if(pstmt != null) pstmt.close();
					if(con != null) con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
      }
      return returns;
   }// end select()

}
