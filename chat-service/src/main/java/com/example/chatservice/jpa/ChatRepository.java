package com.example.chatservice.jpa;

import org.springframework.data.repository.CrudRepository;

public interface ChatRepository extends CrudRepository<ChatEntity,Long> {
//    Iterable<ChatEntity> findByChatMemberNumAndIsLive(int chatMemberNum, int isLive);
}
