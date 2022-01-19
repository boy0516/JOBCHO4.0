package com.example.chatservice.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<MemberEntity,Long> {
    MemberEntity findByMemberNum(int memberNum);
    @Query(value = "select m from MemberEntity m where m.memberNum not in " +
            "(select c.memberEntity.memberNum from ChatMemberEntity c " +
            "where c.chatRoomEntity.chatRoomNum = ?1 )" )
    Iterable<MemberEntity> findByMemberNumWithOutChatMember(int chatRoomNum);
}
