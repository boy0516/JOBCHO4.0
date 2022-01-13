package com.example.chatservice.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ResponseChatRoom {
    private int chatRoomNum;
    private String chatRoomName;
    private Date createAt;
    private List<ResponseChatMember> chatMemberList;
    private List<ResponseChat> chatList;
}
