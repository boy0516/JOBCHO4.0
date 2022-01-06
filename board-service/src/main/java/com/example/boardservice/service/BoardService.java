package com.example.boardservice.service;


import java.util.List;

import com.example.boardservice.dto.BoardDto;
import com.example.boardservice.jpa.BoardEntity;

public interface BoardService {

	public BoardDto insertBoard(BoardDto board);

	public Iterable<BoardEntity> getListBoard(Integer teamNum);

	public BoardEntity getBoard(Long boardNum);

//	public int updateBoard(BoardVO board);
//
//	public void deleteBoard(int board_num);
//


}
