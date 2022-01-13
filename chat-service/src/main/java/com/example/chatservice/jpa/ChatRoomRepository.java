package com.example.chatservice.jpa;

import org.springframework.data.repository.CrudRepository;

public interface ChatRoomRepository extends CrudRepository<ChatRoomEntity, Integer> {
//    Iterable<ChatRoomEntity> findByMemberEntityMemberNum(int memberNum);
    ChatRoomEntity findByChatRoomNum(int chatRoomNum);
}
