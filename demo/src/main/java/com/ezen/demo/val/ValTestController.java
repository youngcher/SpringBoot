package com.ezen.demo.val;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/val")
public class ValTestController {

	@Autowired
	private PersonRepository personRepository;
	
	@GetMapping("/list")
	public ModelAndView list() {
		
		
		return null;
	}
	
}
