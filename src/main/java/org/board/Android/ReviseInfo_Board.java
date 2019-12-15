package org.board.Android;


import java.sql.*;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

public class ReviseInfo_Board{
	
	//Ŀ�ؼ� Ǯ �����ϱ� ���� ������ (bean) ��ü �������� ����
	String DB_URL = "jdbc:oracle:thin:@mydb.cqcoya9ktimf.ap-northeast-2.rds.amazonaws.com:1521:ORCL";
	String DB_USER = "khs9628";
	String DB_PASSWORD= "1q2w3e4r"; 

	
   private static ReviseInfo_Board reviseinfo_board = new ReviseInfo_Board();

   public static ReviseInfo_Board getReviseInfo_Board() {
      return reviseinfo_board;
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
        
        String query = "SELECT mem_no FROM go_member where mem_id='"+id+"'";
        pstmt = con.prepareStatement(query);
        rs = pstmt.executeQuery();
        
        while(rs.next()) {
           return rs.getString("mem_no");
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