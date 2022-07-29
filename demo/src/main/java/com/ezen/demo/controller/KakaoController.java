package com.ezen.demo.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/kakao")
public class KakaoController {

	@GetMapping("/code")
	public String code(Model model) {
		
		try {
		BufferedReader reader = new BufferedReader(new FileReader("C:/Users/young/Downloads/test.txt"));

		String str;
		
			while ((str = reader.readLine()) != null) {
				System.out.println(str);
				}
			reader.close();
			model.addAttribute("code", str);
			
			return "thymeleaf/kakao_share_api_test";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@GetMapping("/kakao/share/login")
	@ResponseBody
	   public String login() throws Exception 
	   {
	      String js_login="   function kakao_share_login()\r\n"
	         + "   {\r\n"
	         + "      Kakao.Auth.login({\r\n"
	         + "           success: function(response) {\r\n"
	         + "             console.log(response);\r\n"
	         + "             alert('로그인 성공');\r\n"
	         + "           },\r\n"
	         + "           fail: function(error) {\r\n"
	         + "             console.log(error);\r\n"
	         + "             alert('로그인 실패');\r\n"
	         + "           },\r\n"
	         + "      });\r\n"
	         + "   }";
	      return js_login;
	   }
	
}
