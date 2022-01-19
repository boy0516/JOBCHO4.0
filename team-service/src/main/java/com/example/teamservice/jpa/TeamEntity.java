package com.example.teamservice.jpa;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name="team")
public class TeamEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int teamNum;

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

    @ManyToOne
    @JoinColumn(name="USER_NUM")
    private UserEntity userEntity;
}
