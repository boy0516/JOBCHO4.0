package com.example.userservice.jpa;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity,Long> {
    UserEntity findByUserNumAndIsLive(int userNum, int isLive);

    UserEntity findByUserEmail(String userEmail);
}
