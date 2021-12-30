package com.example.teamservice.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseTeam {
    private int teamNum;
    private int userNum;
    private String teamName;
    private String teamInfo;
    private int isLive;
    private Date teamDate;
}
