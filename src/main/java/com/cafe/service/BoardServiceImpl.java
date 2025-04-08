package com.cafe.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.cafe.domain.BoardVO;
import com.cafe.domain.Criteria;
import com.cafe.mapper.BoardMapper;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
@AllArgsConstructor
public class BoardServiceImpl implements BoardService {

	private final BoardMapper mapper;

	@Override
	   public List<BoardVO> getList(Criteria cri, String boardName) {
	      log.info("게시판 목록 조회");
	      return mapper.getListWithPaging(cri, boardName);  // Mapper에서 게시판 목록을 반환하도록 해야 함
	   }
	   
	   @Override
	   public List<BoardVO> getNoticeList() {
	      return mapper.getNoticeList();
	   }
	   
	   @Override
	   public void create(BoardVO board) {
	      mapper.create(board);
	      log.info("게시물 생성 완료: " + board);
	   }

	   @Override
	   public BoardVO read(Long bno) {
	      log.info("게시물 조회: " + bno);
	      return mapper.read(bno);  // Mapper에서 게시물 하나를 반환하도록 해야 함
	   }

	   @Override
	   public boolean update(BoardVO board) {
	      log.info("게시물 수정: " + board);
	      int updatedCount = mapper.update(board);  // Mapper에서 수정된 건수 반환
	      return updatedCount > 0;  // 수정된 건수가 1 이상이면 성공
	   }

	   @Override
	   public boolean delete(Long bno) {
	      log.info("게시물 삭제: " + bno);
	      int deletedCount = mapper.delete(bno);  // Mapper에서 삭제된 건수 반환
	      return deletedCount > 0;  // 삭제된 건수가 1 이상이면 성공
	   }
	   
	   @Override
	   public int getTotal(@Param("cri") Criteria cri, @Param("boardName") String boardName) {
		   return mapper.getTotalCount(cri, boardName);
	   }
	   
	   @Override
	   public void readCountUp(Long bno) {
		   mapper.readCountUp(bno);
	   }
	
}
