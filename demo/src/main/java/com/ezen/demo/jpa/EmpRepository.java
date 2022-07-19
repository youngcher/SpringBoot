package com.ezen.demo.jpa;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

@Transactional
public interface EmpRepository extends JpaRepository<Employee, Integer> {

	// ename_by_empno
	   @Procedure(name="sp_ename_by_empno") 
	   String ename_by_empno(@Param("p_empno") int empno);
	   
	   @Procedure(name="sp_cur_by_empno")
	   Employee cur_by_empno(@Param("p_empno") int empno);
	
	   @Procedure(name="sp_emp_by_deptno")
	   List<Employee> emp_by_deptno(@Param("deptno") int deptno);
	   
}
