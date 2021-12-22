package com.example.teamservice.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestTeam {

    private String teamName;
    private String teamInfo;
    private int userNum;

//    private UserVO user;
}
