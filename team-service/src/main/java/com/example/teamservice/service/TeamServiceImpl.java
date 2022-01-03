package com.example.teamservice.service;

import com.example.teamservice.client.MemberServiceClient;
import com.example.teamservice.client.UserServiceClient;
import com.example.teamservice.dto.TeamDto;
import com.example.teamservice.jpa.TeamEntity;
import com.example.teamservice.jpa.TeamRepository;
import com.example.teamservice.vo.RequestMember;
import com.example.teamservice.vo.ResponseTeam;
import com.example.teamservice.vo.RequestTeam;
import com.example.teamservice.vo.ResponseUser;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class TeamServiceImpl implements TeamService{
    TeamRepository teamRepository;

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository){
        this.teamRepository = teamRepository;
    }

    @Autowired
    MemberServiceClient memberServiceClient;

    @Autowired
    UserServiceClient userServiceClient;

    @Override
    public int insertTeam(TeamDto teamDto) {

        RequestMember requestMember= new RequestMember();

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        log.info("팀vo"+teamDto);
        TeamEntity teamEntity = mapper.map(teamDto, TeamEntity.class);
        log.info("팀엔티티"+teamEntity);
        if(teamEntity.getTeamNum()!=0){
            return 0;
        }
        TeamEntity postedEntity = teamRepository.save(teamEntity);

        ResponseUser responseUser = userServiceClient.getUser(teamDto.getUserNum());
        requestMember.setMemberName(responseUser.getUserName());
        requestMember.setUserNum(teamDto.getUserNum());
        requestMember.setMemberPosition("팀장");
        memberServiceClient.insertMember(postedEntity.getTeamNum(),requestMember);

        return postedEntity!=null?1:0;
    }

    @Override
    public Iterable<TeamEntity> getListTeam(int user_num) {

        return teamRepository.findByUserNumAndIsLive(user_num,1);
    }

    @Override
    public TeamEntity getTeam(int team_num) {

        return teamRepository.findByTeamNum(team_num);
    }

    @Override
    public TeamEntity updateTeam(TeamDto teamDto) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        log.info("팀vo"+teamDto);
        TeamEntity teamEntity = mapper.map(teamDto, TeamEntity.class);
        log.info("팀엔티티"+teamEntity);
        if(teamEntity.getTeamNum()==0){
            return null;
        }
        teamEntity.setIsLive(1);
        TeamEntity postedEntity = teamRepository.save(teamEntity);
        return postedEntity;
    }

    @Override
    public int deleteTeam(int team_num) {
        try{
            TeamEntity teamEntity = teamRepository.findByTeamNum(team_num);
            teamEntity.setIsLive(0);
            teamRepository.save(teamEntity);
        }catch(Exception e){
            return 0;
        }
        return 1;
    }


}
