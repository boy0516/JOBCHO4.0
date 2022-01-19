package com.example.memberservice.jpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity,Long> {
    UserEntity findByUserNumAndIsLive(int userNum, int isLive);

    UserEntity findByUserEmail(String userEmail);
    @Query(value="SELECT u FROM UserEntity u where u.userNum not in " +
            "(select m.userEntity.userNum from MemberEntity m where m.teamEntity.teamNum=?1)")
    Iterable<UserEntity> findByUserWithOutMember(int teamNum);
}

