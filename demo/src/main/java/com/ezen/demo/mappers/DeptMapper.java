package com.ezen.demo.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ezen.demo.vo.Dept;

@Mapper
public interface DeptMapper {
	
	//SELECT
	List<Dept> getList();  //전체 목록
	List<Dept> getListByDeptno(int deptno);  //특정 부서 리스트
	Dept getInfoByDeptno(int deptno);  //특정 사원정보 출력

	//INSERT
	int add(Dept dept);
	
	//UPDATE
	int update(Dept dept);
	
	//DELETE
	int delete(Dept dept);
	
	//추가하고 key값을 가져온다.
	int addAndGetKey(Dept dept);
	
	//검색 기능
	List<Dept> serchList(String key);
	
	List<Map> getListMap();
	
	//Dynamic SQL
	List<Dept> deptList(List<Integer> list);
	
}
