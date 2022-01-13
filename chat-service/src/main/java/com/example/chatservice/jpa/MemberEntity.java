package com.example.chatservice.jpa;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "member",uniqueConstraints = {@UniqueConstraint(columnNames = {"userNum","team_num"})})
@DynamicInsert
@DynamicUpdate
public class MemberEntity implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int memberNum;

    @Column(nullable = false)
    private int userNum;

    @Column(nullable = false)
    private String memberName;
    @Column(nullable = false)
    @ColumnDefault(value = "'팀원'")
    private String memberPosition;

    @Column(nullable = false, insertable = false)
    @ColumnDefault(value = "1")
    private int isLive;

    @Column(nullable = false, updatable = false, insertable = false)
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    private Date createAt;

    @Column(nullable = false)
    @ColumnDefault(value = "'default.jpg'")
    private String profileName;

}
