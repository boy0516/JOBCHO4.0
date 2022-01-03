package com.example.memberservice.service;

import com.example.memberservice.dto.MemberDto;
import com.example.memberservice.jpa.MemberEntity;
import com.example.memberservice.vo.ResponseUser;

import java.util.List;

public interface MemberService {
    List<MemberDto> getListMember(int team_num);
    MemberDto getMemberByTeamNumAndUserNum(int teamNum, int userNum);
//    MemberVO getMember(int member_num);
    MemberEntity insertMember(MemberDto members);
    MemberEntity updateMember( MemberDto members);
    int deleteMember( int member_num);
    List<ResponseUser> getListWithoutMembers(int team_num);

//    List<MemberVO> getListWithOutChatMember(Map<String, Integer> map);
    MemberEntity updateMemberProfile(MemberDto memberDto);
}
