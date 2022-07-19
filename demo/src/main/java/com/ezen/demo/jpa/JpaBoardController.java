package com.ezen.demo.jpa;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;




//import lombok.extern.slf4j.Slf4j;

//@Slf4j
@Controller
@RequestMapping("/jpa/board")
public class JpaBoardController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private BoardRepositoy boardRepositoy;

	@GetMapping("/write_form")
	public String inputform() {
		return "board/inputform";
	}

	@PostMapping("/save")
	@ResponseBody
	public Map<String, Object> saved(@RequestParam("title")String title,
			@RequestParam("contents")String contents) {

		Board board = new Board();
		board.setTitle(title);
		board.setContents(contents);
		board.setAuthor("young");
		java.sql.Date wdate = new java.sql.Date(new java.util.Date().getTime());
		board.setWdate(wdate);
		
		Board saveBoard = boardRepositoy.save(board);
		
		boolean result = board.getTitle().equals(saveBoard.getTitle());
		Map<String, Object> map = new HashMap<>();
		map.put("result", result);
		return map;
	}

	@GetMapping("/list")
	public String getList(Model model) {
		System.out.println("1");
		List<Board> list = boardRepositoy.findAll();
		System.out.println("2");
		model.addAttribute("list", list);
		return "board/boardList";
	}

	@GetMapping("/detail/{num}")
	public String detail(@PathVariable("num")int num, Model model) {
		Optional<Board> op = boardRepositoy.findById(num);
		Board board = op.get();
		model.addAttribute("board", board);
		return "board/detailBoard";
	}
	
	@GetMapping("/edit_form/{num}")
	public String editForm(@PathVariable("num")int num, Model model) {
		Optional<Board> op = boardRepositoy.findById(num);
		Board board = op.get();
		model.addAttribute("board", board);
		return "board/edited";
	}
	
	@PostMapping("/edited")
	@ResponseBody
	public Map<String, Object> edited(@RequestParam("num")int num,
			@RequestParam("title")String title,
			@RequestParam("contents")String contents){
		int aaa = boardRepositoy.update(num, title, contents);
		boolean result;
		if(aaa>0) {
			result=true;
		} else {
			result=false;
		}
		
		Map<String, Object> map = new HashMap<>();
		map.put("result", result);
		return map;
	}
	
	@PostMapping("/deleted")
	@ResponseBody
	public Map<String, Object> deleted(@RequestParam("num")int num){
		
		Optional<Board> op = boardRepositoy.findById(num);
		
		boolean result;
		if(op.isPresent()) {
			boardRepositoy.deleteById(num);
			result=true;
		} else {
			result=false;
		}
		
		Map<String, Object> map = new HashMap<>();
		map.put("result", result);
		return map;
	}
	
	//list?page=0&size=5
	@GetMapping("/getpage/list")
	public String getPageList(Pageable pageable,Model model) {	
		
		
		Page<Board> pageinfo = boardRepositoy.findAll(pageable);
//		List<Board> list = pageinfo.
		
		System.out.println(pageinfo.getSize()+" "
				+ pageinfo.getTotalPages()+" "+
				pageinfo.getNumber());
		
		//List<Board> list = pageinfo.getContent();
		
		model.addAttribute("pageinfo", pageinfo);
		
		return "board/boardList";
		
	}
	

}