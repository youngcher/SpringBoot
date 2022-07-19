package com.ezen.demo.jpa;


import javax.transaction.Transactional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface BoardRepositoy extends JpaRepository <Board, Integer> {

	@Transactional      // UPDATE, DELETE 문장에 요구됨
	@Modifying		    // @Modifying 사용하면 쿼리에 의해 변경된 행수가 리턴됨
	@Query("UPDATE Board b SET b.title=?2, b.contents=?3 WHERE b.num=?1")
	int update(int num, String title, String contents);

	
}
