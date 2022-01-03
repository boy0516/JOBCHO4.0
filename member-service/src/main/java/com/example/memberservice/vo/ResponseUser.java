package com.example.memberservice.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseUser {
    private int userNum;
    private String userName;
    private String userEmail;
    private String userPhoneNum;
}