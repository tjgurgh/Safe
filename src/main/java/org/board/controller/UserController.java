package org.board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.board.domain.UserVO;
import org.board.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@AllArgsConstructor
@RequestMapping("/*")
public class UserController { 
	
	private UserService userservice;
	
	@GetMapping("/signUp")
	public void signPage() {
	}
	
	@PostMapping("/signUpComplete")
	public String create(UserVO user) {
		log.info(user);
		userservice.register(user);
		userservice.registerAuthority(user.getMem_id(), "ROLE_USER");
		return "redirect:/mainPage";
    }
	
	
	
	@ResponseBody
	@PostMapping("/idCheck")
    public String idCheck(HttpServletRequest request) {
		String mem_id = request.getParameter("mem_id");
		UserVO idCheck = userservice.idCheck(mem_id);
		log.info("아이디 중복 체크 : "  + mem_id + "\t" + idCheck);
		
		String check="";

		if(idCheck != null) {
			 check="yes";         //중복
		} else {
			check="no";         //중복x
		}
		log.info("아이디 중복 결과 : " + check);
		return check;	
    }
	
	@GetMapping("/findIdForm")
	public void findIdForm() {
		
	}
	
	@GetMapping("/findId")
	public void findId() {
		
	}
	
	@PostMapping("/findId")
	public String findId(HttpServletResponse response, @RequestParam("mem_email") String mem_email, Model model) throws Exception{
		model.addAttribute("id", userservice.findId(response, mem_email));
		return "/findId";
	}

	@PostMapping("/findPassword")
	public void findPassword(@ModelAttribute UserVO userVO, HttpServletResponse response) throws Exception{
		userservice.findPassword(response, userVO);
	}
	
	@GetMapping("/findPassword")
	public void findPage() {
		
	}
	 
	@GetMapping("/userEdit")
	public String userEdit() {
		
		return "/userEdit";
	}
	
	@PostMapping("/userEdit")
	public String userEditChange(UserVO userVO, HttpSession session) {
		userservice.modify(userVO);
		session.invalidate();
		return "redirect:/mainPage";
	}
	
	// 비밀번호 변경
	@PostMapping("/changePassword")
	public void changePassword(UserVO userVO, @RequestParam("oldPassword") String oldPassword, HttpServletResponse response) throws Exception{
//		log.info("원래 비밀번호 : " + oldPassword + "			" + userVO + "		" + userservice.changePassword(userVO, oldPassword, response));
		userservice.changePassword(userVO, oldPassword, response);
	}
}
 