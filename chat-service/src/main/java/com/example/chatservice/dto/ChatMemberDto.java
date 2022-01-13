package com.example.chatservice.dto;

import com.example.chatservice.jpa.ChatEntity;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ChatMemberDto {
    private int chatMemberNum;
    private int chatRoomNum;
    private int memberNum;
    private Date createAt;
    private int isLive;

    private List<ChatEntity> chatEntities;
}
