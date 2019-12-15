package org.board.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Param;
import org.board.domain.UserVO;

public interface UserService {
	
	public void register(UserVO user);
	
	public void registerAuthority(@Param("mem_id") String mem_id, @Param("auth") String auth);
		
	/** 회원 아이디 중복 체크 */
	public UserVO idCheck(String mem_id); 
	
	public void sendMail(UserVO userVO);
	
	public List<String> findId(HttpServletResponse response, String mem_email) throws Exception;
	
	public void findPassword(HttpServletResponse response, UserVO userVO) throws Exception;
	
	public int modify(UserVO userVO);
	
	public UserVO changePassword(UserVO userVO, String oldPassword, HttpServletResponse response) throws Exception;
	
	public UserVO allUserById(String mem_id);
	
	public void findPasswordAndroid(HttpServletResponse response, UserVO userVO) throws Exception;

	public void sendMailAndroid(UserVO userVO);
	
	public void sendMailPhoto(UserVO userVO);
	
	public List<UserVO> AlertServer();
}
	