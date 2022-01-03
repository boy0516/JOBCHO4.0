package com.example.userservice.service;

import com.example.userservice.dto.UserDto;
import com.example.userservice.vo.ResponseUser;

import java.util.List;


public interface UserService{
    int insertUser(UserDto userDto);
    UserDto getUser(int userNum);
}
