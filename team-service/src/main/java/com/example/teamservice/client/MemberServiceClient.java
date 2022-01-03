package com.example.teamservice.client;

import com.example.teamservice.vo.RequestMember;
import com.example.teamservice.vo.ResponseMember;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name="member-service")
public interface MemberServiceClient {



    @PostMapping("/team/{team_num}/member/invite")
    ResponseMember insertMember(@PathVariable("team_num") int team_num, RequestMember requestMember);
}
