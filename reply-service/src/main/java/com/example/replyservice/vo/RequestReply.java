package com.example.replyservice.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class RequestReply {

    private Long replyNum;
    private String replyContents;
    private Integer memberNum;
    private Integer postNum;
    private String replyWriter;
    private int teamNum;
    private int boardNum;
}
