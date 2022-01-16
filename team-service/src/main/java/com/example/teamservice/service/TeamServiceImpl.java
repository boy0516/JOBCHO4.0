package com.example.teamservice.service;


import com.example.teamservice.dto.MemberDto;
import com.example.teamservice.dto.TeamDto;
import com.example.teamservice.dto.UserDto;
import com.example.teamservice.jpa.*;
import com.example.teamservice.vo.RequestMember;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
public class TeamServiceImpl implements TeamService{
    TeamRepository teamRepository;
    MemberService memberService;
    UserRepository userRepository;
    MemberRepository memberRepository;
    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository,
                           MemberService memberService,
                           UserRepository userRepository,
                           MemberRepository memberRepository){
        this.teamRepository = teamRepository;
        this.memberService = memberService;
        this.userRepository = userRepository;
        this.memberRepository = memberRepository;
    }



    @Override
    @Transactional
    public int insertTeam(TeamDto teamDto) {

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        log.info("팀vo"+teamDto);
        TeamEntity teamEntity = mapper.map(teamDto, TeamEntity.class);
        log.info("팀엔티티"+teamEntity);
        if(teamEntity.getTeamNum()!=0){
            return 0;
        }
        UserEntity userEntity = userRepository.findByUserNumAndIsLive(teamDto.getUserNum(),1);
        teamEntity.setUserEntity(userEntity);
        TeamEntity postedEntity = teamRepository.save(teamEntity);

        MemberEntity memberEntity = new MemberEntity();

        memberEntity.setMemberName(userEntity.getUserName());
        memberEntity.setUserEntity(userEntity);
        memberEntity.setMemberPosition("팀장");
        memberEntity.setTeamEntity(postedEntity);

        memberRepository.save(memberEntity);

        return postedEntity!=null?1:0;
    }

    @Override
    public Iterable<TeamEntity> getListTeam(int user_num) {

        return teamRepository.findByUserEntityUserNumAndIsLive(user_num,1);
    }

    @Override
    public TeamEntity getTeam(int team_num) {

        return teamRepository.findByTeamNum(team_num);
    }

    @Override
    @Transactional
    public TeamEntity updateTeam(TeamDto teamDto) {
        TeamEntity updateEntity = teamRepository.findByTeamNum(teamDto.getTeamNum());
        updateEntity.setTeamInfo(teamDto.getTeamInfo());
        updateEntity.setTeamName(teamDto.getTeamName());
        TeamEntity postedEntity = teamRepository.save(updateEntity);
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
