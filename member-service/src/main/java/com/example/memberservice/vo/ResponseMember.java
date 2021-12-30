package com.example.memberservice.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMember {
    private int memberNum;
    private int userNum;
    private int teamNum;
    private String memberName;
    private String memberPosition;
    private String profileName;
}
