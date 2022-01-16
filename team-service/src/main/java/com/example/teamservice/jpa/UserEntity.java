package com.example.teamservice.jpa;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name="users")
@DynamicInsert
@DynamicUpdate
public class UserEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userNum;

    @Column(nullable = false)
    private String userName;
    @Column(nullable = false)
    private String userPhoneNum;
    @Column(nullable = false)
    private String userEmail;
    @Column(nullable = false)
    private String userPw;
    @Column(nullable = false)
    private String userPwHint;
    @Column(nullable = true)
    private String userBirth;
    @Column(nullable = false, insertable = false)
    @ColumnDefault(value = "1")
    private int isLive;
    @Column(nullable = false, updatable = false, insertable = false)
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    private Date userDate;
    @ColumnDefault(value = "'default.jpg'")
    private String profileName;
}
