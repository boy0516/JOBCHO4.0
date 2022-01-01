package com.example.postservice.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PostDto implements Serializable {

    private Long postNum;
    private String postTitle;
    private String postContents;
    private Integer boardNum;
    private Integer memberNum;
    private String writer;
    private Date postDate;
}
