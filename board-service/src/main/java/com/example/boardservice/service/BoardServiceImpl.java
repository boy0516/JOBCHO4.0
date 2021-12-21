package com.example.boardservice.service;

import java.util.HashMap;
import java.util.List;


import com.example.boardservice.jpa.BoardEntity;
import com.example.boardservice.jpa.BoardRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Service
@Slf4j
public class BoardServiceImpl implements BoardService {

	BoardRepository boardRepository;

	@Autowired
	public BoardServiceImpl(BoardRepository boardRepository){
		this.boardRepository = boardRepository;
	}
	
	//게시판 등록
	@Override
	public BoardEntity insertBoard(BoardEntity board) {

		BoardEntity boardEntity = boardRepository.save(board);
		return boardEntity;

	}


	//게시판 목록
//	@Override
//	public List<BoardEntity> getListBoard(int team_num) {
//
//		return null;
//	}

	

}
