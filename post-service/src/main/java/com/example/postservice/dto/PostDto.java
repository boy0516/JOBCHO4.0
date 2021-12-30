package com.example.postservice.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PostDto implements Serializable {

    private Long postNum;
    private String postTitle;
    private String postContexts;
    private Integer boardNum;
    private Integer memberNum;
    private String writer;
}
