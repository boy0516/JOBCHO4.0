package com.example.postservice;

import com.example.postservice.dto.PostDto;
import com.example.postservice.jpa.PostEntity;
import com.example.postservice.service.PostServiceImpl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
@Rollback(false)
class PostServiceApplicationTests {

    @Autowired
    PostServiceImpl postService;

    @Test
    public void paging() {
        PostDto post = new PostDto();
        post.setPostTitle("PostEntity");
        post.setPostContents("PostEntity");
        post.setBoardNum(1);
        post.setMemberNum(1);
        post.setWriter("권도현");

        postService.createPost(post);

        //pageRequest.of(페이지, 한페이지에서 갯수, 정렬조건(생략가능))
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "postNum"));

        Page<PostEntity> page = postService.getListPost(post.getBoardNum(), pageRequest);


        //조건에의해 페이징 된 content
        List<PostEntity> content = page.getContent();
        for (PostEntity member : content) {
            System.out.println("결과!! : " + member);
        }

    }
}
