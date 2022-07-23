package com.ezen.demo.message;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@Entity
@Table(name="message")
public class Message 
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int num;
	
	private String sender;
	private String receiver;
	private String contents;
}
