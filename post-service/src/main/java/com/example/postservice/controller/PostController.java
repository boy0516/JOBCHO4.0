package com.example.postservice.controller;


import com.example.postservice.dto.Criteria;
import com.example.postservice.dto.PageInfo;
import com.example.postservice.dto.PostDto;
import com.example.postservice.jpa.PostEntity;
import com.example.postservice.service.PostService;
import com.example.postservice.vo.RequestPost;
import com.example.postservice.vo.ResponsePost;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/team/{team_num}/board/{board_num}/post")
@Slf4j
public class PostController {

    @Autowired
    PostService postService;


    /*게시글 생성*/
    @PostMapping("/new")
    public ResponseEntity<ResponsePost> createOrder(@PathVariable("board_num") Integer boardNum,
                                                    @RequestBody RequestPost requestPost) {
        log.info("Before 게시글 생성: " + boardNum);
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        PostDto postDto = mapper.map(requestPost, PostDto.class);
        postDto.setBoardNum(boardNum);

        /* jpa */
        PostDto createdPost = postService.createPost(postDto);
        ResponsePost responsePost = mapper.map(createdPost, ResponsePost.class);


        log.info("After 게시글 생성");
        return ResponseEntity.status(HttpStatus.CREATED).body(responsePost);
    }


    /*게시글 리스트 조회*/
    @GetMapping("")
    public ResponseEntity<HashMap<Object, Object>> getListPost(@PathVariable("board_num") Integer boardNum,
                                                                @RequestBody Criteria cri){

        log.info("게시글 리스트 요청: " + boardNum);

        //페이징 처리
        PageRequest pageRequest = PageRequest.of(cri.getPageNum(), cri.getAmount());
        Page<PostEntity> postList = postService.getListPost(boardNum, pageRequest);

        PageInfo page = new PageInfo(cri, postList.getTotalElements());

        HashMap<Object, Object> map = new HashMap<Object, Object>();
        map.put("getListPost", postList); //게시글 목록
        map.put("pageMaker", page);
        //map.put("board", boardService.getBoard(board_num)); //게시판 정보

        log.info("게시글 리스트 반환: "+postList);
        return ResponseEntity.status(HttpStatus.OK).body(map);
    }


    /*게시글 상세조회*/
    @GetMapping("{post_num}")
    public ResponseEntity<ResponsePost> getPost(@PathVariable("post_num") Long postNum){

        log.info("게시글 상세정보 요청: " +postNum);
        ModelMapper mapper =  new ModelMapper();
        PostDto postDto = postService.getPost(postNum);

        ResponsePost result = mapper.map(postDto, ResponsePost.class);

        log.info("게시글 상세정보 반환:" + result );
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


    /*게시글 수정*/
    @PutMapping("{post_num}")
    public ResponseEntity<ResponsePost> updatePost(@PathVariable("post_num") Long postNum,
                                                   @RequestBody RequestPost requestPost){

        log.info("게시글 수정 요청:  "+ postNum);
        ModelMapper mapper =  new ModelMapper();
        requestPost.setPostNum(postNum);

        //requestPost -> postDto
        PostDto postDto = mapper.map(requestPost, PostDto.class);
        PostDto returnValue = postService.updatePost(postDto);

        ResponsePost result = mapper.map(returnValue, ResponsePost.class);

        log.info("게시글 수정완료: " + result);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


    /*게시글 삭제*/
    @DeleteMapping("{post_num}")
    public ResponseEntity<String> deletePost(@PathVariable("post_num") Long postNum){

        log.info("게시글 삭제 요청: " + postNum);
        postService.deletePost(postNum);

        return new ResponseEntity<>("삭제 완료", HttpStatus.OK);
    }



}
