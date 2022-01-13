package com.example.chatservice.jpa;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name="team")
public class TeamEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int teamNum;

    @Column(nullable = false)
    private int userNum;
    @Column(nullable = false)
    private String teamName;
    @Column(nullable = true)
    private String teamInfo;
    @Column(nullable = false, insertable = false)
    @ColumnDefault(value = "1")
    private int isLive;
    @Column(nullable = false, updatable = false, insertable = false)
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    private Date teamDate;

    @OneToMany
    @JoinColumn(name = "TEAM_NUM")
    private List<MemberEntity> memberEntities = new ArrayList<>();

}
