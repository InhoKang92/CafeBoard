package com.cafe.service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cafe.domain.BoardVO;
import com.cafe.domain.Criteria;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class BoardServiceImplTest {

	@Autowired
    private BoardService service;
    
    @Test
    public void testGetList() {
    	List<BoardVO> list = service.getList(new Criteria(2, 10), "뉴스게시판");
    	list.forEach(board->log.info(board));
    }
    
    @Test
    public void testGetNoticeList() {
    	List<BoardVO> list = service.getNoticeList();
    	list.forEach(board->log.info(board));
    }
    
    @Test
    public void testCreate() {
        BoardVO board = new BoardVO();
        board.setBoardName("자유게시판");
        board.setClassification("유머");
        board.setTitle("서비스테스트");
        board.setContent("서비스테스트");
        board.setWriter("서비스작성자");
        
        service.create(board);
        
        log.info("생성된 게시물의 번호: " + board.getBno());
    }
    
    @Test
    public void testRead() {
    	log.info(service.read(23L));
    }
    
    @Test
    public void testUpdate() {
    	BoardVO board = service.read(24L);
    	
    	if(board == null) {
    		return;
    	};
    	
    	board.setTitle("서비스수정테스트");
    	
    	log.info("수정 결과 : " + service.update(board));
    }
    
    @Test
    public void testDelete() {
    	log.info("삭제 결과 : " + service.delete(24L));

    }
    
    @Test
    public void testReadCountUp() {
    	service.readCountUp(21L);
    }

}
