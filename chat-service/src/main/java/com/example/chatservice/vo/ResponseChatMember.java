package com.example.chatservice.vo;

import com.example.chatservice.dto.MemberDto;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ResponseChatMember {
    private int chatMemberNum;
    private ResponseMember member;
    private Date createAt;
}
