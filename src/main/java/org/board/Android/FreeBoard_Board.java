package org.board.Android;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FreeBoard_Board{

	/*
	 * String DB_URL =
	 * "jdbc:oracle:thin:@mydb.cqcoya9ktimf.ap-northeast-2.rds.amazonaws.com:1521:ORCL";
	 * String DB_USER = "khs9628"; String DB_PASSWORD= "1q2w3e4r";
	 */
	String DB_URL = "jdbc:log4jdbc:oracle:thin:@localhost:1521:orcl";
	String DB_USER = "hyeokho";
	String DB_PASSWORD= "hjseo687"; 
   private static FreeBoard_Board FreeBoard_board = new FreeBoard_Board();

   public static FreeBoard_Board getFreeBoard_Board() {
      return FreeBoard_board;
   }

   private String returns;
   private String sResult;
   private String brd_id;
   private String count;
   private Connection conn = null;
   private PreparedStatement pstmt = null;
   private ResultSet rs = null;
  
   public String select(String id) {
	   try {
		   
		   Class.forName("oracle.jdbc.driver.OracleDriver");
	    	 conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD); 
	        returns ="";
	        sResult="";
	        brd_id="";
	        count = "";

		
			String query = "SELECT COUNT(*)count FROM GO_BOARD WHERE BRD_TYPE = 'F'";
	        pstmt = conn.prepareStatement(query);
	        rs = pstmt.executeQuery();
	        
	       while(rs.next()) {
	        	 sResult += rs.getString("count");
	        	 sResult += "_";
	        	 count = rs.getString("count");
	       }  
			
	       query = "SELECT BRD_ID, BRD_SUB, BRD_WRITER, TO_CHAR(BRD_DATE,'YYYY/MM/DD')BRD_DATE, BRD_CNT, BRD_CONTENT, CMT_CNT FROM GO_BOARD WHERE BRD_TYPE = 'F' order by BRD_DATE DESC";
	        pstmt = conn.prepareStatement(query);
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
	    	     sResult += rs.getString("CMT_CNT");
	    	     sResult += "_";
	    	     
	    	     //brd_id += rs.getString("BRD_ID");
	    	     //brd_id += "_";
	       }
	       /*
	       String [] element = brd_id.split("_");
	       
	       for(int i=0;i<Integer.parseInt(count);i++){
	  		query = "SELECT count(cmt_id)count from go_comment where cmt_board='"+element[i]+"'";
	  		pstmt = con.prepareStatement(query);
	  		rs = pstmt.executeQuery();
	  		while(rs.next()) {
	  			sResult += rs.getString("count");
	  			sResult += "_";
	  		}
	       }
	       */
	       
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
