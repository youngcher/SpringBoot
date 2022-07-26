package com.ezen.demo.email;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/mail")
public class MailController {

	@Autowired
	private MailService svc;
	
	@GetMapping("/test")
	@ResponseBody
	public String test() {
		
		
		svc.sendMimeMessage();  //구글 -> 네이버메일로 간단한 텍스트 메일 전송
		
		return "true";
	}
	
	@GetMapping("/auth/{code}")
	@ResponseBody
	public String auth(@PathVariable("code")String code) {
		
//		svc.checkAuth(auth);
		
		return code;
	}
	
	@GetMapping("/sendFile")
	@ResponseBody
	public boolean sendFile() {

		svc.sendAttachMail();
		
		return true;
	}
	
	@GetMapping("/mailForm")
	public String mailForm() {
		return "thymeleaf/mail/input_mail_Form";
	}
	
	@PostMapping("/sendMSG")
	@ResponseBody
	public boolean sendMSG(@RequestParam("title")String title,
			@RequestParam("contents")String contents,
			@RequestParam("files")MultipartFile[] mfiles, HttpServletRequest request) {
		
		ServletContext context = request.getServletContext();
		String savePath = context.getRealPath("/WEB-INF/files");
		
		List<String> fileNames = new ArrayList<>();
		
		for(int i=0;i<mfiles.length;i++) { 
			String fileName = mfiles[i].getOriginalFilename();
			try {
				mfiles[i].transferTo(new File(savePath+"/"+fileName));
				fileNames.add(fileName);
			} catch (IllegalStateException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		svc.sendMSG(title, contents, fileNames);
		
		return true;
	}
	
	
}
