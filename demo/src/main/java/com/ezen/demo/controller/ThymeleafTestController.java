package com.ezen.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ezen.demo.model.Emp;

@Controller
@RequestMapping("/thymeleaf")
public class ThymeleafTestController {

	
	@GetMapping("/test1")
	public String test1() {
		
		return "thymeleaf/index";
	}
	
	@GetMapping("/test2")
	public String test2(Model model) {
		model.addAttribute("item", "Smith");
		return "thymeleaf/index";
	}
	
	@GetMapping("/test3")
	public String test3(Model model) {
		Emp emp = new Emp();
		emp.setEmpno(1111);
		emp.setEname("Neymar");
		emp.setDeptno(50);
		emp.setHiredate(java.sql.Date.valueOf("2010-10-21"));
		emp.setSal(2100);
		
		model.addAttribute("emp", emp);
		return "thymeleaf/index";
	}
	
	@GetMapping("/test4")
	public String test4(Model model) {
		List<Emp> list = new ArrayList<>();
		
		Emp emp = new Emp();
		
		emp.setEmpno(1111);
		emp.setEname("Neymar");
		emp.setDeptno(50);
		emp.setHiredate(java.sql.Date.valueOf("2010-10-21"));
		emp.setSal(2100);
		
		Emp emp2 = new Emp();
		emp2.setEmpno(1004);
		emp2.setEname("손흥민");
		emp2.setDeptno(60);
		emp2.setHiredate(java.sql.Date.valueOf("2011-05-25"));
		emp2.setSal(9999);
		
		list.add(emp);
		list.add(emp2);
		
		model.addAttribute("list", list);
		return "thymeleaf/index";
	}
	
	@GetMapping("/test5")
	public String test5(Model model) {
		
		model.addAttribute("user1", "Smith");
		model.addAttribute("user2", "Alen");
		return "thymeleaf/index";
	}
	
	@GetMapping("/menu")
	public String menu(Model model) {
		
		model.addAttribute("menu", "Menu 입니다.");
		return "thymeleaf/menu";
	}
	
	@GetMapping("/input_form")
	public String input_form() {
		return "thymeleaf/input_form";
	}
	
	@GetMapping("/save")
	public String save(Emp emp, Model model) {
		//sal가 5000보다 낮아야 한다
		if(emp.getSal()>=5000) {
			model.addAttribute("emp", emp);
			model.addAttribute("sal_err", "5000미만");
			return "thymeleaf/input_form";
		}
		//log.trace("form")
		
		return null;
	}
	
}
