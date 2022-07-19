package com.ezen.demo.vo;

import org.springframework.stereotype.Component;

@Component
public class Dept {
	
	private int deptno;
	private String dname;
	private String loc;
	
	
	
	@Override
	public String toString() {
		return String.format("%d\t%s\t%s", deptno, dname, loc);
	}
	
	public int getDeptno() {
		return deptno;
	}
	public void setDeptno(int deptno) {
		this.deptno = deptno;
	}
	public String getDname() {
		return dname;
	}
	public void setDname(String dname) {
		this.dname = dname;
	}
	public String getLoc() {
		return loc;
	}
	public void setLoc(String loc) {
		this.loc = loc;
	}
	
}
