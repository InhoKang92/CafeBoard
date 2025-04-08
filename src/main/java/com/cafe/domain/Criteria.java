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

	private String type; // �˻�����, T(����), C(����), W(�ۼ���)
	private String keyword; // �˻�Ű����

	public Criteria() {
		this(1, 15);
	}

	public Criteria(int pageNum, int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
	}

	// TCW(���ڿ�) T C W �˻�����
	public String[] getTypeArr() {
		return type == null ? new String[] {} : type.split("");
	}

}
