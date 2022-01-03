package com.example.replyservice.service;


import com.example.replyservice.dto.ReplyDto;
import com.example.replyservice.jpa.ReplyEntity;
import com.example.replyservice.jpa.ReplyRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ReplyServiceImpl implements ReplyService{

    ReplyRepository replyRepository;

    @Autowired
    public ReplyServiceImpl(ReplyRepository replyRepository) {
        this.replyRepository = replyRepository;
    }


    /**
     * 댓글 생성
     */
    @Override
    public ReplyDto createReply(ReplyDto replyDto) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        ReplyEntity replyEntity = mapper.map(replyDto, ReplyEntity.class);

        replyRepository.save(replyEntity);

        ReplyDto returnValue = mapper.map(replyEntity, ReplyDto.class);

        return returnValue;
    }


    /**
     * 댓글 리스트 조회
     */
    @Override
    public List<ReplyEntity> getListReply(Integer postNum) {
        return replyRepository.findListByPostNum(postNum);
    }


    /**
     * 댓글 상세조회
     */
    @Override
    public ReplyDto getReply(Long replyNum) {
        ReplyEntity replyEntity = replyRepository.findByReplyNum(replyNum);
        ReplyDto replyDto = new ModelMapper().map(replyEntity, ReplyDto.class);

        return replyDto;
    }


    /**
     * 댓글 수정
     */
    @Override
    public ReplyDto updateReply(ReplyDto replyDto) {
        ReplyEntity replyEntity = replyRepository.findByReplyNum(replyDto.getReplyNum());

        replyEntity.setReplyContents(replyDto.getReplyContents());

        //변경사항 저장
        replyRepository.save(replyEntity);

        ReplyDto returnValue = new ModelMapper().map(replyEntity, ReplyDto.class);

        return returnValue;
    }


    /**
     * 댓글 삭제
     */
    @Override
    public void deleteReply(Long replyNum) {
        replyRepository.deleteById(replyNum);
    }
}
