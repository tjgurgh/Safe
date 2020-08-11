package org.board.mapper;

import java.util.List;

import org.board.domain.BoardAttachVO;

public interface BoardAttachMapper {
	public void insert(BoardAttachVO vo);
	
	public void delete(String uuid);
	
	public List<BoardAttachVO> findByBrd_id(Long brd_id);
	
	public void deleteAll(Long brd_id);
	
	public List<BoardAttachVO> getOldFiles();
}
