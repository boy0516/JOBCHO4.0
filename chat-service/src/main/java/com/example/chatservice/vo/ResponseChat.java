package com.example.chatservice.vo;

import lombok.Data;

import java.util.Date;

@Data
public class ResponseChat {
    private int chatNum;
    private String chatContents;
    private String uploadName;
    private Date createAt;
    private ResponseMember member;
}
