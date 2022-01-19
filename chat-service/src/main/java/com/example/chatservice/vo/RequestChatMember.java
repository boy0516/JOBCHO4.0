package com.example.chatservice.vo;

import com.example.chatservice.dto.MemberDto;
import com.example.chatservice.jpa.MemberEntity;
import lombok.Data;

import java.util.List;

@Data
public class RequestChatMember {
    private MemberEntity memberEntity;
}
