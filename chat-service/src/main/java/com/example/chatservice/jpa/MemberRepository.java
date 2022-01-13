package com.example.chatservice.jpa;

import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<MemberEntity,Long> {
    MemberEntity findByMemberNum(int memberNum);
}
