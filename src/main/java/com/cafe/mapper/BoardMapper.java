package com.cafe.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.cafe.domain.BoardVO;
import com.cafe.domain.Criteria;

public interface BoardMapper {

	public List<BoardVO> getList();
	public List<BoardVO> getNoticeList();
	
	public void create(BoardVO board);
	public BoardVO read(Long bno);
	public int update(BoardVO board);
	public int delete(Long bno); 
	
	public List<BoardVO> getListWithPaging(@Param("cri") Criteria cri, @Param("boardName") String boardName);
	
	public int getTotalCount(@Param("cri") Criteria cri, @Param("boardName") String boardName);
	
	public void readCountUp(Long bno);
	
	public List<BoardVO> searchTest(Map<String, Map<String, String>> map);
	
}
