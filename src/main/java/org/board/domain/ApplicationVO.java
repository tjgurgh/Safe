package org.board.domain;

import java.util.Date;

import lombok.Data;

@Data
public class ApplicationVO {

	private int app_no; //요청사항 고유 번호
	private int app_mem; //요청한 멤버 고유 번호
	private int app_safe_no;
	private int app_item_no; //요청한 금고 고유 번호
	
	private String app_content; //요청사유
	private char app_type; // 요청의 타입 (R수령, K보관)
	private char app_stat; // 요청의 관리자 승인 여부(Y : 관리자 승인, N : 미승인, R : 승인 거절,  C : 처리 완료)
	
	private Date app_date;
}
