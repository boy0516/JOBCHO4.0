package com.example.chatservice.jpa;

import org.springframework.data.repository.CrudRepository;

public interface ChatMemberRepository extends CrudRepository<ChatMemberEntity,Long> {
    ChatMemberEntity findByChatMemberNum(int chatMemberNum);
    Iterable<ChatMemberEntity> findByMemberEntityMemberNum(int memberNum);
    ChatMemberEntity findByChatRoomEntityChatRoomNumAndMemberEntityMemberNum(int chatRoomNum, int memberNum);
}
