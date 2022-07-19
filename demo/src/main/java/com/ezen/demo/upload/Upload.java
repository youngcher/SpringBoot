package com.ezen.demo.upload;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class Upload {
	
	private int num;
	private String writer;
	private java.sql.Date udate;
	private String comments;
	private List<String> fname = new ArrayList<>();
	private String fpath;
	
	private List<Attach> attach = new ArrayList<>();
	
	@Override
	public boolean equals(Object obj) {
		Upload other = (Upload) obj;
		return this.num==other.num;
	}
	
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public java.sql.Date getUdate() {
		return udate;
	}
	public void setUdate(java.sql.Date udate) {
		this.udate = udate;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public List<Attach> getAttach() {
		return attach;
	}
	public void setAttach(List<Attach> attach) {
		this.attach = attach;
	}


	public List<String> getFname() {
		return fname;
	}


	public void setFname(List<String> fname) {
		this.fname = fname;
	}


	public String getFpath() {
		return fpath;
	}


	public void setFpath(String fpath) {
		this.fpath = fpath;
	}


	
}
