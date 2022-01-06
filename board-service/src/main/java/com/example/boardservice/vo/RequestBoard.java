package com.example.boardservice.vo;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
public class RequestBoard implements Serializable {

	private Long boardNum;
	private String boardName;
	private String boardInfo;
	private Integer memberNum;
	private Integer teamNum;
}
