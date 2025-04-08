package com.cafe.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cafe.domain.BoardVO;
import com.cafe.domain.Criteria;
import com.cafe.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@CrossOrigin(origins = "*")
@RestController
@Log4j
@RequiredArgsConstructor
public class BoardController {

	private final BoardService service;
	
	@GetMapping("/message")
    public String getMessage() {
        return "Hello from Spring!";
    }
	
	@GetMapping("/board/list")
	public List<BoardVO> getList(Criteria cri, String display_value,  String boardName) {
		
		List<BoardVO> list = new ArrayList<BoardVO>();;
		list = service.getList(cri, boardName);			

		return list;
	}
	
	@GetMapping("/board/noticeList")
	public List<BoardVO> getNoticeList(){
		List<BoardVO> list = service.getNoticeList();
		return list;
	}
	
	@GetMapping("/board/getTotal")
	public int getTotal(Criteria cri,  String boardName) {
		return service.getTotal(cri, boardName);
	}
	
	@GetMapping(value = "/board/readBoard", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public BoardVO readBoard(@RequestParam("bno") Long bno) {
		return service.read(bno);
	}
	
	@GetMapping("board/readCountUp")
	public Long readCountUp(@RequestParam("bno") Long bno) {
		service.readCountUp(bno);
		BoardVO board = service.read(bno);
		return board.getReadCount();
	}
	
	@PostMapping("/board/write")
	@ResponseBody
	public String write(@RequestBody BoardVO boardRequest) {
		log.info("게시판 이름: " + boardRequest.getBoardName());
		log.info("말머리: " + boardRequest.getClassification());
		log.info("제목: " + boardRequest.getTitle());
		log.info("내용: " + boardRequest.getContent());
		log.info("작성자: " + boardRequest.getWriter());
		
        service.create(boardRequest);
        
        return "Wrote successfully";
	}
	
	@PostMapping("/board/remove")
	public boolean remove(@RequestParam("bno") Long bno) {
		return service.delete(bno);
	}
	
	@PostMapping("/board/modify")
	@ResponseBody
	public boolean modify(@RequestBody BoardVO boardRequest) {
		log.info("게시판 이름: " + boardRequest.getBoardName());
		log.info("말머리: " + boardRequest.getClassification());
		log.info("제목: " + boardRequest.getTitle());
		log.info("내용: " + boardRequest.getContent());
		log.info("작성자: " + boardRequest.getWriter());
		
        return service.update(boardRequest);
	}
	
}
