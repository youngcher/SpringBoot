package com.ezen.demo.model;

import java.util.Objects;

public class User {

	private String uid;
	private String upw;
	
	
	
	@Override
	public int hashCode() {
		return Objects.hash(this.uid, this.upw);
	}
	@Override
	public boolean equals(Object obj) {
		User other = (User)obj;
		return this.uid.equals(other.uid) && this.upw.equals(other.upw);
	}
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUpw() {
		return upw;
	}
	public void setUpw(String upw) {
		this.upw = upw;
	}
	
	
}
