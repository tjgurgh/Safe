package org.board.Android;

import java.sql.*;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

public class Record_Board{
	
	/*
	 * String DB_URL =
	 * "jdbc:oracle:thin:@mydb.cqcoya9ktimf.ap-northeast-2.rds.amazonaws.com:1521:ORCL";
	 * String DB_USER = "khs9628"; String DB_PASSWORD= "1q2w3e4r";
	 */
	String DB_URL = "jdbc:log4jdbc:oracle:thin:@localhost:1521:orcl";
	String DB_USER = "hyeokho";
	String DB_PASSWORD= "hjseo687"; 
   private static Record_Board Record_board = new Record_Board();

   public static Record_Board getRecord_Board() {
      return Record_board;
   }

   private String returns;
   private String sResult;
   private Connection con = null;
   private PreparedStatement pstmt = null;
   private ResultSet rs = null;
  
   public String select(String id) {
	   String mem_no;
      try {
        returns ="";
        sResult="";
        mem_no="";

        Class.forName("oracle.jdbc.driver.OracleDriver");
        con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD); 
        
		//������ �̸�, ��ȸ ��¥, ������ �з�, ������ ���, Ÿ��

		String query = "SELECT mem_no from go_member where mem_id ='"+id+"'";
        pstmt = con.prepareStatement(query);
        rs = pstmt.executeQuery();
        
       while(rs.next()) {
        	 mem_no = rs.getString("mem_no");
       }
       query = "SELECT count(app_no)app_no from go_application where app_mem='"+mem_no+"'";
       pstmt = con.prepareStatement(query);
       rs = pstmt.executeQuery();
       
      while(rs.next()) {
       	 sResult += rs.getString("app_no");
       	 sResult += "_";
      }
      String sql = "update go_application set app_stat ='E' where app_mem='"+mem_no+"' and app_stat = 'W'";
      pstmt = con.prepareStatement(sql);
      pstmt.executeUpdate();
      
		query = "SELECT a.app_no, a.app_safe_no, a.app_type, to_char(a.app_date,'YYYY/MM/DD')app_date, a.app_stat from go_application a where a.app_mem='"+mem_no+"' order by app_date desc";
        pstmt = con.prepareStatement(query);
        rs = pstmt.executeQuery();
        
       while(rs.next()) {
    	     sResult += rs.getString("app_no");
    	     sResult += ("_");
        	 sResult += rs.getString("app_safe_no");
        	 sResult += "_";
        	 sResult += rs.getString("app_type");
        	 sResult += "_";
        	 sResult += rs.getString("app_date");
        	 sResult += "_";
        	 sResult += rs.getString("app_stat");
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
