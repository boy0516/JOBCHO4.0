package com.example.userservice.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestUser {
    private String userName;
    private String userEmail;
    private String userPw;
    private String userPwHint;
    private String userPhoneNum;
    private String profileName;
}
