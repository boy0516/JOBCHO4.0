package com.example.memberservice.service;

import com.example.memberservice.client.UserServiceClient;
import com.example.memberservice.dto.MemberDto;
import com.example.memberservice.dto.UserDto;
import com.example.memberservice.jpa.MemberEntity;
import com.example.memberservice.jpa.MemberRepository;
import com.example.memberservice.vo.ResponseUser;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    UserServiceClient userServiceClient;

    @Override
    public List<MemberDto> getListMember(int team_num) {
        Iterable<MemberEntity> EntityList = memberRepository.findByTeamNumAndIsLive(team_num,1);
        List<MemberDto> memberDtoList = new ArrayList<>();
        EntityList.forEach(v->{
            memberDtoList.add(new ModelMapper().map(v,MemberDto.class));
        });
        return memberDtoList;
    }

    @Override
    public MemberDto getMemberByTeamNumAndUserNum(int teamNum, int userNum) {
        MemberEntity memberEntity = memberRepository.findByTeamNumAndUserNum(teamNum,userNum);
        MemberDto memberDto;
        try{
            memberDto = new ModelMapper().map(memberEntity,MemberDto.class);
        }catch(Exception e){
            log.info("memberEntity is null");
            return null;
        }
        return memberDto;
    }
//
//    @Override
//    public MemberVO getMember(int member_num) {
//        return null;
//    }
//
    @Override
    public MemberEntity insertMember(MemberDto memberDto) {
        MemberEntity memberEntity = new ModelMapper().map(memberDto,MemberEntity.class);
        if(memberEntity.getMemberNum()!=0){
            return null;
        }
        log.info(String.valueOf(memberEntity));
        MemberEntity postedEntity = memberRepository.save(memberEntity);
        return postedEntity;
    }

    @Override
    public MemberEntity updateMember(MemberDto memberDto) {
        MemberEntity memberEntity = memberRepository.findByMemberNum(memberDto.getMemberNum());
        log.info(String.valueOf(memberEntity));
        memberEntity.setMemberName(memberDto.getMemberName());
        memberEntity.setMemberPosition(memberDto.getMemberPosition());
        if(memberEntity.getMemberNum()==0){
            return null;
        }
        MemberEntity updatedEntity = memberRepository.save(memberEntity);
        return updatedEntity;
    }
//
    @Override
    public int deleteMember(int member_num) {
        MemberEntity memberEntity = memberRepository.findByMemberNum(member_num);
        try{
            if(memberEntity.getIsLive() == 1){
                memberEntity.setMemberNum(1);
                memberRepository.save(memberEntity);
            }
        }catch(Exception e){
            return 0;
        }
        return 1;
    }
//
    @Override
    public List<ResponseUser> getListWithoutMembers(int team_num) {
        Iterable<MemberEntity> memberEntities = memberRepository.findByTeamNumAndIsLive(team_num,1);
        List<UserDto> userDtoList = userServiceClient.getUserList();
        log.info(String.valueOf(userDtoList));
        log.info(String.valueOf(memberEntities));
        List<ResponseUser> responseUserList = new ArrayList<>();

        memberEntities.forEach(v->{
            for(int i=0; i<userDtoList.size(); i++) {
                if (userDtoList.get(i).getUserNum() == v.getUserNum()){
                    userDtoList.remove(i);
                    break;
                }
            }
        });

        log.info(String.valueOf(userDtoList));
        userDtoList.forEach(v->{
            responseUserList.add(new ModelMapper().map(v, ResponseUser.class));
        });
        log.info(String.valueOf(responseUserList));
        return responseUserList;
    }
//

//
//    @Override
//    public List<MemberVO> getListWithOutChatMember(Map<String, Integer> map) {
//        return null;
//    }
//
    @Override
    public MemberEntity updateMemberProfile(MemberDto memberDto) {
        MemberEntity memberEntity = memberRepository.findByMemberNum(memberDto.getMemberNum());
        if(memberDto.getMemberNum()==0){
            return null;
        }
        memberEntity.setProfileName(memberDto.getProfileName());
        MemberEntity updatedEntity = memberRepository.save(memberEntity);
        return updatedEntity;
    }
}
