package org.board.mapper;

import java.util.List;

import org.board.domain.ChestVO;

public interface ChestMapper {

	public ChestVO read(int item_safe_no);

	public List<ChestVO> getList();
	
	public void insert(ChestVO chest);
	
	public int emptyUpdate(int item_safe_no);
	
	public int chestDelete(int item_safe_no);
	
	public int receiptDenyUpdate(int item_safe_no);
	
	public int update(ChestVO chest);
	
	public int delete(int item_safe_no);
	
}
