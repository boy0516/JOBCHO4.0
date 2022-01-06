package com.example.replyservice.controller;

import com.example.replyservice.dto.ReplyDto;
import com.example.replyservice.jpa.ReplyEntity;
import com.example.replyservice.service.ReplyService;
import com.example.replyservice.vo.RequestPost;
import com.example.replyservice.vo.RequestReply;
import com.example.replyservice.vo.ResponseReply;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/reply")
@Slf4j
public class ReplyController {

    private final ReplyService replyService;

    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    /**
     * 댓글 생성
     */
    @PostMapping("/new")
    public ResponseEntity<String> createReply(@RequestBody RequestReply requestReply){

        RequestPost requestPost = new RequestPost(requestReply.getTeamNum(), requestReply.getBoardNum());

        log.info("before 댓글 생성: " + requestReply.getReplyContents());
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        ReplyDto replyDto = mapper.map(requestReply, ReplyDto.class);

        /* jpa */
        ReplyDto createdReply = replyService.createReply(replyDto, requestPost);
        ResponseReply responseReply = mapper.map(createdReply, ResponseReply.class);

        log.info("After 댓글 생성"+ requestReply.getReplyWriter());
        return new ResponseEntity<>("success", HttpStatus.CREATED);
    }


    /**
     * 댓글 리스트 조회
     */
    @GetMapping("/post/{post_num}")
    public ResponseEntity<List<ReplyEntity>> getListReply(@PathVariable("post_num") Long postNum){

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        /* jpa */
        List<ReplyEntity> replyEntity = replyService.getListReply(postNum);

        log.info("댓글 리스트 조회");

        return ResponseEntity.status(HttpStatus.OK).body(replyEntity);

    }


    /**
     * 댓글 상세조회
     */
    @GetMapping("{reply_num}")
    public ResponseEntity<ResponseReply> getReply(@PathVariable("reply_num")Long replyNum){

        log.info("댓글 상세정보 요청: " +replyNum);
        ModelMapper mapper =  new ModelMapper();

        ReplyDto replyDto = replyService.getReply(replyNum);

        ResponseReply result = mapper.map(replyDto, ResponseReply.class);
        log.info("댓글 상세정보 반환:" + result );

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


    /**
     * 댓글 수정
     */
    @PutMapping("{reply_num}")
    public ResponseEntity<ResponseReply> updatePost(@PathVariable("reply_num") Long replyNum,
                                                   @RequestBody RequestReply requestReply){

        log.info("댓글 수정 요청:  "+ replyNum);
        ModelMapper mapper =  new ModelMapper();
        requestReply.setReplyNum(replyNum);

        //requestPost -> postDto
        ReplyDto replyDto = mapper.map(requestReply, ReplyDto.class);
        ReplyDto returnValue = replyService.updateReply(replyDto);

        ResponseReply result = mapper.map(returnValue, ResponseReply.class);

        log.info("댓글 수정완료: " + result);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


    /**
     * 댓글 삭제
     */
    @DeleteMapping("{reply_num}")
    public ResponseEntity<String> deletePost(@PathVariable("reply_num") Long replyNum,
                                             @RequestBody RequestPost requestPost){

        log.info("댓글 삭제 요청: " + replyNum);
        replyService.deleteReply(replyNum, requestPost);

        return new ResponseEntity<>("삭제 완료", HttpStatus.OK);
    }




}
