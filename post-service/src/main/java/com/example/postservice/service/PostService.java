package com.example.postservice.service;

import com.example.postservice.dto.PostDto;
import com.example.postservice.jpa.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {

	PostDto createPost(PostDto postDto); //게시글 생성
	Page<PostEntity> getListPost(Integer boardNum, Pageable pageable);
	PostDto getPost(Long postNum);
	PostDto updatePost(PostDto postDto);
	void deletePost(Long postNum);


	
}
