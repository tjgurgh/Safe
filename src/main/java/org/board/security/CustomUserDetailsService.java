package org.board.security;

import org.board.domain.UserVO;
import org.board.mapper.UserMapper;
import org.board.security.domain.CustomUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
public class CustomUserDetailsService implements UserDetailsService{
	
	@Setter(onMethod_= {@Autowired})
	private UserMapper userMapper;
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException{
		log.warn("Load User By UserName : " + userName);
		
		UserVO vo = userMapper.read(userName);
		log.warn("queried by member mapper : " + vo);
		
		return vo == null ? null : new CustomUser(vo);
	}
}
