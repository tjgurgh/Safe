package org.board.Android;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class Login_Board{
	
	/*
	 * String DB_URL =
	 * "jdbc:oracle:thin:@mydb.cqcoya9ktimf.ap-northeast-2.rds.amazonaws.com:1521:ORCL";
	 * String DB_USER = "khs9628"; String DB_PASSWORD= "1q2w3e4r";
	 */
	String DB_URL = "jdbc:log4jdbc:oracle:thin:@localhost:1521:orcl";
	String DB_USER = "hyeokho";
	String DB_PASSWORD= "hjseo687"; 
	
   private static Login_Board login_board = new Login_Board();

   public static Login_Board getLogin_Board() {
      return login_board;
   }

   private String returns;
   private Connection con = null;
   private PreparedStatement pstmt = null;
   private ResultSet rs = null;
  
   public String select(String id, String pw) {
      try {
        returns ="";
        Class.forName("oracle.jdbc.driver.OracleDriver");
        con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD); 
		
		String query = "SELECT mem_stat FROM go_member where mem_id='"+id+"' and mem_pwd ='"+pw+"'";
	    pstmt = con.prepareStatement(query);
	    rs = pstmt.executeQuery();
	       
	    while(rs.next()) {
	       returns = rs.getString("mem_stat");
	    }
	    if(returns.equals("W")) {
	    	return "yet";
	    }
		
		query = "SELECT mem_name FROM go_member where mem_id='"+id+"' and mem_pwd ='"+pw+"'";
        pstmt = con.prepareStatement(query);
        rs = pstmt.executeQuery();
        
       while(rs.next()) {
        	 return rs.getString("mem_name");
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
