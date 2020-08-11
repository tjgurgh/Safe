package org.board.domain;

import java.util.Date;

import lombok.Data;

@Data
public class ReplyVO {

	private Long cmt_id;
	private Long cmt_board;
	private String cmt_writer;
	private String cmt_content;
	private Date cmt_date;
	private Date cmt_updateDate;
}
