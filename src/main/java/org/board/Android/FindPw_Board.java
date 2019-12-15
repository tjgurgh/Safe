package org.board.Android;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

public class FindPw_Board{
	
	/*
	 * String DB_URL =
	 * "jdbc:oracle:thin:@mydb.cqcoya9ktimf.ap-northeast-2.rds.amazonaws.com:1521:ORCL";
	 * String DB_USER = "khs9628"; String DB_PASSWORD= "1q2w3e4r";
	 */
	String DB_URL = "jdbc:log4jdbc:oracle:thin:@localhost:1521:orcl";
	String DB_USER = "hyeokho";
	String DB_PASSWORD= "hjseo687"; 
   private static FindPw_Board findpw_board = new FindPw_Board();

   public static FindPw_Board getFindPw_Board() {
      return findpw_board;
   }

   private String returns;
   private Connection con = null;
   private PreparedStatement pstmt = null;
   private ResultSet rs = null;
   DataSource ds;
  
   public String select(String id, String name, String phone) {
      try {
        returns ="";
        Class.forName("oracle.jdbc.driver.OracleDriver");
        con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD); 

		String query = "SELECT mem_email FROM go_member where mem_id= ? and mem_name = ?";
        pstmt = con.prepareStatement(query);
        pstmt.setString(1,id);
        pstmt.setString(2,name);
        rs = pstmt.executeQuery();
        
        while(rs.next()) {
           	returns = rs.getString("mem_email");
       }
        return returns;
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