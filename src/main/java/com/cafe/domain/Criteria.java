package com.cafe.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class Criteria {

	private int pageNum;
	private int amount;

	private String type; // 검색조건, T(제목), C(내용), W(작성자)
	private String keyword; // 검색키워드

	public Criteria() {
		this(1, 15);
	}

	public Criteria(int pageNum, int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
	}

	// TCW(문자열) T C W 검색조건
	public String[] getTypeArr() {
		return type == null ? new String[] {} : type.split("");
	}

}
