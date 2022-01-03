package com.example.postservice.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PostRepository extends JpaRepository<PostEntity, Long> {

    Page<PostEntity> findByBoardNum(Integer boardNum, Pageable pageable);
    PostEntity findByPostNum(Long postNum);



}
