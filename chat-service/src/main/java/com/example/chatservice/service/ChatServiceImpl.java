package com.example.chatservice.service;

import com.example.chatservice.dto.ChatDto;
import com.example.chatservice.dto.ChatMemberDto;
import com.example.chatservice.dto.ChatRoomDto;

import com.example.chatservice.jpa.*;
import com.example.chatservice.vo.ResponseChat;
import com.example.chatservice.vo.ResponseChatMember;
import com.example.chatservice.vo.ResponseMember;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ChatServiceImpl implements ChatService{

    @Autowired
    ChatRepository chatRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ChatMemberRepository chatMemberRepository;

    @Autowired
    ChatRoomRepository chatRoomRepository;

    @Override
    public int insertChat(ChatDto chatDto) {
        log.info("Service: create chat");
        ChatEntity chatEntity = new ModelMapper().map(chatDto,ChatEntity.class);
        MemberEntity memberEntity = memberRepository.findByMemberNum(chatDto.getMemberNum());
        chatEntity.setMemberEntity(memberEntity);
        log.info(String.valueOf(chatEntity));
        ChatRoomEntity chatRoomEntity = chatRoomRepository.findByChatRoomNum(chatDto.getChatRoomNum());
        chatEntity.setChatRoomEntity(chatRoomEntity);
        try{
            chatRepository.save(chatEntity);
            log.info("success create chat");
            return 1;
        }catch(Exception e){
            log.info("Fail create chat");
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<ResponseChat> getListChat(int memberNum,int chatRoomNum) {
        Iterable<ChatEntity> chatEntities= chatRepository.
                findByMemberEntityMemberNumAndChatRoomEntityChatRoomNum(
                        memberNum
                        ,chatRoomNum);
        List<ResponseChat> responseChatList = new ArrayList<>();
        chatEntities.forEach(v->{
            responseChatList.add(new ModelMapper().map(v, ResponseChat.class));
        });
        return responseChatList;
    }

    @Override
    public int deleteChat(int chat_num) {
        return 0;
    }

    @Override
    public int insertChatMember(List<ChatMemberDto> chatMemberDtoList, int chatRoomNum) {
        log.info("Service: create chatMember");
        ChatRoomEntity chatRoomEntity = chatRoomRepository.findByChatRoomNum(chatRoomNum);
        chatMemberDtoList.forEach(v->{
            ChatMemberEntity chatMemberEntity = new ModelMapper().map(v,ChatMemberEntity.class);
            log.info(String.valueOf(chatMemberEntity));
            chatMemberEntity.setChatRoomEntity(chatRoomEntity);
            chatMemberRepository.save(chatMemberEntity);
        });


//        try{
//            chatMemberRepository.save(chatMemberEntity);
//            log.info("success create chatMember");
//            return 1;
//        }catch(Exception e){
//            log.info("Fail create chatMember");
//            e.printStackTrace();
//            return 0;
//        }
        return 1;
    }

    @Override
    public List<ResponseChatMember> getListChatMember(int chatRoomNum, int memberNum) {
        Iterable<ChatMemberEntity> chatMemberEntities = chatMemberRepository.
                findByChatRoomEntityChatRoomNumAndMemberEntityMemberNum(chatRoomNum, memberNum);
        log.info(String.valueOf(chatMemberEntities));
        List<ResponseChatMember> chatMemberList = new ArrayList<>();
        chatMemberEntities.forEach(v->{
            chatMemberList.add(new ModelMapper().map(v, ResponseChatMember.class));
        });

        return chatMemberList;
    }

    @Override
    public ChatMemberDto getChatMember(int chatMember_num) {
        return null;
    }

    @Override
    public int deleteChatMember(int chatMember_num) {
        return 0;
    }

    @Override
    public int insertChatRoom(ChatRoomDto chatRoomDto) {
        log.info("Service: create chatRoom");

        MemberEntity memberEntity = memberRepository.findByMemberNum(chatRoomDto.getMemberDto().getMemberNum());
        ChatMemberEntity chatMemberEntity = new ChatMemberEntity();
        chatMemberEntity.setMemberEntity(memberEntity);


        ChatRoomEntity chatRoomEntity = new ModelMapper().map(chatRoomDto,ChatRoomEntity.class);
        log.info(String.valueOf(chatRoomEntity));
        chatRoomEntity.setMemberEntity(memberEntity);

        log.info(String.valueOf(chatRoomEntity));
        try{
            chatRoomEntity = chatRoomRepository.save(chatRoomEntity);
            chatMemberEntity.setChatRoomEntity(chatRoomEntity);
            chatMemberRepository.save(chatMemberEntity);
            log.info("success create chatRoom");
            return 1;
        }catch(Exception e){
            log.info("Fail create chatRoom");
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<ChatRoomDto> getListChatRoom(int member_num) {
        log.info("Service: getListChatRoom");
        try{
//            Iterable<ChatMemberEntity> chatMemberEntities = chatMemberRepository.findByMemberEntityMemberNum(member_num);
//            log.info(String.valueOf(chatMemberEntities));
            Iterable<ChatRoomEntity> chatRoomEntities = chatRoomRepository.findByMemberEntityMemberNum(member_num);
            List<ChatRoomDto> chatRoomDtoList = new ArrayList<>();
            chatRoomEntities.forEach(v->{
                chatRoomDtoList.add(new ModelMapper().map(v,ChatRoomDto.class));
            });
//            chatMemberEntities.forEach(v->{
//                log.info("씨발");
//                log.info(String.valueOf(v.getChatRoomEntity()));
//                chatRoomDtoList.add(new ModelMapper().map(v.getChatRoomEntity(),ChatRoomDto.class));
//            });
            log.info(String.valueOf(chatRoomDtoList));
            log.info("Service Done: getListChatRoom service done");
            return chatRoomDtoList;
        }catch(Exception e){
            log.info("error: getListChatRoom service error");
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public ChatRoomDto getChatRoom(int chatRoom_num) {
        return null;
    }

    @Override
    public int updateChatRoom(ChatRoomDto chatRoom) {
        return 0;
    }

    @Override
    public int deleteChatRoom(int chatRoom_num) {
        return 0;
    }

    @Override
    public List<ResponseMember> getListMemberWithoutChatMember(int chatRoomNum, int memberNum) {
        Iterable<MemberEntity> memberEntities = memberRepository.findByMemberNumWithOutChatMember(chatRoomNum);
        log.info(String.valueOf(memberEntities));
        List<ResponseMember> responseMemberList = new ArrayList<>();
        memberEntities.forEach(v->{
            responseMemberList.add(new ModelMapper().map(v, ResponseMember.class));
        });
        log.info(String.valueOf(responseMemberList));
        return responseMemberList;
    }
}
