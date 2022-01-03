package com.example.replyservice.service;

import com.example.replyservice.dto.ReplyDto;
import com.example.replyservice.jpa.ReplyEntity;

import java.util.List;


public interface ReplyService {

    ReplyDto createReply(ReplyDto replyDto); //댓글 생성
    List<ReplyEntity> getListReply(Integer postNum);//댓글 리스트 조회
    ReplyDto getReply(Long replyNum);
    ReplyDto updateReply(ReplyDto replyDto);
    void deleteReply(Long replyNum);
}
