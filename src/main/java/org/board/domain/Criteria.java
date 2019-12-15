package org.board.domain;

import org.springframework.web.util.UriComponentsBuilder;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Criteria {
	//페이지 수 , 페이지당 출력될 개수
	private int pageNum;
	private int amount;
	
	//검색 종류와 키워드
	private String type;
	private String keyword;
	// this(페이지, 개수)
	public Criteria() {
		this(1, 10);
	}
	
	public Criteria(int pageNum, int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
	}
	
	
	public String[] getTypeArr(){
		return type == null? new String[] {}: type.split("");
	}
	
	//URIComponentsBuilder는 브라우저에서 GET 방식의 파라미터 전송을 자동으로 도와줌
	public String getListLink() {
		UriComponentsBuilder builder = UriComponentsBuilder.fromPath("")
				.queryParam("pageNum", this.getPageNum())
				.queryParam("amount", this.getAmount())
				.queryParam("type", this.getType())
				.queryParam("keyword", this.getKeyword());
		return builder.toUriString();
	}
}
