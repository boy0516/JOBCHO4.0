package com.example.boardservice.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardVO implements Serializable {
	private Long board_num;
	private String board_name;
	private String board_info;
	private Long member_num;
	private Long team_num;
	private int isLive; // delete 여부
	private Date board_date;
	
	
	
}
