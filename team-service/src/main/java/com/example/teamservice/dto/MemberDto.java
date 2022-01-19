package com.example.teamservice.dto;

import lombok.Data;

import java.util.Date;

@Data
public class MemberDto {
    private int memberNum;
    private int userNum;
    private int teamNum;
    private String memberName;
    private String memberPosition;
    private int isLive;
    private Date createAt;
    private String profileName;

}
