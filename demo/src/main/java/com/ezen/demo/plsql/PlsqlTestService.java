package com.ezen.demo.plsql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import com.ezen.demo.model.Emp;

@Service
public class PlsqlTestService {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	
	// jdbc call해서 프로시드를 불러오는 방법1 jdbcTemplate사용
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public Map<String, Object> getEnameByEmpno(int empno) {
		
		List<SqlParameter> param = Arrays.asList(
			new SqlParameter(Types.INTEGER),
			new SqlOutParameter("ename", Types.VARCHAR) //sql out parameter
		);
		
		//익명 인터페이스 객체 구현
		return jdbcTemplate.call(new CallableStatementCreator() {
			
			//익명 클래스를 사용하여 복잡하다
			@Override
			public CallableStatement createCallableStatement(Connection con) throws SQLException {
				CallableStatement cs = con.prepareCall("{call ename_by_empno(?,?) }");
				cs.setInt(1, empno);
				cs.registerOutParameter(2, Types.VARCHAR);
				cs.executeQuery();
				
				String ename = cs.getString(2);
				
				logger.info("서비스에서 리턴한 값: {}",ename);
				
				return cs;
			}
			
		}, param);
	}
	
	// jdbc call해서 프로시드를 불러오는 방법2 SimpleJdbcCall 사용
	public Map<String, Object> getEnameByEmpno2(int empno){
		
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("ename_by_empno");
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("eno", empno);
		SqlParameterSource in = new MapSqlParameterSource(paramMap);
		
		Map<String,Object> resultMap = simpleJdbcCall.execute(in); //입력(in) 과 결과(out)을 한번에 한다.
		
		logger.debug("서비스에서 리턴한 값: {}", resultMap.get("OUT_NAME"));
		
		return resultMap;
	}
	
	
	//SimpleJdbcCall을 사용하여 오라클로부터 커서를 받아오는 예
	public List<Emp> getEmpByDeptno(int deptno) {
		
		SimpleJdbcCall simple = new SimpleJdbcCall(jdbcTemplate)
			.withProcedureName("emp_by_deptno")
			.declareParameters(
					new SqlParameter("p_deptno", Types.INTEGER),
					new SqlOutParameter("refcur", Types.REF_CURSOR)
					)
			.returningResultSet("refcur", BeanPropertyRowMapper.newInstance(Emp.class));
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("p_deptno", deptno);
		SqlParameterSource in = new MapSqlParameterSource(paramMap);
		
		Map<String, Object> resultMap = simple.execute(in);
		logger.info("결과맵={}", resultMap.toString());
		List<Emp> list = (List<Emp>)resultMap.get("refcur");
		
		return list;
	}
	
	public List<Emp> getEmpByHiredate(int hiredate){
		
		SimpleJdbcCall simple = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("emp_by_hiredate")
				.declareParameters(
						new SqlParameter("in_hiredate", Types.INTEGER),
						new SqlOutParameter("refcur", Types.REF_CURSOR)
						)
				.returningResultSet("refcur", BeanPropertyRowMapper.newInstance(Emp.class));
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("in_hiredate", hiredate);
		SqlParameterSource in = new MapSqlParameterSource(paramMap);
		
		Map<String, Object> resultMap = simple.execute(in);
		logger.info("결과맵={}", resultMap.toString());
		List<Emp> list = (List<Emp>)resultMap.get("refcur");
		
		return list;
	}
	
	
}
