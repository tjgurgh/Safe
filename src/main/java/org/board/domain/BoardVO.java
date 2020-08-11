package org.board.domain;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class BoardVO {
	private Long brd_id;
	private int brd_cnt;
	private int cmt_cnt;
	private String brd_sub;
	private String brd_writer;
	private String brd_content;
	private char brd_type;
	private Date brd_date;
	private Date brd_updateDate;

	private List<BoardAttachVO> attachList;
}
