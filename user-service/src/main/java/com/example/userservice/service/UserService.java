package com.example.userservice.service;

import com.example.userservice.dto.UserDto;
import com.example.userservice.vo.ResponseUser;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;


public interface UserService extends UserDetailsService {
    int insertUser(UserDto userDto);
    UserDto getUser(int userNum);
}
