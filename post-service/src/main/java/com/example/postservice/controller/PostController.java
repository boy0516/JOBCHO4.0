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
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;


@RestController
@RequestMapping("/team/{team_num}/board/{board_num}/post")
@Slf4j
public class PostController {

    @Autowired
    PostService postService;

    /*게시글 생성*/
    @PostMapping("/new")
    public ResponseEntity<String> createPost(@PathVariable("board_num") Integer boardNum,
                                                    @RequestBody RequestPost requestPost) {
        log.info("Before 게시글 생성: " + requestPost.getPostContents());
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        PostDto postDto = mapper.map(requestPost, PostDto.class);
        postDto.setBoardNum(boardNum);

        /* jpa */
        PostDto createdPost = postService.createPost(postDto);
        ResponsePost responsePost = mapper.map(createdPost, ResponsePost.class);


        log.info("After 게시글 생성"+ responsePost.getPostTitle());
        return new ResponseEntity<>("success", HttpStatus.CREATED);
    }


    /*게시글 리스트 조회*/
    @PostMapping("")
    public ResponseEntity<HashMap<Object,Object>> getListPost(@PathVariable("board_num") Integer boardNum,
                                                               @RequestBody Criteria cri){

        //임시 데이터
        cri.setAmount(5);
        Integer num = cri.getPageNum() -1;

        //페이징 처리
        Pageable pageRequest = PageRequest.of(num, 5, Sort.by(Sort.Direction.DESC, "postNum"));

        Page<PostEntity> postList = postService.getListPost(boardNum, pageRequest);


        PageInfo page = new PageInfo(cri, postList.getTotalElements());

        HashMap<Object, Object> map = new HashMap<Object, Object>();
        map.put("getListPost", postList); //게시글 목록
        map.put("pageMaker", page);
        //map.put("board", boardService.getBoard(board_num)); //게시판 정보


        log.info("게시글 리스트 반환: "+postList.getContent());
        return ResponseEntity.status(HttpStatus.OK).body(map);
    }


    /*게시글 상세조회*/
    @GetMapping("{post_num}")
    public ResponseEntity<ResponsePost> getPost(@PathVariable("post_num") Long postNum){

        log.info("게시글 상세정보 요청: " +postNum);
        ModelMapper mapper =  new ModelMapper();

        PostDto postDto = postService.getPost(postNum);
        log.info("게시글 상세정보 요청: " +postDto.getPostTitle());

        ResponsePost result = mapper.map(postDto, ResponsePost.class);
        log.info("게시글 상세정보 반환:" + result );

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


    /*게시글 수정*/
    @PutMapping("{postNum}")
    public ResponseEntity<ResponsePost> updatePost(@PathVariable("postNum") Long postNum,
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


    /**
     * 특정 게시글 댓글 수 +1
     */
    @PostMapping("/{post_num}")
    public ResponseEntity<String> updateReplyCnt(@PathVariable("post_num") Long postNum){

        log.info("게시글 댓글 수 증가: " + postNum);
        postService.updateReplyCnt(postNum);

        return new ResponseEntity<>("댓글 수 +1 완료", HttpStatus.OK);
    }


    /**
     * 특정 게시글 댓글 수 -1
     */
    @PostMapping("/reply/{post_num}")
    public ResponseEntity<String> deleteReplyCnt(@PathVariable("post_num") Long postNum){

        log.info("게시글 댓글 수 -1: " + postNum);
        postService.deleteReplyCnt(postNum);

        return new ResponseEntity<>("댓글 수 -1 완료", HttpStatus.OK);
    }


}
