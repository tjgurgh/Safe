package org.board.service;

import java.util.List;

import org.board.domain.ChestVO;

public interface ChestService {
	
	public ChestVO read(int item_safe_no);
	
	public List<ChestVO> getList();
	
	public List<ChestVO> getSearch();

	public void insert(ChestVO chest);
	
	public int emptyUpdate(int item_safe_no);
	
	public int chestupdate(int item_safe_no);
	
	public int chestDelete(int item_safe_no);
	
	public int receiptDenyUpdate(int item_safe_no);
	
	public boolean update(ChestVO chest);
	
	public boolean delete(int item_safe_no);
}
