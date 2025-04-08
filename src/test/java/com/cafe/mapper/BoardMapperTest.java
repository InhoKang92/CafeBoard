package com.cafe.mapper;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class BoardMapperTest {

	@Autowired
	private BoardMapper mapper;
	
	@Test
	public void testGetList() {
		mapper.getList().forEach(board -> log.info(board));
	}
	
	@Test
	public void testGetNoticeList() {
		mapper.getNoticeList().forEach(board -> log.info(board));
	}
	
	@Test
	public void testCreate() {
		BoardVO board = new BoardVO();
		board.setBoardName("자유게시판");
		board.setClassification("유머");
		board.setTitle("새글테스트");
		board.setContent("새글테스트");
		board.setWriter("홍길동");
		
		mapper.create(board);
		
		log.info(board.getBno());
	}
	
	@Test
	public void testRead() {
		log.info(mapper.read(25L));
	}
	
	@Test
	public void testUpdate() {
		BoardVO board = new BoardVO();
		board.setBno(25L);
		board.setBoardName("뉴스게시판");
		board.setClassification("뉴스");
		board.setTitle("수정된글제목");
		board.setContent("수정된글내용");
		board.setWriter("김길동");
		
		int count = mapper.update(board);
		log.info(count);
	}
	
	@Test
	public void testDelete() {
		int count = mapper.delete(5L);
		log.info(count);
	}
	
	@Test
	public void testPageing() {
		Criteria cri = new Criteria();
		
//		cri.setKeyword("user00");
//		cri.setType("TCW");
		
		String boardName = "";
		List<BoardVO> list = mapper.getListWithPaging(cri, boardName);  // 2페이지 10개 보여주세요.
		list.forEach(board->log.info(board));
	}
	
	@Test
	public void testReadCountUp() {
		mapper.readCountUp(21L);
	}
	
	@Test
	public void testSearch() {
		Map<String, String> map = new HashMap<String, String>();
			
		map.put("T", "1");
		map.put("C", "테스트");
        map.put("W", "테스트");

			
		Map<String, Map<String, String>> outer = new HashMap<>();
			
		outer.put("map", map);
			
		List<BoardVO> list = mapper.searchTest(outer);
		log.info(list);
			
	}
	
	@Test
	public void testGetTotal() {
		Criteria cri = new Criteria();
		
//		cri.setKeyword("user00");
//		cri.setType("TCW");
		
		String boardName = "";
		int total = mapper.getTotalCount(cri, boardName);
		log.info(total);
	}

}
