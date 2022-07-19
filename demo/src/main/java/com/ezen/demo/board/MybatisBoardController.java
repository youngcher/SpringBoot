package com.ezen.demo.board;

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

import com.ezen.demo.model.Board;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("/mybatis/board")
public class MybatisBoardController {

	@Autowired
	private BoardMapper dao;
	
	@GetMapping("/list")
	public String getList(Model model) {
		List<Board> list = dao.getList();
		System.out.println(list.size());
		model.addAttribute("list", list);
		return "boardList";
	}
	
	@GetMapping("/write_form")
	public String write_form() {
		return "write_form";
	}
	
	@PostMapping("/write")
	@ResponseBody
	public Map<String, Object> writed(Board board){
		Map<String, Object> map = new HashMap<>();
		boolean result = dao.writed(board)>0;
		map.put("result", result);
		return map;
	}
	
	@GetMapping("/detail/{num}")
	public String detail(@PathVariable("num")int num, Model model) {
		Board board = dao.detail(num);
		model.addAttribute("board", board);
		return "detailBoard";
	}
	
	@GetMapping("/edit_form/{num}")
	public String edit_form(@PathVariable("num")int num, Model model) {
		Board board = dao.detail(num);
		model.addAttribute("board", board);
		return "edited";
	}
	
	@PostMapping("/edited")
	@ResponseBody
	public Map<String, Object> edited(Board board){
		Map<String, Object> map = new HashMap<>();
		boolean result = dao.edited(board)>0;
		map.put("result", result);
		return map;
	}
	
	@PostMapping("/deleted")
	@ResponseBody
	public Map<String, Object> deleted(Board board){
		Map<String, Object> map = new HashMap<>();
		boolean result = dao.deleted(board)>0;
		map.put("result", result);
		return map;
	}
	
	@PostMapping("/serch")
	public String serch(@RequestParam("serchType")String serchType,
					@RequestParam("serchKey")String serchKey,
					@RequestParam(name ="psize", defaultValue="3")int pagesize,
					@RequestParam(name ="page", defaultValue="1")int page, Model model) {
		PageHelper.startPage(page, pagesize);
//		List<Board> list = dao.serch(serchType, serchKey);
		PageInfo<Board> pageInfo = new PageInfo<>(dao.serch(serchType, serchKey));
//		model.addAttribute("list", list);
		model.addAttribute("serchType", serchType);
		model.addAttribute("serchKey", serchKey);
		model.addAttribute("pageInfo", pageInfo);
		return "serchboard";
	}
	
	@GetMapping("/pageList")
	public String pageList(Model model) {
		PageHelper.startPage(1, 12);
		PageInfo<Board> pageInfo = new PageInfo<>(dao.getList());
		List<Board> list = pageInfo.getList();
		model.addAttribute("pageInfo", pageInfo);
		return "boardList";
	}
	
	@GetMapping("/pageList/page/{ipage}")
	public String selectpage(@PathVariable("ipage")int ipage, Model model) {
		PageHelper.startPage(ipage, 12);
		PageInfo<Board> pageInfo = new PageInfo<>(dao.getList());
		List<Board> list = pageInfo.getList();
		model.addAttribute("pageInfo", pageInfo);
		return "boardList";
	}
}
