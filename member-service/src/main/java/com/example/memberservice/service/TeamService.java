package com.example.memberservice.service;


import com.example.memberservice.jpa.TeamEntity;

public interface TeamService {
    public Iterable<TeamEntity> getListTeam(int user_num);
    public TeamEntity getTeam(int team_num);

}
