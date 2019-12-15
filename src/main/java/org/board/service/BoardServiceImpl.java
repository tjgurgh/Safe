package org.board.service;

import java.util.List;

import org.board.domain.BoardAttachVO;
import org.board.domain.BoardVO;
import org.board.domain.Criteria;
import org.board.mapper.BoardAttachMapper;
import org.board.mapper.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
public class BoardServiceImpl implements BoardService{
	@Setter(onMethod_=@Autowired)
	private BoardMapper mapper;
	
	@Setter(onMethod_=@Autowired)
	private BoardAttachMapper attachMapper;
	
	@Transactional //�듃�옖�옲�뀡 泥섎━
	@Override
	public void register(BoardVO board) {
		log.info("register......." + board);
		
		mapper.insertSelectKey(board);
		
		if(board.getAttachList() == null || board.getAttachList().size()<=0) {
			return;
		}
		board.getAttachList().forEach(attach ->{
			attach.setBrd_id(board.getBrd_id());
			attachMapper.insert(attach);
		});
	}
	
	public void register_N(BoardVO board) {
	log.info("register......." + board);
	
	mapper.insertSelectKey_N(board);
	
	if(board.getAttachList() == null || board.getAttachList().size()<=0) {
		return;
	}
	board.getAttachList().forEach(attach ->{
		attach.setBrd_id(board.getBrd_id());
		attachMapper.insert(attach);
	});
}
	
	@Override
	public List<BoardVO> getListWithPaging(Criteria cri){
		log.info("get List with criteria : " + cri);
		return mapper.getListWithPaging(cri);
	}
	
	public List<BoardVO> getListWithPaging_N(Criteria cri){
		log.info("get List with criteria : " + cri);
		return mapper.getListWithPaging_N(cri);
	}
	
	@Override
	public BoardVO get(Long brd_id) {
		log.info("get......" + brd_id);
		return mapper.read(brd_id);
	}
	
	@Transactional // �듃�옖�옲�뀡 泥섎━
	@Override
	public boolean modify(BoardVO board) {
		log.info("modify........" + board);
		attachMapper.deleteAll(board.getBrd_id());
		
		boolean modifyResult = mapper.update(board) == 1;
		
		if(modifyResult && board.getAttachList() != null && board.getAttachList().size() > 0) {
			board.getAttachList().forEach(attach -> {
				attach.setBrd_id(board.getBrd_id());
				attachMapper.insert(attach);
			});
		}
		return modifyResult;
	}
	
	@Transactional // �듃�옖�옲�뀡 泥섎━
	@Override
	public boolean modify_N(BoardVO board) {
		log.info("modify........" + board);
		attachMapper.deleteAll(board.getBrd_id());
		
		boolean modifyResult = mapper.update_N(board) == 1;
		
		if(modifyResult && board.getAttachList() != null && board.getAttachList().size() > 0) {
			board.getAttachList().forEach(attach -> {
				attach.setBrd_id(board.getBrd_id());
				attachMapper.insert(attach);
			});
		}
		return modifyResult;
	}
	
	@Transactional
	@Override
	public boolean remove(Long brd_id) {
		log.info("remove......" + brd_id);
		attachMapper.deleteAll(brd_id);
		return mapper.delete(brd_id) == 1;
	}
	
	@Transactional
	@Override
	public boolean remove_N(Long brd_id) {
		log.info("remove......" + brd_id);
		attachMapper.deleteAll(brd_id);
		return mapper.delete_N(brd_id) == 1;
	}
	
	@Override
	public void updateViewCnt(Long brd_id) {
		mapper.updateViewCnt(brd_id);
	}
	
	@Override
	public int getTotal(Criteria cri) {
		log.info("get total count");
		return mapper.getTotalCount(cri);
	}
	
	@Override
	public int getTotal_N(Criteria cri) {
		log.info("get total count");
		return mapper.getTotalCount_N(cri);
	}
	
	@Override
	public List<BoardAttachVO> getAttachList(Long brd_id){
		log.info("get Attach list by brd_id" + brd_id);
		return attachMapper.findByBrd_id(brd_id);
	}
}
