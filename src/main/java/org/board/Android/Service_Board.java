package org.board.Android;


import java.sql.*;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

public class Service_Board{
	
	/*
	 * String DB_URL =
	 * "jdbc:oracle:thin:@mydb.cqcoya9ktimf.ap-northeast-2.rds.amazonaws.com:1521:ORCL";
	 * String DB_USER = "khs9628"; String DB_PASSWORD= "1q2w3e4r";
	 */
	String DB_URL = "jdbc:log4jdbc:oracle:thin:@localhost:1521:orcl";
	String DB_USER = "hyeokho";
	String DB_PASSWORD= "hjseo687"; 
	
   private static Service_Board Service_board = new Service_Board();

   public static Service_Board getService_Board() {
      return Service_board;
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

    	/*
		String query = "SELECT count(a.app_no)app_no from go_application a inner join member m on a.app_mem = m.mem_no where m.mem_id ='"+id+"' and a.app_stat ='Y'";
        pstmt = con.prepareStatement(query);
        rs = pstmt.executeQuery();
        
       while(rs.next()) {
        	 sResult += rs.getString("app_no");
        	 sResult += "_";
       }*/
		String app_safe_no = ""; 
		String query = "SELECT a.app_stat, a.app_safe_no from go_application a inner join go_member m on a.app_mem = m.mem_no where m.mem_id ='"+id+"' and a.app_stat ='Y'";
        pstmt = con.prepareStatement(query);
        rs = pstmt.executeQuery();
        
       while(rs.next()) {
        	 sResult += rs.getString("app_stat");
        	 sResult += "_";
        	 sResult += rs.getString("app_safe_no");
        	 app_safe_no += rs.getString("app_safe_no");
        	 app_safe_no += ("_");
       }
       String[] element = app_safe_no.split("_");
       
       for(int i=0; i<element.length;i++) {
    	   String sql = "update go_application set app_stat = 'E' where app_safe_no ='"+element[i]+"'";
    	   pstmt = con.prepareStatement(sql);
    	   pstmt.executeUpdate();
       }
       
      query = "SELECT a.app_safe_no from go_application a inner join go_member m on a.app_mem = m.mem_no where m.mem_id ='"+id+"' and a.app_stat ='R'";
       pstmt = con.prepareStatement(query);
       rs = pstmt.executeQuery();
       
      String safe_no = "";
       while(rs.next()) {
       	 safe_no += rs.getString("app_safe_no");
       	 safe_no += ("_");
      }
       String[] safe_no_element = safe_no.split("_");
      for(int i=0; i<safe_no_element.length;i++) {
   	   String sql = "update go_item set item_stat = 'E' where item_safe_no ='"+safe_no_element[i]+"'";
   	   pstmt = con.prepareStatement(sql);
   	   pstmt.executeUpdate();
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
