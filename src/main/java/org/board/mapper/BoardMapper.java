package org.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.board.domain.BoardVO;
import org.board.domain.Criteria;

public interface BoardMapper {

	public List<BoardVO> getList();
	
	public List<BoardVO> getListWithPaging(Criteria cri);
	
	public List<BoardVO> getListWithPaging_N(Criteria cri);
	
	public void insert(BoardVO board);
	
	public Integer insertSelectKey(BoardVO board);
	
	public Integer insertSelectKey_N(BoardVO board);
	
	public BoardVO read(Long brd_id);
	
	public int delete(Long brd_id);
	
	public int delete_N(Long brd_id);
	
	public int update(BoardVO board);
	
	public int update_N(BoardVO board);
	
	public int getTotalCount(Criteria cri);
	
	public int getTotalCount_N(Criteria cri);
	
	public void updateViewCnt(Long brd_id);
	
	public void updateReplyCnt(@Param("brd_id") Long brd_id, @Param("amount") int amount);
}
