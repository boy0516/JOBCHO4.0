package com.example.boardservice.controller;

import java.util.ArrayList;
import java.util.List;


import com.example.boardservice.dto.BoardDto;
import com.example.boardservice.jpa.BoardEntity;
import com.example.boardservice.service.BoardService;
import com.example.boardservice.vo.RequestBoard;
import com.example.boardservice.vo.ResponseBoard;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
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
@Slf4j
public class BoardController {
	
	
	@Autowired
	private BoardService boardService;
	
	

	/* REST API
	 * 게시판 리스트 조회(PostMan 확인O 12/6)
	 * 메인 화면에서 항상 호출
	 */
	@GetMapping(value = "", produces = "application/json")
	public ResponseEntity<List<ResponseBoard>> getListBoard(@PathVariable("team_num") Integer teamNum){

		log.info("게시판 목록 불러오기 전" + teamNum);
		Iterable<BoardEntity> boardList = boardService.getListBoard(teamNum);


		//Entity -> ResponseBoard
		List<ResponseBoard> board = new ArrayList<>();
		boardList.forEach(v -> {
			board.add(new ModelMapper().map(v, ResponseBoard.class));
		});

		log.info("게시판 목록 불러온 후" + board);

		return ResponseEntity.status(HttpStatus.OK).body(board);
	}
	
	
	
	/* REST API
	 * 게시판 생성
	 * member_num 받아서 오기
	 */
	@PostMapping(value = "new", produces = "application/json")
	public ResponseEntity<ResponseBoard> insertBoard(@RequestBody RequestBoard board,
													 @PathVariable("team_num") Integer teamNum){

		log.info("데이터 입력 전" + board);
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		//RequestBoard -> BoardDto 변경
		BoardDto boardDto = mapper.map(board, BoardDto.class);

		//team 번호저장
		boardDto.setTeamNum(teamNum);
		BoardDto createdBoard = boardService.insertBoard(boardDto);

		//ResponseBoard 변경
		ResponseBoard responseBoard = mapper.map(createdBoard, ResponseBoard.class);

		log.info("데이터 입력 후" + responseBoard);
		return ResponseEntity.status(HttpStatus.CREATED).body(responseBoard);
	}
	
	
	
	

	
	
	
	
	
	
	
	
	
	
}
