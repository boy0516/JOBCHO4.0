package com.example.chatservice.service;

import com.example.chatservice.dto.ChatDto;
import com.example.chatservice.dto.ChatMemberDto;
import com.example.chatservice.dto.ChatRoomDto;

import java.util.List;


public interface ChatService {
    int insertChat(ChatDto chatDto);
    List<ChatDto> getListChat(int chatRoom_num);
    int deleteChat(int chat_num);
    int insertChatMember(ChatMemberDto chatMemberDto);
    List<ChatMemberDto> getListChatMember(int chatRoom_num);
    ChatMemberDto getChatMember(int chatMember_num);
    int deleteChatMember(int chatMember_num);
    int insertChatRoom(ChatRoomDto chatRoomDto);
    List<ChatRoomDto> getListChatRoom(int team_num, int member_num);
    ChatRoomDto getChatRoom(int chatRoom_num);
    int updateChatRoom(ChatRoomDto chatRoom);
    int deleteChatRoom(int chatRoom_num);
}
