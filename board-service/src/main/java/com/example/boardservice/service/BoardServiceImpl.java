package com.example.boardservice.service;

import com.example.boardservice.dto.BoardDto;
import com.example.boardservice.jpa.BoardEntity;
import com.example.boardservice.jpa.BoardRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BoardServiceImpl implements BoardService {

	private final BoardRepository boardRepository;


	public BoardServiceImpl(BoardRepository boardRepository) {
		this.boardRepository = boardRepository;
	}

	//게시판 등록
	@Override
	public BoardDto insertBoard(BoardDto boardDto) {

		ModelMapper mapper = new ModelMapper();

		//BoardDto -> BoardEntity 변경
		BoardEntity boardEntity = mapper.map(boardDto, BoardEntity.class);

		//BoardEntity 타입 DB 저장
		boardRepository.save(boardEntity);

		//BoardEntity -> BoardDto 변경
		BoardDto returnValue = mapper.map(boardEntity, BoardDto.class);

		return returnValue;

	}


	//게시판 목록
	@Override
	public Iterable<BoardEntity> getListBoard(Integer teamNum) {

		return boardRepository.findByTeamNum(teamNum);
	}

	@Override
	public BoardEntity getBoard(Long boardNum) {
		return boardRepository.findByBoardNum(boardNum);
	}


}
