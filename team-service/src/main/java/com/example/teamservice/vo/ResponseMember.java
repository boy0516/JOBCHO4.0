package com.example.teamservice.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
