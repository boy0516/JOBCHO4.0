package com.example.teamservice.jpa;

import org.springframework.data.repository.CrudRepository;



public interface TeamRepository extends CrudRepository<TeamEntity,Long> {
    Iterable<TeamEntity> findByUserNumAndIsLive(int userNum,int isLive);
    TeamEntity findByTeamNum(int teamNum);


}
