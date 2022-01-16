package com.example.teamservice.service;

import com.example.teamservice.dto.MemberDto;
import com.example.teamservice.jpa.MemberEntity;
import com.example.teamservice.vo.ResponseUser;

import java.util.List;

public interface MemberService {
    List<MemberDto> getListMember(int team_num);
    MemberDto getMemberByTeamNumAndUserNum(int teamNum, int userNum);
    MemberEntity insertMember(MemberDto members);
    MemberEntity updateMember( MemberDto members);
    int deleteMember( int member_num);
    MemberEntity updateMemberProfile(MemberDto memberDto);
}
