package com.example.teamservice.dto;

import com.example.teamservice.vo.ResponseMember;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamDto {
    private int teamNum;
    private int userNum;
    private String teamName;
    private String teamInfo;
    private int isLive;
    private Date teamDate;

    List<ResponseMember> members;
}
