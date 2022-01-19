package com.example.teamservice.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestMember {
    private int userNum;
    private String memberName;
    private String memberPosition;
}
