package com.example.replyservice.service;


import com.example.replyservice.client.PostServiceClient;
import com.example.replyservice.dto.ReplyDto;
import com.example.replyservice.jpa.ReplyEntity;
import com.example.replyservice.jpa.ReplyRepository;
import com.example.replyservice.vo.RequestPost;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Slf4j
public class ReplyServiceImpl implements ReplyService{

    private final ReplyRepository replyRepository;
    private final PostServiceClient postServiceClient;


    public ReplyServiceImpl(ReplyRepository replyRepository, PostServiceClient postServiceClient) {
        this.replyRepository = replyRepository;
        this.postServiceClient = postServiceClient;
    }


    /**
     * 댓글 생성
     */
    @Override
    public ReplyDto createReply(ReplyDto replyDto, RequestPost requestPost) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        ReplyEntity replyEntity = mapper.map(replyDto, ReplyEntity.class);

        replyRepository.save(replyEntity);

        ReplyDto returnValue = mapper.map(replyEntity, ReplyDto.class);

        ResponseEntity<String> result = postServiceClient.updateReplyCnt(
                                            replyEntity.getPostNum(), requestPost.getTeamNum(),
                                            requestPost.getBoardNum());
        log.info("FeignClient: " + result);

        return returnValue;
    }


    /**
     * 댓글 리스트 조회
     */
    @Override
    public List<ReplyEntity> getListReply(Long postNum) {
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
    public void deleteReply(Long replyNum, RequestPost requestPost) {
        ReplyEntity replyEntity = replyRepository.findByReplyNum(replyNum);
        if (replyEntity.getReplyNum() == replyNum) {
            replyRepository.deleteById(replyNum);
        }
        ResponseEntity<String> result = postServiceClient.deleteReplyCnt(
                                                                replyEntity.getPostNum(),
                                                                requestPost.getTeamNum(),
                                                                requestPost.getBoardNum());
        log.info("FeignClient: " + result);
    }
}
