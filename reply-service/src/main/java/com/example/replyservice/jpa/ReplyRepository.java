package com.example.replyservice.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<ReplyEntity, Long> {


    List<ReplyEntity> findListByPostNum(Integer postNum); //댓글 리스트 조회

    ReplyEntity findByReplyNum(Long replyNum); //댓글 조회
}
