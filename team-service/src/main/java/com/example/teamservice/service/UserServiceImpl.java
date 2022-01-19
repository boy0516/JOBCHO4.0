package com.example.teamservice.service;

import com.example.teamservice.dto.UserDto;
import com.example.teamservice.jpa.UserEntity;
import com.example.teamservice.jpa.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDto getUser(int userNum) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userEntity;
        try{
            userEntity = userRepository.findByUserNumAndIsLive(userNum,1);
        }catch(Exception e){
            log.info("fail find user by userNum");
            return null;
        }
        log.info(String.valueOf(userEntity));
        UserDto userDto = mapper.map(userEntity, UserDto.class);
        return userDto;
    }


}
