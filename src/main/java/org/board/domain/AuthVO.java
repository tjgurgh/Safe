package org.board.domain;

import lombok.Data;

@Data
public class AuthVO{
	private String mem_id; 
	private String auth;
	
	  public boolean isRole(String role) {
	        return auth.equals("ROLE_" + role.toUpperCase());
	    }

}
 