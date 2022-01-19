package com.example.boardservice.vo;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseBoard {

    private Long boardNum;
    private String boardName;
    private String boardInfo;
    private Integer teamNum;
}
