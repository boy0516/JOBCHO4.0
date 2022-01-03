package com.example.userservice.jpa;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name="users")
public class UserEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userNum;

    @Column(nullable = false)
    private String userName;
    @Column(nullable = false)
    private String userPhoneNum;
    @Column(nullable = true)
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
}
