package com.example.chatservice.service;

import com.example.chatservice.dto.ChatDto;
import com.example.chatservice.dto.ChatMemberDto;
import com.example.chatservice.dto.ChatRoomDto;
import com.example.chatservice.vo.ResponseChat;
import com.example.chatservice.vo.ResponseChatMember;
import com.example.chatservice.vo.ResponseMember;

import java.util.List;


public interface ChatService {
    int insertChat(ChatDto chatDto);
    List<ResponseChat> getListChat(int member_num, int chatRoom_num);
    int deleteChat(int chat_num);
    int insertChatMember(List<ChatMemberDto> chatMemberDtoList, int chatRoomNum);
    List<ResponseChatMember> getListChatMember(int chatRoomNum, int memberNum);
    ChatMemberDto getChatMember(int chatMember_num);
    int deleteChatMember(int chatMember_num);
    int insertChatRoom(ChatRoomDto chatRoomDto);
    List<ChatRoomDto> getListChatRoom(int member_num);
    ChatRoomDto getChatRoom(int chatRoom_num);
    int updateChatRoom(ChatRoomDto chatRoom);
    int deleteChatRoom(int chatRoom_num);
    List<ResponseMember> getListMemberWithoutChatMember(int chatRoomNun, int memberNum);
}
