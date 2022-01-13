package com.example.chatservice.vo;

import lombok.Data;

@Data
public class RequestChat {
    private int chatMemberNum;
    private String chatContents;
}
