package com.example.chatservice.dto;

import com.example.chatservice.jpa.ChatEntity;
import com.example.chatservice.jpa.ChatMemberEntity;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ChatRoomDto {
    private int chatRoomNum;
    private String chatRoomName;

    private Date createAt;
    private int isLive;

    private TeamDto teamDto;
    private MemberDto memberDto;
    private List<ChatMemberEntity> chatMemberEntities;
    private List<ChatEntity> chatEntities;
}
