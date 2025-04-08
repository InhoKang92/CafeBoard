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
	      log.info("�Խ��� ��� ��ȸ");
	      return mapper.getListWithPaging(cri, boardName);  // Mapper���� �Խ��� ����� ��ȯ�ϵ��� �ؾ� ��
	   }
	   
	   @Override
	   public List<BoardVO> getNoticeList() {
	      return mapper.getNoticeList();
	   }
	   
	   @Override
	   public void create(BoardVO board) {
	      mapper.create(board);
	      log.info("�Խù� ���� �Ϸ�: " + board);
	   }

	   @Override
	   public BoardVO read(Long bno) {
	      log.info("�Խù� ��ȸ: " + bno);
	      return mapper.read(bno);  // Mapper���� �Խù� �ϳ��� ��ȯ�ϵ��� �ؾ� ��
	   }

	   @Override
	   public boolean update(BoardVO board) {
	      log.info("�Խù� ����: " + board);
	      int updatedCount = mapper.update(board);  // Mapper���� ������ �Ǽ� ��ȯ
	      return updatedCount > 0;  // ������ �Ǽ��� 1 �̻��̸� ����
	   }

	   @Override
	   public boolean delete(Long bno) {
	      log.info("�Խù� ����: " + bno);
	      int deletedCount = mapper.delete(bno);  // Mapper���� ������ �Ǽ� ��ȯ
	      return deletedCount > 0;  // ������ �Ǽ��� 1 �̻��̸� ����
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
