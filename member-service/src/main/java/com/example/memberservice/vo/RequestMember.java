package com.example.memberservice.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestMember {
    private int userNum;
    private String memberName;
    private String memberPosition;
}
