package com.cafe.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cafe.domain.Criteria;
import com.cafe.domain.ReplyVO;

public interface ReplyMapper {
	public int insert(ReplyVO vo);
    public ReplyVO read(Long rno);
    public int delete(Long rno);
  //reply, update만 수정가능
  	public int update(ReplyVO reply);
 // 특정 댓글 페이징 처리
    public List<ReplyVO> getListAsc(Long bno);
    public List<ReplyVO> getListDesc(Long bno);
    
    public int getTotalReplyCount(Long bno);

}
