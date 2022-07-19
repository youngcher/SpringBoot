package com.ezen.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.demo.model.User;
import com.ezen.demo.model.UserDAO;

@Service
public class UserService {

	@Autowired
	private UserDAO dao;
	
	public boolean login(User user) {
		boolean result = dao.login(user);
		return result;
	}

}
