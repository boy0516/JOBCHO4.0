package com.example.replyservice.vo;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class ResponseReply {

    private Long replyNum;
    private String replyContents;
    private Integer postNum;
    private String replyWriter;
    private Date replyDate;
}
