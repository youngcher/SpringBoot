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
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.ezen.demo.model.Board;
import com.ezen.demo.model.User;
import com.ezen.demo.service.UserService;

@SessionAttributes("uid")
@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService svc;
	
	@GetMapping("")
	public String nolink() {
		return "login_form";
	}
	
	@PostMapping("/login")
	public String login(User user, Model model) {
		
		model.addAttribute("uid",user.getUid());
		boolean result = svc.login(user);
		System.out.println(result);
		
		return "showUser";
	}
	
	@GetMapping("/write")
	public String write(Board board, @SessionAttribute(name="uid",required=false) String uid) {

		if(uid==null) {
			return "redirect:/user";
		} else {
			return "board_inputform";
		}
	}
	
	@PostMapping("/logout")
	@ResponseBody
	public Map<String,Object> logout(SessionStatus status) {
		status.setComplete();
		Map<String,Object> map = new HashMap<>();
		map.put("result", true);
		return map;
	}
}
