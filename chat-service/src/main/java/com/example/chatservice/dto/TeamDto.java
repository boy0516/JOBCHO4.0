package com.example.chatservice.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class TeamDto {
    private int teamNum;
    private int userNum;
    private String teamName;
    private String teamInfo;
    private int isLive;
    private Date teamDate;
    private List<MemberDto> memberDtoList = new ArrayList<>();
}
