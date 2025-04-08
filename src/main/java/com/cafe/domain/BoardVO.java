package com.cafe.domain;

import java.util.Date;

import lombok.Data;

@Data
public class BoardVO {

	private Long bno;
	private String boardName;
	private String classification;
	private String displayType;
	private String title;
	private String content;
	private String writer;
	private Date regDate;
	private Date updateDate;
	private Long readCount;
	
}
