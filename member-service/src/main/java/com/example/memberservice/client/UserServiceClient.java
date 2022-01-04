package com.example.memberservice.client;

import com.example.memberservice.dto.UserDto;
import com.example.memberservice.vo.ResponseUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="user-service")
public interface UserServiceClient {
    @GetMapping("/user/{user_num}")
    List<ResponseUser> getUser(@PathVariable("user_num") int user_num);

    @GetMapping("/user")
    List<UserDto> getUserList();
}
