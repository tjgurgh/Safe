package org.board.controller;

import java.util.List;

import org.board.domain.UserVO;
import org.board.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/*")  
public class AdminController {
	
	@Autowired
	private UserMapper userMapper;   
	@GetMapping("/admin")  
	public void adminMain(Model model) {
		  List<UserVO> users = userMapper.selectUser();   
	        model.addAttribute("users", users);
	        
	}
	 @RequestMapping(value = "/admin/role/{userId}/{role}")
	    public String changeRole(@PathVariable String userId, @PathVariable String role) {
	        toggleRole(userId, role);
	        return "redirect:/admin/admin";
	    }

	    private void toggleRole(String userId, String role) {
	        UserVO user = userMapper.selectUserById(userId);

	        if (! user.hasRole(role))
	            userMapper.insertAuthority(user.getMem_id(), "ROLE_" + role.toUpperCase());
	        else
	            userMapper.deleteAuthority(user.getMem_id(), "ROLE_" + role.toUpperCase());
	    }


	
}
