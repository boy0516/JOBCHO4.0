package com.example.userservice.controller;

import com.example.userservice.dto.UserDto;
import com.example.userservice.jpa.UserEntity;
import com.example.userservice.jpa.UserRepository;
import com.example.userservice.service.UserService;
import com.example.userservice.vo.RequestUser;
import com.example.userservice.vo.ResponseUser;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/health_check")
    public String status() {
        return "user-service is running";
    }

    @GetMapping("/user")
    public ResponseEntity<List<UserDto>> getUserList(){
        Iterable<UserEntity> userEntities = userRepository.findAll();
        List<UserDto> result = new ArrayList<>();
        userEntities.forEach(v->{
            result.add(new ModelMapper().map(v, UserDto.class));
        });
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/user/{user_num}")
    public ResponseEntity<ResponseUser> getUser(@PathVariable("user_num") int user_num){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDto userDto = userService.getUser(user_num);
        ResponseUser responseUser;
        try{
            responseUser = mapper.map(userDto, ResponseUser.class);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity.status(HttpStatus.OK).body(responseUser);
    }

    @PostMapping("/signup")
    public ResponseEntity<RequestUser> insertUser(@RequestBody RequestUser requestUser){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDto userDto = mapper.map(requestUser, UserDto.class);
        userService.insertUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(requestUser);
    }

    @GetMapping("/userbytoken")
    public ResponseEntity<ResponseUser> getUserByEmail(
            @CookieValue(value = "accessToken", defaultValue = "Atta") String accessToken){
        log.info(accessToken);
        String subject = Jwts.parser().setSigningKey("user_token")
                .parseClaimsJws(accessToken).getBody()
                .getSubject();
        log.info(subject);
        UserEntity userEntity = userRepository.findByUserEmail(subject);
        ResponseUser responseUser  = new ModelMapper().map(userEntity,ResponseUser.class);
        return ResponseEntity.status(HttpStatus.OK).body(responseUser);
    }

}

