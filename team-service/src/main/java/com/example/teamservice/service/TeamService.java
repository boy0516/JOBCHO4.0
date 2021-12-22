package com.example.teamservice.service;

import com.example.teamservice.dto.TeamDto;
import com.example.teamservice.jpa.TeamEntity;
import com.example.teamservice.vo.ResponseTeam;
import com.example.teamservice.vo.RequestTeam;

import java.util.List;

public interface TeamService {
    public int insertTeam(TeamDto team);
    public Iterable<TeamEntity> getListTeam(int user_num);
    public TeamEntity getTeam(int team_num);
    public TeamEntity updateTeam(TeamDto team);
    public int deleteTeam(int team_num);
//    TeamVO selectTeam(Map<String, String> map);

}
