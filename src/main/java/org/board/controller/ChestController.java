	package org.board.controller;

import java.util.List;

import org.board.domain.ChestVO;
import org.board.service.ChestService;
import org.board.service.RequestService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@RequestMapping("/chest/*")
@Controller
@AllArgsConstructor
public class ChestController {

	private ChestService chestService;
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/chestMain")
	public void chestMain(ChestVO chest ,Model model) {
		List<ChestVO> chests = chestService.getList();
		model.addAttribute("chests", chests);
	}
	
	@GetMapping("/chestSearch")
	public void chestSearch(ChestVO chest ,Model model) {
		List<ChestVO> search = chestService.getList();
		model.addAttribute("search", search);
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping({"/chestDetail", "/chestDetailUpdate"})	
	public void chestDetail(@RequestParam("item_safe_no") int item_safe_no, Model model) {
		log.info("chestDetail");
		model.addAttribute("chest", chestService.read(item_safe_no));
	}
	
	@GetMapping({"/chestRegister", "/keepRegister"})
	public void Register() {
		
	}
	
	@PostMapping("/update")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String update(ChestVO chest, RedirectAttributes rttr) {
		log.info("chest update");
		chestService.update(chest);
		return "redirect:/chest/chestMain";
	}
	
	@PostMapping("/delete")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String delete(@RequestParam("item_safe_no") int item_safe_no , RedirectAttributes rttr) {
		log.info("chest delete");
		chestService.delete(item_safe_no);
		return "redirect:/chest/chestMain";
	}
	
	
}
