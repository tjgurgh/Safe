package org.board.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.board.domain.UserVO;
import org.board.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/android/*")
@AllArgsConstructor
public class AndroidController {

	private UserService userservice;

	@PostMapping("/Android")
	public void Android(HttpServletRequest request, Model model) {
		String type = request.getParameter("type");
		model.addAttribute("type", type);
		log.info("type : " + type);
		// return "redirect:/android/Android?type=" + type;
	}

	@GetMapping("/ArduinoImage")
	public void ArduinoImage() {

	}

	@GetMapping("/Video")
	public void Video() {

	}

	@GetMapping("/AndroidMailSend")
	public void AndroidMailSend(HttpServletRequest request, HttpServletResponse response) throws Exception {

		UserVO userVO = new UserVO();

		String mem_id = null;
		if (request.getParameter("mem_id") != null) {
			mem_id = request.getParameter("mem_id");
		}
		userVO = userservice.allUserById(mem_id);

		log.info("==================");
		log.info("userVO : " + userVO);
		log.info("==================");
		userservice.findPasswordAndroid(response, userVO);
	}

	@GetMapping("/PhotoAlert")
	public void PhotoAlert(HttpServletRequest request, HttpServletResponse response) throws Exception {
		UserVO userVO = new UserVO();
		List<UserVO> list = userservice.AlertServer();
		log.info("==================");
		log.info("이메일이 발송되었습니다.");
		log.info("==================");
		for (int i = 0; i < list.size(); i++) {
			userVO = list.get(i);
			userservice.sendMailPhoto(userVO);
			log.info("메일 전송 : " + list.get(i));
			log.info("정상적으로 전송하였습니다.");
		}
		response.sendRedirect("http://192.168.43.10/clear");
	}

}
