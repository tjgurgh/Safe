package org.board.Android;

import java.sql.*;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

public class Receipt_Board{
	
	//Ŀ�ؼ� Ǯ �����ϱ� ���� ������ (bean) ��ü �������� ����
	String DB_URL = "jdbc:oracle:thin:@mydb.cqcoya9ktimf.ap-northeast-2.rds.amazonaws.com:1521:ORCL";
	String DB_USER = "khs9628";
	String DB_PASSWORD= "1q2w3e4r"; 
	
   private static Receipt_Board Receipt_board = new Receipt_Board();

   public static Receipt_Board getReceipt_Board() {
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
		
        String query = "SELECT a.app_stat from go_application a inner join go_member m on a.app_mem = m.mem_no where m.mem_id ='"+id+"' and a.app_type='R'";
        pstmt = con.prepareStatement(query);
        rs = pstmt.executeQuery();
        
       while(rs.next()) {
        	 sResult = rs.getString("app_stat");
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
