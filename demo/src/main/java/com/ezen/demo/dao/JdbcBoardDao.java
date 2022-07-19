package com.ezen.demo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ezen.demo.model.Board;

@Repository
public class JdbcBoardDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<Board> getList() {
		
		String sql = "SELECT * FROM BBS";
		
		List<Board> list = jdbcTemplate.query(sql, (rs,i)->{
			Board board = new Board();
			board.setNum(rs.getInt("NUM"));
			board.setTitle(rs.getString("TITLE"));
			board.setContents(rs.getString("CONTENTS"));
			board.setAuthor(rs.getString("AUTHOR"));
			board.setWdate(rs.getDate("WDATE"));
			board.setPcode(rs.getInt("PCODE"));
			return board;
		});
		
		return list;
	}

	public boolean write(Board board) {
		String sql = "INSERT into bbs(NUM, TITLE, CONTENTS, AUTHOR, WDATE, PCODE) values(BBS_NUM.nextval,?,?,?,SYSDATE,?)";
		int res = jdbcTemplate.update(sql,
				board.getTitle(), board.getContents(), board.getAuthor(), board.getPcode());
		
		return res>0;
	}

	public Board getBoard(int num) {
		
		String sql = "SELECT * FROM BBS WHERE NUM = ?";
		
		List<Board> list = jdbcTemplate.query(sql, (rs, i)-> {
			Board board = new Board();
			board.setNum(rs.getInt("NUM"));
			board.setTitle(rs.getString("TITLE"));
			board.setContents(rs.getString("CONTENTS"));
			board.setAuthor(rs.getString("AUTHOR"));
			board.setWdate(rs.getDate("WDATE"));
			board.setPcode(rs.getInt("PCODE"));
			return board;
		}, num);
		
		return list.get(0);
	}

	public boolean deleted(int num) {
		String sql = "delete from BBS where num = ?";
		int result = jdbcTemplate.update(sql, num);
		return result>0;
	}

	public boolean updated(Board board) {
		String sql = "update BBS set title=?, contents=? where num=?";
		int res = jdbcTemplate.update(sql,
				board.getTitle(), board.getContents(), board.getNum());
		
		return res>0;
	}
	
	
	
}
