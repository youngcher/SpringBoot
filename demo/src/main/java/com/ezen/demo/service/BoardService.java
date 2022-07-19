package com.ezen.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.demo.model.Board;
import com.ezen.demo.model.BoardDAO;

@Service
public class BoardService {

	
	@Autowired
	private BoardDAO dao;

	public boolean save(Board board) {
		boolean result = dao.save(board);
		return result;
	}

	
}
