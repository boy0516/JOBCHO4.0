package com.example.postservice.service;


import com.example.postservice.dto.PostDto;
import com.example.postservice.jpa.PostEntity;
import com.example.postservice.jpa.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@Slf4j
public class PostServiceImpl implements PostService {

    PostRepository postRepository;


    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    //게시글 생성
    @Override
    public PostDto createPost(PostDto postDto) {

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        PostEntity postEntity = mapper.map(postDto, PostEntity.class);

        postRepository.save(postEntity);

        PostDto returnValue = mapper.map(postEntity, PostDto.class);

        return returnValue;
    }

    //게시글 리스트 조회
    @Override
    public Page<PostEntity> getListPost(Integer boardNum, Pageable pageable){

        Page postList = postRepository.findByBoardNum(boardNum, pageable);
        log.info("service 게시글 리스트 반환: "+postList.getContent());

        return postList;
    }

    //게시글 상세조회
    @Override
    public PostDto getPost(Long postNum){
        PostEntity postEntity = postRepository.findByPostNum(postNum);
        log.info("게시글 상세정보 서비스: " +postEntity.getPostTitle());
        PostDto postDto = new ModelMapper().map(postEntity, PostDto.class);


        return postDto;
    }
	

    //게시글 수정
    @Override
    public PostDto updatePost(PostDto postDto) {

        PostEntity postEntity = postRepository.findByPostNum(postDto.getPostNum());

        postEntity.setPostTitle(postDto.getPostTitle());
        postEntity.setPostContents(postDto.getPostContents());

        //변경사항 저장
        postRepository.save(postEntity);

        PostDto returnValue = new ModelMapper().map(postEntity, PostDto.class);

        return returnValue;
    }


    //게시글 삭제
    @Override
    public void deletePost(Long postNum){
        postRepository.deleteById(postNum);
    }
	

}
