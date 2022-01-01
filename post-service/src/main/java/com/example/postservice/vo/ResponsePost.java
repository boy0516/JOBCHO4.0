package com.example.postservice.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class ResponsePost {

    private Long postNum;
    private String postTitle;
    private String postContents;
    private Integer boardNum;
    private String writer;
    private Integer replyCnt;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date PostDate;

}
