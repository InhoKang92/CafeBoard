package com.cafe.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cafe.domain.BoardVO;
import com.cafe.domain.Criteria;

public interface BoardService {

	public List<BoardVO> getList(@Param("cri") Criteria cri, @Param("boardName") String boardName);
	public List<BoardVO> getNoticeList();
	public void create(BoardVO board);
	public BoardVO read(Long bno);
	public boolean update(BoardVO board);
	public boolean delete(Long bno);
	
	public int getTotal(@Param("cri") Criteria cri, @Param("boardName") String boardName);
	
	public void readCountUp(Long bno);
	
}
