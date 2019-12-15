package org.board.Android;

import java.sql.*;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

public class SignUp_Board{
	
	/*
	 * String DB_URL =
	 * "jdbc:oracle:thin:@mydb.cqcoya9ktimf.ap-northeast-2.rds.amazonaws.com:1521:ORCL";
	 * String DB_USER = "khs9628"; String DB_PASSWORD= "1q2w3e4r";
	 */
	String DB_URL = "jdbc:log4jdbc:oracle:thin:@localhost:1521:orcl";
	String DB_USER = "hyeokho";
	String DB_PASSWORD= "hjseo687"; 
	
   private static SignUp_Board signup_board = new SignUp_Board();

   public static SignUp_Board getSignUp_Board() {
      return signup_board;
   }

   private String returns;
   private Connection con = null;
   private PreparedStatement pstmt = null;
   private ResultSet rs = null;
  
   public String select(String id) {
      try {
        returns ="";

        Class.forName("oracle.jdbc.driver.OracleDriver");
        con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD); 


        String query = "SELECT mem_id, mem_name FROM go_member";
        pstmt = con.prepareStatement(query);
        rs = pstmt.executeQuery();
        
        while(rs.next()) {
           if(rs.getString("mem_id").equals(id)) {
           	return rs.getString("mem_name");
           }
        } // end while
     } catch (Exception e) {
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