package com.example.webserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Controller
@Slf4j
@RequestMapping("/")
public class WebController {
    @GetMapping("/home")
    public String hello(){
        log.info("/home");
        return "home";
    }

    //로그인페이지
    @GetMapping("/users/login")
    public void loginForm(){
    }

    @GetMapping("/users/main")
    public void mainForm(){
    }

    //회원가입 페이지
    @GetMapping("/users/register")
    public void register(){
    }

    //비밀번호 찾기 첫번째 페이지
    @GetMapping("/users/findPw_1")
    public void findPw_1(){
    }

    //비밀번호 찾기 두번째 페이지
    @GetMapping("/users/findPw_2")
    public void findPw_2(){
    }

    //비밀번호 찾기 세번째 페이지
    @GetMapping("/users/findPw_3")
    public void findPw_3(){
    }


    @GetMapping("/users/update")
    public void updateForm(){
    }

    //이메일로 비밀번호 찾기
    @GetMapping("/users/emailFindPw")
    public void emailFindPw(){
    }

    @GetMapping("/main")
    public String main(){
        log.info("/main");
        return "main";
    }

    @GetMapping("/team")
    public String team(){
        log.info("/team");
        return "team";
    }

    @GetMapping("/display")
    public ResponseEntity<byte[]> getFile(@RequestParam("filename") String fileName){
        log.info("fileName:" + fileName);
        File file = new File("c:\\upload\\"+fileName);

        log.info("file:" +file);

        ResponseEntity<byte[]> result = null;

        try{
            HttpHeaders header = new HttpHeaders();

            header.add("Content-Type", Files.probeContentType(file.toPath()));
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file),header, HttpStatus.OK);
        }catch(IOException e){
            e.printStackTrace();
        }
        System.out.println(result);
        return result;
    }
}
