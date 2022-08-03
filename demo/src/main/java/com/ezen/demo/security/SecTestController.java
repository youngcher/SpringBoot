package com.ezen.demo.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/sec")
public class SecTestController {
	
	@GetMapping("")
	@ResponseBody
	public String index() {
		return "Spring security Test";
	}

	@GetMapping("/loginForm")
	public String showLoginForm() {
		return "sec/loginForm";
	}
	
}
