package org.board.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.board.domain.BoardAttachVO;
import org.board.domain.BoardVO;
import org.board.domain.Criteria;
import org.board.domain.PageDTO;
import org.board.service.BoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;


@Controller
@Log4j
@RequestMapping("/board/*")
@AllArgsConstructor
public class BoardController { 
	
	private BoardService service;
	@GetMapping("/list") 
	public void list(Criteria cri, Model model){
		log.info("list : " + cri);
		model.addAttribute("list", service.getListWithPaging(cri));
			
		int total = service.getTotal(cri);
		log.info("total : " + total);
		model.addAttribute("pageMaker", new PageDTO(cri, total));
	}
	
	@GetMapping("/list_N") 
	public void list_N(Criteria cri, Model model){
		log.info("list : " + cri);
		model.addAttribute("list_N", service.getListWithPaging_N(cri));
		
		int total = service.getTotal_N(cri);
		log.info("total : " + total);
		model.addAttribute("pageMaker", new PageDTO(cri, total));
	}
	
	@GetMapping("/register")
	@PreAuthorize("isAuthenticated()")
	public void register() {
		
	}
	

	@PostMapping("/register")
	@PreAuthorize("isAuthenticated()") 
	public String register(BoardVO board, RedirectAttributes rttr){
		log.info("============================");
		log.info("register : " + board);
		
		if(board.getAttachList() != null) {
		board.getAttachList().forEach(attach -> log.info(attach));
		}
		log.info("============================");
		service.register(board);
		rttr.addFlashAttribute("result", board.getBrd_id());
		return "redirect:/board/list";
	}
	
	@GetMapping("/register_N")
	@PreAuthorize("isAuthenticated()")
	public void register_N() {
		
	}
	

	@PostMapping("/register_N")
	@PreAuthorize("isAuthenticated()")
	public String register_N(BoardVO board, RedirectAttributes rttr){
		log.info("============================");
		log.info("register : " + board);
		
		if(board.getAttachList() != null) {
		board.getAttachList().forEach(attach -> log.info(attach));
		}
		log.info("============================");
		service.register_N(board);
		rttr.addFlashAttribute("result", board.getBrd_id());
		return "redirect:/board/list_N";
	}
	
	

	@GetMapping({"/get", "/get_N", "/modify", "/modify_N"})	
	public void get(@RequestParam("brd_id") Long brd_id, @ModelAttribute("cri") Criteria cri, Model model){
		log.info("/get or modify");
		service.updateViewCnt(brd_id);
		model.addAttribute("board", service.get(brd_id));
	}
	
	@PreAuthorize("principal.username == #board.brd_writer")
	@PostMapping("/modify")
	public String modify(BoardVO board, @ModelAttribute("cri")Criteria cri, RedirectAttributes rttr) {
		log.info("modify : " + board);
		
		if(service.modify(board)) {
			rttr.addFlashAttribute("result", "success");
		}
		
		return "redirect:/board/list" + cri.getListLink();
	}
	
	@PreAuthorize("principal.username == #board.brd_writer")
	@PostMapping("/modify_N")
	public String modify_N(BoardVO board, @ModelAttribute("cri")Criteria cri, RedirectAttributes rttr) {
		log.info("modify : " + board);
		
		if(service.modify_N(board)) {
			rttr.addFlashAttribute("result", "success");
		}
		
		return "redirect:/board/list_N" + cri.getListLink();
	}
	
	@PreAuthorize("principal.username == #brd_writer")
	@PostMapping("/remove")
	public String remove(@RequestParam("brd_id") Long brd_id, Criteria cri,
			RedirectAttributes rttr, String brd_writer) {
		log.info("remove...." + brd_id);
		List<BoardAttachVO> attachList = service.getAttachList(brd_id);
		if(service.remove(brd_id)) {
			deleteFiles(attachList);
			rttr.addFlashAttribute("result", "success");
		}

		return "redirect:/board/list" + cri.getListLink(); 
	}

	@PreAuthorize("principal.username == #brd_writer")
	@PostMapping("/remove_N")
	public String remove_N(@RequestParam("brd_id") Long brd_id, Criteria cri,
			RedirectAttributes rttr, String brd_writer) {
		log.info("remove...." + brd_id);
		List<BoardAttachVO> attachList = service.getAttachList(brd_id);
		if(service.remove_N(brd_id)) {
			deleteFiles(attachList);
			rttr.addFlashAttribute("result", "success");
		}

		return "redirect:/board/list_N" + cri.getListLink(); 
	}

	//�뙆�씪 �궘�젣 泥섎━
	private void deleteFiles(List<BoardAttachVO> attachList) {
		if(attachList == null || attachList.size() == 0)  {
			return;
		}
		log.info("delete attach files.................................");
		log.info(attachList);
		
		attachList.forEach(attach -> {
			try {
				Path file = Paths.get("c:\\upload\\"+attach.getUploadPath()+"\\"+attach.getUuid()+"_"+attach.getFileName());
				
				Files.deleteIfExists(file);
				if(Files.probeContentType(file).startsWith("image")) {
					Path thumbNail = Paths.get("c:\\upload\\"+attach.getUploadPath()+"\\s_"+attach.getUuid()+"_"+attach.getFileName());
					Files.delete(thumbNail);
					/*
					 * Path file =
					 * Paths.get("/usr/local/tomcat9/webapps/upload"+attach.getUploadPath()+
					 * "\\"+attach.getUuid()+"_"+attach.getFileName());
					 * 
					 * Files.deleteIfExists(file);
					 * if(Files.probeContentType(file).startsWith("image")) { Path thumbNail =
					 * Paths.get("/usr/local/tomcat9/webapps/upload"+attach.getUploadPath()+"\\s_"+
					 * attach.getUuid()+"_"+attach.getFileName()); Files.delete(thumbNail);
					 */				}
			} catch(Exception e) {
				log.error("delete file error : " + e.getMessage());
			}
		}); //foreach end
	}
	 
	@GetMapping(value = "/getAttachList", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<List<BoardAttachVO>> getAttachList(Long brd_id) {
		log.info("getAttachList : " + brd_id);
		
		return new ResponseEntity<>(service.getAttachList(brd_id), HttpStatus.OK);
	}
	
}
