package com.ezen.demo.mappers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezen.demo.vo.Dept;

@Controller
@RequestMapping("/mybatis/dept")
public class DeptController {
	
	@Autowired
	private DeptMapper dao;  //인터페이스 > 구현 클래스 > 인스턴스 참조
	
	@GetMapping("")
	public String showForm() {
		return "dept/form";
	}
	
	@GetMapping("/list")
	public String getlist() {
		return dao.getList().toString();
	}

	@GetMapping("/listByDeptno")
	public String getListByDeptno() {
		return dao.getListByDeptno(10).toString();	
	}
	
	@GetMapping("/getDeptno")
	public String getDept() {
		return dao.getInfoByDeptno(10).toString();
	}
	
	@GetMapping("/add")
	public String add() {
		Dept dept = new Dept();
		dept.setDeptno(26);
		dept.setDname("god");
		dept.setLoc("seoul");
		boolean added = dao.add(dept)>0;
		return "added=" + added;
	}
	
	@GetMapping("/update")
	public String update() {
		Dept dept = new Dept();
		dept.setDeptno(25);
		dept.setDname("god");
		dept.setLoc("seoul");
		boolean updated = dao.update(dept)>0;
		return "updated=" + updated;
	}
	
	@GetMapping("/delete")
	public String delete() {
		Dept dept = new Dept();
		dept.setDeptno(25);
		boolean deleted = dao.delete(dept)>0;
		return "deleted=" + deleted;
	}
	
	@GetMapping("/insertAndGet")
	public String insertAndGet() {
		Dept dept = new Dept();
		dept.setDeptno(62);
		dept.setDname("god");
		dept.setLoc("heaven");
		int n = dao.addAndGetKey(dept);
		return "deptno = " + String.valueOf(n);
	}

	@GetMapping("/serch")
	public String serch() {
		return dao.serchList("ES").toString();	
	}
	
	@GetMapping("/getListMap")
	public String getListMap() {
		return dao.getListMap().toString();
	}
	
	@PostMapping("/deptList")
	@ResponseBody
	public String deptList(HttpServletRequest request) {
		String sDeptno[] = request.getParameterValues("deptno");
		List<Integer> list = new ArrayList<>();
		
//		list.add(10);  list.add(20);

		for(int i=0; i<sDeptno.length; i++) {
			list.add(Integer.parseInt(sDeptno[i]));
		}
		return dao.deptList(list).toString();
	}
	
	
	
	
}
