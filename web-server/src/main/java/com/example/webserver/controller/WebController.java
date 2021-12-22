package com.example.webserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/")
public class WebController {
    @GetMapping("/home")
    public String hello(){
        log.info("/home");
        return "home";
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
}
