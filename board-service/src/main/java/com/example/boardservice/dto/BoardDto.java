package com.example.boardservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
public class BoardDto {

    private Integer boardNum;
    private String boardName;
    private String boardInfo;
    private Integer memberNum;
    private Integer teamNum;
    private Integer isLive; // delete 여부
    private Date boardDate;
}
