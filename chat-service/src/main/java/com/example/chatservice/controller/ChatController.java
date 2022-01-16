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
@RequestMapping("member/{memberNum}")
@Slf4j
public class ChatController {
    @Autowired
    ChatService chatService;

    //채팅 생성
    @PostMapping("/chatroom/{chatRoomNum}/chat/new")
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

//    채팅리스트 가져오기
    @GetMapping("/chatroom/{chatRoomNum}/chat")
    public ResponseEntity<List<ResponseChat>> getListChat(@PathVariable("memberNum") int memberNum,
            @PathVariable("chatRoomNum") int chatRoomNum){
        List<ResponseChat> result = chatService.getListChat(memberNum,chatRoomNum);
        return result!=null?ResponseEntity.status(HttpStatus.CREATED).body(result)
                :ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    //채팅삭제
    @DeleteMapping("/chatroom/{chatRoomNum}/chat/{chatNum}")
    public ResponseEntity<String> deleteChat(@PathVariable("chat_num") int chat_num){
        return null;
    }

    //채팅멤버 초대
    @PostMapping("/chatroom/{chatRoomNum}/chatmember/new")
    public ResponseEntity<ResponseChatMember> insertChatMember(
            @PathVariable("chatRoomNum") int chatRoomNum,
            @RequestBody  RequestMemberList requestMemberList){
        log.info("Controller: Do post chatMember");
        log.info(String.valueOf(requestMemberList));
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT); //강력한 매칭 규칙
        List<ChatMemberDto> chatMemberDtoList = new ArrayList<>();
        requestMemberList.getMemberList().forEach(v->{
            ChatMemberDto chatMemberDto = new ChatMemberDto();
            chatMemberDto.setMemberEntity(v);
            chatMemberDtoList.add(chatMemberDto);
        });

        log.info("success dto mapped");
        log.info(String.valueOf(chatMemberDtoList));
        int result = chatService.insertChatMember(chatMemberDtoList, chatRoomNum);
        return result==1?ResponseEntity.status(HttpStatus.CREATED).body(null)
                :ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    //채팅멤버리스트 불러오기
    @GetMapping("/chatroom/{chatRoomNum}/chatmember")
    public ResponseEntity<List<ResponseChatMember>> getListChatMember(
            @PathVariable("chatRoomNum") int chatRoomNum,
            @PathVariable("memberNum") int memberNum){
        List<ResponseChatMember> result = chatService.getListChatMember(chatRoomNum,memberNum);
        return result!=null?ResponseEntity.status(HttpStatus.CREATED).body(result)
                :ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    //채팅멤버가져오기
//    @GetMapping("/{chatRoom_num}/chatmember/{member_num}")
//    public ResponseEntity<ResponseChatMember> getChatMember(@PathVariable("member_num") int member_num){
//        return null;
//    }

    //멤버삭제
    @DeleteMapping("/chatroom/{chatRoomNum}/chatmember/{chatmember_num}")
    public ResponseEntity<String> deleteChatMember(@PathVariable("chatmember_num") int chatMember_num){
        return null;
    }

    //채팅방 생성
    @PostMapping("/chatroom/new")
    public ResponseEntity<ResponseChatRoom> insertChatRoom(@RequestBody RequestChatRoom requestChatRoom,
                                                           @PathVariable("memberNum") int memberNum){
        log.info("Controller: Do post chatRoom");
        log.info(String.valueOf(requestChatRoom));
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT); //강력한 매칭 규칙
        ChatRoomDto chatRoomDto = new ChatRoomDto();
        TeamDto teamDto = new TeamDto();
        MemberDto memberDto = new MemberDto();
        try{
            memberDto.setMemberNum(memberNum);
            chatRoomDto = mapper.map(requestChatRoom,ChatRoomDto.class);
            chatRoomDto.setMemberDto(memberDto);
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
    @GetMapping("/chatroom")
    public ResponseEntity<List<ResponseChatRoom>> getListChatRoom(@PathVariable("memberNum") int memberNum){
        List<ChatRoomDto> chatRoomDtoList = chatService.getListChatRoom(memberNum);
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
        log.info("get chatroom success");
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    //채팅방하나 가져오기
//    @GetMapping("/{chatRoom_num}")
//    public ResponseEntity<ResponseChatRoom> getChatRoom(@PathVariable("chatRoom_num") int chatRoom_num){
//        return null;
//    }

    //채팅방 정보 수정
    @PutMapping("/chatroom/{chatRoom_num}")
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

    //채팅방 초대할 수 있는 멤버 리스트 호출
    @GetMapping("/chatroom/{chatRoomNum}/withoutchatmember")
    public ResponseEntity<List<ResponseMember>> getWithoutChatMemberList(
            @PathVariable("memberNum")int memberNum,
            @PathVariable("chatRoomNum") int chatRoomNum){
        List<ResponseMember> result = chatService.getListMemberWithoutChatMember(chatRoomNum,memberNum);
        return result!=null?ResponseEntity.status(HttpStatus.CREATED).body(result)
                :ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
}
