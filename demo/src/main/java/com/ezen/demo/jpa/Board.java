package com.ezen.demo.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="board_tb")
public class Board {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "BOARD_NUM_SEQ")
	@SequenceGenerator(sequenceName = "BOARD_NUM_SEQ", allocationSize = 1, name = "BOARD_NUM_SEQ")
	private int num;
	
	private String title;
	private String author;
	private String contents;
	private java.sql.Date wdate;
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public java.sql.Date getWdate() {
		return wdate;
	}
	public void setWdate(java.sql.Date wdate) {
		this.wdate = wdate;
	}

	
	
	
}
