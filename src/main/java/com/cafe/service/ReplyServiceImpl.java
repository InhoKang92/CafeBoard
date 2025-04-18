package com.cafe.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cafe.domain.Criteria;
import com.cafe.domain.ReplyVO;
import com.cafe.mapper.ReplyMapper;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Service
@Log4j
@AllArgsConstructor
public class ReplyServiceImpl implements ReplyService {

	private ReplyMapper mapper;

	@Override
	public int register(ReplyVO vo) {
		log.info("register......" + vo);
		return mapper.insert(vo);

	}

	@Override
	public ReplyVO get(Long rno) {
		log.info("get......" + rno);
		return mapper.read(rno);

	}

	@Override
	public int modify(ReplyVO vo) {
		log.info("modify......" + vo);
		return mapper.update(vo);

	}

	@Override
	public int remove(Long rno) {
		log.info("remove...." + rno);
		return mapper.delete(rno);

	}

	@Override
	public List<ReplyVO> getListAsc(Long bno) {
		log.info("get Reply List of a Board " + bno);
		return mapper.getListAsc(bno);

	}
	
	@Override
	public List<ReplyVO> getListDesc(Long bno) {
		log.info("get Reply List of a Board " + bno);
		return mapper.getListDesc(bno);

	}
	
	@Override
	public int getTotalReplyCount(Long bno) {
		return mapper.getTotalReplyCount(bno);
	}

}
