package org.board.controller;

import org.board.domain.Criteria;
import org.board.domain.ReplyPageDTO;
import org.board.domain.ReplyVO;
import org.board.service.ReplyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@RequestMapping("/replies/")
@RestController
@Log4j
@AllArgsConstructor
public class ReplyController {
	private ReplyService service;
	
	//��� �۾��� �׽�Ʈ 
	@PreAuthorize("isAuthenticated()")
	@PostMapping(value = "/new", consumes = "application/json", produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> create(@RequestBody ReplyVO vo)	{
		log.info("ReplyVO : " + vo);
		int insertCount = service.register(vo);
		log.info("Reply INSERT COUNT : " + insertCount);
		return insertCount == 1
		? new ResponseEntity<>("success", HttpStatus.OK)
		: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	//Ư�� �Խù��� ��� ��� Ȯ��
	@GetMapping(value = "/pages/{cmt_board}/{page}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
	public ResponseEntity<ReplyPageDTO> getList(@PathVariable("page")int page, @PathVariable("cmt_board")Long cmt_board){
		log.info("get Reply List cmt_board : " + cmt_board);
		Criteria cri = new Criteria(page,10);
		log.info("cri : " + cri);
		return new ResponseEntity<>(service.getListPage(cri, cmt_board), HttpStatus.OK);	
	}
	
	//���  ��ȸ
	@GetMapping(value = "/{cmt_id}", produces= {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
	public ResponseEntity<ReplyVO> get(@PathVariable("cmt_id") Long cmt_id)	{
		log.info("get : " + cmt_id);
		return new ResponseEntity<>(service.get(cmt_id), HttpStatus.OK);
	}
	
	//��� ����
	@PreAuthorize("principal.username == #vo.cmt_writer")
	@DeleteMapping(value= "/{cmt_id}")
	public ResponseEntity<String> remove(@RequestBody ReplyVO vo, @PathVariable("cmt_id") Long cmt_id) {
		log.info("remove : " + cmt_id);
		return service.remove(cmt_id) == 1
		? new ResponseEntity<>("success", HttpStatus.OK)
		: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);		
	}
	
	//��� ����
	@PreAuthorize("principal.username == #vo.cmt_writer")
	@RequestMapping(method = {RequestMethod.PUT, RequestMethod.PATCH},
			value = "/{cmt_id}",
			consumes = "application/json")
	public ResponseEntity<String> modify(@RequestBody ReplyVO vo, @PathVariable("cmt_id")Long cmt_id){
		
		log.info("cmt_id : " + cmt_id);
		log.info("modify : " + vo);
		return service.modify(vo) == 1
		? new ResponseEntity<>("success", HttpStatus.OK)
		: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);		
	}
	
}
