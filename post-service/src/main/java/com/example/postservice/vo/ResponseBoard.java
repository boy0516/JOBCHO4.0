package com.example.postservice.vo;

import lombok.Data;

@Data
public class ResponseBoard {

    private Long boardNum;
    private String boardName;
    private String boardInfo;
    private Integer memberNum;
    private Integer teamNum;
}
