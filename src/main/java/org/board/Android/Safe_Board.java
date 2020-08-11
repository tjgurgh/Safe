package org.board.Android;


import java.sql.*;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

public class Safe_Board{
	/*
	 * String DB_URL =
	 * "jdbc:oracle:thin:@mydb.cqcoya9ktimf.ap-northeast-2.rds.amazonaws.com:1521:ORCL";
	 * String DB_USER = "khs9628"; String DB_PASSWORD= "1q2w3e4r";
	 */
	String DB_URL = "jdbc:log4jdbc:oracle:thin:@localhost:1521:orcl";
	String DB_USER = "hyeokho";
	String DB_PASSWORD= "hjseo687"; 

	
   private static Safe_Board Receipt_board = new Safe_Board();

   public static Safe_Board getReceipt_Board() {
      return Receipt_board;
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


        if(id.equals("R")) {
			String query = "SELECT ITEM_SAFE_NO FROM GO_ITEM where item_stat='F' AND ITEM_SAFE_NO>0 ORDER BY ITEM_SAFE_NO";
	        pstmt = con.prepareStatement(query);
	        rs = pstmt.executeQuery();
	        
	       while(rs.next()) {
	        	 sResult += rs.getString("ITEM_SAFE_NO");
	        	 sResult += "_";
	       }  
			
	       return sResult;
		}
		else if(id.equals("K")) {
			String query = "SELECT ITEM_SAFE_NO FROM GO_ITEM where item_stat='E' AND ITEM_SAFE_NO>0 ORDER BY ITEM_SAFE_NO";
	        pstmt = con.prepareStatement(query);
	        rs = pstmt.executeQuery();
	        
	       while(rs.next()) {
	        	 sResult += rs.getString("ITEM_SAFE_NO");
	        	 sResult += "_";
	       }  
			
	       return sResult;
		}
        
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
