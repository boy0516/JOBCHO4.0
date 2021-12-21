package com.example.boardservice.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
public class RequestBoard implements Serializable {

	private String boardName;
	private String boardInfo;
	private Integer memberNum;
	private Integer teamNum;
}
