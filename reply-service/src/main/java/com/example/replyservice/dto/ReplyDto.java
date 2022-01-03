package com.example.replyservice.dto;

import lombok.Data;


import java.util.Date;

@Data
public class ReplyDto {

    private Long replyNum;
    private String replyContents;
    private Integer memberNum;
    private Integer postNum;
    private Date replyDate;
    private String replyWriter;


}
