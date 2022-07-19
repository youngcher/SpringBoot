package com.ezen.demo.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/jpa/emp")
public class JpaEmpController {
	
	@Autowired
	private EmpRepository empRepository;
	
	@GetMapping("/list")
	public String getList(Model model) {
		List<Employee> list = empRepository.findAll();
		model.addAttribute("list", list);
		return "empList";
	}
	
	@GetMapping("test1")
	@ResponseBody
	public String test1() {
	    String ename = empRepository.ename_by_empno(1);
	    return ename;
	}
	
	@GetMapping("test2")
	@ResponseBody
	@Transactional
	public String test2() {
	    Employee emp = empRepository.cur_by_empno(1);
	    return emp.toString();
	}
	
	@GetMapping("test3")
	@ResponseBody
	@Transactional
	public String test3() {
	    List<Employee> list = empRepository.emp_by_deptno(3);
	    return list.toString();
	}
	
	
}
