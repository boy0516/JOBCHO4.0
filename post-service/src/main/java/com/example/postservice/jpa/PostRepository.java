package com.example.postservice.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<PostEntity, Long> {

    Page<PostEntity> findListByBoardNum(Integer boardNum,Pageable pageable);
    PostEntity findByPostNum(Long postNum);

    Page<PostEntity> findAll(Pageable pageable); //페이징 처리

}
