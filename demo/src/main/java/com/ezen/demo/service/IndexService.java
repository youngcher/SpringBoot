package com.ezen.demo.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;


@Service
public class IndexService {
	
	private HttpServletRequest request;
	
	public IndexService() {
		
	}
	
	public IndexService(HttpServletRequest request) {
		this.request = request;
	}
	
	//자동 주입, Dependency와 Setter를 이용한 자동 주입이다.
	@Autowired  //Dependency Injection, Setter Injection , 의존성 injection을 
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public List<String> getGugu(int dan) {
//		int gugudan;
//		try {
//			gugudan = Integer.parseInt(request.getParameter("gugudan"));
//		} catch(Exception e) {
//			gugudan = 2;
//		}

		
		List<String> list = new ArrayList<String>();
		for(int i =1; i<=9; i++) {
			list.add(String.format("%d * %d = %d",dan,i,dan*i));
		}
		return list;
	}
}
