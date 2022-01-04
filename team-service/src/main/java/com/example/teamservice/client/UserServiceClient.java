package com.example.teamservice.client;

import com.example.teamservice.vo.ResponseUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name="user-service")
public interface UserServiceClient {
    @GetMapping ("/user/{user_num}")
    ResponseUser getUser(@PathVariable("user_num") int user_num);
}
