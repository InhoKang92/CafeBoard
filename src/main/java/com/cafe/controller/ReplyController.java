package com.cafe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cafe.domain.Criteria;
import com.cafe.domain.ReplyVO;
import com.cafe.service.ReplyService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/replies/*")
@AllArgsConstructor
@NoArgsConstructor
@Log4j
public class ReplyController {

	@Autowired
	private ReplyService service;

	@GetMapping("/test")
	public String testReply() {
		return "test"; // @RestController ���ڿ��� ����
	}

	// consumes Ŭ���̾�Ʈ�� �������� ������ ������ ���� (�Ű�����)
	// produces�� ������ Ŭ���̾�Ʈ���� �����ϴ� ������ ���� (���ϰ�)
	// �Է��� json ����� text
	@PostMapping(value = "/new", consumes = "application/json", produces = { MediaType.TEXT_PLAIN_VALUE })
	public ResponseEntity<String> create(@RequestBody ReplyVO vo) { // Ŭ���̾�Ʈ�� jsonŸ������ �����ָ� Reply vo �ڹ�Ÿ������ ��ȯ�ض�
		log.info("ReplyVO: " + vo);
		int insertCount = service.register(vo);
		return insertCount == 1 ? new ResponseEntity<String>("success", HttpStatus.OK)
				: new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);

	}

	// ��û --> localhost:8181/replies/pages/2/10 bno2���Խù��� ��� 10��������
	@GetMapping(value = "/replyListAsc/{bno}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<ReplyVO>> getListAsc(@PathVariable("bno") Long bno) {

		log.info("getList..........");

		return new ResponseEntity<List<ReplyVO>>(service.getListAsc(bno), HttpStatus.OK);

	}
	
	@GetMapping(value = "/replyListDesc/{bno}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<ReplyVO>> getListDesc(@PathVariable("bno") Long bno) {

		log.info("getList..........");

		return new ResponseEntity<List<ReplyVO>>(service.getListDesc(bno), HttpStatus.OK);

	}

	// http://localhost:8081/replies/8 ��۹�ȣ 8���������� , ��ȯŸ�� json���� ��������
	@GetMapping(value = "/{rno}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ReplyVO> get(@PathVariable("rno") Long rno) {
		log.info("get: " + rno);
		return new ResponseEntity<>(service.get(rno), HttpStatus.OK);
	}

	// http:localhost:8081/replies/8 ��� 8�������ϱ� ��ȯŸ�� ����
	@DeleteMapping(value = "/{rno}", produces = { MediaType.TEXT_PLAIN_VALUE })
	public ResponseEntity<String> remove(@PathVariable("rno") Long rno) {
		log.info("remove: " + rno);
		return service.remove(rno) == 1 ? new ResponseEntity<>("success", HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// http://localhost:8081/replies/30 30�� ��ۼ����� �Ű����� json���� ������ �ڹٰ�ü�� ���� , ��ȯŸ�� ����
	@RequestMapping(method = RequestMethod.PUT, value = "/{rno}", consumes = "application/json", produces = {
			MediaType.TEXT_PLAIN_VALUE })
	public ResponseEntity<String> modify(@RequestBody ReplyVO vo, @PathVariable("rno") Long rno) {

		vo.setRno(rno);

		log.info(vo);

		log.info("rno: " + rno);
		log.info("modify: " + vo);

		return service.modify(vo) == 1 ? new ResponseEntity<>("success", HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@GetMapping("/getTotalReplyCount")
	public int getTotalReplyCount(Long bno) {
		return service.getTotalReplyCount(bno);
	}
}