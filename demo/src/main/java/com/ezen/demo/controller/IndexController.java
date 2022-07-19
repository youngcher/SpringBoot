package com.ezen.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

// 이런 클래스를 pojo 클래스라고 한다.

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezen.demo.service.IndexService;

//spring 컨트롤러 선언
@Controller
@RequestMapping("/index")
public class IndexController {
	
	@Autowired
	private IndexService svc;
	
	//어떠 url로 받을지 설정해주어야 한다.
	//루트로 접근한다.
	@GetMapping("")
	public String index() {
		
		return "index";
	}
	
	//get방식으로 주소창에 Mapping된 곳에서 RequestParam을 사용해서 변수를 넘겨받을수 있음.
//	@GetMapping("/gugu")
	@RequestMapping(value="/gugu", method=RequestMethod.GET)
	public String gugu(@RequestParam(value = "gugudan", required=false, defaultValue="2" )int dan,Model model) {
	//													없을수도 있다		기본 default값은 2	
//		int gugudan;
//		try {
//			gugudan = Integer.parseInt(request.getParameter("gugudan"));
//		} catch(Exception e) {
//			gugudan = 2;
//		}
		System.out.println(dan);
//		IndexService svc = new IndexService(request);
		
		model.addAttribute("dan", svc.getGugu(dan));
		
		return "gugu"; // /WEB-INF/jsp/*.jsp
	}
	
	//get방식으로 url에 직접 parmater를 칠 수 있다. PathVariable로 데이터를 가져온다.
	@GetMapping("/gugu/{gugudan}")
	public String gugu3(@PathVariable("gugudan")int dan,Model model) {
		model.addAttribute("dan", svc.getGugu(dan));
		return "gugu"; // /WEB-INF/jsp/*.jsp
	}
	
	//Map을 사용하지 않고 직접 json 형식의 txt를 넘겨주는 방식
	//@ResponseBody는 직접 jsp파일을 만들지 않고 출력값을 띄우므로 alert창에 보이기 유용함
	@PostMapping("/res")
	@ResponseBody
	public String gugu4() {
		return "{\"save\":true}";
	}
	
	//Map을 사용하여 json형식을 사용하지 않아도 key와 value값을 넘겨줄수있음
	@PostMapping("/res2")
	@ResponseBody
	public Map<String,Object> gugu5() {
		Map<String,Object> map = new HashMap<>();
		map.put("saved", true);
		return map;
	}
	
	//Map을 사용하고 @PathVariable을 사용하여 parameter에서 데이터를 추출하여 메소드 parameter에 넘겨줌
	//request를 사용하지 않고 편하게 가져올 수 있음.
	@PostMapping("/res3/dan/{num}")  //Post방식
	@ResponseBody
	public Map<String,Object> gugu6(@PathVariable("num")int num) {
		
		Map<String,Object> map = new HashMap<>();
		map.put("num", num);
		return map;
	}
	
	@PostMapping("/res4/{fruit}")
	@ResponseBody
	public Map<String,Object> fruit(@PathVariable("fruit")String fruit){
		Map<String,Object> map = new HashMap<>();
		map.put("selection", fruit);
		return map;
	}
}
