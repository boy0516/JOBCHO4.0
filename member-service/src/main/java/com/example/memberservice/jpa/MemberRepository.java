package com.example.memberservice.jpa;

import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<MemberEntity,Long> {
    Iterable<MemberEntity> findByTeamNumAndIsLive(int teamNum,int isLive);
    MemberEntity findByTeamNumAndUserNum(int teamNum, int userNum);
    MemberEntity findByMemberNum(int memberNum);
}
