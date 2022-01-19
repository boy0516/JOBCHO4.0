package com.example.memberservice.jpa;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "member",uniqueConstraints = {@UniqueConstraint(columnNames = {"user_num","team_num"})})
@DynamicInsert
@DynamicUpdate
public class MemberEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int memberNum;

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

    @ManyToOne
    @JoinColumn(name="USER_NUM")
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name="TEAM_NUM")
    private TeamEntity teamEntity;

}
