package org.board.service;

import java.util.List;

import org.board.domain.ChestVO;
import org.board.mapper.ChestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
public class ChestServiceImpl implements ChestService {

	@Setter(onMethod_= @Autowired)
	private ChestMapper chestMapper;
	
	@Override
	public List<ChestVO> getList(){
		return chestMapper.getList();
	}
	
	@Override
	public void insert(ChestVO chest) {
		chestMapper.insert(chest);
	}
	
	@Override
	public ChestVO read(int item_safe_no) {
		log.info("item_safe_no" + item_safe_no);
		return chestMapper.read(item_safe_no);
	}
	
	@Override
	public int emptyUpdate(int item_safe_no) {
		return chestMapper.emptyUpdate(item_safe_no);
	}

	@Override
	public int chestDelete(int item_safe_no) {
		return chestMapper.chestDelete(item_safe_no);
	}
	
	@Override
	public int receiptDenyUpdate(int item_safe_no) {
		return chestMapper.receiptDenyUpdate(item_safe_no);
	}
	@Override
	public boolean update(ChestVO chest) {
		
		return chestMapper.update(chest) == 1;
	}

	@Override
	public boolean delete(int item_safe_no) {

		return chestMapper.delete(item_safe_no) == 1;
	}

	
}