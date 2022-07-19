package com.ezen.demo.jpa;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer> 
{
	//JPA는 메소드 이름을 이용하여 SQL을 파생한다. (Query Method)
	// User 클래스의 uname 속성을 이용하여 이름으로 검색되도록 메소드 작성 
	public List<User> findByUname(String uname);  //select * from user_tb where uname=uname;
	
	public List<User> findByUnameAndEmail(String uname, String email);

	// num 값이 5~10 사이에 있는 행을 추출하려고 한다.
	@Query("SELECT u FROM User u WHERE num BETWEEN :start AND :end") //JPQL 
	List<User> findAllNumBet(@Param("start")int start,@Param("end") int end);
	
}
