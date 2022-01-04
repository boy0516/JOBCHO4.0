package com.example.userservice.service;

import com.example.userservice.dto.UserDto;
import com.example.userservice.jpa.UserEntity;
import com.example.userservice.jpa.UserRepository;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUserEmail(userEmail);

        if(userEntity == null){
            throw new UsernameNotFoundException(userEmail);
        }
        return new User(userEntity.getUserEmail(), userEntity.getUserPw(),
                true, true, true, true,
                new ArrayList<>());
    }

    @Override
    public int insertUser(UserDto userDto) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userEntity = mapper.map(userDto, UserEntity.class);
        userEntity.setUserPw(passwordEncoder.encode(userDto.getUserPw()));
        log.info(String.valueOf(userEntity));

        userRepository.save(userEntity);
        return 0;
    }

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
