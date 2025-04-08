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
    	List<BoardVO> list = service.getList(new Criteria(2, 10), "�����Խ���");
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
        board.setBoardName("�����Խ���");
        board.setClassification("����");
        board.setTitle("�����׽�Ʈ");
        board.setContent("�����׽�Ʈ");
        board.setWriter("�����ۼ���");
        
        service.create(board);
        
        log.info("������ �Խù��� ��ȣ: " + board.getBno());
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
    	
    	board.setTitle("���񽺼����׽�Ʈ");
    	
    	log.info("���� ��� : " + service.update(board));
    }
    
    @Test
    public void testDelete() {
    	log.info("���� ��� : " + service.delete(24L));

    }
    
    @Test
    public void testReadCountUp() {
    	service.readCountUp(21L);
    }

}
