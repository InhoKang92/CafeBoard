package com.cafe.service;

import java.util.List;

import com.cafe.domain.Criteria;
import com.cafe.domain.ReplyVO;

public interface ReplyService {
	
	public int register(ReplyVO vo);

	public ReplyVO get(Long rno);

	public int modify(ReplyVO vo);

	public int remove(Long rno);

           // Ư�� �Խñ��� ����� ����¡�Ͽ� �������� �޼���
	public List<ReplyVO> getListAsc(Long bno);
	public List<ReplyVO> getListDesc(Long bno);
	
	public int getTotalReplyCount(Long bno);
}
