package com.example.postservice.vo;

import lombok.Data;

import java.util.Date;

@Data
public class ResponsePost {

    private Long postNum;
    private String postTitle;
    private String postContexts;
    private Integer boardNum;
    private String writer;
    private Integer replyCnt;
    private Date PostDate;

}
