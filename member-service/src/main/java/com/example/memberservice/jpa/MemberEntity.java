package com.example.memberservice.jpa;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "member",uniqueConstraints = {@UniqueConstraint(columnNames = {"userNum","teamNum"})})
public class MemberEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int memberNum;

    @Column(nullable = false)
    private int userNum;
    @Column(nullable = false)
    private int teamNum;
    @Column(nullable = false)
    private String memberName;
    @Column(nullable = false)
    private String memberPosition;

    @Column(nullable = false, insertable = false)
    @ColumnDefault(value = "1")
    private int isLive;

    @Column(nullable = false, updatable = false, insertable = false)
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    private Date createAt;

    @Column(columnDefinition = "varchar(255) default 'default.jpg'")
    private String profileName;
}
