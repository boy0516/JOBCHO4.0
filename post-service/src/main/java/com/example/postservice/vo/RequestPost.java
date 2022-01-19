package com.example.postservice.vo;

import lombok.Data;

@Data
public class RequestPost {

    private Long postNum;
    private String postTitle;
    private String postContents;
    private Integer boardNum;
    private Integer memberNum;
    private String writer;

}
