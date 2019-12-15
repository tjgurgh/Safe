package org.board.domain;

import java.util.List;

import lombok.Data;

@Data
public class UserVO {
	private int mem_no;
	private String mem_id;
	private String mem_pwd;
	private String mem_name;
	private String mem_phone;
	private String mem_email;
	private char mem_stat;
	private boolean enabled;
	private List<AuthVO> authList;
	
	public boolean hasRole(String role) {
        for (AuthVO auth : authList)
            if (auth.isRole(role))
                return true;

        return false;
    }
	
	public UserVO() {
		
	}
	public UserVO(String mem_id) {
		super();
		this.mem_id = mem_id;
	}

}
