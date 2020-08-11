package org.board.controller;

import java.util.List;

import org.board.domain.ApplicationVO;
import org.board.domain.ChestVO;
import org.board.domain.UserVO;
import org.board.service.ChestService;
import org.board.service.RequestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@Controller
@RequestMapping("/request/*")
@AllArgsConstructor
public class RequestController {

	private RequestService requestService;
	private ChestService chestService;

	@GetMapping("/page")
	public String mainPage(ChestVO chest, UserVO user, ApplicationVO app, Model model) {
		List<ChestVO> chests = chestService.getList();
		model.addAttribute("chests", chests);
		
		List<UserVO> users = requestService.getList();
		model.addAttribute("users", users);

		List<ApplicationVO> apps_R = requestService.getList_R();
		model.addAttribute("apps_R", apps_R);

		List<ApplicationVO> apps_K = requestService.getList_K();
		model.addAttribute("apps_K", apps_K);
		return "/request/page";
	}

	@GetMapping("/detail")
	public String detail() {
		return "/request/detail";
	}

	// 수령 신청
	@PostMapping("/page/receipt/approvalReceipt")
	public String approvalReceipt(@RequestParam("item_safe_no")int item_safe_no, @RequestParam("app_no")int app_no, @RequestParam("app_mem") int app_mem, ApplicationVO app) {
		requestService.approvalReceipt(app);
		chestService.delete(item_safe_no);
		log.info("수령 신청 : " + app);
		return "redirect:/request/page";
	}

	// 수령 거절
	@PostMapping("/page/receipt/denyApprovalReceipt")
	public String denyApprovalReceipt(ApplicationVO app) {

		requestService.denyApprovalReceipt(app);
		log.info("수령 거절 : " + app);
		return "redirect:/request/page";
	}

	// 보관 신청
	@PostMapping("/page/keep/approvalKeep")
	public String approvalKeep(@RequestParam("item_safe_no")int item_safe_no, @RequestParam("app_item_no") int app_item_no, @RequestParam("app_mem") int app_mem, ApplicationVO app) {
		chestService.chestDelete(item_safe_no);
		chestService.chestupdate(item_safe_no);
		requestService.approvalKeep(app);
		log.info("보관 신청 : " + app);
		return "redirect:/request/page";
	}

	// 보관 거절
	@PostMapping("/page/keep/denyApprovalKeep")
	public String denyApprovalKeep(@RequestParam("item_safe_no")int item_safe_no, ApplicationVO app) {
		requestService.denyApprovalKeep(app);
		chestService.chestDelete(item_safe_no);
		chestService.delete(item_safe_no);
		log.info("보관 거절 : " + app);
		return "redirect:/request/page";
	}
	
	//회원 승인
	@PostMapping("/page/approvalMember")
	public String approvalMember(@RequestParam("mem_id") String mem_id, UserVO user) {
		requestService.approvalMember(user);
		log.info("회원 승인  : " + user);
		return "redirect:/request/page";
	}
	
	//회원 거절
	@PostMapping("/page/denyApprovalMember")
	public String denyApprovalMember(@RequestParam("mem_id") String mem_id) {
		requestService.denyApprovalMember(mem_id);
		log.info("회원 거절 : " + mem_id);
		return "redirect:/request/page";
	}
}
