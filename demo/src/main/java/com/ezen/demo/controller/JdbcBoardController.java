package com.ezen.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezen.demo.dao.JdbcBoardDao;
import com.ezen.demo.model.Board;

@Controller
@RequestMapping("/jdbc/board")
public class JdbcBoardController {

	@Autowired
	private JdbcBoardDao dao;
	
	@GetMapping("/list")
	public String getboardList(Model model) {
		List<Board> list = dao.getList();
		model.addAttribute("list", list);
		return "boardList";
	}
	
	@GetMapping("/write_form")
	public String writeForm() {
		return "write_form";
	}
	
	@PostMapping("/write")
	@ResponseBody
	public Map<String,Object> write(Board board) {
		boolean result = dao.write(board);
		Map<String,Object> map = new HashMap<>();
		map.put("result", result);
		return map;
	}
	
	@GetMapping("/get_board/{num}")
	public String getBoard(@PathVariable("num")int num, Model model) {
		Board board = dao.getBoard(num);
		model.addAttribute("board", board);
		return "detailBoard";
	}
	
	@PostMapping("/deleted")
	@ResponseBody
	public Map<String,Object> deleted(@RequestParam("num")int num){
		boolean deleted = dao.deleted(num);
		Map<String,Object> map = new HashMap<>();
		map.put("deleted", deleted);
		return map;
	}
	
	@GetMapping("/edit_form/{num}")
	public String edit_form(@PathVariable("num")int num, Model model){
		Board board = dao.getBoard(num);
		model.addAttribute("board", board);
		return "edited";
	}
	
	@PostMapping("/edited")
	@ResponseBody
	public Map<String,Object> edited(Board board){
		boolean updated = dao.updated(board);
		Map<String,Object> map = new HashMap<>();
		map.put("updated", updated);
		return map;
	}
	
}
