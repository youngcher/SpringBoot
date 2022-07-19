package com.ezen.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezen.demo.model.Board;
import com.ezen.demo.service.BoardService;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService svc;
	
	@GetMapping("/input")
	public String inputform(Model model) {
		
		Board b = new Board();
		model.addAttribute("board",b); //폼의 hidden 필드(pcode)에 0이 전달되돌고 함.
		return "board_inputform";
	}
	
//	@PostMapping("/save")
//	@ResponseBody
//	public Map<String, Object> save(Board board) {
//		
//		System.out.println(board.getTitle());
//		System.out.println(board.getContents());
//		
//		Map<String, Object> map = new HashMap<>();
//		map.put("title", board.getTitle());
//		map.put("contents", board.getContents());
//	
//		BoardDAO dao = new BoardDAO();
//		boolean result = dao.save(map);
//		
//		map.put("result", result);
//		
//		return map;
//	}
	
	@PostMapping("/save")
	@ResponseBody
	public Map<String,Object> save(Board board) {
		boolean result = svc.save(board);
		Map<String,Object> map = new HashMap<>();
		map.put("result", result);
		
		return map;
	}
	
	
}
