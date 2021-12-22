package com.example.postservice.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Criteria { //페이지 처리 도메인
	
	private int pageNum;
	private int amount;
	
//	private String type;
//	private String keyword;


	//기본 페이지
	public Criteria() {
		this(1, 10); //페이지 디폴트값 설정
	}

	//페이지 설정
	public Criteria(int pageNum, int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
	}

	//검색 처리
//	public String[] getTypeArr() {
//		return type == null ? new String[] {} : type.split("");
//	}
	
	
}
