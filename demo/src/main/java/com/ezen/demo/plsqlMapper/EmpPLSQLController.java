package com.ezen.demo.plsqlMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ezen.demo.model.Emp;

@RestController
@RequestMapping("/MapPLSQL")
public class EmpPLSQLController {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private EmpPLSQLMapper mapper;
	
	@GetMapping("/ename/{empno}")
	public String ename(@PathVariable("empno")String empno) {
		
		Map<String,Object> map = new HashMap<>();
		
		map.put("empno",empno);
		
		mapper.getEnameByEmpno(map);
		logger.info("paramMap={}",map.toString());
		return map.toString();
	}
	
	@GetMapping("/empList/{deptno}")
	public String empList(@PathVariable("deptno")String deptno) {
		
		Map<String,Object> map = new HashMap<>();
		
		map.put("deptno",deptno);
		
		mapper.getEmpListByDeptno(map);
		
		return map.toString();
	}

}
