package org.board.mapper;

import java.util.List;

import org.board.domain.BoardVO;
import org.board.domain.Criteria;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class BoardMapperTests {
	@Setter(onMethod_= {@Autowired})
	private BoardMapper mapper;
	@Test
	public void testGetList() {
		Criteria cri = new Criteria();
		mapper.getListWithPaging(cri).forEach(board -> log.info(board));
	}
	@Test
	public void testInsert() {
		BoardVO board = new BoardVO();
		board.setBrd_sub("���� �ۼ��ϴ� ��");
		board.setBrd_content("���� �ۼ��ϴ� ����");
		board.setBrd_writer("newbie");
		mapper.insert(board);
		log.info(board);
	}
	
	@Test
	public void testInsertSelectKey() {
		BoardVO board = new BoardVO();
		board.setBrd_sub("���� �ۼ��ϴ� �� select key");
		board.setBrd_content("���� �ۼ��ϴ� ���� select key");
		board.setBrd_writer("newbie");
		mapper.insertSelectKey(board);
		log.info(board);
	}
	
	@Test
	public void testRead() {
		BoardVO board = mapper.read(5L);
		log.info(board);
	}
	
	@Test
	public void testDelete() {
		log.info("DELETE COUNT : " + mapper.delete(3L));
	}
	
	@Test
	public void testUpdate() {
		BoardVO board = new BoardVO();
		board.setBrd_id(5L);
		board.setBrd_sub("������ ����");
		board.setBrd_content("������ ����");
		board.setBrd_writer("user00");
		
		int count = mapper.update(board);
		log.info("UPDATE COUNT : " + count);
	}
	
	@Test
	public void testPaging() {
		Criteria cri = new Criteria();
		cri.setPageNum(3);
		cri.setAmount(10);
		List<BoardVO> list = mapper.getListWithPaging(cri);
		list.forEach(board -> log.info(board));
	}
	
	@Test
	public void testSearch() {
		Criteria cri = new Criteria();
		cri.setKeyword("����");
		cri.setType("TC");
		List<BoardVO> list = mapper.getListWithPaging(cri);
		list.forEach(board -> log.info(board));
	}
	
}
