package com.example.chatservice.jpa;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class ChatEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int chatNum;

    @Column(nullable = false)
    private String chatContents;

    @Column(nullable = false, insertable = false)
    @ColumnDefault(value = "1")
    private int isLive;
    @Column(nullable = false, updatable = false, insertable = false)
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    private Date createAt;
    @Column
    private String uploadName;

    @ManyToOne
    @JoinColumn(name = "CHAT_ROOM_NUM")
    private ChatRoomEntity chatRoomEntity;

    @ManyToOne
    @JoinColumn(name = "MEMBER_NUM")
    private MemberEntity memberEntity;

}
