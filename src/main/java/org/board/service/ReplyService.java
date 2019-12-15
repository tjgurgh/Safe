package org.board.service;

import java.util.List;

import org.board.domain.Criteria;
import org.board.domain.ReplyPageDTO;
import org.board.domain.ReplyVO;

public interface ReplyService {
	
	public int register(ReplyVO vo);
	
	public ReplyVO get(Long cmt_board);
	
	public int modify(ReplyVO vo);
	
	public int remove(Long cmt_id);
	
	public List<ReplyVO> getList(Criteria cri, Long cmt_board);
	
	public ReplyPageDTO getListPage(Criteria cri, Long cmt_board);
}
