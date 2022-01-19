package com.example.memberservice.jpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<MemberEntity,Long> {
    Iterable<MemberEntity> findByTeamEntityTeamNumAndIsLive(int teamNum,int isLive);
    MemberEntity findByTeamEntityTeamNumAndUserEntityUserNum(int teamNum, int userNum);
    MemberEntity findByMemberNum(int memberNum);

}
