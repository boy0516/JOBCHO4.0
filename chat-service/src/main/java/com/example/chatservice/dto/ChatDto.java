package com.example.chatservice.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ChatDto {
    private int chatNum;
    private int chatMemberNum;
    private int chatRoomNum;
    private int memberNum;
    private String chatContents;
    private Date createAt;
    private int isLive;
    private String uploadName;
    private ChatMemberDto chatMemberDto;
}
