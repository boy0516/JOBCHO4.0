package com.example.memberservice.jpa;

import org.springframework.data.repository.CrudRepository;


public interface TeamRepository extends CrudRepository<TeamEntity,Long> {

    Iterable<TeamEntity> findByUserEntityUserNumAndIsLive(int userNum,int isLive);
    TeamEntity findByTeamNum(int teamNum);


}
