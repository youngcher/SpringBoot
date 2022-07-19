package com.ezen.demo.board;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ezen.demo.model.Board;

@Mapper
public interface BoardMapper {
	
	//Select
	List<Board> getList();

	//insert
	int writed(Board board);

	//getBoard
	Board detail(int num);

	//update
	int edited(Board board);

	int deleted(Board board);

	List<Board> serch(String serchType, String serchKey);

	
}
