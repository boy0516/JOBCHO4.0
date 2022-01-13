package com.example.chatservice.vo;

import lombok.Data;

import java.util.Date;

@Data
public class ResponseMember {
    private int memberNum;
    private String memberName;
    private String memberPosition;
    private String profileName;
}
