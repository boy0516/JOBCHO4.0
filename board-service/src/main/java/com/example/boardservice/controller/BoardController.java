package com.example.boardservice.controller;

import java.util.List;


import com.example.boardservice.jpa.BoardEntity;
import com.example.boardservice.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;



import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/team/{team_num}/board/*")
public class BoardController {
	
	
	@Autowired
	private BoardService service;
	
	

	/* REST API
	 * 게시판 리스트 조회(PostMan 확인O 12/6)
	 * 메인 화면에서 항상 호출
	 */
//	@GetMapping(value = "", produces = "application/json")
//	public ResponseEntity<List<>> getListBoard(@PathVariable int team_num){
//
//		List<BoardEntity> board = service.getListBoard(team_num);
//
//		return new ResponseEntity<>(board, HttpStatus.OK);
//	}
	
	
	
	/* REST API
	 * 게시판 생성
	 * member_num 받아서 오기
	 */
	@PostMapping(value = "new", produces = "application/json")
	public ResponseEntity<BoardEntity> insertBoard(@RequestBody BoardEntity board,
												@PathVariable("team_num") Long team_num){



		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	
	
	/*
	 * 게시판 수정
	 * 
	 */
	



	/*
	 * 게시판 삭제
	 * 
	 */
	@DeleteMapping(value = "/{board_num}")
	public ResponseEntity<String> deleteBoard(@PathVariable int board_num) {



		return new ResponseEntity<>("success", HttpStatus.OK);
	}
	
	
	
	
	
	
	
	
	
	
}
