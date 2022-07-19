package com.ezen.demo.model;

import java.io.BufferedReader;
import java.io.FileReader;

import org.springframework.stereotype.Repository;

@Repository
public class UserDAO {

	private static final String path = "C:/Users/young/OneDrive/바탕 화면/idpw.txt";
	
	public boolean login(User user) {
		System.out.println("dao");
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			String line = br.readLine();
			String info[] = line.split(" ");
			String id = info[0];
			String pw = info[1];
			
			System.out.println(user.getUid());
			
			if(user.getUid().equals(id) && user.getUpw().equals(pw)) {
				br.close();
				return true;
			} else {
				br.close();
				return false;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
