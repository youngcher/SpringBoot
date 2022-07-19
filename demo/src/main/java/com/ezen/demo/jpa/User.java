package com.ezen.demo.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

//@Data
@Entity
@Table(name="user_tb")
public class User 
{
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USER_NUM_SEQ")
	@SequenceGenerator(sequenceName="USER_NUM_SEQ", allocationSize=1, name="USER_NUM_SEQ")
	private int num;
	
	@Column(name="name")
	private String uname;
	
	private String email;
	
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

}
