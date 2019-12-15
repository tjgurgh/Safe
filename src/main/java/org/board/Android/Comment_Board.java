package org.board.Android;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Comment_Board{
	/*
	 * String DB_URL =
	 * "jdbc:oracle:thin:@mydb.cqcoya9ktimf.ap-northeast-2.rds.amazonaws.com:1521:ORCL";
	 * String DB_USER = "khs9628"; String DB_PASSWORD= "1q2w3e4r";
	 */
	String DB_URL = "jdbc:log4jdbc:oracle:thin:@localhost:1521:orcl";
	String DB_USER = "hyeokho";
	String DB_PASSWORD= "hjseo687"; 
   private static Comment_Board Comment_board = new Comment_Board();

   public static Comment_Board getComment_Board() {
      return Comment_board;
   }

   private String returns;
   private String sResult;
   private Connection conn = null;
   private PreparedStatement pstmt = null;
   private ResultSet rs = null;
  
   public String select(String brd_id) {
	   try {
	        returns ="";
	        sResult="";	     
	        Class.forName("oracle.jdbc.driver.OracleDriver");
	   	 	conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD); 

			/*
			String query = "SELECT COUNT(*)count FROM GO_COMMENT WHERE CMT_BOARD = '"+brd_id+"'";
	        pstmt = con.prepareStatement(query);
	        rs = pstmt.executeQuery();
	        */
			
		   String query = "SELECT CMT_CNT FROM GO_BOARD WHERE BRD_ID = '"+brd_id+"'";
		   pstmt = conn.prepareStatement(query);
		   rs = pstmt.executeQuery();
	       while(rs.next()) {
	        	 sResult += rs.getString("CMT_CNT");
	        	 sResult += "_";
	       }  
			
	       query = "SELECT CMT_ID, CMT_WRITER, TO_CHAR(CMT_DATE,'YYYY/MM/DD HH:MI:SS')CMT_DATE, CMT_CONTENT FROM GO_COMMENT WHERE CMT_BOARD = '"+brd_id+"' order by CMT_DATE";
	        pstmt = conn.prepareStatement(query);
	        rs = pstmt.executeQuery();
	        
	       while(rs.next()) {
	    	   	 sResult += rs.getString("CMT_ID");
	  	     	 sResult += "_";
	    	     sResult += rs.getString("CMT_WRITER");
	    	     sResult += "_";
	    	     sResult += rs.getString("CMT_DATE");
	    	     sResult += "_";
	    	     sResult += rs.getString("CMT_CONTENT");
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
					if(conn != null) conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
      }
      return returns;
   }// end select()

}
