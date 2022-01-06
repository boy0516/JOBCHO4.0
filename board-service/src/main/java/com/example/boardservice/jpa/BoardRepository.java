package com.example.boardservice.jpa;


import org.springframework.data.repository.CrudRepository;

public interface BoardRepository extends CrudRepository<BoardEntity, Long> {

        Iterable<BoardEntity> findByTeamNum(Integer teamNum);

        BoardEntity findByBoardNum(Long boardNum);

}
