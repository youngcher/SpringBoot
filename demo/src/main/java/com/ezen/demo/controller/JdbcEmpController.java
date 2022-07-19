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
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ezen.demo.dao.JdbcEmpDao;
import com.ezen.demo.model.Emp;

@Controller
@RequestMapping("/jdbc/emp")
public class JdbcEmpController {
	
	@Autowired
	private JdbcEmpDao dao;

	@GetMapping("/all")
	public String getEmpList(Model model) {
		List<Emp> list = dao.getListAll();
		model.addAttribute("list",list);
//		return list.toString();
		return "empList";
	}
	
	@GetMapping("/deptno")
	public String getListByDeptno() {
		List<Emp> list = dao.getListByDeptno(30);
		return list.toString();
	}
	
	@GetMapping("/getEmp")
	public String getEmp() {
		return dao.getEmp(7369).toString();
	}
	
	@GetMapping("/add")
	public String addEmp() {
		Emp emp = new Emp();
		emp.setEmpno(7777);
		emp.setEname("young");
		java.util.Date u = new java.util.Date();
		java.sql.Date s = new java.sql.Date(u.getTime());
		emp.setHiredate(s);
		emp.setSal(77.7f);
//		boolean added = dao.addEmp(emp);
		int added = dao.addAndGetKey(emp);
		return "added="+added;
	}
	
	@GetMapping("/update/{empno}/{deptno}/{sal}")
	public String update(@PathVariable("empno")int empno, @PathVariable("deptno")int deptno, 
							@PathVariable("sal")int sal) {
		boolean updated = dao.updated(empno,deptno,sal);
		return "updated=" +updated;
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam("empno")int empno) {
		boolean deleted = dao.deleted(empno);
		return "deleted="+deleted;
	}
	
	@GetMapping("/detail/{empno}")
	public String detail(@PathVariable("empno")int empno, Model model) {
		Emp emp = dao.getEmp(empno);
		model.addAttribute("emp", emp);
		return "detail";
	}
	
	@GetMapping("/edit_form/{empno}")
	public String edit_form(@PathVariable("empno")int empno, Model model) {
		Emp emp = dao.getEmp(empno);
		model.addAttribute("emp", emp);
		return "edit_form";
	}
	
	@PostMapping("/edit")
	@ResponseBody
	public Map<String,Object> edit(Emp emp) {
		boolean edited = dao.updated(emp.getEmpno(), emp.getDeptno(), (int)emp.getSal());
		Map<String,Object> map = new HashMap<>();
		map.put("edited",edited);
		return map;
	}
}
