package org.board.Android;

import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class Inquire_Board{
	
	/*
	 * String DB_URL =
	 * "jdbc:oracle:thin:@mydb.cqcoya9ktimf.ap-northeast-2.rds.amazonaws.com:1521:ORCL";
	 * String DB_USER = "khs9628"; String DB_PASSWORD= "1q2w3e4r";
	 */
	String DB_URL = "jdbc:log4jdbc:oracle:thin:@localhost:1521:orcl";
	String DB_USER = "hyeokho";
	String DB_PASSWORD= "hjseo687"; 
	
   private static Inquire_Board inquire_board = new Inquire_Board();

   public static Inquire_Board getInquire_Board() {
      return inquire_board;
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
		
		String query = "SELECT COUNT(*)count FROM go_item WHERE ITEM_SAFE_NO > 0";
        pstmt = con.prepareStatement(query);
        rs = pstmt.executeQuery();
        
       while(rs.next()) {
        	 sResult += rs.getString("count");
        	 sResult += "_";
       }  
		
       query = "SELECT item_safe_no, item_name, TO_CHAR(item_keep_start,'YYYY/MM/DD')start_date, TO_CHAR(item_keep_end,'YYYY/MM/DD')end_date, item_class, item_remark, item_stat FROM go_item WHERE ITEM_SAFE_NO>0 order by item_safe_no";
        pstmt = con.prepareStatement(query);
        rs = pstmt.executeQuery();
        
       while(rs.next()) {
    	     sResult += rs.getString("item_safe_no");
    	     sResult += "_";
        	 sResult += rs.getString("item_name");
        	 sResult += "_";
        	 sResult += rs.getString("start_date");
        	 sResult += "_";
        	 sResult += rs.getString("end_date");
        	 sResult += "_";
        	 sResult += rs.getString("item_class");
        	 sResult += "_";
        	 sResult += rs.getString("item_remark");
        	 sResult += "_";
        	 sResult += rs.getString("item_stat");
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
