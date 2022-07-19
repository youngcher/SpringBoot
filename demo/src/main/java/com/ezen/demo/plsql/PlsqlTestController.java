package com.ezen.demo.plsql;

import java.util.List;
import java.util.Map;

import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ezen.demo.model.Emp;

@RestController
@RequestMapping("/plsql")
public class PlsqlTestController {
	
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
//	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PlsqlTestService svc;
	
	@GetMapping("")
	public String test() {
		logger.info("{}. 여기는{}",1,"test()");  //logger에는 trace, debug, info, warn, error
		return "PLSQL Test";
	}
	
	
	@GetMapping("/ename/{empno}")
	public String getEnameByEmpno(@PathVariable("empno")int empno) {
		
		Map<String, Object> map = svc.getEnameByEmpno2(empno);

		logger.debug("OUT_NAME={}",(String)map.get("OUT_NAME"));
		return map.get("OUT_NAME").toString();
	}
	
	// /plsql/emp/20 20번 부서의 사원정보 가져오기
	@GetMapping("/emp/{deptno}")
	public String getEmpByDeptno(@PathVariable("deptno")int deptno) {
		
		List<Emp> list = svc.getEmpByDeptno(deptno);
		
		return list.toString();
	}
	
	@GetMapping("/hiredate/{hiredate}")
	public String getEmpByHiredate(@PathVariable("hiredate")int hiredate) {
		
		List<Emp> list = svc.getEmpByHiredate(hiredate);
		
		return list.toString();
	}
	
}
