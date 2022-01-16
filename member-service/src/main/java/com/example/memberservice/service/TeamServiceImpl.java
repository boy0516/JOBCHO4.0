package com.example.memberservice.service;


import com.example.memberservice.jpa.TeamEntity;
import com.example.memberservice.jpa.TeamRepository;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
@Slf4j
public class TeamServiceImpl implements TeamService{
    TeamRepository teamRepository;
    MemberService memberService;
    UserService userService;
    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository,
                           MemberService memberService,
                           UserService userService){
        this.teamRepository = teamRepository;
        this.memberService = memberService;
        this.userService = userService;
    }

    @Override
    public Iterable<TeamEntity> getListTeam(int user_num) {

        return teamRepository.findByUserEntityUserNumAndIsLive(user_num,1);
    }

    @Override
    public TeamEntity getTeam(int team_num) {

        return teamRepository.findByTeamNum(team_num);
    }




}
