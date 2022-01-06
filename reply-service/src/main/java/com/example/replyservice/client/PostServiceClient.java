package com.example.replyservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "post-service")
public interface PostServiceClient {

    @PostMapping("/team/{team_num}/board/{board_num}/post/{post_num}")
    ResponseEntity<String> updateReplyCnt(@PathVariable("post_num") Long postNum,
                                          @PathVariable("team_num") Integer teamNum,
                                          @PathVariable("board_num") Integer boardNum);

    @PostMapping("/team/{team_num}/board/{board_num}/post/reply/{post_num}")
    ResponseEntity<String> deleteReplyCnt(@PathVariable("post_num") Long postNum,
                                          @PathVariable("team_num") Integer teamNum,
                                          @PathVariable("board_num") Integer boardNum);
}
