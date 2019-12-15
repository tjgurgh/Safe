package org.board.domain;

import java.util.Date;

import lombok.Data;

@Data
public class ChestVO {

	private int item_safe_no; //보관품의 금고 번호
	private int item_no; //보관품 고유번호
	private String item_name; //보관품 이름
	private String item_class; //보관품 분류
	private String item_remark; //보관품 비고
	private char item_stat; //금고상태(F/E) F존재, E빔
	private Date item_keep_start; //보관품 보관 시작 시간
	private Date item_keep_end; //보관품 보관 만료 시간
	
}
