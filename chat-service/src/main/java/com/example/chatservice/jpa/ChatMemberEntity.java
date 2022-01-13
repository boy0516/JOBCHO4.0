package com.example.chatservice.jpa;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Table(uniqueConstraints = {@UniqueConstraint(name="d", columnNames = {"member_num","chat_room_num"})})
@Entity
@DynamicUpdate
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class ChatMemberEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int chatMemberNum;

    @Column(nullable = false, insertable = false)
    @ColumnDefault(value = "1")
    private int isLive;
    @Column(nullable = false, updatable = false, insertable = false)
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    private Date createAt;

    @ManyToOne
    @JoinColumn(name = "CHAT_ROOM_NUM")
    private  ChatRoomEntity chatRoomEntity;


    @ManyToOne
    @JoinColumn(name = "MEMBER_NUM")
    private MemberEntity memberEntity;
}
