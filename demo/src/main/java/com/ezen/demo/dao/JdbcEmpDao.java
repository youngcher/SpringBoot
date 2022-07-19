package com.ezen.demo.dao;

import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.ezen.demo.model.Emp;

@Repository
public class JdbcEmpDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;  //oracle Datasource를 properties에 입력했으므로 사용가능, sql문만 작성.
	
	public List<Emp> getListAll(){
		
		String sql = "SELECT * FROM EMP";
		
		List<Emp> list = jdbcTemplate.query(sql, (rs,i)->{
			Emp emp = new Emp();
			emp.setEmpno(rs.getInt("EMPNO"));
			emp.setEname(rs.getString("ENAME"));
			emp.setHiredate(rs.getDate("HIREDATE"));
			emp.setSal(rs.getFloat("SAL"));
			return emp;
		});
		
		return list;
	}
	
	public List<Emp> getListByDeptno(int deptno){
		
		String sql = "SELECT * FROM EMP WHERE DEPTNO=?";
		
		List<Emp> list = jdbcTemplate.query(sql, (rs,i)->{
			Emp emp = new Emp();
			emp.setEmpno(rs.getInt("EMPNO"));
			emp.setEname(rs.getString("ENAME"));
			emp.setHiredate(rs.getDate("HIREDATE"));
			emp.setSal(rs.getFloat("SAL"));
			return emp;
		}, deptno);
		
		return list;
	}

	public Emp getEmp(int empno){
		
		String sql = "SELECT * FROM EMP WHERE EMPNO=?";
		
		List<Emp> list = jdbcTemplate.query(sql, (rs,i)->{
			Emp emp = new Emp();
			emp.setEmpno(rs.getInt("EMPNO"));
			emp.setEname(rs.getString("ENAME"));
			emp.setHiredate(rs.getDate("HIREDATE"));
			emp.setSal(rs.getFloat("SAL"));
			emp.setDeptno(rs.getInt("DEPTNO"));
			return emp;
		}, empno);
		
		return  list.get(0);
	}

	public boolean addEmp(Emp emp) {
		String sql = "insert into emp(empno, ename, hiredate, sal) values(?,?,?,?)";
		int res = jdbcTemplate.update(sql, 
				emp.getEmpno(), emp.getEname(), emp.getHiredate(), emp.getSal());
		return res>0;
	}	

	public int addAndGetKey(Emp emp) {
		String sql = "insert into emp(empno, ename, hiredate, sal) values(?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		jdbcTemplate.update(conn->{
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(sql, new String[] {"empno"}); //PK 컬럼
			pstmt.setInt(1, emp.getEmpno());
			pstmt.setString(2, emp.getEname());
			pstmt.setDate(3, emp.getHiredate());
			pstmt.setFloat(4, emp.getSal());
			return pstmt;
		}, keyHolder);
		return keyHolder.getKey().intValue();
	}

	public boolean updated(int empno, int deptno, int sal) {
		String sql = "update EMP set deptno=?, sal=? where empno=?";
		int res = jdbcTemplate.update(sql, 
				deptno, sal, empno);
		return res>0;
		
	}

	public boolean deleted(int empno) {
		String sql = "delete from EMP2 where empno=?";
		int res = jdbcTemplate.update(sql, empno);
		return res>0;
	}
	
}
