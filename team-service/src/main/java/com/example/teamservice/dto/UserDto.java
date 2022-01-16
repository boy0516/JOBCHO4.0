package com.example.teamservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private int userNum;
    private String userName;
    private String userPhoneNum;
    private String userEmail;
    private String userBirth;
    private String userPw;
    private String userPwHint;
    private String isLive;
    private Date userDate;
}
