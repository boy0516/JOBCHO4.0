package com.example.chatservice.controller;

import com.example.chatservice.dto.*;
import com.example.chatservice.jpa.MemberEntity;
import com.example.chatservice.service.ChatService;
import com.example.chatservice.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("team/{teamNum}/chatroom")
@Slf4j
public class ChatController {
    @Autowired
    ChatService chatService;

    //채팅 생성
    @PostMapping("/{chatRoomNum}/member/{memberNum}/chat/new")
    public ResponseEntity<ResponseChat> insertChat(
            @PathVariable("chatRoomNum") int chatRoomNum,
            @PathVariable("memberNum") int memberNum,
            @RequestBody RequestChat requestChat){
        log.info("Controller: Do post chat");
        log.info(String.valueOf(requestChat));
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT); //강력한 매칭 규칙
        ChatDto chatDto = new ChatDto();

        try{
            chatDto = mapper.map(requestChat,ChatDto.class);
            chatDto.setChatRoomNum(chatRoomNum);
            chatDto.setMemberNum(memberNum);
        }catch(Exception e){
            log.info("fail chatDto map");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        log.info("success dto mapped");
        log.info(String.valueOf(chatDto));
        int result = chatService.insertChat(chatDto);
        return result==1?ResponseEntity.status(HttpStatus.CREATED).body(null)
                        :ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    //채팅리스트 가져오기
//    @GetMapping("/{chatRoomNum}/chat")
//    public ResponseEntity<List<ResponseChat>> getListChat(@PathVariable("chatRoomNum") int chatRoomNum){
//
//        return null;
//    }

    //채팅삭제
    @DeleteMapping("/{chatRoom_num}/chat/{chat_num}")
    public ResponseEntity<String> deleteChat(@PathVariable("chat_num") int chat_num){
        return null;
    }

    //채팅멤버 초대
    @PostMapping("/{chatRoomNum}/chatmember/new")
    public ResponseEntity<ResponseChatMember> insertChatMember(
            @PathVariable("chatRoomNum") int chatRoomNum,
            @RequestBody RequestChatMember requestChatMember){
        log.info("Controller: Do post chatMember");
        log.info(String.valueOf(requestChatMember));
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT); //강력한 매칭 규칙
        ChatMemberDto chatMemberDto = new ChatMemberDto();
        try{
            chatMemberDto = mapper.map(requestChatMember,ChatMemberDto.class);
            chatMemberDto.setChatRoomNum(chatRoomNum);
        }catch(Exception e){
            log.info("fail chatMemberDto map");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        log.info("success dto mapped");
        log.info(String.valueOf(chatMemberDto));
        int result = chatService.insertChatMember(chatMemberDto);
        return result==1?ResponseEntity.status(HttpStatus.CREATED).body(null)
                :ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    //채팅멤버리스트 불러오기
//    @GetMapping("/{chatRoomNum}/chatmember")
//    public ResponseEntity<List<ResponseChatMember>> getListChatMember(@PathVariable("chatRoomNum") int chatRoomNum){
//        chatService.getListChatMember(chatRoomNum);
//        return null;
//    }

    //채팅멤버가져오기
//    @GetMapping("/{chatRoom_num}/chatmember/{member_num}")
//    public ResponseEntity<ResponseChatMember> getChatMember(@PathVariable("member_num") int member_num){
//        return null;
//    }

    //멤버삭제
    @DeleteMapping("/chatmember/{chatmember_num}")
    public ResponseEntity<String> deleteChatMember(@PathVariable("chatmember_num") int chatMember_num){
        return null;
    }

    //채팅방 생성
    @PostMapping("/new")
    public ResponseEntity<ResponseChatRoom> insertChatRoom(@RequestBody RequestChatRoom requestChatRoom,
                                                           @PathVariable("teamNum") int teamNum){
        log.info("Controller: Do post chatRoom");
        log.info(String.valueOf(requestChatRoom));
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT); //강력한 매칭 규칙
        ChatRoomDto chatRoomDto = new ChatRoomDto();
        TeamDto teamDto = new TeamDto();
        MemberDto memberDto = new MemberDto();
        try{
            teamDto.setTeamNum(teamNum);
            memberDto.setMemberNum(requestChatRoom.getMemberNum());
            chatRoomDto = mapper.map(requestChatRoom,ChatRoomDto.class);
            chatRoomDto.setTeamDto(teamDto);
            chatRoomDto.setMemberDto(memberDto);
//            chatRoomDto.set
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        log.info("success dto mapped");
        log.info(String.valueOf(chatRoomDto));
        int result = chatService.insertChatRoom(chatRoomDto);
        return result==1?ResponseEntity.status(HttpStatus.CREATED).body(null)
                :ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    //채팅방리스트가져오기
    @GetMapping("/member/{memberNum}")
    public ResponseEntity<List<ResponseChatRoom>> getListChatRoom(@PathVariable("teamNum") int teamNum,
                                                                  @PathVariable("memberNum") int memberNum){
        List<ChatRoomDto> chatRoomDtoList = chatService.getListChatRoom(teamNum, memberNum);
        log.info("chatservice return not null check");
        if(chatRoomDtoList==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        log.info("check OK");
        List<ResponseChatRoom> result = new ArrayList<>();
        try{
            log.info("chatRoomDto -> ResponseChatRoom mapping...");
            chatRoomDtoList.forEach(v->{
                result.add(new ModelMapper().map(v, ResponseChatRoom.class));
            });
        }catch(Exception e){
            log.info("mapping error");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        log.info("mapping done, controller return..");
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    //채팅방하나 가져오기
//    @GetMapping("/{chatRoom_num}")
//    public ResponseEntity<ResponseChatRoom> getChatRoom(@PathVariable("chatRoom_num") int chatRoom_num){
//        return null;
//    }

    //채팅방 정보 수정
    @PutMapping("/{chatRoom_num}")
    public ResponseEntity<ResponseChatRoom> updateChatRoom(
            @PathVariable("chatRoom_num")int chatRoom_num,
            @RequestBody RequestChatRoom chatRoom){
        return null;
    }

    //채팅방 삭제
    @DeleteMapping("/{chatRoom_num}")
    public ResponseEntity<String> deleteChatRoom(@PathVariable("chatRoom_num") int chatRoom_num){
        return null;
    }

}
