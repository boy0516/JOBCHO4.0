package com.example.teamservice.controller;

import com.example.teamservice.client.MemberServiceClient;
import com.example.teamservice.dto.TeamDto;
import com.example.teamservice.jpa.TeamEntity;
import com.example.teamservice.service.TeamService;
import com.example.teamservice.vo.ResponseTeam;
import com.example.teamservice.vo.RequestTeam;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/team")
@Slf4j
public class TeamController {

    @Autowired
    private TeamService teamService;



    //팀생성
    @PostMapping("/{user_num}")
    public ResponseEntity<RequestTeam> insertTeam(@RequestBody RequestTeam requestTeam, @PathVariable("user_num") int user_num){
        log.info(String.valueOf(requestTeam));
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        TeamDto teamDto = mapper.map(requestTeam, TeamDto.class);

        log.info(String.valueOf(teamDto));
        //팀생성 실행
        int re = teamService.insertTeam(teamDto);

        return re==1?ResponseEntity.status(HttpStatus.CREATED).body(requestTeam)
                :ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    //팀불러오기
    @GetMapping(value = "/{user_num}")
    public ResponseEntity<List<ResponseTeam>> getListTeam(@PathVariable("user_num") int user_num){
        log.info("get teamlist by userNum");
        Iterable<TeamEntity> list = teamService.getListTeam(user_num);
        List<ResponseTeam> result = new ArrayList<>();
        list.forEach(v->{
            result.add(new ModelMapper().map(v, ResponseTeam.class));
        });

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //팀하나 가져오기
    @GetMapping(value="/{user_num}/{team_num}")
    public ResponseEntity<ResponseTeam> getTeam(@PathVariable("team_num") int team_num){
        log.info("get temaEntity by teamNum");
        TeamEntity teamEntity = teamService.getTeam(team_num);
        ResponseTeam result =new ModelMapper().map(teamEntity, ResponseTeam.class);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //팀업데이트
    @PutMapping(value = "/{team_num}")
    public ResponseEntity<TeamEntity> getTeam(@PathVariable("team_num") int team_num, @RequestBody RequestTeam requestTeam){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        TeamDto teamDto = mapper.map(requestTeam, TeamDto.class);
        teamDto.setTeamNum(team_num);

        TeamEntity result = teamService.updateTeam(teamDto);

        return result != null
                ? new ResponseEntity<>(result,HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //팀삭제
    @DeleteMapping(value = "/{team_num}")
    public ResponseEntity<ResponseTeam> deleteTeam(@PathVariable("team_num") int team_num){

        int re = teamService.deleteTeam(team_num);

        return re == 1
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
//
//
//    @GetMapping(value = "/getlist/{user_num}", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
//    public ResponseEntity<List<TeamVO>> getListTeamByMemberJoin(@PathVariable("user_num") int user_num){
//        log.info("�� ��� ȣ�� Ȯ��");
//        return new ResponseEntity<>(service.getListTeamByMemberJoin(user_num), HttpStatus.OK);
//    }

}
