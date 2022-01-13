package com.example.chatservice.service;

import com.example.chatservice.dto.ChatDto;
import com.example.chatservice.dto.ChatMemberDto;
import com.example.chatservice.dto.ChatRoomDto;

import com.example.chatservice.jpa.*;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ChatServiceImpl implements ChatService{

    @Autowired
    ChatRepository chatRepository;

    @Autowired
    TeamRepository teamRepository;

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
        chatRoomEntity.getChatEntities().add(chatEntity);
        try{
            chatRepository.save(chatEntity);
            chatRoomRepository.save(chatRoomEntity);
            log.info("success create chat");
            return 1;
        }catch(Exception e){
            log.info("Fail create chat");
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<ChatDto> getListChat(int chatRoom_num) {
//        chatRepository.findByChatMemberNumAndIsLive(chatRoom_num, )
        return null;
    }

    @Override
    public int deleteChat(int chat_num) {
        return 0;
    }

    @Override
    public int insertChatMember(ChatMemberDto chatMemberDto) {
        log.info("Service: create chatMember");
        ChatMemberEntity chatMemberEntity = new ModelMapper().map(chatMemberDto,ChatMemberEntity.class);
        log.info(String.valueOf(chatMemberEntity));
        try{
            chatMemberRepository.save(chatMemberEntity);
            log.info("success create chatMember");
            return 1;
        }catch(Exception e){
            log.info("Fail create chatMember");
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<ChatMemberDto> getListChatMember(int chatRoom_num) {
        Iterable<ChatMemberEntity> chatMemberEntities = chatMemberRepository.findAll();
        log.info(String.valueOf(chatMemberEntities));

        return null;
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
        //영속성 문제로 먼저 chatMember save

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
    public List<ChatRoomDto> getListChatRoom(int x, int member_num) {
        log.info("Service: getListChatRoom");
        try{
//            Iterable<ChatRoomEntity> chatRoomEntities = chatRoomRepository.findByMemberEntityMemberNum(member_num);
//            log.info(String.valueOf(chatRoomEntities));
            Iterable<ChatMemberEntity> chatMemberEntities = chatMemberRepository.findByMemberEntityMemberNum(member_num);
            log.info(String.valueOf(chatMemberEntities));
            List<ChatRoomDto> chatRoomDtoList = new ArrayList<>();
            chatMemberEntities.forEach(v->{
                log.info("씨발");
                log.info(String.valueOf(v.getChatRoomEntity()));
                chatRoomDtoList.add(new ModelMapper().map(v.getChatRoomEntity(),ChatRoomDto.class));
            });
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
}
