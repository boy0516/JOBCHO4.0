package com.example.chatservice.jpa;

import org.springframework.data.repository.CrudRepository;

public interface TeamRepository extends CrudRepository<TeamEntity,Long> {
    TeamEntity findByTeamNum(int teamNum);
}
