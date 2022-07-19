package com.ezen.demo.plsqlMapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ezen.demo.model.Emp;

@Mapper
public interface EmpPLSQLMapper {

	void getEnameByEmpno(Map<String,Object> map);

	void getEmpListByDeptno(Map<String, Object> map);




}
